package com.apppfa.pfaapp4iir.repository;

import com.apppfa.pfaapp4iir.model.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.time.LocalDate;
import java.util.List;

public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {
    List<UserLogin> findTop5ByOrderByLoginDateDesc();
    long countByLoginDate(LocalDate date);

    @Query("SELECT COUNT(DISTINCT ul.user) FROM UserLogin ul WHERE ul.loginDate >= :date")
    long countDistinctUsersSince(LocalDate date);

    default long countDistinctUsersThisWeek() {
        return countDistinctUsersSince(LocalDate.now().minusDays(7));
    }

    Object countByLoginDateAfter(LocalDate localDate);
}