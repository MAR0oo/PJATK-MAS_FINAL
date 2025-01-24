package com.example.maro.repository;

import com.example.maro.model.entities.Administrator;
import com.example.maro.model.entities.Dostawca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DostawcaRepository extends JpaRepository<Dostawca, Long> {

    Dostawca findDostawcaByNazwa(String nazwa);

}
