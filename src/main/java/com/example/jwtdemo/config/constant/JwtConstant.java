package com.example.jwtdemo.config.constant;

/**
 * @author liuyiqian
 */
public class JwtConstant {

    // JWT密码
    public final static String SECRET = "JwtTest";
    // Token前缀
    public final static String TOKEN_PREFIX = "Bearer";
    // 存放Token的Header Key
    public final static String HEADER_STRING = "Authorization";
    // 过期时间
    public final static long EXPIRATION_TIME = 60 * 60 * 24 * 1000;
}
