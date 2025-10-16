package ceiti.studiuindividual1;

import java.sql.Date;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class Elev {
    private int idElev;
    private String numeElev;
    private String prenumeElev;
    private String patronimicElev;
    private Date dataNasterii;
    private String idnp;
    private String email;
    private String telefon;

    // ✅ Proprietate pentru checkbox (prezență)
    private BooleanProperty present = new SimpleBooleanProperty(false);

    public Elev(int idElev, String numeElev, String prenumeElev, String patronimicElev,
                Date dataNasterii, String idnp, String email, String telefon) {
        this.idElev = idElev;
        this.numeElev = numeElev;
        this.prenumeElev = prenumeElev;
        this.patronimicElev = patronimicElev;
        this.dataNasterii = dataNasterii;
        this.idnp = idnp;
        this.email = email;
        this.telefon = telefon;
    }

    // 🔹 Getteri clasici
    public int getIdElev() { return idElev; }
    public String getNumeElev() { return numeElev; }
    public String getPrenumeElev() { return prenumeElev; }
    public String getPatronimicElev() { return patronimicElev; }
    public Date getDataNasterii() { return dataNasterii; }
    public String getIdnp() { return idnp; }
    public String getEmail() { return email; }
    public String getTelefon() { return telefon; }

    // ✅ Proprietate pentru tabelul de prezență
    public boolean isPresent() {
        return present.get();
    }

    public void setPresent(boolean value) {
        present.set(value);
    }

    public BooleanProperty presentProperty() {
        return present;
    }
}
