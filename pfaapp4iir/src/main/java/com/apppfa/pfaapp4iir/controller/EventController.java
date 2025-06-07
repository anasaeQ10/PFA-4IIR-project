package com.apppfa.pfaapp4iir.controller;

//import ch.qos.logback.core.model.Model;
import com.apppfa.pfaapp4iir.model.Event;
import com.apppfa.pfaapp4iir.model.User;
import com.apppfa.pfaapp4iir.service.EmailService;
import com.apppfa.pfaapp4iir.service.EventService;
import com.apppfa.pfaapp4iir.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;


import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService; // ou UserRepository
    private final EmailService emailService;

    public EventController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public String listEvents(Model model) {
        model.addAttribute("events", eventService.getAllEvents());
        return "events/list";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("event", new Event());
        return "events/add";
    }


    @PostMapping("/save")
    public String saveEvent(@ModelAttribute Event event,
                            @RequestParam("imageFile") MultipartFile file,
                            Principal principal) throws IOException {
        if (!file.isEmpty()) {
            event.setImage(file.getBytes());
            event.setImageType(file.getContentType());
        }

        // Récupérer l'utilisateur connecté
        String email = principal.getName();
        User user = userService.findByEmail(email);
        event.setUser(user);  // Associe l'utilisateur connecté à l'événement

        eventService.saveEvent(event);

        // ✅ ENVOYER L'ÉVÉNEMENT À TOUS LES UTILISATEURS ROLE_USER
        List<User> allUsers = userService.getAllUsers();
        for (User u : allUsers) {
            if (u.getRole().equals("ROLE_USER")) {
                emailService.sendNewEventEmail(
                        u.getEmail(),
                        u.getFirstName(),
                        u.getLastName(),
                        event.getTitle(),
                        event.getDescription(),
                        event.getLieu(),
                        event.getDate()
                );
            }
        }

        return "redirect:/events";
    }



    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("event", eventService.getEvent(id));
        return "events/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return "redirect:/events";
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long id) {
        Event event = eventService.getEvent(id);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(event.getImageType()))
                .body(event.getImage());
    }

    @GetMapping("/details/{id}")
    public String viewDetails(@PathVariable Long id, Model model) {
        Event event = eventService.getEventById(id);
        if (event == null) return "redirect:/events";
        model.addAttribute("event", event);
        return "events/details";
    }
}

