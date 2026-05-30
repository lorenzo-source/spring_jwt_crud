package it.dynacode.javaJwtCRUD.service.jwt;

import it.dynacode.javaJwtCRUD.repository.UtenteRepository;
import it.dynacode.javaJwtCRUD.entity.Utente;
import it.dynacode.javaJwtCRUD.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class JwtServiceImpl implements JwtService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    AuthenticationManager authManager;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private UserDetailsService userDetailsService;


    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    //TODO CHECK IF THE USER IS ALREADY REGISTERED
    public Utente register(Utente user) {
        user.setPassword(encoder.encode(user.getPassword()));
        utenteRepository.save(user);
        return user;
    }

//    public String login(Utente user) {
//        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
//        if (authentication.isAuthenticated()) {
//            return  jwtUtils.init(user,utenteRepository);
//        } else {
//            return "Errore nel login";
//        }
//    }

    public String login(Utente user) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
        );
        if (authentication.isAuthenticated()) {
            Utente dbUser = utenteRepository.findByEmail(user.getEmail())
                    .orElseThrow(() -> new RuntimeException("Utente non trovato"));
            return jwtUtils.init(dbUser, utenteRepository);
        } else {
            return "Errore nel login";
        }
    }


    public String refresh(Map<String, String> request) {
        String refreshToken = request.get("refreshToken");
        try {
            String username = jwtUtils.extractUserName(refreshToken);

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtUtils.validateToken(refreshToken, userDetails)) {
                Optional<Utente> user = utenteRepository.findById(username);
                if(user.isPresent()){
                    return  jwtUtils.init(user.get(),utenteRepository);
                }else{
                    return "Utente non trovato";
                }
            } else {
                throw new RuntimeException("Refresh token non valido");
            }
        } catch (Exception e) {
            throw new RuntimeException("Errore durante il refresh del token");
        }
    }

}