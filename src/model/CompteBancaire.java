package model;

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
