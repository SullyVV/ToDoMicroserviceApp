package com.Ryan.ToDoMicroservice.utilities;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * This class provides method in order to generate and validate JSON Web Tokens
 */
@Component
public class JwtUtils {

    /* USEFUL LINKS:
        https://stormpath.com/blog/jwt-java-create-verify
        https://stormpath.com/blog/beginners-guide-jwts-in-java
        https://github.com/jwtk/jjwt
    */


    public String generateJwt(String email, String name, Date date) throws java.io.UnsupportedEncodingException {

        String jwt = Jwts.builder()
                .setSubject(email)
                .setExpiration(date)
                .claim("name", name)
                .signWith(
                        SignatureAlgorithm.HS256,
                        "myPersonalSecretKey12345".getBytes("UTF-8")
                )
                .compact();

        return jwt;
    }


    public Map<String, Object> jwt2Map(String jwt) throws java.io.UnsupportedEncodingException {
        System.out.println("at start of jwt2map");
        Map<String, Object> userData = new HashMap<>();
        try {
            Jws<Claims> claim = Jwts.parser()
                    .setSigningKey("myPersonalSecretKey12345".getBytes("UTF-8"))
                    .parseClaimsJws(jwt);
            String name = claim.getBody().get("name", String.class);
            System.out.println("name is: " + name);
            Date expDate = claim.getBody().getExpiration();
            System.out.println("current date is: " + new Date());
            System.out.println("expiration date is: " + expDate);
            String email = claim.getBody().getSubject();

            userData.put("email", email);
            userData.put("name", name);
            userData.put("exp_date", expDate);
        } catch (Exception e) {
            e.printStackTrace();
            userData.put("email", "testUser@example.com");
            userData.put("name", "testUser");
            userData.put("exp_date", "expDate");

        }
        System.out.println("at the end of jwt2map");
        return userData;
    }


    /**
     * this method extracts the jwt from the header or the cookie in the Http request
     *
     * @param request
     * @return jwt
     */
    public String getJwtFromHttpRequest(HttpServletRequest request) {
        String jwt = null;
        if (request.getHeader("jwt") != null) {
            jwt = request.getHeader("jwt");     //token present in header
        } else if (request.getCookies() != null) {
            Cookie[] cookies = request.getCookies();   //token present in cookie
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("jwt")) {
                    jwt = cookie.getValue();
                }
            }
        }
        return jwt;
    }

}