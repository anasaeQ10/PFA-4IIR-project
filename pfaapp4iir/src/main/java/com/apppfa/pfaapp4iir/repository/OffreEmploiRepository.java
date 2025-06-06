// src/main/java/com/apppfa/pfaapp4iir/repository/OffreEmploiRepository.java
package com.apppfa.pfaapp4iir.repository;

import com.apppfa.pfaapp4iir.model.OffreEmploi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface OffreEmploiRepository extends JpaRepository<OffreEmploi, Long> {
    List<OffreEmploi> findTop2ByOrderByDatePostulationDesc();

    @Query("SELECT COUNT(o) FROM OffreEmploi o WHERE o.datePostulation > :date")
    long countByDatePostulationAfter(LocalDateTime date);
    @Query("SELECT FUNCTION('DATE_FORMAT', o.datePostulation, '%Y-%m') as month, COUNT(o) as count " +
            "FROM OffreEmploi o " +
            "GROUP BY FUNCTION('DATE_FORMAT', o.datePostulation, '%Y-%m') " +
            "ORDER BY FUNCTION('DATE_FORMAT', o.datePostulation, '%Y-%m')")
    List<Object[]> countOffersByMonthRaw();

    default Map<String, Long> countOffersByMonth() {
        return countOffersByMonthRaw().stream()
                .collect(Collectors.toMap(
                        arr -> (String) arr[0],
                        arr -> (Long) arr[1]
                ));
    }

    @Query("SELECT o FROM OffreEmploi o WHERE " +
            "LOWER(o.titre) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(o.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(o.typeEmploi) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(o.nomSociete) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(o.fonctionPoste) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(o.secteurActivite) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<OffreEmploi> search(@Param("searchTerm") String searchTerm);
}