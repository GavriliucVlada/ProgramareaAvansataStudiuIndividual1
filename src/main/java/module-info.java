module ceiti.studiuindividual1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens ceiti.studiuindividual1 to javafx.fxml;
    exports ceiti.studiuindividual1;
}