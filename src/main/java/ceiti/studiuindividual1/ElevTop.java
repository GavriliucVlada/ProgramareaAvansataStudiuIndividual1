package ceiti.studiuindividual1;

public class ElevTop {
    private String nume;
    private String prenume;
    private String patronimic;
    private double medie;

    public ElevTop(String nume, String prenume, String patronimic, double medie) {
        this.nume = nume;
        this.prenume = prenume;
        this.patronimic = patronimic;
        this.medie = medie;
    }

    public String getNume() { return nume; }
    public String getPrenume() { return prenume; }
    public String getPatronimic() { return patronimic; }
    public double getMedie() { return medie; }
}
