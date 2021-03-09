package cn.yoozki.campussupport.common.util;

import cn.yoozki.campussupport.common.pojo.UserTokenDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.*;

/**
 * JWT工具类
 * @author yoozki
 * @date 2021/2/2
 */
public class JwtUtils {

    /**
     * 默认有效期 1小时
     */
    public static final Long JWT_DEFAULT_TTL = 3600000L;

    public static final String JWT_SECRET_KEY = "y@@zk!";

    private JwtUtils() {};

    public static String createJWT(String id, String subject) {
        return createJWT(id, subject, JWT_DEFAULT_TTL);
    }

    public static String createJWT(String id, String subject, Long ttlMillis) {
        SignatureAlgorithm hs256 = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        SecretKey secretKey = generateSecretKey();
        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(subject)
                .setIssuer("yoozki")
                .setIssuedAt(now)
                .signWith(hs256, secretKey)
                .setExpiration(expDate);
        return builder.compact();
    }

    /**
     * 生成加密的secretKey
     * @return
     */
    public static SecretKey generateSecretKey() {
        byte[] encodeSecretKey = Base64.getEncoder().encode(JwtUtils.JWT_SECRET_KEY.getBytes());
        SecretKey secretKey = new SecretKeySpec(encodeSecretKey, 0, encodeSecretKey.length, "AES");
        return secretKey;
    }

    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey secretKey = generateSecretKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    public static UserTokenDTO parseSubject(String token) throws Exception {
        Claims claims = JwtUtils.parseJWT(token);
        String subject = claims.getSubject();
        UserTokenDTO userTokenDTO = new ObjectMapper().readValue(subject, UserTokenDTO.class);
        return userTokenDTO;
    }
}
