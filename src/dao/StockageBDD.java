package dao;

import model.CompteBancaire;
import model.Operation;

import java.sql.*;
import java.util.HashMap;
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

    private void chargerHistoriqueCompte(CompteBancaire compte) {
        String sql = "SELECT * FROM operation WHERE c_numero_compte_source = ? ORDER BY o_date";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, compte.getNumeroCompte());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Reconstruction des opérations pour l'historique
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors du chargement de l'historique: " + e.getMessage());
        }
    }

    @Override
    public Map<String, CompteBancaire> chargerComptes() {
        Map<String, CompteBancaire> comptes = new HashMap<>();
        String sql = "SELECT * FROM compte_bancaire";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)){

            while (rs.next()){
                String numeroCompte = rs.getString("c_numero_compte");
                String titulaire = rs.getString("c_titulaire");
                double solde = rs.getDouble("c_solde");

                CompteBancaire compte = new CompteBancaire(numeroCompte, titulaire, solde);

                Double plafond = rs.getDouble("c_plafond");
                if (!rs.wasNull()){
                    compte.setPlafond(plafond);
                }

                chargerHistoriqueCompte(compte);
                comptes.put(numeroCompte, compte);
            }
        } catch (SQLException e){
            System.err.println("Erreur lors du chargement des comptes: " + e.getMessage());
        }

        return comptes;
    }


    @Override
    public void sauvegarderOperation(Operation operation, String numeroCompteDestination) {
        String sql = "INSERT INTO operation (o_date, o_montant, o_type_operation, c_numero_compte_source,+" +
                " c_numero_compte_destination) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setTimestamp(1, Timestamp.valueOf(operation.getDate()));
            statement.setDouble(2, operation.getMontant());
            statement.setString(3, operation.getTypeOperation().name());
            statement.setString(4, operation.getCompteSource().getNumeroCompte());

            if (numeroCompteDestination != null){
                statement.setString(5, numeroCompteDestination);
            } else {
                statement.setNull(5, Types.VARCHAR);
            }

            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur lors de la sauvegarde de l'opération: " + e.getMessage());
        }

    }

    @Override
    public void mettreAjourSolde(CompteBancaire compte) {
        String sql = "UPDATE compte_bancaire SET c_solde = ? WHERE c_numero_compte = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            statement.setDouble(1, compte.getSolde());
            statement.setString(2, compte.getNumeroCompte());
            statement.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du solde: " + e.getMessage());
        }
    }

    @Override
    public void mettreAJourPlafond(CompteBancaire compte) {
        String sql = "UPDATE compte_bancaire SET c_plafond = ? WHERE c_numero_compte = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){

            if (compte.getPlafond() != null) {
                statement.setDouble(1, compte.getPlafond());
            } else {
                statement.setNull(1, Types.DECIMAL);
            }
            statement.setString(2, compte.getNumeroCompte());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erreur lors de la mise à jour du plafond: " + e.getMessage());
        }
    }
}
