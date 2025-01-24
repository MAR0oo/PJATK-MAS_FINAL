package com.example.maro.service;

import com.example.maro.model.entities.Klient;
import com.example.maro.model.entities.Koszulka;
import com.example.maro.repository.KoszulkaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShirtsService {

    @Autowired
    private KoszulkaRepository koszulkaRepository;

    public List<Koszulka> getAllShirts(){
        return koszulkaRepository.findAll();
    }

    public Koszulka getShirtByName(String name){
        return koszulkaRepository.getKoszulkaByNazwa(name);
    }

}
