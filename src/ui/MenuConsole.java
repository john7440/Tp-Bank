package ui;

import service.GestionnaireComptes;
import model.CompteBancaire;
import model.Operation;
import exception.*;
import java.util.List;

import java.util.Scanner;

public class MenuConsole {
    private GestionnaireComptes gestionnaire;
    private Scanner scan;

    public MenuConsole(GestionnaireComptes gestionnaire) {
        this.gestionnaire = gestionnaire;
        this.scan = new Scanner(System.in);
    }

    public void afficherMenuPrincipal() {
        boolean continuer = true;

        while (continuer) {
            System.out.println("\n=== Gestion Bancaire - Menu principal: ===");
            System.out.println("1. Créer un compte");
            System.out.println("2. Consulter un compte");
            System.out.println("3. Effectuer un dépot");
            System.out.println("4. Effectuer un retrait");
            System.out.println("5. Effectuer un virement");
            System.out.println("6. Consulter l'historique");
            System.out.println("7. Gérer le plafond d'un compte");
            System.out.println("8. Lister tous les comptes");
            System.out.println("0. Quitter");
            System.out.println("Votre choix: ");

            int choix = lireEntier();

            switch (choix) {
                case 1:
                    creerCompte();
                    break;
                case 2:
                    consulterCompte();
                    break;
                case 3:
                    effectuerDepot();
                    break;
                case 4:
                    effectuerRetrait();
                    break;
                case 5:
                    effectuerVirement();
                    break;
                case 6:
                    consulterHistorique();
                    break;
                case 7:
                    gererPlafond();
                    break;
                case 8:
                    listerComptes();
                    break;
                case 0:
                    continuer = false;
                    System.out.println("Vous quittez le programme! Au revoir!");
                    break;
                default:
                    System.out.println("Choix invalide!");
            }

            }
        }

        private void creerCompte() {
            System.out.println("\n--- Création de compte ---");
            System.out.print("Nom du titulaire: ");
            String titulaire = scan.nextLine();

            System.out.print("Solde initial (0 par défaut): ");
            double soldeInitial = lireDouble();

            try {
                CompteBancaire compte = gestionnaire.creerCompte(titulaire, soldeInitial);
                System.out.println("Compte créé avec succès!");
                System.out.println(compte);
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }

        private void consulterCompte() {
            System.out.println("\n--- Consultation de compte ---");
            System.out.print("Numéro de compte: ");
            String numero = scan.nextLine();

            try {
                CompteBancaire compte = gestionnaire.consulterCompte(numero);
                System.out.println("\n" + compte);
            } catch (CompteInexistantException e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }

        private void effectuerDepot() {
            System.out.println("\n--- Dépôt ---");
            System.out.print("Numéro de compte: ");
            String numero = scan.nextLine();

            System.out.print("Montant à déposer: ");
            double montant = lireDouble();

            try {
                gestionnaire.effectuerDepot(numero, montant);
                CompteBancaire compte = gestionnaire.consulterCompte(numero);
                System.out.println("Dépôt effectué ! Nouveau solde : " + compte.getSolde() + "€");
            } catch (Exception e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }

        private void effectuerRetrait(){
            System.out.println("\n--- Retrait ---");
            System.out.print("Numéro de compte: ");
            String numero = scan.nextLine();

            System.out.print("Montant à retirer: ");
            double montant = lireDouble();

            try {
                gestionnaire.effectuerRetrait(numero, montant);
                CompteBancaire compte = gestionnaire.consulterCompte(numero);
                System.out.println("Retrait effectué ! Nouveau solde: " + compte.getSolde() + " €");
            } catch (SoldeInsuffisantException | DepassementPlafondException | CompteInexistantException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }

        private void effectuerVirement() {
            System.out.println("\n--- Virement ---");
            System.out.print("Compte source : ");
            String source = scan.nextLine();

            System.out.print("Compte destination : ");
            String destination = scan.nextLine();

            System.out.print("Montant : ");
            double montant = lireDouble();

            try {
                gestionnaire.effectuerVirement(source, destination, montant);
                System.out.println("Virement effectué avec succès !");
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }

        private void consulterHistorique() {
            System.out.println("\n--- Historique des opérations ---");
            System.out.print("Numéro de compte: ");
            String numero = scan.nextLine();

            try {
                List<Operation> historique = gestionnaire.consulterHistorique(numero);
                if (historique.isEmpty()) {
                    System.out.println("Aucune opération enregistrée!");
                } else {
                    System.out.println("\nHistorique :");
                    historique.forEach(System.out::println);
                }
            } catch (CompteInexistantException e) {
                System.out.println("Erreur" + e.getMessage());
            }
        }

        private void gererPlafond() {
            System.out.println("\n--- Gestion du plafond ---");
            System.out.print("Numéro de compte: ");
            String numero = scan.nextLine();

            System.out.print("Nouveau plafond (0 pour désactiver): ");
            double plafond = lireDouble();

            try {
                gestionnaire.definirPlafond(numero, plafond > 0 ? plafond : null);
                System.out.println("Plafond mis à jour!");
            } catch (CompteInexistantException e) {
                System.out.println("Erreur: " + e.getMessage());
            }
        }

        private void listerComptes() {
            System.out.println("\n--- Liste des comptes ---");
            List<CompteBancaire> comptes = gestionnaire.listerTousLesComptes();

            if (comptes.isEmpty()) {
                System.out.println("Aucun compte enregistré.");
            } else {
                comptes.forEach(System.out::println);
            }
        }

        private double lireDouble() {
            while(!scan.hasNextDouble()) {
                scan.next();
                System.out.print("Veuillez entrer un montant valide: ");
            }
            double valeur = scan.nextDouble();
            scan.nextLine();
            return valeur;
        }

        private int lireEntier(){
            while(!scan.hasNextInt()) {
                scan.next();
                System.out.print("Veuillez entrer un nombre valide: ");
            }
            int valeur = scan.nextInt();
            scan.nextLine();
            return valeur;
        }

}
