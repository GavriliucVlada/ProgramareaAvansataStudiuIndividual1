package ceiti.studiuindividual1;

import java.util.ArrayList;
import java.util.List;

public class ElevNote {
    private String nume;
    private String prenume;
    private String patronimic;
    private List<Double> note;

    public ElevNote(String nume, String prenume, String patronimic) {
        this.nume = nume;
        this.prenume = prenume;
        this.patronimic = patronimic;
        this.note = new ArrayList<>();
    }

    // Getters
    public String getNume() { return nume; }
    public String getPrenume() { return prenume; }
    public String getPatronimic() { return patronimic; }
    public List<Double> getNote() { return note; }

    // Adaugă nota
    public void addNota(double nota) { note.add(nota); }
}

