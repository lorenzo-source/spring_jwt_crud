package it.dynacode.javaJwtCRUD.restcontroller;

import it.dynacode.javaJwtCRUD.entity.Utente;
import it.dynacode.javaJwtCRUD.service.utente.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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
     *  Una GET per avere la lista di tutti gli utenti non cancellati
     */
     @GetMapping("/utenti")
     public ResponseEntity<List<Utente>> findAll() {
         List<Utente> utenti = utenteService.findByDataCancellazioneNull();
         return ResponseEntity.ok(utenti); // 200 OK
     }

    /**
     * Una GET per avere un record della tabella utenti
     * @param utenteId L'id dell'utente che si vuole cercare
     * @return L'utente trovato oppure Http status 500.
     */
     @GetMapping("/utenti/{utenteId}")
     public ResponseEntity<Utente> getUtente(@PathVariable String utenteId) {

         Utente theUtente = utenteService.findById(utenteId);

         if (theUtente == null) {
             return new ResponseEntity<>(HttpStatusCode.valueOf(500));
         }

         return ResponseEntity.ok(theUtente); // 200 OK
     }

    /**
     *  Una POST per aggiungere un nuovo record
     * @param theUtente L'utente da censire
     * @return Http status 200 se utente inserito. Http status 500 se si verifica un'eccezione.
     */
     @PostMapping("/utenti")
     public ResponseEntity<Object> addEmployee(@RequestBody Utente theUtente) {

         try {
             utenteService.save(theUtente);
             return new ResponseEntity<>(HttpStatusCode.valueOf(200));
         }
         catch (Exception e){
             return new ResponseEntity<>(HttpStatusCode.valueOf(500));
         }

     }

    /**
     * Una PUT per la modifica dei dati di un utente
     * @param theUtente L'utente che si vuole modificare
     * @return ttp status 200 se utente modificato. Http status 500 se si verifica un'eccezione.
     */
     @PutMapping("/utenti")
     public ResponseEntity<Object> updateEmployee(@RequestBody Utente theUtente) {

         try {
             Utente utenteFound = utenteService.findById(theUtente.getEmail()) ;
             utenteService.save(theUtente);
             return new ResponseEntity<>(HttpStatusCode.valueOf(200));
         }
         catch (Exception e){
             return new ResponseEntity<>(HttpStatusCode.valueOf(500));
         }

     }

    /**
     * Una DELETE per la cancellazione dei dati di un utente
     * @param utenteId L'id dell'utente da cancellare.
     * @return ttp status 200 se utente viene cancellato. Http status 500 se si verifica un'eccezione.
     */
     @DeleteMapping("/utenti/{utenteId}")
     public ResponseEntity<Object> deleteEmployee(@PathVariable String utenteId) {

         try {
             Utente tempUtente = utenteService.findById(utenteId);



             utenteService.softDelete(utenteId , Timestamp.from(new Date().toInstant()));

             return new ResponseEntity<>(HttpStatusCode.valueOf(200));
         }
         catch (Exception e){
             return new ResponseEntity<>(HttpStatusCode.valueOf(500));
         }

     }

}