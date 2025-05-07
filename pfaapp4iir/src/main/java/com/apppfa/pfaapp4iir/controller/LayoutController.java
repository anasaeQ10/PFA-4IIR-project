package com.apppfa.pfaapp4iir.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpServletRequest;
import com.apppfa.pfaapp4iir.model.User;
import com.apppfa.pfaapp4iir.service.UserService;

@ControllerAdvice
public class LayoutController {

    private final UserService userService;

    public LayoutController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute
    public void addLayoutAttributes(Model model, HttpServletRequest request) {
        // Gestion sidebar
        String requestUri = request.getRequestURI();
        boolean isAuthPage = requestUri.startsWith("/auth/") || requestUri.equals("/login") || requestUri.equals("/register");
        model.addAttribute("showSidebar", !isAuthPage);

        // Ajout de l'utilisateur courant
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            User currentUser = userService.getCurrentUser(authentication);
            model.addAttribute("currentUser", currentUser);
        }
    }
}