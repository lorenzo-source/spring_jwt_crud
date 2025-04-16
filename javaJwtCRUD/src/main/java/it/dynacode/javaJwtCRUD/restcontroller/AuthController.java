package it.dynacode.javaJwtCRUD.restcontroller;


import it.dynacode.javaJwtCRUD.entity.Utente;
import it.dynacode.javaJwtCRUD.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public Utente register(@RequestBody Utente user) {
        return jwtService.register(user);

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Utente user) {
        try {
            return ResponseEntity.ok(jwtService.login(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login fallito");
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Utente user) {
        try {
            return ResponseEntity.ok(jwtService.refresh(user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }
}