package it.dynacode.javaJwtCRUD.repository;

import it.dynacode.javaJwtCRUD.entity.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

public interface UtenteRepository extends JpaRepository<Utente,String>{


    List<Utente> findByDataCancellazioneNull();

    @Modifying
    @Transactional
    @Query("UPDATE Utente u SET u.dataUltimoLogin = :dataUltimoLogin WHERE u.email = :email")
    void updateLoginDate(@Param("email") String email, @Param("dataUltimoLogin") Timestamp dataUltimoLogin);

    @Modifying
    @Transactional
    @Query("UPDATE Utente u SET u.refreshToken = :refreshToken WHERE u.email = :email")
    void updateRefreshToken(@Param("email") String email, @Param("refreshToken") String refreshToken);


    @Modifying
    @Transactional
    @Query("UPDATE Utente u SET u.dataCancellazione = :deletedAt WHERE u.email = :email")
    void softDelete(@Param("email") String email, @Param("deletedAt") Timestamp deletedAt);


}