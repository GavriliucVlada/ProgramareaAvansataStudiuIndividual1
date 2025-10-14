package ceiti.studiuindividual1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedieDAO {

    // Obține lista de elevi (nume + prenume + patronimic) pentru ComboBox
    public static List<String> getAllElevi() {
        List<String> elevi = new ArrayList<>();
        String query = "SELECT idElev, numeElev, prenumeElev, patronimicElev FROM Elevi";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("idElev");
                String fullName = id + " - " + rs.getString("numeElev") + " " +
                        rs.getString("prenumeElev") + " " +
                        rs.getString("patronimicElev");
                elevi.add(fullName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return elevi;
    }

    // Obține mediile elevului selectat
    public static ObservableList<MedieDisciplina> getMediiByElev(int idElev) {
        ObservableList<MedieDisciplina> list = FXCollections.observableArrayList();
        String query = "SELECT d.disciplina, CAST(AVG(n.nota*1.0) AS DECIMAL(4,2)) AS medie " +
                "FROM Note n " +
                "JOIN Disciplini d ON n.idDisciplina = d.idDisciplina " +
                "WHERE n.idElev = ? " +
                "GROUP BY d.disciplina " +
                "ORDER BY d.disciplina";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idElev);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                list.add(new MedieDisciplina(rs.getString("disciplina"), rs.getDouble("medie")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
