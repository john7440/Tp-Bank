# Tp-Bank - Application de gestion bancaire en console (Java / MariaDB)

#### 1. Présentation

C'est une application Java en mode console permettant à un consiller bancaire de gérer des clients et leurs opérations bancaires (dépots, retraits et virements).
L’application s’appuie sur une base de données MariaDB pour assurer la persistance des données (comptes et opérations).

Foncionnalités principales:
- Création et consultation de comptes bancaires
- Dépôts, retraits et virements entre comptes
- Historique des opérations du compte
- Gestion des plafonds par compte
- Gestion d'exceptions (compte inexistant, solde insuffisant et dépassement de plafond)

#### 2. Prérequis
2.1 Logiciels nécessaires
- Java JDK 8 ou supérieur
- MariaDB (serveur de base de données)
- Un IDE Java (optionnel)
- Le driver JDBC MariaDB : mariadb-java-client-2.3.0.jar (placé dans un dossier lib/)

2.2 Base de données
- Serveur MariaDB démarré en local sur le port 3306 (par défaut)
- Droits suffisants pour créer une base, un utilisateur et des tables.

#### 3. Installation de la base de données
3.1 Création: 
Connectez-vous à MariaDB avec un compte administrateur:

```bash
CREATE DATABASE IF NOT EXISTS tp_bank CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

CREATE USER IF NOT EXISTS 'bank_admin'@'localhost' IDENTIFIED BY 'B@nk2025!Secure';

GRANT ALL PRIVILEGES ON tp_bank.* TO 'bank_admin'@'localhost';

FLUSH PRIVILEGES;

USE tp_bank;

CREATE TABLE compte_bancaire(
   c_numero_compte VARCHAR(50),
   c_titulaire VARCHAR(50) NOT NULL,
   c_solde DECIMAL(15,2) NOT NULL DEFAULT 0,
   c_plafond DECIMAL(15,2),
   PRIMARY KEY(c_numero_compte)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE operation(
   Id_operation INT AUTO_INCREMENT,
   o_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
   o_montant DECIMAL(15,2) NOT NULL,
   o_type_operation VARCHAR(20) NOT NULL,
   c_numero_compte_source VARCHAR(50) NOT NULL,
   c_numero_compte_destination VARCHAR(50),
   PRIMARY KEY(Id_operation),
   FOREIGN KEY(c_numero_compte_source) REFERENCES compte_bancaire(c_numero_compte) ON DELETE CASCADE,
   FOREIGN KEY(c_numero_compte_destination) REFERENCES compte_bancaire(c_numero_compte) ON DELETE SET NULL,
   CHECK(o_type_operation IN ('DEPOT', 'RETRAIT', 'VIREMENT')),
   CHECK(o_montant > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE INDEX idx_operation_date ON operation(o_date);
CREATE INDEX idx_operation_type ON operation(o_type_operation);
CREATE INDEX idx_compte_source ON operation(c_numero_compte_source);
```

Ces tables correspondent au MCD et au modèle de classes de l’application (entités compte_bancaire et operation, relations « effectuer » et « recevoir »).

3.2 Données d’exemple (optionnel)

Vous pouvez pré-remplir la base avec quelques comptes et opérations de test pour vos démonstrations:
```bash
-- Comptes d’exemple
INSERT INTO compte_bancaire (c_numero_compte, c_titulaire, c_solde, c_plafond) VALUES
('FR-7630-0001', 'Jean Dupont', 2500.00, 1000.00),
('FR-7630-0002', 'Marie Martin', 5800.50, 1500.00),
('FR-7630-0003', 'Pierre Durand', 1200.75, 800.00),
('FR-7630-0004', 'Sophie Bernard', 12500.00, 2000.00);
```

#### 4. Structure du projet
Packages principaux:
- ```model```: objets métier (compte, opérations)
- ```exception```: exceptions métiers pour les cas d’erreur (compte inexistant, solde insuffisant, dépassement plafond)
- ```service```: logique métier (création de comptes, dépôts, retraits, virements, plafonds, historique)
- ```dao```: accès à la base de données
- ```ui```: interface console (menus, saisies, affichages)
- ```main```: point d’entrée de l’application



