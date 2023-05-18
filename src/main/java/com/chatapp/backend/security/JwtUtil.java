package com.chatapp.backend.security;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.chatapp.backend.model.userDB;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
 
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static final String SECRET = "my_secret";
    private static final long EXPIRATION = 1800L;// 秒
 
    public static String createToken(userDB user) {
        Date expireDate = new Date(System.currentTimeMillis() + EXPIRATION * 1000);

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                .withHeader(map)
                .withClaim("username", user.username)//userName
                .withClaim("id", user.id)//userId
                .withExpiresAt(expireDate)
                .withIssuedAt(new Date())
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }
 

    public Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
 
        } catch (Exception e) {
            logger.error(e.getMessage());
            logger.error("token Unauthorized");
            return null;
        }
        return jwt.getClaims();
    }
 
}
