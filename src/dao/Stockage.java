package dao;

import model.CompteBancaire;
import model.Operation;

import java.util.Map;

public interface Stockage {
    void sauvegarderCompte(CompteBancaire compte);
    Map<String, CompteBancaire> chargerComptes();
    void sauvegarderOperation(Operation operation? String numeroCompteDestination);
    void mettreAjourSolde(CompteBancaire compte);
    void mettreAjourPlafond(CompteBancaire compte);
}
