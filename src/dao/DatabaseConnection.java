package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3308/tp_bank";
    private static final String USER = "bank_admin";
    private static final String PASSWORD = "B@nk2025!Secure";

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException{
        if (connection == null || connection.isClosed()){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (ClassNotFoundException e){
                throw new SQLException("Driver MySQL non trouvé", e);
            }
        }
        return connection;
    }
}
