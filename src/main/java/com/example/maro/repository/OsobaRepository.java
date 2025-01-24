package com.example.maro.repository;

import com.example.maro.model.entities.Administrator;
import com.example.maro.model.entities.Osoba;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OsobaRepository extends JpaRepository<Osoba, Long> {

    Boolean existsByLogin(String login);

}
