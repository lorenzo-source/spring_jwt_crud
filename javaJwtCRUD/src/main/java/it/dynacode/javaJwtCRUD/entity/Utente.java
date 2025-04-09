package it.dynacode.javaJwtCRUD.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "UTENTI")
public class Utente {
    @Id
    @Column(name = "email")
    String email;

    @Column(name = "password")
    String password;

    @Column(name = "nome")
    String nome;
    @Column(name = "cognome")
    String cognome;
    @Column(name = "data_nascita")
    Date dataNascita;

    @CreationTimestamp
    @Column(name = "data_creazione")
    Timestamp dataCreazione;


    @Column(name = "data_ultimologin")
    Timestamp dataUltimoLogin;


    @Column(name = "data_cancellazione")
    Timestamp dataCancellazione;

    @Column(name = "refresh_token")
    String refreshToken;

}