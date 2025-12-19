package model;

import exception.DepassementPlafondException;
import exception.SoldeInsuffisantException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CompteBancaire {
    private String numeroCompte;
    private String titulaire;
    private double solde;
    private Double plafond;
    private List<Operation> historique;

    private static final Pattern PATTERN_NUMERO_COMPTE = Pattern.compile("^FR-\\d{4}-\\d{4}$");

    //Constructeur
    public CompteBancaire(String numeroCompte, String titulaire, double soldeInitial) {
        if (!validerNumeroCompte(numeroCompte)) {
            throw new IllegalArgumentException("Format de numéro de compte invalide! Attendu: FR-XXXX-XXXX");
        }
        if (soldeInitial < 0) {
            throw new IllegalArgumentException("Le solde initial ne peut pas être négatif!");
        }
        this.numeroCompte = numeroCompte;
        this.titulaire = titulaire;
        this.solde = soldeInitial;
        this.historique = new ArrayList<>();
    }

    //Méthodes
    public static boolean validerNumeroCompte(String numero) {
        return PATTERN_NUMERO_COMPTE.matcher(numero).matches();
    }

    public synchronized void deposer(double montant) {
        if (montant < 0) {
            throw new IllegalArgumentException("Le montant doit être positif");
        }
        this.solde += montant;
    }

    public synchronized void retirer(double montant) throws SoldeInsuffisantException, DepassementPlafondException {
        if (montant <= 0){
            throw new IllegalArgumentException("Le montant doit être positif");
        }
        if (this.solde <= montant){
            throw new DepassementPlafondException("Solde insuffisant! Solde actuel: " + this.solde + " €");
        }
        if (plafond != null && montant > plafond){
            throw new DepassementPlafondException("Montant supérieur au plafond: " + plafond + " €");
        }
        this.solde -= montant;
    }
    
    public void ajouterOperation(Operation operation){
        this.historique.add(operation);
    }

    //Getters et Setters
    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getTitulaire() {
        return titulaire;
    }

    public void setTitulaire(String titulaire) {
        this.titulaire = titulaire;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public Double getPlafond() {
        return plafond;
    }

    public void setPlafond(Double plafond) {
        this.plafond = plafond;
    }

    public List<Operation> getHistorique() {
        return new ArrayList<>(historique);
    }

    @Override
    public String toString() {
        return String.format("Compte: %s | Titulaire: %s | Solde: %.2f€ | Plafond: %s",
                numeroCompte, titulaire, solde, plafond != null ? plafond + "€" : "Non défini");
    }
}
