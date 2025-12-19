package service;

import dao.Stockage;
import model.CompteBancaire;

import java.util.HashMap;

public class GestionnaireComptes {
    private Map<String, CompteBancaire> comptes;
    private Stockage stockage;

    public GestionnaireComptes(Stockage stockage) {
        this.comptes = new HashMap<>();
        this.stockage = stockage;
        chargerComptes();
    }

    private void chargerComptes(){
        this.comptes = stockage.chargerComptes();
    }
}
