package com.example.maro.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "sportowe_oversize")
public class Sportowe_Oversize extends Oversize implements ISportowe {

    private String typAktywnosci;
    @Override
    public String getTypAktywnosci() {
        return typAktywnosci;
    }

}
