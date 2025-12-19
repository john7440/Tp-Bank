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

            int choix = scan.nextInt();

            switch (choix) {
                case 1:
                    creerCompte();
                    break;
                case 2:
                    consulterCompte();
                    break;
            }

            }
        }

        private void creerCompte() {
            System.out.println("\n--- Création de compte ---");
            System.out.print("Nom du titulaire : ");
            String titulaire = scan.nextLine();

            System.out.print("Solde initial (0 par défaut) : ");
            double soldeInitial = lireDouble();

            try {
                CompteBancaire compte = gestionnaire.creerCompte(titulaire, soldeInitial);
                System.out.println("Compte créé avec succès!");
                System.out.println(compte);
            } catch (Exception e) {
                System.out.println("Erreur : " + e.getMessage());
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

        private void consulterCompte() {
            System.out.println("\n--- Consultation de compte ---");
            System.out.print("Numéro de compte : ");
            String numero = scan.nextLine();

            try {
               CompteBancaire compte = gestionnaire.consulterCompte(numero);
               System.out.println("\n" + compte);
            } catch (CompteInexistantException e) {
                System.out.println("Erreur : " + e.getMessage());
            }
        }
}
