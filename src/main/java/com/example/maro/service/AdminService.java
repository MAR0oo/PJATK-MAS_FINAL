package com.example.maro.service;

import com.example.maro.model.entities.Administrator;
import com.example.maro.model.entities.Klient;
import com.example.maro.model.entities.Zamowienie;
import com.example.maro.repository.AdminRepository;
import com.example.maro.repository.RealizacjaRepository;
import com.example.maro.repository.ZamowienieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private ZamowienieRepository zamowienieRepository;

    public Zamowienie getOrderDetails(Long id) {
        return zamowienieRepository.findById(id)
                .orElse(null);
    }

    public List<Zamowienie> findAllZamowienia(){
        return zamowienieRepository.findAll();
    }

    public Administrator authenticate(String login, String password){
        return adminRepository.findAdministratorByLoginAndHaslo(login, password);
    }

    public boolean completeOrder(Long zamowienieId, Administrator administrator) {
        // Pobierz zamówienie
        Zamowienie zamowienie = zamowienieRepository.findById(zamowienieId).orElse(null);
        if (zamowienie == null || zamowienie.getRealizacja() == null) {
            return false; // Zamówienie lub realizacja nie istnieje
        }

        // Aktualizuj realizację
        zamowienie.getRealizacja().setCzySkompletowane(true);
        zamowienie.getRealizacja().setAdministrator(administrator);

        // Zapisz zmiany
        zamowienieRepository.save(zamowienie);
        return true;
    }


}
