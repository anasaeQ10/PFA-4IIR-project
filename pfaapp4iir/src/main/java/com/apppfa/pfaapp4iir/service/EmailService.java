package com.apppfa.pfaapp4iir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String to, String fname,String lname) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@votreapp.com");
        message.setTo(to);
        message.setSubject("Création de compte réussie");
        message.setText("Bonjour M. " + fname +" "+lname+",\n\n"
                + "Merci d’avoir créé un compte sur notre plateforme. Nous sommes ravis de vous compter parmi nos utilisateurs.\n"
                + "N’hésitez pas à nous contacter si vous avez la moindre question.\n\n"
                + "Cordialement,\n"
                + "L’équipe de Career Center");
        mailSender.send(message);
    }
}
