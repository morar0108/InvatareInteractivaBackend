package com.example.InvatareInteractivaBackend.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class TokenService {

    private static final String SECRET = "mySecretKey";

    private static final String PREFIX = "Bearer ";

    /**
     * @return username for current logged-in user
     */
    public String getCurrentUsernameFromToken() {
        if (SecurityContextHolder.getContext() != null)
            return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return "";
    }

    public String getUsernameFromJWT(String jwt){
        if (jwt != null && !jwt.isEmpty()) {
            return decodeJWT(jwt).getSubject();
        }
        return null;
    }
    private static Claims decodeJWT(String jwt) {
        jwt = jwt.replace(PREFIX, "");
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(jwt).getBody();
    }

}
