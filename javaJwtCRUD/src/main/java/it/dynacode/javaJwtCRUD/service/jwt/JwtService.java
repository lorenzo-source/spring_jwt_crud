package it.dynacode.javaJwtCRUD.service.jwt;

import it.dynacode.javaJwtCRUD.entity.Utente;

import java.util.Map;

public interface JwtService {

    Utente register(Utente user);

    String login(Utente user);

    String refresh(Map<String, String> user) ;
}
