package dao;

import model.CompteBancaire;
import model.Operation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Map;

public class StockageBDD implements Stockage{

    @Override
    public void sauvegarderCompte(CompteBancaire compte) {
        String sql = "INSERT INTO compte_bancaire (c_numero_compte, c_titulaire, c_solde, c_plafond) VALUES (?, ?, ?, ?)";

        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, compte.getNumeroCompte());
            statement.setString(2, compte.getTitulaire());
            statement.setDouble(3, compte.getSolde());
            if (compte.getPlafond() != null) {
                statement.setDouble(4, compte.getPlafond());
            } else {
                statement.setNull(4, Types.DECIMAL);
            }
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde du compte: " + e.getMessage());
        }
    }

    @Override
    public Map<String, CompteBancaire> chargerComptes() {
        return Map.of();
    }

    @Override
    public void sauvegarderOperation(Operation operation) {

    }

    @Override
    public void mettreAjourSolde(CompteBancaire compte) {

    }

    @Override
    public void mettreAjourPlafond(CompteBancaire compte) {

    }
}
