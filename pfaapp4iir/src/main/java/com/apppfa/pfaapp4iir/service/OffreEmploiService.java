// src/main/java/com/apppfa/pfaapp4iir/service/OffreEmploiService.java
package com.apppfa.pfaapp4iir.service;

import com.apppfa.pfaapp4iir.model.OffreEmploi;
import com.apppfa.pfaapp4iir.model.User;
import com.apppfa.pfaapp4iir.repository.OffreEmploiRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OffreEmploiService {
    private final OffreEmploiRepository offreEmploiRepository;
    private Long userId;

    public OffreEmploiService(OffreEmploiRepository offreEmploiRepository) {
        this.offreEmploiRepository = offreEmploiRepository;
    }
/*
    public OffreEmploi saveOffre(OffreEmploi offre) {
        offre.setUserId(userId);
        return offreEmploiRepository.save(offre);
    }
*/
@Transactional
public OffreEmploi saveOffre(OffreEmploi offre) {
    // Validation
    if(offre.getUserId() == null) {
        throw new IllegalStateException("L'ID utilisateur est requis");
    }

    // DEBUG
    System.out.println("Sauvegarde de l'offre: " + offre);

    OffreEmploi saved = offreEmploiRepository.save(offre);

    // Vérification
    if(saved.getId() == null) {
        throw new RuntimeException("Échec de la sauvegarde");
    }

    return saved;
}
    public List<OffreEmploi> getAllOffres() {
        return offreEmploiRepository.findAll();
    }

    public void createOffre(OffreEmploi offre, User user) {
        offre.setUserId(user.getId());
        // Autres initialisations si besoin
        offreEmploiRepository.save(offre);
    }
    public Optional<OffreEmploi> getOffreById(Long id) {
        return offreEmploiRepository.findById(id);
    }
    public void updateOffre(OffreEmploi offre) {
        if (!offreEmploiRepository.existsById(offre.getId())) {
            throw new RuntimeException("Offre introuvable");
        }
        offreEmploiRepository.save(offre);
    }
    public void deleteOffre(Long id) {
        if (!offreEmploiRepository.existsById(id)) {
            throw new RuntimeException("Offre introuvable");
        }
        offreEmploiRepository.deleteById(id);
    }

    public List<OffreEmploi> searchOffres(String searchTerm) {
        return offreEmploiRepository.search(
                searchTerm.toLowerCase() // Recherche insensible à la casse
        );
    }

}