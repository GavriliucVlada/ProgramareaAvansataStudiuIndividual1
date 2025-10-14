package ceiti.studiuindividual1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDB {

    static final String URL =
            "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=ManagementEducational;"
                    + "integratedSecurity=true;trustServerCertificate=true;";


    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("✅ Conexiune realizată cu succes!");
        } catch (SQLException e) {
            System.out.println("❌ Eroare la conectarea la baza de date: " + e.getMessage());
        }
        return conn;
    }

    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("🔒 Conexiune închisă.");
            }
        } catch (SQLException e) {
            System.out.println("⚠️ Eroare la închiderea conexiunii: " + e.getMessage());
        }
    }
}
