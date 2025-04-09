package it.dynacode.javaJwtCRUD.rest;

import it.dynacode.javaJwtCRUD.entity.Utente;
import it.dynacode.javaJwtCRUD.service.UtenteService;
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
         List<Utente> utenti = utenteService.findByDataCancellazioneNotNull();
         return ResponseEntity.ok(utenti); // 200 OK
     }

    /**
     * Una GET per avere un record della tabella utenti
     * @param utenteId
     * @return
     */
     @GetMapping("/utenti/{utenteId}")
     public ResponseEntity<Utente> getUtente(@PathVariable String utenteId) {

         Utente theUtente = utenteService.findById(utenteId);

         if (theUtente == null) {
             throw new RuntimeException("Utente id not found - " + utenteId);
         }

         return ResponseEntity.ok(theUtente); // 200 OK
     }

    /**
     *  Una POST per aggiungere un nuovo record
     * @param theUtente
     * @return
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
     * @param theUtente
     * @return
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
     * @param utenteId
     * @return
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

    // TODO Una POST per eseguire il login che utilizzi la tecnologia JWT;

}