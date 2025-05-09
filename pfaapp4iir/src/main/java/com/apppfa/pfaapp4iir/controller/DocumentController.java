package com.apppfa.pfaapp4iir.controller;

import com.apppfa.pfaapp4iir.model.Document;
import com.apppfa.pfaapp4iir.model.User;
import com.apppfa.pfaapp4iir.repository.DocumentRepository;
import com.apppfa.pfaapp4iir.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("/documents")
public class DocumentController {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/ajouter")
    public String showUploadForm(Model model) {
        model.addAttribute("document", new Document());
        return "documents/ajouter";
    }

    @PostMapping("/ajouter")
    public String uploadDocument(@RequestParam("title") String title,
                                 @RequestParam("file") MultipartFile file,
                                 @AuthenticationPrincipal UserDetails userDetails,
                                 Model model) throws IOException {

        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        if (user.isEmpty()) {
            model.addAttribute("error", "Utilisateur non trouvé.");
            return "documents/ajouter";
        }

        Document document = new Document();
        document.setTitle(title);
        document.setFileName(StringUtils.cleanPath(file.getOriginalFilename()));
        document.setFileType(file.getContentType());
        document.setContent(file.getBytes());
        document.setUser(user.orElse(null));

        documentRepository.save(document);

        return "redirect:/documents/liste";
    }

    @GetMapping("/liste")
    public String listDocuments(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        Optional<User> user = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("documents", documentRepository.findByUser(user.orElse(null)));
        return "documents/liste";
    }

    @GetMapping("/download/{id}")
    public void downloadFile(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Optional<Document> document = documentRepository.findById(id);
        if (document.isPresent()) {
            Document doc = document.get();
            response.setContentType(doc.getFileType());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + doc.getFileName() + "\"");
            response.getOutputStream().write(doc.getContent());
            response.getOutputStream().flush();
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteDocument(@PathVariable Long id) {
        documentRepository.deleteById(id);
        return "redirect:/documents/liste";
    }
}
