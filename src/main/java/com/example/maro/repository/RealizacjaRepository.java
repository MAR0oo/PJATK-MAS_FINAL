package com.example.maro.repository;

import com.example.maro.model.entities.Klient;
import com.example.maro.model.entities.Realizacja;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealizacjaRepository extends JpaRepository<Realizacja, Long> {

}
