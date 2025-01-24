package com.example.maro.service;

import com.example.maro.model.entities.Klient;
import com.example.maro.model.entities.Koszulka;
import com.example.maro.repository.KoszulkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private KoszulkaRepository koszulkaRepository;

    public List<Koszulka> getClientsShirts(Klient klient){
        List<Koszulka> koszulka = new ArrayList<>();
        List<String> koszyk = klient.getKoszyk();
        koszyk.forEach(nazwa -> koszulka.add(koszulkaRepository.getKoszulkaByNazwa(nazwa)));
        return koszulka;
    }


}
