package com.apppfa.pfaapp4iir.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserForm {

    @Size(max = 50, message = "Le prénom ne doit pas dépasser 50 caractères")
    private String firstName;

    @Size(max = 50, message = "Le nom ne doit pas dépasser 50 caractères")
    private String lastName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    private String email; // Champ maintenant modifiable

    @Pattern(regexp = "^\\+?[0-9\\s-]*$", message = "Numéro de téléphone invalide")
    @Size(max = 20, message = "Le téléphone ne doit pas dépasser 20 caractères")
    private String phoneNumber;

    @Size(max = 200, message = "L'adresse ne doit pas dépasser 200 caractères")
    private String address; // Accepte tous les caractères

    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).*$",
            message = "Le mot de passe doit contenir au moins 1 majuscule, 1 minuscule, 1 chiffre et 1 caractère spécial")
    private String newPassword;

    private String confirmPassword;

    // Getters et Setters
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    // Formatage optionnel
    public void setEmail(String email) {
        this.email = email != null ? email.toLowerCase().trim() : null;
    }

    public void setAddress(String address) {
        this.address = address != null ? address.trim() : null;
    }
    // Méthodes de transformation personnalisées
    public void setLastName(String lastName) {
        this.lastName = lastName != null ? lastName.toUpperCase() : null;
    }



    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            this.phoneNumber = null;
            return;
        }

        // Nettoyage du numéro
        String cleaned = phoneNumber.replaceAll("\\s", "");

        if (cleaned.startsWith("+")) {
            cleaned = cleaned.substring(1);
        }
        if (cleaned.startsWith("0")) {
            cleaned = cleaned.substring(1);
            cleaned = "216" + cleaned;
        }

        this.phoneNumber = cleaned;
    }


}