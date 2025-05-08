// src/main/java/com/apppfa/pfaapp4iir/model/OffreEmploi.java
package com.apppfa.pfaapp4iir.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "offres_emploi")
public class OffreEmploi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String nomSociete;
    private String affiche; // URL de l'image
    private String typeEmploi;
    private String fonctionPoste;
    private String secteurActivite;
    private String description;
    private String lienPostuler;

    @Column(name = "user_id")
    private Long userId; // ID de l'utilisateur (pas de relation JPA)
}