package com.example.maro.service;

import com.example.maro.model.entities.Klient;
import com.example.maro.repository.KlientRepository;
import com.example.maro.repository.OsobaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomeService {

    @Autowired
    private OsobaRepository osobaRepository;
    @Autowired
    private KlientRepository klientRepository;

    public Boolean isLoginTaken(String login){
        return osobaRepository.existsByLogin(login);
    }

    public void addUser(Klient klient){
        klientRepository.save(klient);
    }

    public Klient authenticate(String login, String password){
        return klientRepository.findKlientByLoginAndHaslo(login, password);
    }


}
