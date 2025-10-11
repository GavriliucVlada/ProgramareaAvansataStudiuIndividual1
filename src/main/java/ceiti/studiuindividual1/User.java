package ceiti.studiuindividual1;

public class User {
    private String prenume;
    private String nume;
    private String imagine; // calea absoluta

    public User(String prenume, String nume, String imagine) {
        this.prenume = prenume;
        this.nume = nume;
        this.imagine = imagine;
    }

    public String getPrenume() {
        return prenume;
    }

    public String getNume(){
        return nume;
    }

    public String getImagine() {
        return imagine;
    }
}
