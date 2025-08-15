package me.nguyenn.AuthenticationTest.util;

import io.jsonwebtoken.*;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "Nothingtakesplaceintheworldwhosemeaningisnotthatofsomemaximumorminimum.";

    public static String generateToken(String email) {
        long experienceTime = 3600000;

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + experienceTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static String validateToken(String token) {
        try {
            Claims claims = Jwts.parser()
                                .setSigningKey(SECRET_KEY)
                                .parseClaimsJws(token)
                                .getBody();

            return claims.getSubject();
        } catch (ExpiredJwtException e) {
            System.out.println("Token is expired!!");
        } catch (UnsupportedJwtException e) {
            System.out.println("Token is not supported!!");
        } catch (MalformedJwtException e) {
            System.out.println("Token is invalid!!");
        } catch (SignatureException e) {
            System.out.println("Token's signature is wrong!!");
        } catch (IllegalArgumentException e) {
            System.out.println("Empty or null token!!");
        }

        return null;
    }
}