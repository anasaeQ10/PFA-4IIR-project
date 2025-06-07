package com.apppfa.pfaapp4iir.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRegistrationEmail(String to, String fname,String lname) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@CareerCenter.com");
        message.setTo(to);
        message.setSubject("Création de compte réussie");
        message.setText("Bonjour M. " + fname +" "+lname+",\n\n"
                + "Merci d’avoir créé un compte sur notre plateforme. Nous sommes ravis de vous compter parmi nos utilisateurs.\n"
                + "N’hésitez pas à nous contacter si vous avez la moindre question.\n\n"
                + "Cordialement,\n"
                + "L’équipe de Career Center");
        mailSender.send(message);
    }

    public void sendNewOffreEmail(String to, String fname, String lname, String titreOffre, String description) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@CareerCenter.com");
        message.setTo(to);
        message.setSubject("Nouvelle offre d'emploi : " + titreOffre);
        message.setText("Bonjour Mr. " + fname + " " + lname + ",\n\n"
                + "Une nouvelle offre d'emploi a été publiée :\n\n"
                + "Titre : " + titreOffre + "\n"
                + "Description : " + description + "\n\n"
                + "Connectez-vous à votre compte pour en savoir plus.\n\n"
                + "Cordialement,\n"
                + "L’équipe Career Center");
        mailSender.send(message);
    }

    public void sendNewEventEmail(String to, String fname, String lname,
                                  String titreEvent, String description, String lieu, LocalDate dateEvent) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@CareerCenter.com");
        message.setTo(to);
        message.setSubject("Nouvel événement : " + titreEvent);
        message.setText("Bonjour Mr. " + fname + " " + lname + ",\n\n"
                + "Un nouvel événement a été publié :\n\n"
                + "Titre : " + titreEvent + "\n"
                + "Description : " + description + "\n"
                + "Lieu : " + lieu + "\n"
                + "Date : " + dateEvent.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) + "\n\n"
                + "Connectez-vous à votre compte pour en savoir plus.\n\n"
                + "Cordialement,\n"
                + "L’équipe Career Center");

        mailSender.send(message);
    }

}
