package com.example.maro.service;

import com.example.maro.model.entities.*;
import com.example.maro.model.enums.Status;
import com.example.maro.model.enums.TypDostawy;
import com.example.maro.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private ZamowienieRepository zamowienieRepository;
    @Autowired
    private DostawaRepository dostawaRepository;
    @Autowired
    private RealizacjaRepository realizacjaRepository;
    @Autowired
    private ShirtsService shirtService;
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private KoszulkaRepository koszulkaRepository;

    public List<Zamowienie> findAllOrdersByClient(Klient klient){
        return zamowienieRepository.findAllByKlient(klient);
    }

    public void addOrder(Zamowienie zamowienie) {
        Dostawa dostawa = zamowienie.getDostawa();
        Realizacja realizacja = zamowienie.getRealizacja();
        zamowienie.setDostawa(null);
        zamowienie.setRealizacja(null);
        Zamowienie savedOrder = zamowienieRepository.save(zamowienie);
        savedOrder.setDostawa(dostawa);
        savedOrder.setRealizacja(realizacja);

        dostawa.setZamowienie(savedOrder);
        Dostawa savedDostawa = dostawaRepository.save(dostawa);

        realizacja.setZamowienie(savedOrder);
        Realizacja savedRealizacja = realizacjaRepository.save(realizacja);

        savedOrder.setRealizacja(savedRealizacja);
        savedOrder.setDostawa(savedDostawa);
        zamowienieRepository.save(savedOrder);
    }


    public Zamowienie zlozZamowienie(String address, TypDostawy deliveryType, Dostawca deliveryProvider, Klient klient,  String paymentMethod) {
        final List<String> koszyk = klient.getKoszyk();
        if (koszyk.isEmpty()) {
            throw new IllegalStateException("Koszyk jest pusty! Nie można złożyć zamówienia.");
        }

        Zamowienie zamowienie = new Zamowienie();
        zamowienie.setDataZlozenia(LocalDate.now());
        zamowienie.setKoszulkiKoszyk(new ArrayList<>(koszyk));
        zamowienie.setKlient(klient);
        zamowienie.setStatus(Status.PRZYJETE);

        List<Koszulka> koszulka = new ArrayList<>();
        koszyk.forEach(nazwa -> koszulka.add(koszulkaRepository.getKoszulkaByNazwa(nazwa)));
        zamowienie.setKoszulki(koszulka);

        int orderPrice = (int) koszyk.stream()
                .mapToDouble(name -> shirtService.getShirtByName(name).getCena())
                .sum();

        zamowienie.setCena(orderPrice + deliveryProvider.getOplata());

        Dostawa dostawa = new Dostawa();
        dostawa.setAdres(address);
        dostawa.setTyp(deliveryType);
        dostawa.setDostawca(deliveryProvider);
        dostawa.setDataDostawy(LocalDate.now());
        zamowienie.setDostawa(dostawa);
        dostawa.setZamowienie(zamowienie);

        Realizacja realizacja = new Realizacja();
        Administrator administrator = adminRepository.findAll().get(0);
        List<Realizacja> realizacje = administrator.getRealizacja();
        realizacja.setCzyOplacone(paymentMethod.equalsIgnoreCase("pobranie"));
        realizacja.setCzySkompletowane(false);
        realizacja.setZamowienie(zamowienie);
        realizacja.setAdministrator(administrator);
        zamowienie.setRealizacja(realizacja);
        realizacje.add(realizacja);
        administrator.setRealizacja(realizacje);

        koszyk.clear();

        return zamowienie;
    }

    private Koszulka getKoszulkaByName(String name) {
        return koszulkaRepository.getKoszulkaByNazwa(name);
    }

}
