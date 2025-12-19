package ui;

import service.GestionnaireComptes;

import java.util.Scanner;

public class MenuConsole {
    private GestionnaireComptes gestionnaire;
    private Scanner scan;

    public MenuConsole(GestionnaireComptes gestionnaire) {
        this.gestionnaire = gestionnaire;
        this.scan = new Scanner(System.in);
    }
}
