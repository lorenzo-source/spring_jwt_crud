CREATE TABLE UTENTI (
    email VARCHAR(255) PRIMARY KEY,          -- email come chiave primaria
    password VARCHAR(255) NOT NULL,          -- password
    nome VARCHAR(100) NOT NULL,              -- nome
    cognome VARCHAR(100) NOT NULL,           -- cognome
    data_nascita DATE,                       -- data di nascita
    data_creazione TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- data di creazione (impostata automaticamente alla creazione del record)
    data_ultimologin TIMESTAMP,              -- data dell'ultimo login
    data_cancellazione TIMESTAMP,            -- data di cancellazione
    refresh_token VARCHAR(255)               -- refresh token
);