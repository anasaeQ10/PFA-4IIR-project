package com.apppfa.pfaapp4iir.service;

import com.apppfa.pfaapp4iir.dto.UserForm;
import com.apppfa.pfaapp4iir.model.User;
import com.apppfa.pfaapp4iir.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User getCurrentUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé"));
    }

    public void updateUserProfile(Authentication authentication, UserForm userForm) {
        User user = getCurrentUser(authentication);

        user.setFirstName(userForm.getFirstName());
        user.setLastName(userForm.getLastName());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setAddress(userForm.getAddress());

        userRepository.save(user);
    }
    public void registerUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setEnabled(true);
        userRepository.save(user);
    }

    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email incorrect"));

        // Force le rôle à avoir le préfixe ROLE_
        String role = user.getRole().startsWith("ROLE_") ? user.getRole() : "ROLE_" + user.getRole();

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(), // Doit être true
                true, true, true, // Compte non expiré/verrouillé
                Collections.singletonList(new SimpleGrantedAuthority(role))
        );
    }

    public void saveUser(User user) {
        // Vérification supplémentaire optionnelle
        if (user.getEmail() != null) {
            user.setEmail(user.getEmail().toLowerCase());
        }
        userRepository.save(user);
    }
    /*
    public void updateUser(User user) {
        // Vous pouvez ajouter ici une logique métier supplémentaire si nécessaire
        userRepository.save(user);
    }
*/
    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        userRepository.save(existingUser);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public boolean existsByEmailAndIdNot(String email, Long id) {
        return userRepository.existsByEmailAndIdNot(email, id);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Ou une méthode personnalisée si besoin de filtres
    }
    /*
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        // Ajoutez ici toute logique métier avant suppression
        userRepository.delete(user);
    }*/

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));
    }
/*
    public void updateUser(User user) {
        User existingUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé"));

        // Mettez à jour les champs nécessaires
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setPhoneNumber(user.getPhoneNumber());
        existingUser.setAddress(user.getAddress());

        userRepository.save(existingUser);
    }
    */

    public List<User> searchUsers(String searchTerm) {
        return userRepository.findByFirstNameContainingOrLastNameContainingOrEmailContaining(
                searchTerm, searchTerm, searchTerm);
    }
}