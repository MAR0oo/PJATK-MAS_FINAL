package com.example.maro.repository;

import com.example.maro.model.entities.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Administrator, Long> {

    Administrator findAdministratorByLoginAndHaslo(String login, String haslo);

}
