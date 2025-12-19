package model;

import exception.DepassementPlafondException;
import exception.SoldeInsuffisantException;

public class Virement extends Operation {
    private final CompteBancaire compteDestination;

    public Virement(double montant, CompteBancaire compteSource, CompteBancaire compteDestination) {
        super(montant, compteSource, TypeOperation.VIREMENT);
        this.compteDestination = compteDestination;
    }

    @Override
    public void executer() throws SoldeInsuffisantException, DepassementPlafondException {
        getCompteSource().retirer(getMontant());
        compteDestination.deposer(getMontant());
        getCompteSource().ajouterOperation(this);
        compteDestination.ajouterOperation(this);
    }

    public CompteBancaire getCompteDestination() {
        return compteDestination;
    }

    @Override
    public String toString() {
        return String.format("[%s] VIREMENT - %.2f€ - De: %s vers: %s",
                getDate().toString(), getMontant(),
                getCompteSource().getNumeroCompte(),
                compteDestination.getNumeroCompte());
    }
}

