package it.dynacode.javaJwtCRUD.restcontroller;

import it.dynacode.javaJwtCRUD.entity.Utente;
import it.dynacode.javaJwtCRUD.service.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UtenteRestController {

    private final UtenteService utenteService;

    @Autowired
    public UtenteRestController(UtenteService theUtenteService) {
        utenteService = theUtenteService;
    }

    @GetMapping("/utenti")
    public ResponseEntity<List<?>> findAll() {
        List<Utente> utenti = utenteService.findByDataCancellazioneNull();
        utenti.forEach(u -> {
            u.setPassword(null);
            u.setRefreshToken(null);
        });
        return ResponseEntity.ok(utenti);
    }

    @GetMapping("/utenti/deleted")
    public ResponseEntity<List<?>> findDeleted() {
        List<Utente> utenti = utenteService.findByDataCancellazioneNotNull();
        utenti.forEach(u -> {
            u.setPassword(null);
            u.setRefreshToken(null);
        });
        return ResponseEntity.ok(utenti);
    }

    @GetMapping("/utenti/{utenteId}")
    public ResponseEntity<?> getUtente(@PathVariable String utenteId) {
        try {
            Utente theUtente = utenteService.findById(utenteId);
            if (theUtente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Utente non trovato");
            }
            theUtente.setPassword(null);
            theUtente.setRefreshToken(null);
            return ResponseEntity.ok(theUtente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Utente non trovato");
        }
    }

    @PostMapping("/utenti")
    public ResponseEntity<Object> addUtente(@RequestBody Utente theUtente) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(utenteService.save(theUtente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Email già in uso");
        }
    }

    @PutMapping("/utenti")
    public ResponseEntity<Object> updateUtente(@RequestBody Utente theUtente) {
        try {
            Utente utenteFound = utenteService.findById(theUtente.getId());
            if (utenteFound == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Utente non trovato");
            }
            return ResponseEntity.ok(utenteService.save(theUtente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Problemi nel fare l'update dell'utente");
        }
    }

    @DeleteMapping("/utenti/{utenteId}")
    public ResponseEntity<?> deleteUtente(@PathVariable String utenteId) {
        try {
            Utente utenteFound = utenteService.findById(utenteId);
            if (utenteFound == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Utente non trovato");
            }
            return ResponseEntity.ok(utenteService.softDelete(utenteId,
                    Timestamp.from(new Date().toInstant())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Problemi nel cancellare l'utente");
        }
    }
}