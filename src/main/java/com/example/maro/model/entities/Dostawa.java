package com.example.maro.model.entities;

import com.example.maro.model.enums.TypDostawy;
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
@Table(name = "dostawa")
public class Dostawa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dostawaId;

    private LocalDate dataDostawy;
    private TypDostawy typ;
    private String adres;

    @OneToOne
    @JoinColumn(name = "zamowienie_id")
    private Zamowienie zamowienie;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "dostawca_id", nullable = false)
    private Dostawca dostawca;


}

