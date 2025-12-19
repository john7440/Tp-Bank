package main;

import dao.DatabaseConnection;
import dao.StockageBDD;
import service.GestionnaireComptes;
import ui.MenuConsole;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("Bienvenue");

        try{
            StockageBDD stockage = new StockageBDD();

            GestionnaireComptes gestionnaire = new GestionnaireComptes(stockage);

            MenuConsole menu = new MenuConsole(gestionnaire);
            menu.afficherMenuPrincipal();
        } catch (Exception e) {
            System.err.println("Erreur lors du démarrage: " + e.getMessage());
            e.printStackTrace();
        } finally {
            DatabaseConnection.closeConnection();
        }
    }
}