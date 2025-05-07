package com.apppfa.pfaapp4iir.controller;

import com.apppfa.pfaapp4iir.repository.UserRepository;
import com.apppfa.pfaapp4iir.repository.UserLoginRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;

@Controller
public class StatsController {

    private final UserRepository userRepository;
    private final UserLoginRepository userLoginRepository;

    public StatsController(UserRepository userRepository,
                           UserLoginRepository userLoginRepository) {
        this.userRepository = userRepository;
        this.userLoginRepository = userLoginRepository;
    }
/*
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/stats")
    public String showStats(Model model, HttpServletRequest request) {
        try {
            // Vérification manuelle de sécurité
            if (!request.isUserInRole("ROLE_ADMIN")) {
                return "redirect:/access-denied";
            }

            // Ajout d'attributs TEST
            model.addAttribute("testValue", "TEST OK");
            return "admin/stats";

        } catch (Exception e) {
            return "redirect:/error?message=" + e.getMessage();
        }
    }
    */
@GetMapping("/admin/stats")
public String testStats(Model model) {
    // Valeurs de test minimales
    model.addAttribute("totalUsers", 10);
    model.addAttribute("activeUsers", 42);
    model.addAttribute("newUsers", 15);
    model.addAttribute("adminCount", 5);
    model.addAttribute("studentCount", 120);
    model.addAttribute("rhCount", 25);

    return "admin/stats"; // Chemin exact du template
}
}