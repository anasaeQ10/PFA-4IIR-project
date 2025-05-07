package com.apppfa.pfaapp4iir.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Collections;

@Controller
public class MainController {

    @GetMapping("/")
    public String welcome(Model model) {
        try {
            model.addAttribute("offers", Collections.emptyList());
            return "welcome";
        } catch (Exception e) {
            return "redirect:/error";
        }
    }
}
