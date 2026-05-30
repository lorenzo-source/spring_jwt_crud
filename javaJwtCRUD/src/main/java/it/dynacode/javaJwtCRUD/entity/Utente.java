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
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;


    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "data_nascita")
    private Date dataNascita;

    @CreationTimestamp
    @Column(name = "data_creazione")
    private Timestamp dataCreazione;

    @Column(name = "data_ultimologin")
    private Timestamp dataUltimoLogin;

    @Column(name = "data_cancellazione")
    private Timestamp dataCancellazione;

    @Column(name = "refresh_token")
    private String refreshToken;

}