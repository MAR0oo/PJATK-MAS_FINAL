package com.example.maro.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "osoba")
public abstract class Osoba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long osobaId;

    private String login;
    private String haslo;
    private String imie;
    private String nazwisko;
    private String adresEmail;
    private String numerTelefonu;

}
