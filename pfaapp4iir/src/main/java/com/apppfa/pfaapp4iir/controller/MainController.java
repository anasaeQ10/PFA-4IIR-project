package com.apppfa.pfaapp4iir.controller;

import com.apppfa.pfaapp4iir.model.OffreEmploi;
import com.apppfa.pfaapp4iir.repository.OffreEmploiRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class MainController {

    private final OffreEmploiRepository offreEmploiRepository;

    public MainController(OffreEmploiRepository offreEmploiRepository) {
        this.offreEmploiRepository = offreEmploiRepository;
    }

    @GetMapping("/")
    public String welcome(Model model) {
        try {
            // Récupérer les 2 premières offres triées par date décroissante
            List<OffreEmploi> offres = offreEmploiRepository.findTop2ByOrderByDatePostulationDesc();
            model.addAttribute("offers", offres);
            return "welcome";
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }
    }
}