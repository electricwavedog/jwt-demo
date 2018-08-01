package com.example.jwtdemo.web.common.filter;

import com.alibaba.fastjson.JSONObject;
import com.example.jwtdemo.config.constant.JwtConstant;
import com.example.jwtdemo.config.constant.MessageConstant;
import com.example.jwtdemo.domain.model.APIResult;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author liuyiqian
 */
public class JWTAuthenticationFilter extends BasicAuthenticationFilter {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

//        if (request.getRequestURI().equals("/register")) {
//            chain.doFilter(request, response);
//            return;
//        }

        // 从Header中拿到token
        String token = request.getHeader(JwtConstant.HEADER_STRING);

        if (token == null || !token.startsWith(JwtConstant.TOKEN_PREFIX)) {
//            setResponse(response, HttpServletResponse.SC_FORBIDDEN, MessageConstant.ACCESS_DENIED);
            chain.doFilter(request, response);
            return;
        }

        try {
            UsernamePasswordAuthenticationToken authentication = getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }catch (ExpiredJwtException e) {
            logger.error("Token已过期: {} " + e);
            setResponse(response, HttpServletResponse.SC_FORBIDDEN, MessageConstant.TOKEN_OVERDUE);
            return;
        } catch (SignatureException e) {
            logger.error("Token错误: {} " + e);
            setResponse(response, HttpServletResponse.SC_FORBIDDEN, MessageConstant.TOKEN_ERROR);
            return;
        }

        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {

        // parse the token.
        String user = Jwts.parser()
                // 验签
                .setSigningKey(JwtConstant.SECRET)
                // 去掉 Bearer
                .parseClaimsJws(token.replace(JwtConstant.TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        }

        return null;
    }

    private void setResponse(HttpServletResponse response, int status, String message) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(status);
        response.getWriter().write(JSONObject.toJSONString(APIResult.failure().setMessage(message)));
    }
}
