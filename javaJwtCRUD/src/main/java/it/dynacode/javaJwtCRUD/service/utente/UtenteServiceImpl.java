package it.dynacode.javaJwtCRUD.service.utente;
import it.dynacode.javaJwtCRUD.repository.UtenteRepository;
import it.dynacode.javaJwtCRUD.entity.Utente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class UtenteServiceImpl implements UtenteService {

    private final UtenteRepository utenteRepository;

    @Autowired
    public UtenteServiceImpl(UtenteRepository theUtenteRepository) {
        utenteRepository = theUtenteRepository;
    }

    @Override
    public List<Utente> findByDataCancellazioneNull() {
        return utenteRepository.findByDataCancellazioneNull();
    }

    @Override
    public Utente findById(String theId) {
        Optional<Utente> result = utenteRepository.findById(theId);

        Utente theUtente;

        if (result.isPresent()) {
            theUtente = result.get();
        }
        else {
            throw new RuntimeException("Utente non trovato - " + theId);
        }

        return theUtente;
    }

    @Override
    public Utente save(Utente theUtente) {
        return utenteRepository.save(theUtente);
    }

    @Override
    public String softDelete(String theId, Timestamp deletedAt) {
        utenteRepository.softDelete(theId , deletedAt);
        return "Utente cancellato con successo";
    }





}