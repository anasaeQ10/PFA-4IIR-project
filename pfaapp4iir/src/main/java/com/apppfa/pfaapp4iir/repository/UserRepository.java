package com.apppfa.pfaapp4iir.repository;

import com.apppfa.pfaapp4iir.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Méthode existante
    Optional<User> findByEmail(String email);

    // Nouvelle méthode pour vérifier si un autre utilisateur a déjà cet email
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email AND u.id <> :userId")
    boolean existsByEmailAndIdNot(@Param("email") String email, @Param("userId") Long userId);
    List<User> findAllByOrderByIdAsc(); // Pour un tri par défaut
    boolean existsByEmail(@Email(message = "Email invalide") @NotBlank(message = "Email requis") String email);
    @Query("SELECT u FROM User u WHERE " +
            "LOWER(u.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<User> findByFirstNameContainingOrLastNameContainingOrEmailContaining(
            @Param("searchTerm") String searchTerm, String term, String s);
    //long countByRole(String role);
    long countByCreatedAtAfter(LocalDate date);

    @Query("SELECT COUNT(DISTINCT u) FROM User u WHERE u.lastLoginDate >= :date")
    long countActiveUsersSince(LocalDate date);
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    long countByRole(String role);
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'ADMIN'")
    long countByAdminRole();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'STUDENT'")
    long countByStudentRole();

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'RH'")
    long countByRhRole();

}
