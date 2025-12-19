package model;

import java.util.regex.Pattern;

public class CompteBancaire {
    private String numeroCompte;
    private String titulaire;
    private double solde;
    private Double plafond;
    private List<Operation> historique;

    private static final Pattern PATTERN_NUMERO_COMPTE = Pattern.compile("^FR-\\d{4}-\\d{4}$");
}
