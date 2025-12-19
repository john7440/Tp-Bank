package model;

import exception.DepassementPlafondException;
import exception.SoldeInsuffisantException;

public class Virement extends Operation{
    private CompteBancaire compteDestination;

    public Virement(double montant, CompteBancaire compteSource,CompteBancaire compteDestination) {
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
}
