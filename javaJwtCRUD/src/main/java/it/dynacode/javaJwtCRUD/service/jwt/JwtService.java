package it.dynacode.javaJwtCRUD.service.jwt;

import it.dynacode.javaJwtCRUD.entity.Utente;

public interface JwtService {

    Utente register(Utente user);

    String login(Utente user);

    String refresh(Utente user) ;
}
