package com.example.maro.controller;

import com.example.maro.model.entities.Klient;
import com.example.maro.model.entities.Zamowienie;
import com.example.maro.service.HomeService;
import com.example.maro.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private HomeService homeService;
    @Autowired
    private OrderService orderService;

    @GetMapping("")
    public String showMainSite(HttpSession session, Model model){
        Object loggedUser = session.getAttribute("loggedUser");
        model.addAttribute("loggedUser", loggedUser);
        return "home";
    }

    @GetMapping("/register")
    public String showRegisterPage(HttpSession session, Model model) {
        Object loggedUser = session.getAttribute("loggedUser");
        if (loggedUser == null) {
            model.addAttribute("loggedUser", loggedUser);
        } else {
            model.addAttribute("loggedUser", loggedUser);
            return "home";
        }

        return "register";
    }

    @PostMapping("/register")
    public String registerUser(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam String phone,
            Model model) {

        // Walidacja numeru telefonu
        if (!phone.matches("\\d+")) {
            model.addAttribute("error", "Numer telefonu może zawierać tylko cyfry.");
            return "register";
        }

        // Sprawdzenie, czy login jest zajęty
        if (homeService.isLoginTaken(login)) {
            model.addAttribute("error", "Login jest już zajęty. Wybierz inny.");
            return "register";
        }

        // Tworzenie nowego użytkownika
        Klient klient = new Klient();
        klient.setLogin(login);
        klient.setHaslo(password);
        klient.setImie(firstName);
        klient.setNazwisko(lastName);
        klient.setAdresEmail(email);
        klient.setNumerTelefonu(phone);
        klient.setDataRejestracji(LocalDate.now());

        homeService.addUser(klient);

        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage(HttpSession session, Model model) {
        Object loggedUser = session.getAttribute("loggedUser");
        if (loggedUser == null) {
            model.addAttribute("loggedUser", loggedUser);
        } else {
            model.addAttribute("loggedUser", loggedUser);
            return "home";
        }
        return "login"; // Wyświetla login.html
    }

    @PostMapping("/login")
    public String loginUser(
            @RequestParam String login,
            @RequestParam String password,
            HttpServletRequest request,
            Model model) {

        Klient user = homeService.authenticate(login, password);

        if (user == null) {
            model.addAttribute("error", "Nieprawidłowy login lub hasło.");
            return "login";
        }

        request.getSession().setAttribute("loggedUser", user);

        return "redirect:/"; // Przekierowanie na stronę główną
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate(); // Wyczyszczenie sesji
        return "redirect:/"; // Przekierowanie na stronę główną
    }

    @GetMapping("/thankyou")
    public String showThankYouPage(HttpSession session, Model model) {
        Klient loggedUser = (Klient) session.getAttribute("loggedUser");
        if (loggedUser != null) {
            model.addAttribute("loggedUser", loggedUser);
        }
        return "thankyou";
    }

    @GetMapping("/profil")
    public String showProfile(HttpSession session, Model model) {
        Klient loggedUser = (Klient) session.getAttribute("loggedUser");
        if (loggedUser == null) {
            return "redirect:/login";
        }

        List<Zamowienie> userOrders = orderService.findAllOrdersByClient(loggedUser);

        model.addAttribute("user", loggedUser);
        model.addAttribute("orders", userOrders);
        return "profil";
    }

}
