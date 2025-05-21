# PFA - Application Web de Gestion (4IIR)

Ce projet est une application web développée dans le cadre du Projet de Fin d'Année (PFA) de la filière 4IIR à l’EMSI. Il s'agit d'une plateforme de gestion d’utilisateurs, documents, statistiques et offres d'emploi avec une interface web conviviale.

## 🛠 Technologies utilisées

- Java 17
- Spring Boot
- Spring Security
- Thymeleaf
- Maven
- HTML/CSS
- MySQL

## 📁 Structure du projet

- pfaapp4iir/
- ├── controller/ # Contrôleurs Spring MVC
- ├── model/ # Entités JPA
- ├── repository/ # Interfaces JPA
- ├── service/ # Services métier
- ├── config/ # Configuration Spring (sécurité, MVC)
- ├── resources/
- │ ├── templates/ # Fichiers HTML Thymeleaf
- │ └── static/css/ # Feuilles de style
- └── application.properties



## ✅ Fonctionnalités

- Authentification et inscription des utilisateurs
- Tableau de bord personnalisé
- Gestion des documents (ajout, suppression, visualisation)
- Affichage et gestion des offres d’emploi
- Visualisation des statistiques
- Design responsive

## ▶️ Lancement du projet

### Prérequis

- Java 17+
- Maven
- MySQL

### Étapes

1. Cloner le dépôt :
   ```bash
   git clone https://github.com/anasaeQ10/PFA-4IIR-project.git
   cd PFA-4IIR-project/pfaapp4iir
2. Configurer la base de données dans "src/main/resources/application.properties" :

   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/nom_de_la_base
   spring.datasource.username=utilisateur
   spring.datasource.password=mot_de_passe

3. Lancer l’application :
   ```bash
   ./mvnw spring-boot:run

4. Accéder à l'application :
   ```bash 
   http://localhost:8080



## 📸 Captures d'écran

<img width="1440" alt="Screenshot 2025-05-21 at 20 01 51" src="https://github.com/user-attachments/assets/93bd5d84-dd3d-4113-bed0-377093782762" />

<img width="1440" alt="Screenshot 2025-05-21 at 20 02 57" src="https://github.com/user-attachments/assets/aaeaaabf-e159-40fa-90f9-5b888ac397f8" />

<img width="1440" alt="Screenshot 2025-05-21 at 20 03 26" src="https://github.com/user-attachments/assets/04520d9e-527a-4c44-9cc4-78ec1c6e65c1" />


## 👨‍💻 Auteurs

- Anas AIT EL QADI 
- Mohamed Taha IZMAR

