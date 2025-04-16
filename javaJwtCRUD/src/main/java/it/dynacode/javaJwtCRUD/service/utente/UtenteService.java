package it.dynacode.javaJwtCRUD.service.utente;

import it.dynacode.javaJwtCRUD.entity.Utente;

import java.sql.Timestamp;
import java.util.List;

public interface UtenteService {
    List<Utente> findByDataCancellazioneNull();

    Utente findById(String theId);

    Utente save(Utente theEmployee);

    void softDelete(String theId, Timestamp deletedAt);


}
