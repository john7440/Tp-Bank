package service;

import dao.Stockage;
import exception.CompteInexistantException;
import model.CompteBancaire;
import model.Depot;

import java.util.*;
import java.util.stream.Collectors;

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

    public CompteBancaire consulterCompte(String numeroCompte) throws CompteInexistantException {
        CompteBancaire compte = comptes.get(numeroCompte);
        if (compte == null) {
            throw new CompteInexistantException("Le Compte " + numeroCompte + " n'existe pas");
        }
        return compte;
    }

    public List<CompteBancaire> rechercherParTitulaire(String nomTitulaire) {
        return comptes.values().stream().filter(c -> c.getTitulaire().toLowerCase().contains(nomTitulaire.toLowerCase())).collect(Collectors.toList());
    }

    public void effectuerDepot(String numeroCompte, double montant) throws CompteInexistantException {
        CompteBancaire compte = consulterCompte(numeroCompte);
        Depot depot = new Depot(montant, compte);
        depot.executer();
        stockage.sauvegarderOperation(depot, null);
        stockage.mettreAjourSolde(compte);
    }

    private List<CompteBancaire> listerTousLesComptes(){
        return new ArrayList<>(comptes.values());
    }

    private void chargerComptes(){
        this.comptes = stockage.chargerComptes();
    }
}
