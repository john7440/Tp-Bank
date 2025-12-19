package service;

import dao.Stockage;
import model.CompteBancaire;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class GestionnaireComptes {
    private Map<String, CompteBancaire> comptes;
    private Stockage stockage;

    public GestionnaireComptes(Stockage stockage) {
        this.comptes = new HashMap<>();
        this.stockage = stockage;
        chargerComptes();
    }

    private String genererNumeroCompte(){
        Random rand = new Random();
        String numero;
        do {
            int partie1 = 1000 + rand.nextInt(1000);
            int partie2 = 1000 + rand.nextInt(1000);
            numero = String.format("FR-%04d-%04d", partie1, partie2);
        } while (comptes.containsKey(numero));
        return numero;
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
