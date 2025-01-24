package com.example.maro.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "realizacja")
public class Realizacja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long realizacjaId;

    private Boolean czySkompletowane;
    private Boolean czyOplacone;

    @NonNull
    @ManyToOne
    @JoinColumn(name = "administrator_id", nullable = false)
    private Administrator administrator;

    @OneToOne
    @JoinColumn(name = "zamowienie_id")
    private Zamowienie zamowienie;

}
