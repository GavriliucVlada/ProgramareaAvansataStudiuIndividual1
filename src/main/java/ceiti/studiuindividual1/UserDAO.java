package ceiti.studiuindividual1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class UserDAO {

    /**
     * Creează un utilizator în baza de date.
     */
    public static void createUser(String username, String password, String avatarPath,
                                  String nume, String prenume, String email, String dob) {

        // Validăm data înainte de a o folosi
        Date dateOfBirth;
        try {
            dateOfBirth = Date.valueOf(dob.trim());
        } catch (IllegalArgumentException e) {
            System.err.println("❌ Data introdusă nu este validă: " + dob);
            return;
        }

        // Validăm calea avatarului
        if (avatarPath == null || avatarPath.isEmpty()) {
            System.err.println("❌ Avatar invalid: " + avatarPath);
            return;
        }

        String sql = "INSERT INTO Users (userName, passwordUser, imagine, nume, prenume, email, DOB) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, avatarPath);
            stmt.setString(4, nume);
            stmt.setString(5, prenume);
            stmt.setString(6, email);
            stmt.setDate(7, dateOfBirth);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("✅ User creat cu succes!");
            }

        } catch (SQLException e) {
            System.err.println("❌ Eroare la inserarea userului: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Verifică login-ul fără a returna userul.
     */
    public static boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM Users WHERE userName = ? AND passwordUser = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Returnează obiectul User cu prenume și imagine.
     */
    public static User getUser(String username, String password) {
        User user = null;
        String sql = "SELECT prenume, nume, imagine FROM Users WHERE userName = ? AND passwordUser = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String prenume = rs.getString("prenume");
                String nume = rs.getString("nume");
                String imagine = rs.getString("imagine"); // calea absolută
                user = new User(prenume, nume, imagine);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }


    public static int getUserCount() {
        int totalUsers = 0;
        String sql = "SELECT TotalUsers FROM vwUserCount";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalUsers = rs.getInt("TotalUsers");
            }

        } catch (SQLException e) {
            System.err.println("❌ Eroare la preluarea numărului de useri: " + e.getMessage());
            e.printStackTrace();
        }

        return totalUsers;
    }



    public static int getElevCount() {
        int totalElevi = 0;
        String sql = "SELECT TotalElevi FROM vwElevCount";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalElevi = rs.getInt("TotalElevi");
            }

        } catch (SQLException e) {
            System.err.println("❌ Eroare la preluarea numărului de elevi: " + e.getMessage());
            e.printStackTrace();
        }

        return totalElevi;
    }



    public static int getDisciplinaCount() {
        int totalDiscipline = 0;
        String sql = "SELECT TotalDiscipline FROM vwDisciplinaCount";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalDiscipline = rs.getInt("TotalDiscipline");
            }

        } catch (SQLException e) {
            System.err.println("❌ Eroare la preluarea numărului de discipline: " + e.getMessage());
            e.printStackTrace();
        }

        return totalDiscipline;
    }




    public static int getNoteCount() {
        int totalNotes = 0;
        String sql = "SELECT TotalNotes FROM vwNoteCount";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                totalNotes = rs.getInt("TotalNotes");
            }

        } catch (SQLException e) {
            System.err.println("❌ Eroare la preluarea numărului total de note: " + e.getMessage());
            e.printStackTrace();
        }

        return totalNotes;
    }
}
