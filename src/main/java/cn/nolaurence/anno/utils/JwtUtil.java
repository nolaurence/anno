package cn.nolaurence.anno.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

/**
 * @Description: Jwt工具类 copy-paste from Naccl's NBlog.
 * @Author: nolaurence
 * @Date: 2021-11-24 22:59:27
 */
@Component
public class JwtUtil {
    private static long expireTime;
    private static String secretKey;

    @Value("${token.secretKey}")
    public void setSecretKey(String key) {
        secretKey = key;
    }

    @Value("${token.expireTime}")
    public void setExpireTime(long time) {
        expireTime = time;
    }

    /**
     *  判断token是否存在
     *
     * @param token String
     * @return boolean
     */
    public static boolean judgeTokenIsExist(String token) {
        return token != null && !"".equals(token) && !"null".equals(token);
    }

    /**
     * 生成token
     *
     * @param subject String
     * @return jwt
     */
    public static String generateToken(String subject) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * 生成带角色权限的token
     *
     * @param subject String
     * @param authorities ? extends GrantedAuthority
     */
    public static String generateToken(String subject, Collection<? extends GrantedAuthority> authorities) {
        StringBuilder stringBuilder = new StringBuilder();
        for (GrantedAuthority authority : authorities) {
            stringBuilder.append(authority.getAuthority()).append(",");
        }
        return Jwts.builder()
                .setSubject(subject)
                .claim("authorities", stringBuilder)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * 生成自定义过期时间token
     *
     * @param subject String
     * @param expireTime long
     * @return String
     */
    public static String generateToken(String subject, long expireTime) {
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * 获取tokenBody同时校验token是否有效（无效则会抛出异常）
     * @param token String
     * @return io.jsonwebtoken.Claims
     */
    public static Claims getTokenBody(String token) {
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token.replace("Bearer", ""))
                .getBody();
    }
}
