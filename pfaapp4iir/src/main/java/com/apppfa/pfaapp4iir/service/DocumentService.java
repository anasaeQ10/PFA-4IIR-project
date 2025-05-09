package com.apppfa.pfaapp4iir.service;

import com.apppfa.pfaapp4iir.model.Document;
import com.apppfa.pfaapp4iir.model.User;
import com.apppfa.pfaapp4iir.repository.DocumentRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DocumentService {
    private final DocumentRepository documentRepository;

    public DocumentService(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public void save(Document document) {
        documentRepository.save(document);
    }

    public List<Document> getUserDocuments(User user) {
        return documentRepository.findByUser(user);
    }

    public void deleteById(Long id) {
        documentRepository.deleteById(id);
    }

    public Document findById(Long id) {
        return documentRepository.findById(id).orElse(null);
    }
}
