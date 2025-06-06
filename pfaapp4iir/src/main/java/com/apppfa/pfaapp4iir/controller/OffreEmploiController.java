// src/main/java/com/apppfa/pfaapp4iir/controller/OffreEmploiController.java
package com.apppfa.pfaapp4iir.controller;

import com.apppfa.pfaapp4iir.model.OffreEmploi;
import com.apppfa.pfaapp4iir.model.User;
import com.apppfa.pfaapp4iir.service.OffreEmploiService;
import com.apppfa.pfaapp4iir.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/offres")
public class OffreEmploiController {
    private final OffreEmploiService offreEmploiService;
    private final UserService userService;

    public OffreEmploiController(OffreEmploiService offreEmploiService, UserService userService) {
        this.offreEmploiService = offreEmploiService;
        this.userService = userService;
    }

    @GetMapping("/ajouter")
    public String showAddForm(Model model) {
        model.addAttribute("offre", new OffreEmploi());
        return "offres/ajouter";
    }
/*
    @PostMapping("/ajouter")
    public String addOffre(
            @ModelAttribute OffreEmploi offre,
            Principal principal, // Autre façon de récupérer l'utilisateur
            RedirectAttributes redirectAttributes) {

        User currentUser = userService.findByEmail(principal.getName());

        // Créez une méthode dans le service
        offreEmploiService.createOffre(offre, currentUser);

        redirectAttributes.addFlashAttribute("success", "Offre créée !");
        return "redirect:/offres/liste";
    }
*/

    @PostMapping("/ajouter")
    public String addOffre(@ModelAttribute OffreEmploi offre,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        try {
            // DEBUG
            System.out.println("Données reçues: " + offre.toString());

            // 1. Récupérer l'utilisateur connecté
            User currentUser = userService.findByEmail(authentication.getName());
            System.out.println("User ID: " + currentUser.getId());

            // 2. Associer l'utilisateur
            offre.setUserId(currentUser.getId());

            // 3. Validation des données
            if(offre.getTitre() == null || offre.getTitre().isEmpty()) {
                throw new IllegalArgumentException("Le titre est requis");
            }

            // 4. Sauvegarde
            OffreEmploi savedOffre = offreEmploiService.saveOffre(offre);
            System.out.println("Offre sauvegardée avec ID: " + savedOffre.getId());

            redirectAttributes.addFlashAttribute("success", "Offre créée avec succès");
            return "redirect:/offres/liste";

        } catch (Exception e) {
            System.err.println("Erreur lors de l'ajout: " + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error",
                    "Erreur: " + e.getMessage());
            return "redirect:/offres/ajouter";
        }
    }
    @GetMapping("/liste")
    public String listOffres(Model model,@RequestParam(value = "search", required = false) String searchTerm) {
        List<OffreEmploi> offres = offreEmploiService.getAllOffres();

        if (searchTerm != null && !searchTerm.isEmpty()) {
            // Recherche avec terme
            offres = offreEmploiService.searchOffres(searchTerm);
        } else {
            // Toutes les offres
            offres = offreEmploiService.getAllOffres();
        }

        Map<Long, String> usersMap = offres.stream()
                .collect(Collectors.toMap(
                        OffreEmploi::getId,
                        offre -> {
                            User user = userService.getUserById(offre.getUserId());
                            return user.getEmail();
                        }
                ));


        model.addAttribute("offres", offres);
        model.addAttribute("usersMap", usersMap);
        model.addAttribute("searchTerm", searchTerm);

        return "offres/liste";
    }
    @GetMapping("/details/{id}")
    public String showOffreDetails(@PathVariable Long id, Model model) {
        try {
            // 1. Récupérer l'offre
            OffreEmploi offre = offreEmploiService.getOffreById(id)
                    .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

            // 2. Récupérer l'auteur
            User auteur = userService.getUserById(offre.getUserId());

            // 3. Ajouter au modèle
            model.addAttribute("offre", offre);
            model.addAttribute("auteur", auteur);

            return "offres/details";

        } catch (Exception e) {
            model.addAttribute("error", "Offre introuvable");
            return "redirect:/offres/liste";
        }

    }
    @PostMapping("/modifier/{id}")
    public String updateOffre(@PathVariable Long id,
                              @ModelAttribute OffreEmploi offre,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes) {
        try {
            // 1. Récupérer l'offre existante
            OffreEmploi existingOffre = offreEmploiService.getOffreById(id)
                    .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

            // 2. Conserver l'ID utilisateur original
            offre.setUserId(existingOffre.getUserId());

            // 3. Forcer l'ID de l'offre
            offre.setId(id);

            // 4. Vérifier que l'utilisateur est bien l'auteur
            User currentUser = userService.findByEmail(authentication.getName());
            if (!offre.getUserId().equals(currentUser.getId())) {
                redirectAttributes.addFlashAttribute("error", "Action non autorisée");
                return "redirect:/offres/liste";
            }

            // 5. Sauvegarder
            offreEmploiService.updateOffre(offre);
            redirectAttributes.addFlashAttribute("success", "Offre modifiée avec succès");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur: " + e.getMessage());
        }
        return "redirect:/offres/liste";
    }
    @GetMapping("/supprimer/{id}")
    public String deleteOffre(@PathVariable Long id,
                              RedirectAttributes redirectAttributes) {
        try {
            offreEmploiService.deleteOffre(id);
            redirectAttributes.addFlashAttribute("success", "Offre supprimée avec succès");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erreur lors de la suppression");
        }
        return "redirect:/offres/liste";
    }
    // Modification d'une offre (GET)
    @GetMapping("/modifier/{id}")
    public String showEditForm(@PathVariable Long id, Model model, Authentication authentication) {
        try {
            OffreEmploi offre = offreEmploiService.getOffreById(id)
                    .orElseThrow(() -> new RuntimeException("Offre non trouvée"));

            // Vérifier que l'utilisateur est l'auteur
            User currentUser = userService.findByEmail(authentication.getName());
            if (!offre.getUserId().equals(currentUser.getId())) {
                return "redirect:/offres/liste";
            }

            model.addAttribute("offre", offre);
            return "offres/edit";

        } catch (Exception e) {
            return "redirect:/offres/liste";
        }
    }




}