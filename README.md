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
