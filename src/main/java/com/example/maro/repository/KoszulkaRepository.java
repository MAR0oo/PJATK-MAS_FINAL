package com.example.maro.repository;

import com.example.maro.model.entities.Klient;
import com.example.maro.model.entities.Koszulka;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KoszulkaRepository extends JpaRepository<Koszulka, Long> {

    Koszulka getKoszulkaByNazwa(String nazwa);

}
