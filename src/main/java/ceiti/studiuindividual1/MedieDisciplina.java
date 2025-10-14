package ceiti.studiuindividual1;

public class MedieDisciplina {
    private String disciplina;
    private double medie;

    public MedieDisciplina(String disciplina, double medie) {
        this.disciplina = disciplina;
        this.medie = medie;
    }

    public String getDisciplina() { return disciplina; }
    public double getMedie() { return medie; }
}
