package ceiti.studiuindividual1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class RegistruDAO {

    public static List<ElevNote> getEleviNoteByDisciplina(int idDisciplina) {
        Map<Integer, ElevNote> mapElevi = new LinkedHashMap<>();
        String query = "SELECT e.idElev, e.numeElev, e.prenumeElev, e.patronimicElev, n.nota " +
                "FROM Note n JOIN Elevi e ON n.idElev = e.idElev " +
                "WHERE n.idDisciplina = ? ORDER BY e.idElev, n.idNote";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idDisciplina);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idElev = rs.getInt("idElev");
                ElevNote elev = mapElevi.getOrDefault(idElev, new ElevNote(
                        rs.getString("numeElev"),
                        rs.getString("prenumeElev"),
                        rs.getString("patronimicElev")
                ));
                elev.addNota(rs.getDouble("nota"));
                mapElevi.put(idElev, elev);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(mapElevi.values());
    }

    public static List<String> getAllDisciplines() {
        return DisciplinaDAO.getAllDisciplines();
    }
}

