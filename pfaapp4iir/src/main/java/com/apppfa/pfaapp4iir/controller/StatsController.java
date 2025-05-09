package com.apppfa.pfaapp4iir.controller;

import com.apppfa.pfaapp4iir.repository.UserRepository;
import com.apppfa.pfaapp4iir.repository.OffreEmploiRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.time.LocalDate;
import java.util.Map;

@Controller
public class StatsController {

    private final UserRepository userRepository;
    private final OffreEmploiRepository offreEmploiRepository;

    public StatsController(UserRepository userRepository,
                           OffreEmploiRepository offreEmploiRepository) {
        this.userRepository = userRepository;
        this.offreEmploiRepository = offreEmploiRepository;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/stats")
    public String showStats(Model model) {
        // Statistiques utilisateurs
        long totalUsers = userRepository.count();
        long adminCount = userRepository.countByRole("ROLE_ADMIN");
        long studentCount = userRepository.countByRole("ROLE_USER");
        long rhCount = userRepository.countByRole("ROLE_RH");

        // Statistiques offres
        long totalOffers = offreEmploiRepository.count();
        long newOffersLastWeek = offreEmploiRepository.countByDatePostulationAfter(
                LocalDate.now().minusWeeks(1).atStartOfDay());
        // Données pour les graphiques
        Map<String, Long> offersByMonth = offreEmploiRepository.countOffersByMonth();
        Map<String, Long> usersByRole = Map.of(
                "ROLE_ADMIN", adminCount,
                "ROLE_USER", studentCount,
                "ROLE_RH", rhCount
        );

        model.addAttribute("offersByMonth", offersByMonth);
        model.addAttribute("usersByRole", usersByRole);

        // Ajout au modèle
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("adminCount", adminCount);
        model.addAttribute("studentCount", studentCount);
        model.addAttribute("rhCount", rhCount);
        model.addAttribute("totalOffers", totalOffers);
        model.addAttribute("newOffersLastWeek", newOffersLastWeek);

        return "admin/stats";
    }
}