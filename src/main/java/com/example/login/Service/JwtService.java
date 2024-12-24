package com.example.login.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    private static final SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256); // enkripsi pass

    public String generateToken(
            Map<String, Object> extractClaims,
            UserDetails userdetails) {
        return Jwts.builder()
                .setClaims(extractClaims)
                .setSubject(userdetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(key)
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    // paylod token
    private io.jsonwebtoken.Claims extractedClaims(String token) {
        return Jwts.parserBuilder() // parsing jwt
                .setSigningKey(key)// penetapan secret key
                .build()// pembuatan parser setelah key ditetapkan
                .parseClaimsJws(token)// memproses jwt yg diberikan token yg memisahkan jwt menjadi header, payload
                                      // dan signatured
                .getBody();// mengambil payload
    }

    // <T> return dapat berupa apa saja ex:String, Date, Integer,...
    // mengambil informasi spesifik dari payload token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractedClaims(token); // mengambil payload tadi
        return claimsResolver.apply(claims);//
    }

    // mengambil username
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

}
