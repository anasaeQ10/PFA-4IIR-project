package com.apppfa.pfaapp4iir.controller;

import com.apppfa.pfaapp4iir.model.User;
import com.apppfa.pfaapp4iir.repository.UserRepository;
import com.apppfa.pfaapp4iir.service.EmailService;
import com.apppfa.pfaapp4iir.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    @Autowired
    private EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          UserService userService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    @Autowired
    private UserRepository userRepository;
/*
    @GetMapping("/dashboard")
    public String showDashboard(Model model, Authentication authentication) {
        // Récupère l'utilisateur connecté
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));

        // Ajoute les données au modèle
        model.addAttribute("userInfo", user);

        return "dashboard";
    }
*/
    @PostMapping("/login")
    public String loginUser(
            @RequestParam String email,
            @RequestParam String password,
            RedirectAttributes redirectAttributes) {

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(auth);
            return "redirect:/dashboard";
        } catch (DisabledException e) {
            redirectAttributes.addFlashAttribute("error", "Compte non activé. Vérifiez vos emails.");
            return "redirect:/login";
        } catch (BadCredentialsException e) {
            redirectAttributes.addFlashAttribute("error", "Email ou mot de passe incorrect");
            return "redirect:/login";
        }
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult result,
                               @RequestParam("confirmPassword") String confirmPassword,
                               RedirectAttributes redirectAttributes) {

        // Validation des mots de passe
        if (!user.getPassword().equals(confirmPassword)) {
            result.rejectValue("password", "error.user", "Les mots de passe ne correspondent pas");
        }

        // Vérification de l'email unique
        if (userRepository.existsByEmail(user.getEmail())) {
            result.rejectValue("email", "error.user", "Cet email est déjà utilisé");
        }
        // Vérification prénom + nom déjà existants
        if (userRepository.existsByFirstNameAndLastName(user.getFirstName(), user.getLastName())) {
            result.rejectValue("firstName", "error.user", "Ce prénom et nom sont déjà utilisés");
        }

        if (result.hasErrors()) {
            return "auth/register";
        }

        // Enregistrement
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        userRepository.save(user);

        // envoi du mail de bienvenue
        emailService.sendRegistrationEmail(user.getEmail(), user.getLastName(),user.getFirstName());
        // message de succès
        redirectAttributes.addFlashAttribute("successMessage", "Utilisateur ajouté avec succès !");

        return "redirect:/login";
    }
}