package ceiti.studiuindividual1;

public class AttendanceReport {
    private String studentName;
    private long daysPresent;
    private long daysAbsent;

    public AttendanceReport(String studentName, long daysPresent, long daysAbsent) {
        this.studentName = studentName;
        this.daysPresent = daysPresent;
        this.daysAbsent = daysAbsent;
    }

    public String getStudentName() { return studentName; }
    public long getDaysPresent() { return daysPresent; }
    public long getDaysAbsent() { return daysAbsent; }
}
