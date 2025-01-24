package com.example.maro.model.entities;

import com.example.maro.model.enums.TypKoszulki;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "koszulka")
public abstract class Koszulka {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long koszulkaId;

    private TypKoszulki typKoszulki;
    private String nazwa;
    private Double cena;
    private String kolor;
    private String material;
    private Integer rozmiar;
    private String marka;

    @ManyToMany(mappedBy = "koszulki") // Odwzorowanie relacji Many-to-Many
    private List<Zamowienie> zamowienia;
}
