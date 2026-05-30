package it.dynacode.javaJwtCRUD.service.jwt;


import it.dynacode.javaJwtCRUD.repository.UtenteRepository;
import it.dynacode.javaJwtCRUD.entity.UserPrincipal;
import it.dynacode.javaJwtCRUD.entity.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UtenteRepository utenteRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Utente> user = utenteRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Utente non trovato");
        }
        return new UserPrincipal(user.get());
    }
}