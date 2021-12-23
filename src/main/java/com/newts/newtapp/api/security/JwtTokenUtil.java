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

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Component
public class JwtTokenUtil {
    static final Properties properties = loadProperties();
    static final Algorithm algorithm = Algorithm.HMAC256(properties.getProperty("jwt-secret-key")); // secret key should be >= 256 bits
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
            payload.put("exp", issuedAt + Integer.parseInt(properties.getProperty("token-duration-seconds")));
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

    /**
     * Generate a new security.properties file containing some default values.
     */
    private static void generateProperties() {
        String defaultProperties = "jwt-secret-key=replaceMePleaseThisIsNoSecret!\ntoken-duration-seconds=43200\n";
        byte[] data = defaultProperties.getBytes();
        Path path = Paths.get("./src/main/resources/security.properties");

        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(path))) {
            out.write(data, 0, data.length);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Return Properties object containing the security properties. If the properties file does not exist,
     * generate a new one with default values.
     */
    private static Properties loadProperties() {
        Path path = Paths.get("./src/main/resources/security.properties");
        File file = new File(path.toUri());
        Properties properties = new Properties();

        try (FileInputStream in = new FileInputStream(file)) {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (properties.isEmpty()) {
            generateProperties();
            return loadProperties();
        } else {
            return properties;
        }
    }
}
