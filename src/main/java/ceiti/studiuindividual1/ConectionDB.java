package ceiti.studiuindividual1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    // Datele de conexiune
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=NumeleBazei";
    private static final String USER = "username";
    private static final String PASSWORD = "parola";

    // Metodă pentru a obține conexiunea
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexiune realizată cu succes!");
        } catch (SQLException e) {
            System.out.println("Eroare la conectarea la baza de date: " + e.getMessage());
        }
        return conn;
    }

    // Optional: metodă pentru a închide conexiunea
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexiune închisă.");
            }
        } catch (SQLException e) {
            System.out.println("Eroare la închiderea conexiunii: " + e.getMessage());
        }
    }
}
