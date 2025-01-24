package com.example.maro.controller;

import com.example.maro.model.entities.Klient;
import com.example.maro.model.entities.Koszulka;
import com.example.maro.service.OrderService;
import com.example.maro.service.ShirtsService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/shirts")
public class ShirtsController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ShirtsService shirtService;

    @GetMapping("")
    public String showShirts(HttpSession session, Model model) {
        List<Koszulka> shirts = shirtService.getAllShirts();
        model.addAttribute("shirts", shirts);
        return "shirts";
    }

    @GetMapping("/{name}")
    public String showShirtDetails(@PathVariable String name, HttpSession session, Model model) {
        Klient loggedUser = (Klient) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            model.addAttribute("loggedUser", loggedUser);
        }
        Koszulka shirt = shirtService.getShirtByName(name);
        if (shirt == null) {
            return "redirect:/shirts";
        }
        model.addAttribute("shirt", shirt);
        return "shirt-details";
    }

    @PostMapping("/add-to-cart")
    public ResponseEntity<String> addToCart(@RequestParam String productName, HttpSession session) {
        Klient loggedUser = (Klient) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return ResponseEntity.status(403).body("Musisz być zalogowany, aby dodać do koszyka.");
        }

        loggedUser.dodajDoKoszyka(productName);

        return ResponseEntity.ok("Dodano do koszyka.");
    }

}
