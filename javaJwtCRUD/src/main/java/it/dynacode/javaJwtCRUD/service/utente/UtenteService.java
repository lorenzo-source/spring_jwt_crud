package it.dynacode.javaJwtCRUD.service.utente;

import it.dynacode.javaJwtCRUD.entity.Utente;

import java.sql.Timestamp;
import java.util.List;

public interface UtenteService {
    List<Utente> findByDataCancellazioneNull();

    Utente findById(String theId);

    Utente findByEmail(String theEmail);

    Utente save(Utente theEmployee);

    String softDelete(String theId, Timestamp deletedAt);

    // Interface
    List<Utente> findByDataCancellazioneNotNull();
}
