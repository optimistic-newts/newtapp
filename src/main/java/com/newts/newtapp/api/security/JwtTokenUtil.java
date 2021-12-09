package com.newts.newtapp.api.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.newts.newtapp.api.application.datatransfer.UserProfile;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTokenUtil {
    static final Algorithm algorithm = Algorithm.HMAC256("12345678901234567890123456789012"); // secret key is >= 256 bits
    static final JWTVerifier verifier = JWT.require(algorithm).withIssuer("newts").build();

    /**
     * Generates a JWT for the given user issued at the current system time.
     * @param user  Owner of the generated JWT
     * @return      String JWT
     */
    public String generateAccessToken(UserProfile user) {
        try {
            // construct JWT payload
            Map<String,Object> payload = new HashMap<>();
            payload.put("sub", user.id);
            payload.put("username", user.username);
            long issuedAt = Instant.now().getEpochSecond();
            payload.put("iat", issuedAt);
            payload.put("exp", issuedAt + 900); // 900 seconds = 15 minutes

            return JWT.create()
                    .withIssuer("newts")
                    .withPayload(payload)
                    .sign(algorithm);
        } catch (JWTCreationException e) {
            // Invalid Signing config. Should not get here.
            e.printStackTrace();
        }
        return null;
    }

    public boolean validate(String token) {
        try {
            verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            return false;
        }
    }

    public String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}
