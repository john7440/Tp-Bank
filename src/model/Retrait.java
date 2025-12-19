package model;

import exception.DepassementPlafondException;
import exception.SoldeInsuffisantException;

public class Retrait extends Operation{

    public Retrait(double montant, CompteBancaire compte){
        super(montant, compte, TypeOperation.RETRAIT);
    }

    @Override
    public void executer() throws SoldeInsuffisantException, DepassementPlafondException{
        getCompteSource().retirer(getMontant());
        getCompteSource().ajouterOperation(this);
    }
}
