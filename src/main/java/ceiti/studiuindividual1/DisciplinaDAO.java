package ceiti.studiuindividual1;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {

    public static List<String> getAllDisciplines() {
        List<String> disciplines = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT disciplina FROM Disciplini")) {

            while (rs.next()) {
                disciplines.add(rs.getString("disciplina"));
            }

        } catch (SQLException e) {
            System.out.println("❌ Eroare la extragerea disciplinelor: " + e.getMessage());
        }

        return disciplines;
    }
}
