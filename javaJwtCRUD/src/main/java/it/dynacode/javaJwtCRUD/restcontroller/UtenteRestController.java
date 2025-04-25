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

    /**
     * Una GET per avere la lista di tutti gli utenti non cancellati
     */
    @GetMapping("/utenti")
    public ResponseEntity<List<?>> findAll() {
        return ResponseEntity.ok(utenteService.findByDataCancellazioneNull());
    }

    /**
     * Una GET per avere un record della tabella utenti
     *
     * @param utenteId L'id dell'utente che si vuole cercare
     * @return L'utente trovato oppure Http status 500.
     */
    @GetMapping("/utenti/{utenteId}")
    public ResponseEntity<?> getUtente(@PathVariable String utenteId) {
        try {
            Utente theUtente = utenteService.findById(utenteId);
            return ResponseEntity.ok(theUtente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problemi nel trovare l'utente");
        }
    }

    /**
     * Una POST per aggiungere un nuovo record
     *
     * @param theUtente L'utente da censire
     * @return Http status 200 se utente inserito. Http status 500 se si verifica un'eccezione.
     */
    @PostMapping("/utenti")
    public ResponseEntity<Object> addEmployee(@RequestBody Utente theUtente) {
        try {
            return ResponseEntity.ok(utenteService.save(theUtente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problemi nel salvare l'utente");
        }
    }

    /**
     * Una PUT per la modifica dei dati di un utente
     *
     * @param theUtente L'utente che si vuole modificare
     * @return ttp status 200 se utente modificato. Http status 500 se si verifica un'eccezione.
     */
    @PutMapping("/utenti")
    public ResponseEntity<Object> updateEmployee(@RequestBody Utente theUtente) {

        try {
            Utente utenteFound = utenteService.findById(theUtente.getEmail());

            return ResponseEntity.ok(utenteService.save(theUtente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problemi nel fare l'update dell'utente");
        }

    }

    /**
     * Una DELETE per la cancellazione dei dati di un utente
     *
     * @param utenteId L'id dell'utente da cancellare.
     * @return ttp status 200 se utente viene cancellato. Http status 500 se si verifica un'eccezione.
     */
    @DeleteMapping("/utenti/{utenteId}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String utenteId) {
        try {
            Utente utenteFound = utenteService.findById(utenteId);

            return ResponseEntity.ok(utenteService.softDelete(utenteId, Timestamp.from(new Date().toInstant())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Problemi nel cancellare l'utente");
        }

    }

}