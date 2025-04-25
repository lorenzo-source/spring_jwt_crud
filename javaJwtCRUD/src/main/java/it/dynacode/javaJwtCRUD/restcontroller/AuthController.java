package it.dynacode.javaJwtCRUD.restcontroller;


import it.dynacode.javaJwtCRUD.entity.Utente;
import it.dynacode.javaJwtCRUD.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;

    /**
     * Registro un nuovo utente sul db per poter poi effettuare l'accesso
     * @param user L'utente da registrare
     * @return  http status 200 se ho inserito l'utente http status 500 in caso di eccezione.
     */
    @PostMapping("/register")
    public ResponseEntity<?>  register(@RequestBody Utente user) {
        try {
            return ResponseEntity.ok(jwtService.register(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Utente non censito");
        }

    }

    /**
     * Effettuo il login
     * @param user L'utente con cui ci si vuole loggare
     * @return  http status 200 se utente e' registrato sul db http status 500 in caso di eccezione.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utente user) {
        try {
            return ResponseEntity.ok(jwtService.login(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fallito");
        }
    }

    /**
     * Faccio Il refresh del token JWT per l'accesso
     * @param request Il token per il refresh
     * @return  http status 200 se il token di refresh e' valido http status 500 in caso di eccezione.
     */
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> request) {
        try {
            return ResponseEntity.ok(jwtService.refresh(request));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token non valido");
        }
    }
}