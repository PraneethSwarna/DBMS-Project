package dev.praneeth.backend.Doctor;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.stereotype.Service;

@Service
public class DoctorJwtService {

    private final String SECRET_KEY = "YOUR_SECRET_KEY_YOU_SHOULD_REPLACE_WITH_ENV_VARIABLE";
    private final Key signingKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public String generateToken(Doctor doctor) {
        return Jwts.builder()
                .setSubject(doctor.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public void invalidateToken(String token) {
        // Implement token invalidation logic if needed
    }
}
