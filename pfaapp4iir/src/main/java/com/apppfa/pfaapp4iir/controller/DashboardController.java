package com.apppfa.pfaapp4iir.controller;

import com.apppfa.pfaapp4iir.model.User;
import com.apppfa.pfaapp4iir.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DashboardController {

    private final UserService userService;

    public DashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/dashboard")
    public String showDashboard(
            @RequestParam(value = "search", required = false) String searchTerm,
            Model model,
            Authentication authentication) {

        try {
            // 1. Récupérer l'utilisateur connecté
            User currentUser = userService.getCurrentUser(authentication);
            model.addAttribute("currentUser", currentUser);

            // 2. Récupérer les utilisateurs (avec recherche si paramètre présent)
            List<User> users;
            if (searchTerm != null && !searchTerm.isEmpty()) {
                users = userService.searchUsers(searchTerm);
            } else {
                users = userService.getAllUsers();
            }

            model.addAttribute("users", users);
            model.addAttribute("totalUsers", users.size());

            return "dashboard";

        } catch (Exception e) {
            return "redirect:/error";
        }
    }
    /*
    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public ResponseEntity<String> deleteUser(@PathVariable Long id, Authentication authentication) {
        try {
            User currentUser = userService.getCurrentUser(authentication);
            if (!currentUser.getRole().equals("ROLE_ADMIN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Accès refusé");
            }

            userService.deleteUser(id);
            return ResponseEntity.ok("Utilisateur supprimé avec succès");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la suppression: " + e.getMessage());
        }
    }*/
    @GetMapping("admin/edit-user/{id}")
    public String editUserForm(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/edit-user";
    }

    @PostMapping("admin/update/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute User user,
                             RedirectAttributes redirectAttributes) {
        userService.updateUser(user);
        redirectAttributes.addFlashAttribute("success", "Utilisateur mis à jour avec succès");
        return "redirect:/dashboard";
    }

    @GetMapping("admin/delete-user/{id}")
    public String confirmDelete(@PathVariable Long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "admin/delete-user";
    }

    @PostMapping("admin/delete-user/{id}")
    public String deleteUser(@PathVariable Long id,
                             RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("success", "Utilisateur supprimé avec succès");
        return "redirect:/dashboard";
    }
}
