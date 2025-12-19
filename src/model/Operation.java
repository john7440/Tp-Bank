package model;

import java.time.LocalDateTime;

public abstract class Operation {
    private static int compteurId = 1;
    private int id;
    private LocalDateTime date;
    private Double montant;
    private TypeOperation typeOperation;
    private CompteBancaire compteSource;

    //Constructeurs
    public Operation(double montant, CompteBancaire compteSource, TypeOperation type) {
        this.id = compteurId++;
        this.date = LocalDateTime.now();
        this.montant = montant;
        this.compteSource = compteSource;
        this.typeOperation = type;
    }

    public abstract void executer() throws Exception;

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static int getCompteurId() {
        return compteurId;
    }

    public static void setCompteurId(int compteurId) {
        Operation.compteurId = compteurId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public TypeOperation getTypeOperation() {
        return typeOperation;
    }

    public CompteBancaire getCompteSource() {
        return compteSource;
    }

    public void setCompteSource(CompteBancaire compteSource) {
        this.compteSource = compteSource;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s - %.2f€ - Compte: %s",
                date.toString(), typeOperation, montant, compteSource.getNumeroCompte());
    }
}
