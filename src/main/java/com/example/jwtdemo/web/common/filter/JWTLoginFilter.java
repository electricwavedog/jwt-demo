package com.example.jwtdemo.web.common.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.jwtdemo.config.constant.JwtConstant;
import com.example.jwtdemo.config.constant.MessageConstant;
import com.example.jwtdemo.domain.model.APIResult;
import com.example.jwtdemo.domain.model.TUser;
import com.example.jwtdemo.domain.model.enums.Role;
import com.example.jwtdemo.domain.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author liuyiqian
 */
public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

//    private final UserRepository userRepository;

    public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
//        this.userRepository = userRepository;
        setAuthenticationManager(authenticationManager);
    }

    // 接收并解析用户凭证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
        try {
            TUser user = new ObjectMapper()
                    .readValue(req.getInputStream(), TUser.class);

            return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        User user = (User) auth.getPrincipal();
        Field field = null;
        try {
            field = user.getClass().getDeclaredField("user");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        TUser tUser = null;
        try {
            tUser = (TUser) field.get(user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("authorities", "ROLE_" + Role.USER.name());

        // 生成JWT
        String token = Jwts.builder()
                .setClaims(claims)
                // 用户名写入标题
                .setSubject(((User) auth.getPrincipal()).getUsername())
                // 有效期设置
                .setExpiration(new Date(System.currentTimeMillis() + JwtConstant.EXPIRATION_TIME))
                // 签名设置
                .signWith(SignatureAlgorithm.HS512, JwtConstant.SECRET)
                .compact();
        // 将 JWT 写入 body
        res.addHeader(JwtConstant.HEADER_STRING, JwtConstant.TOKEN_PREFIX + token);
        res.setContentType("application/json;charset=utf-8");
        res.getWriter().write(JSONObject.toJSONString(APIResult.success().setData(tUser.getUsername())));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        setResponse(response, HttpServletResponse.SC_OK, MessageConstant.BAD_CREDENTIALS);
    }

    private void setResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(status);
        response.getWriter().write(JSONObject.toJSONString(APIResult.failure().setMessage(message)));
    }
}
