package com.apppfa.pfaapp4iir.repository;

import com.apppfa.pfaapp4iir.model.Document;
import com.apppfa.pfaapp4iir.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    List<Document> findByUser(User user);

}
