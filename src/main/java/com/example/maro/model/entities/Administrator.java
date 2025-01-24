package com.example.maro.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "administrator")
public class Administrator extends Osoba {

    private Integer doswiadczenie;

    @OneToMany(mappedBy = "administrator", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Realizacja> realizacja = new ArrayList<>();

}
