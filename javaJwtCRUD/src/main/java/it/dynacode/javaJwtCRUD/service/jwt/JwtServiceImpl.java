package it.dynacode.javaJwtCRUD.service.jwt;

import it.dynacode.javaJwtCRUD.repository.UtenteRepository;
import it.dynacode.javaJwtCRUD.entity.Utente;
import it.dynacode.javaJwtCRUD.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class JwtServiceImpl implements JwtService{

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UtenteRepository utenteRepository;

   @Autowired
   private UserDetailsService userDetailsService;


    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public Utente register(Utente user) {
        user.setPassword(encoder.encode(user.getPassword()));
        utenteRepository.save(user);
        return user;
    }

    public String login (Utente user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            utenteRepository.updateLoginDate(user.getEmail(), Timestamp.from(new Date().toInstant()));
            return jwtUtils.generateToken(user.getEmail(), JwtUtils.DEFAULT_TOKEN_TIME);
        } else {
            return "fail";
        }
    }



    public String refresh(Utente user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            String refreshedToken = jwtUtils.refreshToken(user.getEmail());
            utenteRepository.updateRefreshToken(user.getEmail(), encoder.encode(refreshedToken));
            return refreshedToken;
        } else {
            return "fail";
        }
    }


}