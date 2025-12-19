package model;

public class Depot extends Operation{
    public Depot(double montant, CompteBancaire compte) {
        super(montant, compte, TypeOperation.DEPOT);
    }

    @Override
    public void executer(){
        getCompteSource().deposer(getMontant());
        getCompteSource().ajouterOperation(this);

    }
}
