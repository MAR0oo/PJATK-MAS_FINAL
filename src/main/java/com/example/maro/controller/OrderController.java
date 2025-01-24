package com.example.maro.controller;

import com.example.maro.model.entities.Dostawca;
import com.example.maro.model.entities.Klient;
import com.example.maro.model.entities.Koszulka;
import com.example.maro.model.entities.Zamowienie;
import com.example.maro.model.enums.TypDostawy;
import com.example.maro.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ShirtsService shirtService;
    @Autowired
    private HomeService homeService;
    @Autowired
    private ProviderService providerService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @GetMapping("")
    public String showOrderDetails(HttpSession session, Model model) {
        Object loggedUser = session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        Klient user = (Klient) loggedUser;

        if (user.getKoszyk().isEmpty()) {
            return "redirect:/cart";
        }

        // Pobierz koszulki z koszyka
        List<Koszulka> products = cartService.getClientsShirts(user);

        // Oblicz cenę całkowitą
        double totalPrice = products.stream()
                .mapToDouble(Koszulka::getCena)
                .sum();

        List<Dostawca> providers = providerService.getAllProviders();

        // Dodanie danych do modelu
        model.addAttribute("products", products);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("providers", providers);
        model.addAttribute("loggedUser", user);

        return "order";
    }

    @PostMapping("/submit")
    public String submitOrder(@RequestParam String address,
                              @RequestParam String deliveryType,
                              @RequestParam String deliveryProvider,
                              @RequestParam String paymentMethod, // Dodano parametr płatności
                              HttpSession session, Model model) {

        Object loggedUser = session.getAttribute("loggedUser");

        if (loggedUser == null) {
            return "redirect:/login";
        }

        Klient user = (Klient) loggedUser;

        try {
            // Pobierz dostawcę
            Dostawca dostawca = providerService.getProviderByName(deliveryProvider);

            // Wywołanie metody zlozZamowienie w klasie Klient z uwzględnieniem metody płatności
            Zamowienie zamowienie = orderService.zlozZamowienie(
                    address,
                    TypDostawy.valueOf(deliveryType.toUpperCase()),
                    dostawca,
                    user,
                    paymentMethod
            );

            orderService.addOrder(zamowienie);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/order";
        }

        return "redirect:/thankyou";
    }

}
