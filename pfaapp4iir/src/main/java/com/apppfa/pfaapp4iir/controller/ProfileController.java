package com.apppfa.pfaapp4iir.controller;

import com.apppfa.pfaapp4iir.dto.UserForm;
import com.apppfa.pfaapp4iir.model.User;
import com.apppfa.pfaapp4iir.repository.UserRepository;
import com.apppfa.pfaapp4iir.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileController(UserService userService,
                             UserRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String showProfile(Model model, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);
        model.addAttribute("userInfo", user);
        return "profile/view";
    }

    @GetMapping("/edit")
    public String editProfileForm(Model model, Authentication authentication) {
        User user = userService.getCurrentUser(authentication);

        UserForm userForm = new UserForm();
        userForm.setFirstName(user.getFirstName());
        userForm.setLastName(user.getLastName());
        userForm.setEmail(user.getEmail());
        userForm.setPhoneNumber(user.getPhoneNumber());
        userForm.setAddress(user.getAddress());

        model.addAttribute("userForm", userForm);
        return "profile/edit";
    }

    @PostMapping("/update")
    public String updateProfile(
            @Valid @ModelAttribute("userForm") UserForm userForm,
            BindingResult result,
            Authentication authentication,
            RedirectAttributes redirectAttributes) {

        User currentUser = userService.getCurrentUser(authentication);

        // Vérification de l'unicité de l'email
        if (!result.hasFieldErrors("email") &&
                userService.existsByEmailAndIdNot(userForm.getEmail(), currentUser.getId())) {
            result.rejectValue("email", "duplicate.email", "Cet email est déjà utilisé");
        }

        // Vérification du mot de passe
        if (userForm.getNewPassword() != null && !userForm.getNewPassword().isEmpty()) {
            if (!userForm.getNewPassword().equals(userForm.getConfirmPassword())) {
                result.rejectValue("confirmPassword", "match.password", "Les mots de passe ne correspondent pas");
            }

            if (passwordEncoder.matches(userForm.getNewPassword(), currentUser.getPassword())) {
                result.rejectValue("newPassword", "duplicate.password", "Le nouveau mot de passe doit être différent de l'actuel");
            }
        }

        if (result.hasErrors()) {
            return "profile/edit";
        }

        try {
            // Mise à jour des informations de base
            currentUser.setEmail(userForm.getEmail().toLowerCase());
            currentUser.setFirstName(userForm.getFirstName());
            currentUser.setLastName(userForm.getLastName());
            currentUser.setPhoneNumber(userForm.getPhoneNumber());
            currentUser.setAddress(userForm.getAddress());

            // Mise à jour du mot de passe si fourni
            if (userForm.getNewPassword() != null && !userForm.getNewPassword().isEmpty()) {
                currentUser.setPassword(passwordEncoder.encode(userForm.getNewPassword()));
            }

            userService.updateUser(currentUser);

            redirectAttributes.addFlashAttribute("successMessage", "Profil mis à jour avec succès");
            return "redirect:/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erreur: " + e.getMessage());
            return "redirect:/profile/edit";
        }
    }
}