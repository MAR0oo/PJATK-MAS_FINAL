package com.example.maro.controller;

import com.example.maro.model.entities.Klient;
import com.example.maro.model.entities.Koszulka;
import com.example.maro.service.CartService;
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
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("")
    public String showCart(HttpSession session, Model model) {

        Object loggedUser = session.getAttribute("loggedUser");

        if (loggedUser != null) {
            model.addAttribute("loggedUser", loggedUser);
        } else {
            return "redirect:/login";
        }

        Klient user = (Klient) loggedUser;
        List<Koszulka> products = cartService.getClientsShirts(user);

        model.addAttribute("products", products);
        model.addAttribute("loggedUser", user);

        return "cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam String productName, HttpSession session, Model model) {

        Object loggedUser = session.getAttribute("loggedUser");

        if (loggedUser != null) {
            model.addAttribute("loggedUser", loggedUser);
        } else {
            return "redirect:/login";
        }

        Klient user = (Klient) loggedUser;
        user.usunZKoszyka(productName);

        return "redirect:/cart";
    }

}


