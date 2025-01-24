package com.example.maro.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "polo")
public class Polo extends Koszulka{

    private String rodzajKolnierzyka;

}
