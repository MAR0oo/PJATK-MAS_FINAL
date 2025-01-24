package com.example.maro.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "opinia")
public class Opinia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long opiniaId;

    private int iloscGwiazek;
    private String opis;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "klient_id", nullable = false)
    private Klient klient;

}
