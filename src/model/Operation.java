package model;

import java.time.LocalDateTime;

public class Operation {
    private static int compteurId = 1;
    private int id;
    private LocalDateTime date;
    private Double montant;
    private TypeOperation typeOperation;
    private CompteBancaire compteSource;
}
