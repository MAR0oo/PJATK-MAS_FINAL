package com.example.maro.service;

import com.example.maro.model.entities.Dostawca;
import com.example.maro.repository.DostawcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderService {

    @Autowired
    private DostawcaRepository dostawcaRepository;

    public List<Dostawca> getAllProviders() {
        return dostawcaRepository.findAll();
    }

    public Dostawca getProviderByName(String name) {
        return dostawcaRepository.findDostawcaByNazwa(name);
    }

}
