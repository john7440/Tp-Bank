package service;

import dao.Stockage;
import model.CompteBancaire;

import java.util.HashMap;
import java.util.Map;

public class GestionnaireComptes {
    private Map<String, CompteBancaire> comptes;
    private Stockage stockage;

    public GestionnaireComptes(Stockage stockage) {
        this.comptes = new HashMap<>();
        this.stockage = stockage;
        chargerComptes();
    }

    public CompteBancaire creerCompte(String titulaire, double soldeInitial) {
        String numeroCompte = genererNumeroCompte();
        CompteBancaire compte = new CompteBancaire(numeroCompte, titulaire, soldeInitial);
        comptes.put(numeroCompte, compte);
        stockage.sauvegarderCompte(compte);
        return compte;
    }

    private void chargerComptes(){
        this.comptes = stockage.chargerComptes();
    }
}
