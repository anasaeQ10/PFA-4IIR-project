// src/main/java/com/apppfa/pfaapp4iir/repository/OffreEmploiRepository.java
package com.apppfa.pfaapp4iir.repository;

import com.apppfa.pfaapp4iir.model.OffreEmploi;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OffreEmploiRepository extends JpaRepository<OffreEmploi, Long> {
    List<OffreEmploi> findTop2ByOrderByDatePostulationDesc();
}