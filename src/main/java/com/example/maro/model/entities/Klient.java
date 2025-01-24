package com.example.maro.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "klient")
public class Klient extends Osoba {

    private LocalDate dataRejestracji;

    @OneToMany(mappedBy = "klient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Opinia> opinie = new ArrayList<>();

    @OneToMany(mappedBy = "klient", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Zamowienie> zamowienia = new ArrayList<>();

    @Transient
    private List<String> koszyk = new ArrayList<>();

    public void dodajDoKoszyka(String koszulka) {
        koszyk.add(koszulka);
    }

    public void usunZKoszyka(String koszulka) {
        koszyk.remove(koszulka);
    }

}
