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
- ├── repository/ # Interfaces JPA (DAO)
- ├── service/ # Services métier
- ├── config/ # Configuration Spring (sécurité, MVC)
- ├── resources/
- │ ├── templates/ # Fichiers HTML Thymeleaf
- │ └── static/css/ # Feuilles de style
- └── application.properties



## ✅ Fonctionnalités

- 🔐 Authentification et inscription des utilisateurs
- 📂 Gestion des documents : ajout, suppression
- 💼 Gestion des offres d’emploi
- 📊 Statistiques interactives
- 📨 gestion des envois des mails en cas de création d’un compte ou NV offre ou évènement 
- 🖥️ Tableau de bord dynamique
- 🎨 Interface claire et responsive
- 📅 Gestion des événements 

## ▶️ Lancement du projet

### Prérequis

- Java 17+
- Maven
- MySQL (MAMP,XAMP,...)
- sévice mail (https://mailtrap.io)

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

<img width="1440" alt="Screenshot 2025-06-06 at 23 15 13" src="https://github.com/user-attachments/assets/ef80a1f1-379e-48ba-ae06-94dbf8617577" />
<img width="1440" alt="Screenshot 2025-06-06 at 23 04 40" src="https://github.com/user-attachments/assets/c2c0466b-eb50-45a6-a30b-aa43c7cdb202" />
<img width="1440" alt="Screenshot 2025-06-06 at 23 03 45" src="https://github.com/user-attachments/assets/71d8db61-f503-486b-a7a0-2efcca046a27" />
<img width="1440" alt="Screenshot 2025-06-06 at 23 03 59" src="https://github.com/user-attachments/assets/ed15f4c7-4c53-4b58-832d-fe1800bafce9" />

## 👨‍💻 Auteurs

- Anas AIT EL QADI 
- Mohamed Taha IZMAR

