package it.dynacode.javaJwtCRUD.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import it.dynacode.javaJwtCRUD.entity.Utente;
import it.dynacode.javaJwtCRUD.repository.UtenteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtils {


    private String secretkey = "";

    public static final long DEFAULT_TOKEN_TIME = 15 * 60 * 1000;

    public static final long REFRESH_TOKEN_TIME = 7 * 24 * 60 * 60 * 1000;

    public JwtUtils() {

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            secretkey = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String init(Utente user, UtenteRepository utenteRepository){
        utenteRepository.updateLoginDate(user.getEmail(), Timestamp.from(new Date().toInstant()));

        String token = this.generateToken(user.getEmail(), JwtUtils.DEFAULT_TOKEN_TIME);

        String refreshToken = this.generateToken(user.getEmail(), JwtUtils.REFRESH_TOKEN_TIME);

        utenteRepository.updateRefreshToken(user.getEmail(), refreshToken);

        return  "Token: " + token + " \n Refresh Token: " + refreshToken;
    }

    public String generateToken(String username, long tokenTime) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenTime))
                .and()
                .signWith(getKey())
                .compact();

    }


    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

}
