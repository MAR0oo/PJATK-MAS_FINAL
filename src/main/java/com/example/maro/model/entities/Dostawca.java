package com.example.maro.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "dostawca")
public class Dostawca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dostawcaId;

    private String nazwa;
    private int oplata;

    @OneToMany(mappedBy = "dostawca", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Dostawa> dostawa = new ArrayList<>();

}
