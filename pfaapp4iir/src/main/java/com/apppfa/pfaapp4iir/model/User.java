package com.apppfa.pfaapp4iir.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @Email(message = "Email invalide")
    @NotBlank(message = "Email requis")
    private String email;

    private String firstName;
    private String lastName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(length = 200)
    private String address; // Accepte tous caractères

    @Column(nullable = false)
    @NotBlank(message = "Mot de passe requis")
    @Size(min = 8, message = "Minimum 8 caractères")
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&]).*$",
            message = "Doit contenir majuscule, minuscule, chiffre et caractère spécial"
    )
    private String password;

    private String role = "ROLE_USER";
    private boolean enabled = true;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime lastLoginDate = LocalDateTime.now();

}