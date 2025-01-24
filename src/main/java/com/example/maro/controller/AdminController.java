package com.example.maro.controller;

import com.example.maro.model.entities.Administrator;
import com.example.maro.model.entities.Zamowienie;
import com.example.maro.service.AdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/administrator")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("")
    public String showAdminLoginPage(HttpSession session) {
        if (session.getAttribute("loggedAdmin") != null) {
            return "redirect:/administrator/panel";
        }
        return "admin-login";
    }

    @PostMapping("")
    public String authenticateAdmin(
            @RequestParam String login,
            @RequestParam String password,
            HttpServletRequest request,
            Model model) {

        Administrator admin = adminService.authenticate(login, password);
        if (admin == null) {
            model.addAttribute("error", "Nieprawidłowy login lub hasło.");
            return "admin-login";
        }

        request.getSession().setAttribute("loggedAdmin", admin);
        return "redirect:/administrator/panel";
    }

    @GetMapping("/panel")
    public String showAdminPanel(HttpSession session, Model model) {
        final Administrator loggedAdmin = (Administrator) session.getAttribute("loggedAdmin");
        if (loggedAdmin == null) {
            return "redirect:/administrator";
        }
        model.addAttribute("admin", loggedAdmin);
        return "admin-panel";
    }

    @GetMapping("/panel/orders")
    public String showAdminPanelOrders(HttpSession session, Model model) {
        final Administrator loggedAdmin = (Administrator) session.getAttribute("loggedAdmin");
        if (loggedAdmin == null) {
            return "redirect:/administrator";
        }
        model.addAttribute("admin", loggedAdmin);
        model.addAttribute("orders", adminService.findAllZamowienia());


        return "orders";
    }
    @GetMapping("/panel/orders/{id}")
    public String showOrderDetails(@PathVariable Long id, HttpSession session, Model model) {
        final Administrator loggedAdmin = (Administrator) session.getAttribute("loggedAdmin");
        if (loggedAdmin == null) {
            return "redirect:/administrator";
        }

        // Pobierz szczegóły zamówienia
        Zamowienie order = adminService.getOrderDetails(id);
        if (order == null) {
            model.addAttribute("error", "Nie znaleziono zamówienia.");
            return "redirect:/administrator/panel/orders";
        }

        model.addAttribute("admin", loggedAdmin);
        model.addAttribute("order", order);
        return "order-details";
    }

    @PostMapping("/panel/orders/complete")
    public String completeOrder(
            @RequestParam Long zamowienieId,
            HttpSession session,
            Model model) {
        final Administrator loggedAdmin = (Administrator) session.getAttribute("loggedAdmin");
        if (loggedAdmin == null) {
            return "redirect:/administrator";
        }

        boolean updated = adminService.completeOrder(zamowienieId, loggedAdmin);
        if (!updated) {
            model.addAttribute("error", "Nie udało się skompletować zamówienia.");
        }

        model.addAttribute("admin", loggedAdmin);
        return "redirect:/administrator/panel/orders";
    }




}
