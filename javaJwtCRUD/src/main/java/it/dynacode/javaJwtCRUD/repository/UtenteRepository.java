package it.dynacode.javaJwtCRUD.repository;

import it.dynacode.javaJwtCRUD.entity.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente,String>{


    Optional<Utente> findByEmail(@Param("email") String email);

    List<Utente> findByDataCancellazioneNull();

    List<Utente> findByDataCancellazioneNotNull();

    @Modifying
    @Transactional
    @Query("UPDATE Utente u SET u.dataUltimoLogin = :dataUltimoLogin WHERE u.id = :id")
    void updateLoginDate(@Param("id") String id, @Param("dataUltimoLogin") Timestamp dataUltimoLogin);

    @Modifying
    @Transactional
    @Query("UPDATE Utente u SET u.refreshToken = :refreshToken WHERE u.id = :id")
    void updateRefreshToken(@Param("id") String id, @Param("refreshToken") String refreshToken);



    @Modifying
    @Transactional
    @Query("UPDATE Utente u SET u.dataCancellazione = :deletedAt WHERE u.id = :id")
    void softDelete(@Param("id") String id, @Param("deletedAt") Timestamp deletedAt);



}