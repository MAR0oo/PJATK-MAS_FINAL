package com.example.maro.model.entities;

import com.example.maro.model.enums.Status;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "zamowienie")
public class Zamowienie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long zamowienieId;
    private LocalDate dataZlozenia;
    private Integer cena;
    private Status status;

    @ManyToMany
    @JoinTable(
            name = "zamowienie_koszulka", // Nazwa tabeli pośredniej
            joinColumns = @JoinColumn(name = "zamowienie_id"), // Klucz obcy dla Zamowienie
            inverseJoinColumns = @JoinColumn(name = "koszulka_id") // Klucz obcy dla Koszulka
    )
    private List<Koszulka> koszulki;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "zamowienie")
    private Dostawa dostawa;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "klient_id", nullable = false)
    private Klient klient;

    @Nullable
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "zamowienie")
    private Realizacja realizacja;

    @Transient
    private List<String> koszulkiKoszyk = new ArrayList<>();

}
