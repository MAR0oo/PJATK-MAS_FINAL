package com.example.maro.repository;

import com.example.maro.model.entities.Klient;
import com.example.maro.model.entities.Koszulka;
import com.example.maro.model.entities.Zamowienie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ZamowienieRepository extends JpaRepository<Zamowienie, Long> {
    List<Zamowienie> findAllByKlient(Klient klient);

}
