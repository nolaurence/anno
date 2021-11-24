package cn.nolaurence.anno.utils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;

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
        String jwt = Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return jwt;
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
        Jwts.builder()
                .setSubject(subject)
                .claim("authorities", stringBuilder)
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        Jwts.builder().setClaims()
                .sign
    }
}
