package ceiti.studiuindividual1;

import java.sql.Date;

public class Elev {
    private int idElev;
    private String numeElev;
    private String prenumeElev;
    private String patronimicElev;
    private Date dataNasterii;
    private String idnp;
    private String email;
    private String telefon;

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

    public int getIdElev() { return idElev; }
    public String getNumeElev() { return numeElev; }
    public String getPrenumeElev() { return prenumeElev; }
    public String getPatronimicElev() { return patronimicElev; }
    public Date getDataNasterii() { return dataNasterii; }
    public String getIdnp() { return idnp; }
    public String getEmail() { return email; }
    public String getTelefon() { return telefon; }
}
