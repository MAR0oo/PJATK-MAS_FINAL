package com.example.maro.repository;

import com.example.maro.model.entities.Administrator;
import com.example.maro.model.entities.Klient;
import com.example.maro.model.entities.Osoba;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KlientRepository extends JpaRepository<Klient, Long> {
    Klient findKlientByLoginAndHaslo(String login, String haslo);

}
