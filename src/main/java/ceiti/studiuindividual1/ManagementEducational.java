package ceiti.studiuindividual1;


import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.PieChart;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;



public class ManagementEducational extends BorderPane {

    int numarUseri = UserDAO.getUserCount();
    int numarElevi = UserDAO.getElevCount();
    int numarDiscipline = UserDAO.getDisciplinaCount();
    int numarNote = UserDAO.getNoteCount();

    private GridPane dashboardPane;

    private User currentUser;

    public ManagementEducational(User user) {
        this.currentUser = user;
        initUI();
    }

    private void initUI() {
        VBox leftPanel = new VBox(15);
        leftPanel.setPadding(new Insets(20));
        leftPanel.setPrefWidth(250);
        leftPanel.setStyle("-fx-background-color: rgba(54,56,68,1);");

        StackPane topLeftBox = new StackPane();
        topLeftBox.setPrefSize(250, 40);
        topLeftBox.setStyle("-fx-background-color: rgba(212,115,122,1);");

        Label lblSchooling = new Label("Schooling");
        lblSchooling.setTextFill(Color.WHITE);
        lblSchooling.setFont(Font.font("Roboto Mono", 18));
        topLeftBox.getChildren().add(lblSchooling);

        BorderPane topBar = new BorderPane();
        topBar.setPrefHeight(40);
        topBar.setStyle("-fx-background-color: white;");

        HBox rightBox = new HBox(10);
        rightBox.setAlignment(Pos.CENTER_RIGHT);
        rightBox.setPadding(new Insets(0, 10, 0, 0));

        Label lblUser = new Label(currentUser.getPrenume());
        Label lblName = new Label(currentUser.getNume());
        lblName.setFont(Font.font("Roboto Mono", 12));
        lblName.setTextFill(Color.DARKGRAY);
        lblUser.setFont(Font.font("Roboto Mono", 12));
        lblUser.setTextFill(Color.DARKGRAY);

        ImageView avatarView = new ImageView(new Image(currentUser.getImagine()));
        avatarView.setFitWidth(28);
        avatarView.setFitHeight(28);

        rightBox.getChildren().addAll(lblUser, lblName, avatarView);
        topBar.setRight(rightBox);

        VBox mainPanel = new VBox(20);
        mainPanel.setStyle("-fx-background-color: rgba(242,242,242,1);");
        mainPanel.setPadding(new Insets(20, 20, 20, 20));

        dashboardPane = new GridPane();
        AnchorPane container1 = new AnchorPane();
        VBox container2 = new VBox(dashboardPane);

        container1.setMaxSize(700, 600);
        container1.setPrefSize(700, 600);
        container1.setMinSize(700, 600);

        HBox statsBox = new HBox(40);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setPadding(new Insets(20, 40, 20, 40));
        statsBox.setStyle("-fx-background-color: white;");
        statsBox.setPrefHeight(120);

        VBox cardStudents = createStatCard("/images/hat.png", String.valueOf(numarElevi), "Total Students", "#398585");
        VBox cardEmployees = createStatCard("/images/person.png", String.valueOf(numarUseri), "Total Employees", "#df5b3a");
        VBox cardSubjects = createStatCard("/images/book.png", String.valueOf(numarDiscipline), "Total Subjects", "#eebf35");
        VBox cardHolidays = createStatCard("/images/calendar.png", String.valueOf(numarNote), "Total Grades", "#008fdc");

        statsBox.getChildren().addAll(cardStudents, cardEmployees, cardSubjects, cardHolidays);


        dashboardPane.setHgap(10);
        dashboardPane.setVgap(10);
        dashboardPane.setAlignment(Pos.CENTER_RIGHT);
        dashboardPane.setPadding(new Insets(0, 20, 0, 0));
        dashboardPane.setStyle("-fx-background-color: transparent;");

        Node topStudentsCard = createActionCard("Top Students", "/images/addS.png");
        topStudentsCard.setOnMouseClicked(e -> {
            AnchorPane topPane = createTopEleviPane();

            container1.getChildren().clear();
            container1.getChildren().setAll(topPane);
        });
        dashboardPane.add(topStudentsCard, 0, 0);

        Node avgGradeCard = createActionCard("Average Grade", "/images/addE.png");
        avgGradeCard.setOnMouseClicked(e -> {
            AnchorPane avgPane = createAverageGradePane();
            container1.getChildren().clear();
            container1.getChildren().setAll(avgPane);
        });
        dashboardPane.add(avgGradeCard, 1, 0);



        Node RegisterCard = createActionCard("Register", "/images/addC.png");
        RegisterCard.setOnMouseClicked(e -> {
            VBox RegPane = createRegistruPane();
            container1.getChildren().clear();
            container1.getChildren().setAll(RegPane);
        });
        dashboardPane.add(RegisterCard, 0, 1);





        Node subjectsCard = createActionCard("Subjects", "/images/addM.png");
        subjectsCard.setOnMouseClicked(e -> {
            VBox subjectsPane = createSubjectsVBox();

            container1.getChildren().clear();
            container1.getChildren().setAll(subjectsPane);
        });
        dashboardPane.add(subjectsCard, 1, 1);






        HBox container = new HBox(container1, container2);
        container2.setPadding(new Insets(0,10,0,20));


        mainPanel.getChildren().addAll(statsBox, container);

        BorderPane topContainer = new BorderPane();
        topContainer.setLeft(topLeftBox);
        topContainer.setCenter(topBar);

        RoundedButton btnStudent = new RoundedButton("Student", "/images/studentapp.png");

        VBox studentSubMenu = new VBox(10);
        studentSubMenu.setPadding(new Insets(0, 0, 0, 20));
        studentSubMenu.setVisible(false);
        studentSubMenu.managedProperty().bind(studentSubMenu.visibleProperty());

        RoundedButton btnAfisare = new RoundedButton("Show", null);
        btnAfisare.setStyle("-fx-background-color: #4E5665; -fx-background-radius: 10;");


        btnAfisare.setOnAction(e -> {
            AnchorPane showStudentPane = createEleviTablePane();
            container1.getChildren().clear();
            container1.getChildren().add(showStudentPane);

        });


        RoundedButton btnAdaugare = new RoundedButton("Add", null);
        btnAdaugare.setStyle("-fx-background-color: #4E5665; -fx-background-radius: 10;");


        btnAdaugare.setOnAction(e -> {

            AnchorPane addStudentPane = AddStudent();
            container1.getChildren().clear();
            container1.getChildren().add(addStudentPane);

        });


        RoundedButton btnStergere = new RoundedButton("Delete", null);
        btnStergere.setStyle("-fx-background-color: #4E5665; -fx-background-radius: 10;");

        btnStergere.setOnAction(e ->{
            VBox deleteStudentPane = createDeleteElevPane();
            container1.getChildren().clear();
            container1.getChildren().add(deleteStudentPane);
        });


        RoundedButton btnModificare = new RoundedButton("Modify", null);
        btnModificare.setStyle("-fx-background-color: #4E5665; -fx-background-radius: 10;");


        btnModificare.setOnAction(e ->{
            AnchorPane modifyStudentPane = UpdateStudent();
            container1.getChildren().clear();
            container1.getChildren().add(modifyStudentPane);

        });


        studentSubMenu.getChildren().addAll(btnAfisare, btnAdaugare, btnStergere, btnModificare);

        btnStudent.setOnAction(e -> {
            boolean visible = studentSubMenu.isVisible();
            studentSubMenu.setVisible(!visible);
            if (!visible) {
                btnStudent.setStyle("-fx-background-color: #6C7A89; -fx-background-radius: 15;");
            } else {
                btnStudent.setStyle("-fx-background-color: rgba(39,42,49,1); -fx-background-radius: 15;");
            }
        });




        RoundedButton btnEmployee = new RoundedButton("Register", "/images/registru.png");

        VBox gradeSubMenu = new VBox(10);
        gradeSubMenu.setPadding(new Insets(0, 0, 0, 20));
        gradeSubMenu.setVisible(false);
        gradeSubMenu.managedProperty().bind(gradeSubMenu.visibleProperty());

        RoundedButton btnAfisareGrade = new RoundedButton("Show", null);
        btnAfisareGrade.setStyle("-fx-background-color: #4E5665; -fx-background-radius: 15;");
        btnAfisareGrade.setOnAction(e -> {

            VBox RegPane = createRegistruPane();
            container1.getChildren().clear();
            container1.getChildren().setAll(RegPane);
        });

        RoundedButton btnAdaugareGrade = new RoundedButton("Add Grade", null);
        btnAdaugareGrade.setStyle("-fx-background-color: #4E5665; -fx-background-radius: 15;");
        btnAdaugareGrade.setOnAction(e -> {
            VBox addGradePane = AddGrade();
            container1.getChildren().clear();
            container1.getChildren().add(addGradePane);
        });

// Buton Delete Grade
        RoundedButton btnStergereGrade = new RoundedButton("Delete Grade", null);
        btnStergereGrade.setStyle("-fx-background-color: #4E5665; -fx-background-radius: 15;");
        btnStergereGrade.setOnAction(e -> {
            VBox deleteGradePane = createDeleteGradePane();
            container1.getChildren().clear();
            container1.getChildren().add(deleteGradePane);
        });

// Buton Modify Grade
        RoundedButton btnModificareGrade = new RoundedButton("Modify Grade", null);
        btnModificareGrade.setStyle("-fx-background-color: #4E5665; -fx-background-radius: 15;");
        btnModificareGrade.setOnAction(e -> {
            VBox modifyGradePane = UpdateGrade();
            container1.getChildren().clear();
            container1.getChildren().add(modifyGradePane);
        });

// Adaugă toate butoanele în sub-meniu
        gradeSubMenu.getChildren().addAll(btnAfisareGrade, btnAdaugareGrade, btnStergereGrade, btnModificareGrade);

// Funcționalitate toggle sub-meniu
        btnEmployee.setOnAction(e -> {
            boolean visible = gradeSubMenu.isVisible();
            gradeSubMenu.setVisible(!visible);
            if (!visible) {
                btnEmployee.setStyle("-fx-background-color: #6C7A89; -fx-background-radius: 15;");
            } else {
                btnEmployee.setStyle("-fx-background-color: rgba(39,42,49,1); -fx-background-radius: 15;");
            }
        });


// ===================== Attendance Section =====================
        RoundedButton btnAttendance = new RoundedButton("Attendance", "/images/Attendance.png");

        VBox attendanceSubMenu = new VBox(10);
        attendanceSubMenu.setPadding(new Insets(0, 0, 0, 20));
        attendanceSubMenu.setVisible(false);
        attendanceSubMenu.managedProperty().bind(attendanceSubMenu.visibleProperty());

// --- Sub-buton 1: Daily Register ---
        RoundedButton btnDailyRegister = new RoundedButton("Daily Register", null);
        btnDailyRegister.setStyle("-fx-background-color: #4E5665; -fx-background-radius: 10;");
        btnDailyRegister.setOnAction(e -> {
            AnchorPane pane = createDailyAttendancePane();
            container1.getChildren().clear();
            container1.getChildren().add(pane);
        });

// --- Sub-buton 2: Attendance Report ---
        RoundedButton btnAttendanceReport = new RoundedButton("View Report", null);
        btnAttendanceReport.setStyle("-fx-background-color: #4E5665; -fx-background-radius: 10;");
        btnAttendanceReport.setOnAction(e -> {
            AnchorPane pane = createAttendanceReportPane();
            container1.getChildren().clear();
            container1.getChildren().add(pane);
        });

        attendanceSubMenu.getChildren().addAll(btnDailyRegister, btnAttendanceReport);

// --- Toggle Submenu ---
        btnAttendance.setOnAction(e -> {
            boolean visible = attendanceSubMenu.isVisible();
            attendanceSubMenu.setVisible(!visible);
            if (!visible) {
                btnAttendance.setStyle("-fx-background-color: #6C7A89; -fx-background-radius: 15;");
            } else {
                btnAttendance.setStyle("-fx-background-color: rgba(39,42,49,1); -fx-background-radius: 15;");
            }
        });

// --- Hover Effects ---
        addHoverEffect(btnAttendance, "rgba(39,42,49,1)", "#6C7A89");
        addHoverEffect(btnDailyRegister, "#4E5665", "#6C7A89");
        addHoverEffect(btnAttendanceReport, "#4E5665", "#6C7A89");



        // ===================== Reports Section =====================
        RoundedButton btnReports = new RoundedButton("Reports", "/images/report.png"); // poți schimba imaginea
        VBox reportsSubMenu = new VBox(10);
        reportsSubMenu.setPadding(new Insets(0, 0, 0, 20));
        reportsSubMenu.setVisible(false);
        reportsSubMenu.managedProperty().bind(reportsSubMenu.visibleProperty());

// --- Sub-butoane ---
        RoundedButton btnAveragePerSubject = new RoundedButton("Average per Subject", null);
        btnAveragePerSubject.setOnAction(e -> {
            hideDashboardButtons();
            AnchorPane pane = createAveragePerSubjectPane();
            Platform.runLater(() -> container1.getChildren().add(pane));
        });

        RoundedButton btnTopStudents = new RoundedButton("Top Students per Subject", null);
        btnTopStudents.setOnAction(e -> {
            hideDashboardButtons();
            AnchorPane pane = createTopStudentsPane();
            container1.getChildren().setAll(pane);
        });

        RoundedButton btnGradeDistribution = new RoundedButton("Grades Distribution Chart", null);
        btnGradeDistribution.setOnAction(e -> {
            hideDashboardButtons();
            AnchorPane pane = createGradeDistributionPane();
            container1.getChildren().setAll(pane);
        });

        RoundedButton btnExport = new RoundedButton("Export PDF / CSV", null);
        btnExport.setOnAction(e -> {
            hideDashboardButtons();
            AnchorPane pane = createExportReportsPane();
            container1.getChildren().setAll(pane);
        });

// Adăugare butoane în submeniu
        reportsSubMenu.getChildren().addAll(btnAveragePerSubject, btnTopStudents, btnGradeDistribution, btnExport);

// Toggle submenu
        btnReports.setOnAction(e -> {
            boolean visible = reportsSubMenu.isVisible();
            reportsSubMenu.setVisible(!visible);
            if (!visible)
                btnReports.setStyle("-fx-background-color: #6C7A89; -fx-background-radius: 15;");
            else
                btnReports.setStyle("-fx-background-color: rgba(39,42,49,1); -fx-background-radius: 15;");
        });

// Hover effects
        addHoverEffect(btnReports, "rgba(39,42,49,1)", "#6C7A89");
        addHoverEffect(btnAveragePerSubject, "#4E5665", "#6C7A89");
        addHoverEffect(btnTopStudents, "#4E5665", "#6C7A89");
        addHoverEffect(btnGradeDistribution, "#4E5665", "#6C7A89");
        addHoverEffect(btnExport, "#4E5665", "#6C7A89");



        addHoverEffect(btnStudent, "rgba(39,42,49,1)", "#6C7A89");
        addHoverEffect(btnEmployee, "rgba(39,42,49,1)", "#6C7A89");
        addHoverEffect(btnAfisare, "#4E5665", "#6C7A89");
        addHoverEffect(btnAdaugare, "#4E5665", "#6C7A89");
        addHoverEffect(btnStergere, "#4E5665", "#6C7A89");
        addHoverEffect(btnModificare, "#4E5665", "#6C7A89");
        addHoverEffect(btnAfisareGrade, "#4E5665", "#6C7A89");
        addHoverEffect(btnAdaugareGrade, "#4E5665", "#6C7A89");
        addHoverEffect(btnStergereGrade, "#4E5665", "#6C7A89");
        addHoverEffect(btnModificareGrade, "#4E5665", "#6C7A89");


        RoundedButton btnClose = new RoundedButton("Close Application", null);
        addHoverEffect(btnClose, "rgba(39,42,49,1)", "#6C7A89");
        btnClose.setOnAction(e -> Platform.exit());
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);


        leftPanel.getChildren().addAll(btnStudent, studentSubMenu, btnEmployee, gradeSubMenu, btnAttendance,
                attendanceSubMenu,  btnReports, reportsSubMenu, spacer, btnClose);

        leftPanel.setAlignment(Pos.TOP_CENTER);
        VBox.setMargin(btnClose, new Insets(20, 0, 10, 0));

        setLeft(leftPanel);
        setTop(topContainer);
        setCenter(mainPanel);
    }

    private void hideDashboardButtons() {
        for (Node node : dashboardPane.getChildren()) {
            node.setVisible(false);
            node.setManaged(false);
        }
    }

    private void showDashboardButtons() {
        for (Node node : dashboardPane.getChildren()) {
            node.setVisible(true);
            node.setManaged(true);
        }
    }


    private void addHoverEffect(RoundedButton button, String normalColor, String hoverColor) {
        button.setStyle("-fx-background-color: " + normalColor + "; -fx-background-radius: 15; -fx-text-fill: white;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + "; -fx-background-radius: 15; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + normalColor + "; -fx-background-radius: 15; -fx-text-fill: white;"));
    }

    private VBox createStatCard(String iconPath, String number, String labelText, String color) {
        VBox card = new VBox();
        card.setAlignment(Pos.CENTER_LEFT);
        card.setPadding(new Insets(15));
        card.setPrefSize(350, 200);
        card.setStyle(
                "-fx-background-color: white; " +
                        "-fx-border-color: " + color + "; " +
                        "-fx-border-width: 0 0 0 4; " +
                        "-fx-background-radius: 10; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 5,0,0,1);"
        );

        HBox content = new HBox(20);
        content.setAlignment(Pos.CENTER_LEFT);

        StackPane iconWrapper = new StackPane();
        iconWrapper.setPrefSize(45, 45);
        iconWrapper.setAlignment(Pos.CENTER);

        ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
        icon.setFitWidth(70);
        icon.setFitHeight(70);
        icon.setPreserveRatio(true);
        icon.setSmooth(true);
        iconWrapper.getChildren().add(icon);

        VBox textBox = new VBox(5);
        textBox.setAlignment(Pos.CENTER);
        textBox.setTranslateX(5);

        Label lblNumber = new Label(number);
        lblNumber.setFont(Font.font("Roboto Mono", 40));
        lblNumber.setTextFill(Color.web("#a1a1a1"));

        Label lblText = new Label(labelText);
        lblText.setFont(Font.font("Roboto Mono", 10));
        lblText.setTextFill(Color.web("#a1a1a1"));
        lblText.setStyle("-fx-font-weight: bold;");

        textBox.getChildren().addAll(lblNumber, lblText);
        content.getChildren().addAll(iconWrapper, textBox);
        card.getChildren().add(content);
        return card;
    }

    private VBox createActionCard(String text, String iconPath) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setPrefSize(180, 90);
        card.setStyle("-fx-background-color: white; "
                + "-fx-background-radius: 10; "
                + "-fx-border-color: #ddd; "
                + "-fx-border-width: 1; "
                + "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.05), 4,0,0,1);"
                + "-fx-cursor: hand;");

        if (iconPath != null) {
            ImageView icon = new ImageView(new Image(getClass().getResourceAsStream(iconPath)));
            icon.setFitWidth(26);
            icon.setFitHeight(26);
            card.getChildren().add(icon);
        }

        Label lbl = new Label(text);
        lbl.setFont(Font.font("Roboto Mono", 13));
        lbl.setTextFill(Color.web("#333"));
        lbl.setStyle("-fx-font-weight: bold;");

        card.getChildren().add(lbl);
        return card;
    }

    private AnchorPane AddStudent() {

        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(700, 550);
        pane.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);
        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(15);
        shadow.setOffsetY(12);
        shadow.setRadius(20);
        shadow.setColor(Color.rgb(200, 193, 193, 0.34));
        pane.setEffect(shadow);


        Label Title = new Label("Add Student");
        Title.setFont(Fonts.RobotoMonoBold32);
        Title.setStyle("-fx-text-fill: #272A31;");
        AnchorPane.setTopAnchor(Title, 46.0);
        AnchorPane.setLeftAnchor(Title, 250.0);


        Label lblNume = new Label("Nume:");
        lblNume.setFont(Fonts.RobotoMonoMedium20);
        lblNume.setStyle("-fx-text-fill: #272A31;");

        Label lblPrenume = new Label("Prenume:");
        lblPrenume.setFont(Fonts.RobotoMonoMedium20);
        lblPrenume.setStyle("-fx-text-fill: #272A31;");

        Label lblPatronimic = new Label("Patronimic:");
        lblPatronimic.setFont(Fonts.RobotoMonoMedium20);
        lblPatronimic.setStyle("-fx-text-fill: #272A31;");

        Label lblData = new Label("Data nașterii:");
        lblData.setFont(Fonts.RobotoMonoMedium20);
        lblData.setStyle("-fx-text-fill: #272A31;");

        Label lblIDNP = new Label("IDNP:");
        lblIDNP.setFont(Fonts.RobotoMonoMedium20);
        lblIDNP.setStyle("-fx-text-fill: #272A31;");

        Label lblEmail = new Label("Email:");
        lblEmail.setFont(Fonts.RobotoMonoMedium20);
        lblEmail.setStyle("-fx-text-fill: #272A31;");

        Label lblTelefon = new Label("Telefon:");
        lblTelefon.setFont(Fonts.RobotoMonoMedium20);
        lblTelefon.setStyle("-fx-text-fill: #272A31;");



        TextField txtNume = createStyledTextField();

        TextField txtPrenume = createStyledTextField();

        TextField txtPatronimic = createStyledTextField();

        TextField dateDataNasterii = createStyledTextField();
        dateDataNasterii.setPromptText("yyyy-mm-dd");

        TextField txtIDNP = createStyledTextField();

        TextField txtEmail = createStyledTextField();
        txtEmail.setPromptText("exemple@gmail.com");

        TextField txtTelefon = createStyledTextField();

        Button btnAdd = new Button("Add");
        btnAdd.setStyle("-fx-background-color: #363844; -fx-text-fill: white; -fx-background-radius: 5; -fx-border-radius: 5;");
        btnAdd.setFont(Fonts.RobotoMonoMedium20);
        btnAdd.setPrefSize(250, 45);
        btnAdd.setMinSize(250, 45);
        btnAdd.setMaxSize(250, 45);

        AnchorPane.setTopAnchor(lblNume, 124.0);
        AnchorPane.setLeftAnchor(lblNume, 70.0);
        AnchorPane.setTopAnchor(txtNume, 161.0);
        AnchorPane.setLeftAnchor(txtNume, 70.0);

        AnchorPane.setTopAnchor(lblPrenume, 211.0);
        AnchorPane.setLeftAnchor(lblPrenume, 70.0);
        AnchorPane.setTopAnchor(txtPrenume, 248.0);
        AnchorPane.setLeftAnchor(txtPrenume, 70.0);

        AnchorPane.setTopAnchor(lblPatronimic, 298.0);
        AnchorPane.setLeftAnchor(lblPatronimic, 70.0);
        AnchorPane.setTopAnchor(txtPatronimic, 335.0);
        AnchorPane.setLeftAnchor(txtPatronimic, 70.0);

        AnchorPane.setTopAnchor(lblData, 385.0);
        AnchorPane.setLeftAnchor(lblData, 70.0);
        AnchorPane.setTopAnchor(dateDataNasterii, 422.0);
        AnchorPane.setLeftAnchor(dateDataNasterii, 70.0);

        AnchorPane.setTopAnchor(lblIDNP, 124.0);
        AnchorPane.setLeftAnchor(lblIDNP, 380.0);
        AnchorPane.setTopAnchor(txtIDNP, 161.0);
        AnchorPane.setLeftAnchor(txtIDNP, 380.0);

        AnchorPane.setTopAnchor(lblEmail, 211.0);
        AnchorPane.setLeftAnchor(lblEmail, 380.0);
        AnchorPane.setTopAnchor(txtEmail, 248.0);
        AnchorPane.setLeftAnchor(txtEmail, 380.0);

        AnchorPane.setTopAnchor(lblTelefon, 298.0);
        AnchorPane.setLeftAnchor(lblTelefon, 380.0);
        AnchorPane.setTopAnchor(txtTelefon, 335.0);
        AnchorPane.setLeftAnchor(txtTelefon, 380.0);

        AnchorPane.setTopAnchor(btnAdd, 422.0);
        AnchorPane.setLeftAnchor(btnAdd, 380.0);

        pane.getChildren().addAll(Title, lblNume, txtNume, lblPrenume, txtPrenume, lblPatronimic, txtPatronimic,
                lblData, dateDataNasterii, lblIDNP, txtIDNP, lblEmail, txtEmail, lblTelefon, txtTelefon,
                btnAdd);

        btnAdd.setOnAction(e -> {
            String nume = txtNume.getText().trim();
            String prenume = txtPrenume.getText().trim();
            String patronimic = txtPatronimic.getText().trim();
            String dataNasterii = dateDataNasterii.getText().trim();
            String idnp = txtIDNP.getText().trim();
            String email = txtEmail.getText().trim();
            String telefon = txtTelefon.getText().trim();

            if (nume.isEmpty() || prenume.isEmpty() || dataNasterii.isEmpty() ||
                    idnp.isEmpty() || email.isEmpty() || telefon.isEmpty()) {
                System.out.println("Completați toate câmpurile obligatorii!");
                return;
            }

            Date dateOfBirth;
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(dataNasterii, formatter);
                dateOfBirth = Date.valueOf(localDate);
            } catch (Exception ex) {
                System.out.println("Formatul datei este invalid! Folosiți formatul yyyy-MM-dd (ex: 2006-08-13)");
                return;
            }

            String sql = "INSERT INTO Elevi (numeElev, prenumeElev, patronimicElev, DataNasterii, IDNP, email, telefon) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, nume);
                ps.setString(2, prenume);
                ps.setString(3, patronimic);
                ps.setDate(4, dateOfBirth);
                ps.setString(5, idnp);
                ps.setString(6, email);
                ps.setString(7, telefon);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    System.out.println("Elev adăugat cu succes!");

                    txtNume.clear();
                    txtPrenume.clear();
                    txtPatronimic.clear();
                    dateDataNasterii.clear();
                    txtIDNP.clear();
                    txtEmail.clear();
                    txtTelefon.clear();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Eroare la inserarea elevului!");
            }
        });


        return pane;
    }






    public AnchorPane createEleviTablePane() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(700, 630);
        pane.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);

        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(15);
        shadow.setOffsetY(12);
        shadow.setRadius(20);
        shadow.setColor(Color.rgb(200, 193, 193, 0.34));
        pane.setEffect(shadow);


        Label lblTitle = new Label("Student's List");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #272A31;");
        AnchorPane.setTopAnchor(lblTitle, 50.0);
        AnchorPane.setLeftAnchor(lblTitle, 194.0);


        TableView<Elev> table = new TableView<>();
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(350);

        table.setStyle("""
        -fx-background-color: #F8F9FA;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-table-cell-border-color: #B0B0B0;
        -fx-font-family: Roboto Mono;
        -fx-font-size: 12px;
        -fx-selection-bar: #DEE2E6;
        -fx-selection-bar-non-focused: #E9ECEF;
    """);


        TableColumn<Elev, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("idElev"));
        colId.setPrefWidth(20);

        TableColumn<Elev, String> colNume = new TableColumn<>("Nume");
        colNume.setCellValueFactory(new PropertyValueFactory<>("numeElev"));
        colNume.setPrefWidth(80);

        TableColumn<Elev, String> colPrenume = new TableColumn<>("Prenume");
        colPrenume.setCellValueFactory(new PropertyValueFactory<>("prenumeElev"));
        colPrenume.setPrefWidth(80);

        TableColumn<Elev, String> colPatronimic = new TableColumn<>("Patronimic");
        colPatronimic.setCellValueFactory(new PropertyValueFactory<>("patronimicElev"));
        colPatronimic.setPrefWidth(80);

        TableColumn<Elev, Date> colData = new TableColumn<>("Data Nașterii");
        colData.setCellValueFactory(new PropertyValueFactory<>("dataNasterii"));
        colData.setPrefWidth(77);

        TableColumn<Elev, String> colIDNP = new TableColumn<>("IDNP");
        colIDNP.setCellValueFactory(new PropertyValueFactory<>("idnp"));
        colIDNP.setPrefWidth(100);

        TableColumn<Elev, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(158);

        TableColumn<Elev, String> colTelefon = new TableColumn<>("Telefon");
        colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        colTelefon.setPrefWidth(75);

        table.getColumns().addAll(colId, colNume, colPrenume, colPatronimic, colData, colIDNP, colEmail, colTelefon);

        table.setRowFactory(tv -> {
            TableRow<Elev> row = new TableRow<>() {
                @Override
                protected void updateItem(Elev item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setStyle("-fx-background-color: #F2F2F2;");
                    } else {
                        setStyle("-fx-background-color: #EBEBEB;");
                    }
                }
            };

            row.hoverProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal) row.setStyle("-fx-background-color: #F2F2F2; -fx-border-color: #B0B0B0;");
                else row.setStyle("-fx-background-color: #EBEBEB;");
            });

            return row;
        });


        ObservableList<Elev> elevi = FXCollections.observableArrayList();
        String sql = "SELECT idElev, numeElev, prenumeElev, patronimicElev, DataNasterii, IDNP, email, telefon FROM Elevi";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                elevi.add(new Elev(
                        rs.getInt("idElev"),
                        rs.getString("numeElev"),
                        rs.getString("prenumeElev"),
                        rs.getString("patronimicElev"),
                        rs.getDate("DataNasterii"),
                        rs.getString("IDNP"),
                        rs.getString("email"),
                        rs.getString("telefon")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println("Eroare la încărcarea elevilor din baza de date!");
        }

        table.setItems(elevi);


        pane.getChildren().addAll(lblTitle, table);
        AnchorPane.setTopAnchor(table, 150.0);
        AnchorPane.setLeftAnchor(table, 25.0);
        AnchorPane.setRightAnchor(table, 25.0);

        return pane;
    }




    public VBox createDeleteElevPane() {
        VBox vbox = new VBox(30);
        vbox.setPrefSize(700, 350);
        vbox.setPadding(new Insets(40, 50, 40, 50));
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);

        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(15);
        shadow.setOffsetY(12);
        shadow.setRadius(20);
        shadow.setColor(Color.rgb(200, 193, 193, 0.34));
        vbox.setEffect(shadow);

        Label lblTitle = new Label("Delete Student");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #272A31;");

        HBox hBoxId = new HBox(15);
        hBoxId.setAlignment(Pos.CENTER);
        Label lblId = new Label("Student ID:");
        lblId.setFont(Fonts.RobotoMonoMedium16);
        lblId.setStyle("-fx-text-fill: #272A31;");

        TextField txtId = new TextField();
        txtId.setPromptText("Introdu ID-ul elevului");
        txtId.setPrefWidth(250);
        txtId.setStyle("""
        -fx-background-color: #FFFFFF;
        -fx-border-color: #B0B0B0;
        -fx-border-radius: 10;
        -fx-background-radius: 10;
        -fx-font-family: 'Roboto Mono';
        -fx-font-size: 14px;
    """);

        hBoxId.getChildren().addAll(lblId, txtId);

        Button btnDelete = new Button("Delete");
        btnDelete.setPrefSize(150, 40);
        btnDelete.setStyle("""
        -fx-background-color: #DC3545;
        -fx-text-fill: white;
        -fx-font-family: 'Roboto Mono';
        -fx-font-size: 16px;
        -fx-background-radius: 15;
    """);
        btnDelete.setOnMouseEntered(e -> btnDelete.setStyle("""
        -fx-background-color: #C82333;
        -fx-text-fill: white;
        -fx-font-family: 'Roboto Mono';
        -fx-font-size: 16px;
        -fx-background-radius: 15;
    """));
        btnDelete.setOnMouseExited(e -> btnDelete.setStyle("""
        -fx-background-color: #DC3545;
        -fx-text-fill: white;
        -fx-font-family: 'Roboto Mono';
        -fx-font-size: 16px;
        -fx-background-radius: 15;
    """));

        Label lblStatus = new Label();
        lblStatus.setFont(Fonts.RobotoMonoMedium14);
        lblStatus.setStyle("-fx-text-fill: #272A31;");

        btnDelete.setOnAction(e -> {
            String idText = txtId.getText().trim();

            if (idText.isEmpty()) {
                lblStatus.setText("Introduceți un ID valid!");
                lblStatus.setStyle("-fx-text-fill: #DC3545;");
                return;
            }

            try {
                int idElev = Integer.parseInt(idText);

                String sql = "DELETE FROM Elevi WHERE idElev = ?";
                try (Connection conn = ConnectionDB.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setInt(1, idElev);
                    int rowsAffected = ps.executeUpdate();

                    if (rowsAffected > 0) {
                        lblStatus.setText("Elevul a fost șters cu succes!");
                        lblStatus.setStyle("-fx-text-fill: #28A745;");
                        txtId.clear();
                    } else {
                        lblStatus.setText("Nu există elev cu acest ID!");
                        lblStatus.setStyle("-fx-text-fill: #DC3545;");
                    }
                }
            } catch (NumberFormatException ex) {
                lblStatus.setText("ID-ul trebuie să fie numeric!");
                lblStatus.setStyle("-fx-text-fill: #DC3545;");
            } catch (SQLException ex) {
                ex.printStackTrace();
                lblStatus.setText("Eroare la ștergerea elevului!");
                lblStatus.setStyle("-fx-text-fill: #DC3545;");
            }
        });

        vbox.getChildren().addAll(lblTitle, hBoxId, btnDelete, lblStatus);
        return vbox;
    }



    private AnchorPane UpdateStudent() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(700, 550);
        pane.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);

        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(15);
        shadow.setOffsetY(12);
        shadow.setRadius(20);
        shadow.setColor(Color.rgb(200, 193, 193, 0.34));
        pane.setEffect(shadow);

        Label lblTitle = new Label("Update Student");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #272A31;");
        AnchorPane.setTopAnchor(lblTitle, 30.0);
        AnchorPane.setLeftAnchor(lblTitle, 220.0);

        TextField txtId = new TextField();
        txtId.setPromptText("Introduceți ID-ul elevului");
        txtId.setPrefWidth(250);
        txtId.setStyle("""
        -fx-background-color: #FFFFFF;
        -fx-border-color: #B0B0B0;
        -fx-border-radius: 10;
        -fx-background-radius: 10;
        -fx-font-family: 'Roboto Mono';
        -fx-font-size: 14px;
    """);
        AnchorPane.setTopAnchor(txtId, 100.0);
        AnchorPane.setLeftAnchor(txtId, 220.0);

        Button btnLoad = new Button("Load Student");
        btnLoad.setStyle("""
        -fx-background-color: #0D6EFD;
        -fx-text-fill: white;
        -fx-background-radius: 10;
        -fx-font-family: 'Roboto Mono';
        -fx-font-size: 14px;
    """);
        btnLoad.setPrefSize(150, 35);
        AnchorPane.setTopAnchor(btnLoad, 140.0);
        AnchorPane.setLeftAnchor(btnLoad, 275.0);

        pane.getChildren().addAll(lblTitle, txtId, btnLoad);

        btnLoad.setOnAction(e -> {
            String idText = txtId.getText().trim();
            if (idText.isEmpty()) {
                System.out.println("Introduceți un ID valid!");
                return;
            }

            try {
                int idElev = Integer.parseInt(idText);

                String sql = "SELECT * FROM Elevi WHERE idElev = ?";
                try (Connection conn = ConnectionDB.getConnection();
                     PreparedStatement ps = conn.prepareStatement(sql)) {

                    ps.setInt(1, idElev);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {
                        pane.getChildren().clear();

                        pane.getChildren().add(lblTitle);

                        Label lblNume = new Label("Nume:");
                        lblNume.setFont(Fonts.RobotoMonoMedium20);
                        lblNume.setStyle("-fx-text-fill: #272A31;");
                        TextField txtNume = createStyledTextField();
                        txtNume.setText(rs.getString("numeElev"));

                        Label lblPrenume = new Label("Prenume:");
                        lblPrenume.setFont(Fonts.RobotoMonoMedium20);
                        lblPrenume.setStyle("-fx-text-fill: #272A31;");
                        TextField txtPrenume = createStyledTextField();
                        txtPrenume.setText(rs.getString("prenumeElev"));

                        Label lblPatronimic = new Label("Patronimic:");
                        lblPatronimic.setFont(Fonts.RobotoMonoMedium20);
                        lblPatronimic.setStyle("-fx-text-fill: #272A31;");
                        TextField txtPatronimic = createStyledTextField();
                        txtPatronimic.setText(rs.getString("patronimicElev"));

                        Label lblData = new Label("Data nașterii:");
                        lblData.setFont(Fonts.RobotoMonoMedium20);
                        lblData.setStyle("-fx-text-fill: #272A31;");
                        TextField txtData = createStyledTextField();
                        txtData.setText(rs.getDate("DataNasterii").toString());

                        Label lblIDNP = new Label("IDNP:");
                        lblIDNP.setFont(Fonts.RobotoMonoMedium20);
                        lblIDNP.setStyle("-fx-text-fill: #272A31;");
                        TextField txtIDNP = createStyledTextField();
                        txtIDNP.setText(rs.getString("IDNP"));

                        Label lblEmail = new Label("Email:");
                        lblEmail.setFont(Fonts.RobotoMonoMedium20);
                        lblEmail.setStyle("-fx-text-fill: #272A31;");
                        TextField txtEmail = createStyledTextField();
                        txtEmail.setText(rs.getString("email"));

                        Label lblTelefon = new Label("Telefon:");
                        lblTelefon.setFont(Fonts.RobotoMonoMedium20);
                        lblTelefon.setStyle("-fx-text-fill: #272A31;");
                        TextField txtTelefon = createStyledTextField();
                        txtTelefon.setText(rs.getString("telefon"));

                        Button btnUpdate = new Button("Update");
                        btnUpdate.setStyle("""
                        -fx-background-color: #28A745;
                        -fx-text-fill: white;
                        -fx-background-radius: 5;
                        -fx-font-family: 'Roboto Mono';
                        -fx-font-size: 16px;
                    """);
                        btnUpdate.setPrefSize(250, 45);

                        AnchorPane.setTopAnchor(lblNume, 100.0);
                        AnchorPane.setLeftAnchor(lblNume, 50.0);
                        AnchorPane.setTopAnchor(txtNume, 135.0);
                        AnchorPane.setLeftAnchor(txtNume, 50.0);

                        AnchorPane.setTopAnchor(lblPrenume, 185.0);
                        AnchorPane.setLeftAnchor(lblPrenume, 50.0);
                        AnchorPane.setTopAnchor(txtPrenume, 220.0);
                        AnchorPane.setLeftAnchor(txtPrenume, 50.0);

                        AnchorPane.setTopAnchor(lblPatronimic, 270.0);
                        AnchorPane.setLeftAnchor(lblPatronimic, 50.0);
                        AnchorPane.setTopAnchor(txtPatronimic, 305.0);
                        AnchorPane.setLeftAnchor(txtPatronimic, 50.0);

                        AnchorPane.setTopAnchor(lblData, 365.0);
                        AnchorPane.setLeftAnchor(lblData, 50.0);
                        AnchorPane.setTopAnchor(txtData, 400.0);
                        AnchorPane.setLeftAnchor(txtData, 50.0);

                        AnchorPane.setTopAnchor(lblIDNP, 100.0);
                        AnchorPane.setLeftAnchor(lblIDNP, 380.0);
                        AnchorPane.setTopAnchor(txtIDNP, 135.0);
                        AnchorPane.setLeftAnchor(txtIDNP, 380.0);

                        AnchorPane.setTopAnchor(lblEmail, 185.0);
                        AnchorPane.setLeftAnchor(lblEmail, 380.0);
                        AnchorPane.setTopAnchor(txtEmail, 220.0);
                        AnchorPane.setLeftAnchor(txtEmail, 380.0);

                        AnchorPane.setTopAnchor(lblTelefon, 270.0);
                        AnchorPane.setLeftAnchor(lblTelefon, 380.0);
                        AnchorPane.setTopAnchor(txtTelefon, 305.0);
                        AnchorPane.setLeftAnchor(txtTelefon, 380.0);

                        AnchorPane.setTopAnchor(btnUpdate, 400.0);
                        AnchorPane.setLeftAnchor(btnUpdate, 380.0);

                        pane.getChildren().addAll(lblNume, txtNume, lblPrenume, txtPrenume,
                                lblPatronimic, txtPatronimic, lblData, txtData,
                                lblIDNP, txtIDNP, lblEmail, txtEmail, lblTelefon, txtTelefon,
                                btnUpdate);

                        btnUpdate.setOnAction(ev -> {
                            try {
                                String sqlUpdate = "UPDATE Elevi SET numeElev = ?, prenumeElev = ?, patronimicElev = ?, " +
                                        "DataNasterii = ?, IDNP = ?, email = ?, telefon = ? WHERE idElev = ?";
                                try (PreparedStatement psUpdate = ConnectionDB.getConnection().prepareStatement(sqlUpdate)) {
                                    psUpdate.setString(1, txtNume.getText().trim());
                                    psUpdate.setString(2, txtPrenume.getText().trim());
                                    psUpdate.setString(3, txtPatronimic.getText().trim());
                                    psUpdate.setDate(4, Date.valueOf(txtData.getText().trim()));
                                    psUpdate.setString(5, txtIDNP.getText().trim());
                                    psUpdate.setString(6, txtEmail.getText().trim());
                                    psUpdate.setString(7, txtTelefon.getText().trim());
                                    psUpdate.setInt(8, idElev);

                                    int rows = psUpdate.executeUpdate();
                                    if (rows > 0) {
                                        System.out.println("Elevul a fost actualizat cu succes!");
                                    }
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                                System.out.println("Eroare la actualizarea elevului!");
                            }
                        });

                    } else {
                        System.out.println("Nu există elev cu acest ID!");
                    }

                }
            } catch (NumberFormatException ex) {
                System.out.println("ID-ul trebuie să fie numeric!");
            } catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Eroare la citirea elevului!");
            }
        });

        return pane;
    }





    private TextField createStyledTextField() {
        TextField txt = new TextField();
        txt.setPrefSize(250, 45);
        txt.setMaxSize(250, 45);
        txt.setMinSize(250, 45);
        txt.setFont(Fonts.RobotoMonoMedium20);
        txt.setStyle("-fx-text-fill: #272A31; -fx-background-color: transparent; -fx-border-color: #272A31; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        return txt;
    }







    private VBox createSubjectsVBox() {
        VBox vbox = new VBox(20);
        vbox.setPadding(new Insets(30, 40, 30, 40));
        vbox.setPrefSize(700, 600);
        vbox.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);
        vbox.setEffect(new DropShadow(8, Color.rgb(0,0,0,0.15)));

        // Titlu
        Label lblTitle = new Label("Subjects List");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #272A31;");
        lblTitle.setAlignment(Pos.CENTER);

        // ScrollPane cu lista de discipline
        VBox listBox = new VBox(15);
        listBox.setPadding(new Insets(5, 0, 5, 20));

        List<String> disciplines = DisciplinaDAO.getAllDisciplines();
        for (String disc : disciplines) {
            Label lbl = new Label("• " + disc);
            lbl.setFont(Fonts.RobotoMonoRegular20);
            lbl.setStyle("-fx-text-fill: #2E2F34;");
            lbl.setPadding(new Insets(5, 10, 5, 10));
            lbl.setOnMouseEntered(e -> lbl.setStyle("-fx-text-fill: #0D3B66; -fx-font-weight: bold;"));
            lbl.setOnMouseExited(e -> lbl.setStyle("-fx-text-fill: #2E2F34;"));
            listBox.getChildren().add(lbl);
        }

        ScrollPane scroll = new ScrollPane(listBox);
        scroll.setFitToWidth(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setStyle("""
        -fx-background: transparent;
        -fx-background-color: transparent;
        -fx-border-color: transparent;
        -fx-padding: 0;
    """);

        VBox.setVgrow(scroll, Priority.ALWAYS);

        vbox.getChildren().addAll(lblTitle, scroll);
        return vbox;
    }



    private ObservableList<ElevTop> getTopElevi() {
        ObservableList<ElevTop> list = FXCollections.observableArrayList();

        String query = "SELECT * FROM VwTop10Elevi";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nume = rs.getString("numeElev");
                String prenume = rs.getString("prenumeElev");
                String patronimic = rs.getString("patronimicElev");
                double medie = rs.getDouble("medie");

                list.add(new ElevTop(nume, prenume, patronimic, medie));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }




    private AnchorPane createTopEleviPane() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(700, 600);
        pane.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);

        Label lblTitle = new Label("Top 10 Students");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #0D1B2A;");
        AnchorPane.setTopAnchor(lblTitle, 75.0);
        AnchorPane.setLeftAnchor(lblTitle, 220.0);

        TableView<ElevTop> table = new TableView<>();
        table.setItems(getTopElevi());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Coloane
        TableColumn<ElevTop, String> colNume = new TableColumn<>("Nume");
        colNume.setCellValueFactory(new PropertyValueFactory<>("nume"));

        TableColumn<ElevTop, String> colPrenume = new TableColumn<>("Prenume");
        colPrenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));

        TableColumn<ElevTop, String> colPatronimic = new TableColumn<>("Patronimic");
        colPatronimic.setCellValueFactory(new PropertyValueFactory<>("patronimic"));

        TableColumn<ElevTop, Double> colMedie = new TableColumn<>("Medie");
        colMedie.setCellValueFactory(new PropertyValueFactory<>("medie"));

        table.getColumns().addAll(colNume, colPrenume, colPatronimic, colMedie);

        // Stilizare TableView
        table.setStyle(
                "-fx-font-family: 'Roboto Mono'; " +
                        "-fx-background-color: #F0F4F8; " +
                        "-fx-border-color: transparent; " +
                        "-fx-table-cell-border-color: transparent;"
        );

        table.setRowFactory(tv -> new TableRow<ElevTop>() {
            @Override
            protected void updateItem(ElevTop item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (getIndex() % 2 == 0) {
                        setStyle("-fx-background-color: #E1EAF6;"); // albastru deschis
                    } else {
                        setStyle("-fx-background-color: white;");
                    }

                    // efect hover
                    setOnMouseEntered(e -> setStyle("-fx-background-color: #B0C4DE;")); // bleu mediu
                    setOnMouseExited(e -> {
                        if (getIndex() % 2 == 0) setStyle("-fx-background-color: #E1EAF6;");
                        else setStyle("-fx-background-color: white;");
                    });
                }
            }
        });

        AnchorPane.setTopAnchor(table, 150.0);
        AnchorPane.setLeftAnchor(table, 70.0);
        AnchorPane.setRightAnchor(table, 70.0);
        AnchorPane.setBottomAnchor(table, 160.0);

        pane.getChildren().addAll(lblTitle, table);
        return pane;
    }




    private AnchorPane createAverageGradePane() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(700, 600);
        pane.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);
        pane.setEffect(new DropShadow(8, Color.rgb(13,27,42,0.2)));

        Label lblTitle = new Label("Average Grade per Subject");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #0D1B2A;");
        AnchorPane.setTopAnchor(lblTitle, 30.0);
        AnchorPane.setLeftAnchor(lblTitle, 120.0);

        // ComboBox elevi
        ComboBox<String> cbElevi = new ComboBox<>();
        cbElevi.setItems(FXCollections.observableArrayList(MedieDAO.getAllElevi()));
        cbElevi.setPrefWidth(400);
        cbElevi.setStyle("-fx-font-size: 16px; -fx-font-family: 'Roboto Mono';");
        AnchorPane.setTopAnchor(cbElevi, 100.0);
        AnchorPane.setLeftAnchor(cbElevi, 150.0);

        // TableView pentru mediile disciplinelor
        TableView<MedieDisciplina> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<MedieDisciplina, String> colDisciplina = new TableColumn<>("Disciplina");
        colDisciplina.setCellValueFactory(new PropertyValueFactory<>("disciplina"));

        TableColumn<MedieDisciplina, Double> colMedie = new TableColumn<>("Medie");
        colMedie.setCellValueFactory(new PropertyValueFactory<>("medie"));

        table.getColumns().addAll(colDisciplina, colMedie);

        // Font mai mare și rânduri alternative
        table.setRowFactory(tv -> new TableRow<MedieDisciplina>() {
            @Override
            protected void updateItem(MedieDisciplina item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null || empty) {
                    setStyle("");
                } else {
                    setStyle("-fx-font-size: 16px; -fx-font-family: 'Roboto Mono';");
                    if (getIndex() % 2 == 0) setStyle(getStyle() + "; -fx-background-color: #F7FAFC;");
                    else setStyle(getStyle() + "; -fx-background-color: white;");

                    setOnMouseEntered(e -> setStyle("-fx-background-color: #D0E4F7; -fx-font-size: 16px; -fx-font-family: 'Roboto Mono';"));
                    setOnMouseExited(e -> {
                        if (getIndex() % 2 == 0) setStyle("-fx-background-color: #F7FAFC; -fx-font-size: 16px; -fx-font-family: 'Roboto Mono';");
                        else setStyle("-fx-background-color: white; -fx-font-size: 16px; -fx-font-family: 'Roboto Mono';");
                    });
                }
            }
        });

        AnchorPane.setTopAnchor(table, 160.0);
        AnchorPane.setLeftAnchor(table, 50.0);
        AnchorPane.setRightAnchor(table, 50.0);
        AnchorPane.setBottomAnchor(table, 50.0);

        // La selectarea unui elev, se încarcă mediile
        cbElevi.setOnAction(e -> {
            String selected = cbElevi.getSelectionModel().getSelectedItem();
            if (selected != null && !selected.isEmpty()) {
                int idElev = Integer.parseInt(selected.split(" - ")[0]);
                table.setItems(MedieDAO.getMediiByElev(idElev));
            }
        });

        pane.getChildren().addAll(lblTitle, cbElevi, table);
        return pane;
    }






    private VBox createRegistruPane() {
        VBox pane = new VBox(20);
        pane.setPadding(new Insets(30));
        pane.setPrefSize(700, 600);

        pane.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);
        pane.setEffect(new DropShadow(8, Color.rgb(0,0,0,0.15)));

        Label lblTitle = new Label("Registru Note");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #272A31;");
        lblTitle.setAlignment(Pos.CENTER);

        // ComboBox discipline
        ComboBox<String> cbDisciplina = new ComboBox<>();
        cbDisciplina.setItems(FXCollections.observableArrayList(RegistruDAO.getAllDisciplines()));
        cbDisciplina.setPrefWidth(400);
        cbDisciplina.setStyle("-fx-font-size: 16px; -fx-font-family: 'Roboto Mono';");

        // TableView pentru elevi + note dinamice
        TableView<ElevNote> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Coloane fixe
        TableColumn<ElevNote, String> colNume = new TableColumn<>("Nume");
        colNume.setCellValueFactory(new PropertyValueFactory<>("nume"));

        TableColumn<ElevNote, String> colPrenume = new TableColumn<>("Prenume");
        colPrenume.setCellValueFactory(new PropertyValueFactory<>("prenume"));

        TableColumn<ElevNote, String> colPatronimic = new TableColumn<>("Patronimic");
        colPatronimic.setCellValueFactory(new PropertyValueFactory<>("patronimic"));

        table.getColumns().addAll(colNume, colPrenume, colPatronimic);

        // La selectarea disciplinei
        cbDisciplina.setOnAction(e -> {
            String discSelect = cbDisciplina.getSelectionModel().getSelectedItem();
            if (discSelect != null) {
                int idDisc = cbDisciplina.getSelectionModel().getSelectedIndex() + 1;
                List<ElevNote> elevi = RegistruDAO.getEleviNoteByDisciplina(idDisc);

                table.getColumns().removeIf(col -> col.getText().startsWith("Nota")); // șterge coloanele vechi

                // găsește nr maxim note
                int maxNote = elevi.stream().mapToInt(ev -> ev.getNote().size()).max().orElse(0);

                // creează coloane dinamice Nota1, Nota2, ...
                for (int i = 0; i < maxNote; i++) {
                    final int idx = i;
                    TableColumn<ElevNote, Double> colNota = new TableColumn<>("Nota " + (i+1));
                    colNota.setCellValueFactory(data -> {
                        List<Double> note = data.getValue().getNote();
                        return new SimpleObjectProperty<>(idx < note.size() ? note.get(idx) : null);
                    });
                    table.getColumns().add(colNota);
                }

                table.setItems(FXCollections.observableArrayList(elevi));
            }
        });

        VBox.setVgrow(table, Priority.ALWAYS);
        pane.getChildren().addAll(lblTitle, cbDisciplina, table);
        return pane;
    }

    private VBox AddGrade() {
        VBox pane = new VBox(20);
        pane.setPrefSize(700, 550);
        pane.setPadding(new Insets(30));
        pane.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);
        pane.setEffect(new DropShadow(8, Color.rgb(0,0,0,0.15)));

        Label lblTitle = new Label("Add Grade");
        lblTitle.setFont(Font.font("Roboto Mono", 28));
        lblTitle.setStyle("-fx-text-fill: #272A31;");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));

        // Discipline
        Label lblDiscipline = new Label("Select Discipline:");
        lblDiscipline.setFont(Font.font("Roboto Mono", 16));
        ComboBox<String> cbDiscipline = new ComboBox<>();
        cbDiscipline.setPrefWidth(250);
        cbDiscipline.setPromptText("Choose discipline");

        // Elevi
        Label lblStudent = new Label("Select Student:");
        lblStudent.setFont(Font.font("Roboto Mono", 16));
        ComboBox<String> cbStudent = new ComboBox<>();
        cbStudent.setPrefWidth(250);
        cbStudent.setPromptText("Choose student");

        // Nota
        Label lblGrade = new Label("Select Grade:");
        lblGrade.setFont(Font.font("Roboto Mono", 16));
        ComboBox<Integer> cbGrade = new ComboBox<>();
        cbGrade.setPrefWidth(150);
        cbGrade.getItems().addAll(1,2,3,4,5,6,7,8,9,10);

        // Populare discipline și elevi din DB
        try (Connection conn = ConnectionDB.getConnection()) {
            ResultSet rsDisc = conn.createStatement().executeQuery("SELECT disciplina FROM Disciplini");
            while (rsDisc.next()) {
                cbDiscipline.getItems().add(rsDisc.getString("disciplina"));
            }

            ResultSet rsElevi = conn.createStatement().executeQuery(
                    "SELECT prenumeElev, numeElev, patronimicElev FROM Elevi"
            );
            while (rsElevi.next()) {
                String fullName = rsElevi.getString("prenumeElev") + " " +
                        rsElevi.getString("numeElev") + " " +
                        rsElevi.getString("patronimicElev");
                cbStudent.getItems().add(fullName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Buton Add
        Button btnAdd = new Button("Add Grade");
        btnAdd.setFont(Font.font("Roboto Mono", 16));
        btnAdd.setStyle("-fx-background-color: #5CB85C; -fx-text-fill: white; -fx-background-radius: 10;");
        btnAdd.setPrefWidth(200);

        btnAdd.setOnAction(e -> {
            String discipline = cbDiscipline.getValue();
            String studentFullName = cbStudent.getValue();
            Integer gradeValue = cbGrade.getValue();

            if (discipline == null || studentFullName == null || gradeValue == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select discipline, student, and grade.");
                alert.showAndWait();
                return;
            }

            String[] names = studentFullName.split(" ");

            try (Connection conn = ConnectionDB.getConnection()) {
                // idElev
                PreparedStatement pstElev = conn.prepareStatement(
                        "SELECT idElev FROM Elevi WHERE prenumeElev=? AND numeElev=? AND patronimicElev=?"
                );
                pstElev.setString(1, names[0]);
                pstElev.setString(2, names[1]);
                pstElev.setString(3, names[2]);
                ResultSet rsElev = pstElev.executeQuery();
                if (!rsElev.next()) return;
                int idElev = rsElev.getInt("idElev");

                // idDisciplina
                PreparedStatement pstDisc = conn.prepareStatement(
                        "SELECT idDisciplina FROM Disciplini WHERE disciplina=?"
                );
                pstDisc.setString(1, discipline);
                ResultSet rsDisc = pstDisc.executeQuery();
                if (!rsDisc.next()) return;
                int idDisciplina = rsDisc.getInt("idDisciplina");

                // Adaugă nota
                PreparedStatement pstInsert = conn.prepareStatement(
                        "INSERT INTO Note(nota, idElev, idDisciplina) VALUES(?, ?, ?)"
                );
                pstInsert.setInt(1, gradeValue);
                pstInsert.setInt(2, idElev);
                pstInsert.setInt(3, idDisciplina);

                int result = pstInsert.executeUpdate();
                if (result > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Grade added successfully!");
                    alert.showAndWait();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Aranjare Grid
        grid.add(lblDiscipline, 0, 0);
        grid.add(cbDiscipline, 1, 0);
        grid.add(lblStudent, 0, 1);
        grid.add(cbStudent, 1, 1);
        grid.add(lblGrade, 0, 2);
        grid.add(cbGrade, 1, 2);
        grid.add(btnAdd, 1, 3);

        pane.getChildren().addAll(lblTitle, grid);
        return pane;
    }




    private VBox createDeleteGradePane() {
        VBox pane = new VBox(20);
        pane.setPrefSize(700, 550);

        pane.setPadding(new Insets(30));
        pane.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);
        pane.setEffect(new DropShadow(8, Color.rgb(0,0,0,0.15)));

        Label lblTitle = new Label("Delete Grade");
        lblTitle.setFont(Font.font("Roboto Mono", 28));
        lblTitle.setStyle("-fx-text-fill: #272A31;");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));

        // Discipline
        Label lblDiscipline = new Label("Select Discipline:");
        lblDiscipline.setFont(Font.font("Roboto Mono", 16));
        ComboBox<String> cbDiscipline = new ComboBox<>();
        cbDiscipline.setPrefWidth(250);
        cbDiscipline.setPromptText("Choose discipline");

        // Elevi
        Label lblStudent = new Label("Select Student:");
        lblStudent.setFont(Font.font("Roboto Mono", 16));
        ComboBox<String> cbStudent = new ComboBox<>();
        cbStudent.setPrefWidth(250);
        cbStudent.setPromptText("Choose student");

        // Nota
        Label lblGrade = new Label("Select Grade:");
        lblGrade.setFont(Font.font("Roboto Mono", 16));
        ComboBox<Integer> cbGrade = new ComboBox<>();
        cbGrade.setPrefWidth(150);

        // Populare discipline și elevi
        try (Connection conn = ConnectionDB.getConnection()) {
            ResultSet rsDisc = conn.createStatement().executeQuery("SELECT disciplina FROM Disciplini");
            while (rsDisc.next()) {
                cbDiscipline.getItems().add(rsDisc.getString("disciplina"));
            }

            ResultSet rsElevi = conn.createStatement().executeQuery(
                    "SELECT prenumeElev, numeElev, patronimicElev FROM Elevi"
            );
            while (rsElevi.next()) {
                String fullName = rsElevi.getString("prenumeElev") + " " +
                        rsElevi.getString("numeElev") + " " +
                        rsElevi.getString("patronimicElev");
                cbStudent.getItems().add(fullName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Populare cbGrade în funcție de disciplină și elev
        ChangeListener<Object> populateGrade = (obs, oldVal, newVal) -> {
            cbGrade.getItems().clear();
            String discipline = cbDiscipline.getValue();
            String studentFullName = cbStudent.getValue();
            if (discipline == null || studentFullName == null) return;

            String[] names = studentFullName.split(" ");
            try (Connection conn = ConnectionDB.getConnection()) {
                // idElev
                PreparedStatement pstElev = conn.prepareStatement(
                        "SELECT idElev FROM Elevi WHERE prenumeElev=? AND numeElev=? AND patronimicElev=?"
                );
                pstElev.setString(1, names[0]);
                pstElev.setString(2, names[1]);
                pstElev.setString(3, names[2]);
                ResultSet rsElev = pstElev.executeQuery();
                if (!rsElev.next()) return;
                int idElev = rsElev.getInt("idElev");

                // idDisciplina
                PreparedStatement pstDisc = conn.prepareStatement(
                        "SELECT idDisciplina FROM Disciplini WHERE disciplina=?"
                );
                pstDisc.setString(1, discipline);
                ResultSet rsDisc = pstDisc.executeQuery();
                if (!rsDisc.next()) return;
                int idDisciplina = rsDisc.getInt("idDisciplina");

                // Note existente
                PreparedStatement pstNote = conn.prepareStatement(
                        "SELECT nota FROM Note WHERE idElev=? AND idDisciplina=?"
                );
                pstNote.setInt(1, idElev);
                pstNote.setInt(2, idDisciplina);
                ResultSet rsNote = pstNote.executeQuery();
                while (rsNote.next()) {
                    cbGrade.getItems().add(rsNote.getInt("nota"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        };

        cbDiscipline.valueProperty().addListener(populateGrade);
        cbStudent.valueProperty().addListener(populateGrade);

        // Buton Delete
        Button btnDelete = new Button("Delete Grade");
        btnDelete.setFont(Font.font("Roboto Mono", 16));
        btnDelete.setStyle("-fx-background-color: #D9534F; -fx-text-fill: white; -fx-background-radius: 10;");
        btnDelete.setPrefWidth(200);

        btnDelete.setOnAction(e -> {
            String discipline = cbDiscipline.getValue();
            String studentFullName = cbStudent.getValue();
            Integer gradeValue = cbGrade.getValue();

            if (discipline == null || studentFullName == null || gradeValue == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select discipline, student, and grade.");
                alert.showAndWait();
                return;
            }

            String[] names = studentFullName.split(" ");

            try (Connection conn = ConnectionDB.getConnection()) {
                // idElev
                PreparedStatement pstElev = conn.prepareStatement(
                        "SELECT idElev FROM Elevi WHERE prenumeElev=? AND numeElev=? AND patronimicElev=?"
                );
                pstElev.setString(1, names[0]);
                pstElev.setString(2, names[1]);
                pstElev.setString(3, names[2]);
                ResultSet rsElev = pstElev.executeQuery();
                if (!rsElev.next()) return;
                int idElev = rsElev.getInt("idElev");

                // idDisciplina
                PreparedStatement pstDisc = conn.prepareStatement(
                        "SELECT idDisciplina FROM Disciplini WHERE disciplina=?"
                );
                pstDisc.setString(1, discipline);
                ResultSet rsDisc = pstDisc.executeQuery();
                if (!rsDisc.next()) return;
                int idDisciplina = rsDisc.getInt("idDisciplina");

                // Ștergere nota
                PreparedStatement pstDelete = conn.prepareStatement(
                        "DELETE FROM Note WHERE idElev=? AND idDisciplina=? AND nota=?"
                );
                pstDelete.setInt(1, idElev);
                pstDelete.setInt(2, idDisciplina);
                pstDelete.setInt(3, gradeValue);

                int result = pstDelete.executeUpdate();
                if (result > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Grade deleted successfully!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No grade found with these details!");
                    alert.showAndWait();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Aranjare Grid
        grid.add(lblDiscipline, 0, 0);
        grid.add(cbDiscipline, 1, 0);
        grid.add(lblStudent, 0, 1);
        grid.add(cbStudent, 1, 1);
        grid.add(lblGrade, 0, 2);
        grid.add(cbGrade, 1, 2);
        grid.add(btnDelete, 1, 3);

        pane.getChildren().addAll(lblTitle, grid);
        return pane;
    }




    private VBox UpdateGrade() {
        VBox pane = new VBox(20);
        pane.setPrefSize(700, 550);

        pane.setPadding(new Insets(30));
        pane.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);
        pane.setEffect(new DropShadow(8, Color.rgb(0,0,0,0.15)));

        Label lblTitle = new Label("Update Grade");
        lblTitle.setFont(Font.font("Roboto Mono", 28));
        lblTitle.setStyle("-fx-text-fill: #272A31;");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20));

        // Discipline
        Label lblDiscipline = new Label("Select Discipline:");
        lblDiscipline.setFont(Font.font("Roboto Mono", 16));
        ComboBox<String> cbDiscipline = new ComboBox<>();
        cbDiscipline.setPrefWidth(250);
        cbDiscipline.setPromptText("Choose discipline");

        // Elevi
        Label lblStudent = new Label("Select Student:");
        lblStudent.setFont(Font.font("Roboto Mono", 16));
        ComboBox<String> cbStudent = new ComboBox<>();
        cbStudent.setPrefWidth(250);
        cbStudent.setPromptText("Choose student");

        // Nota veche
        Label lblOldGrade = new Label("Select Old Grade:");
        lblOldGrade.setFont(Font.font("Roboto Mono", 16));
        ComboBox<Integer> cbOldGrade = new ComboBox<>();
        cbOldGrade.setPrefWidth(150);

        // Nota noua
        Label lblNewGrade = new Label("Select New Grade:");
        lblNewGrade.setFont(Font.font("Roboto Mono", 16));
        ComboBox<Integer> cbNewGrade = new ComboBox<>();
        cbNewGrade.setPrefWidth(150);
        cbNewGrade.getItems().addAll(1,2,3,4,5,6,7,8,9,10);

        // Populare discipline și elevi
        try (Connection conn = ConnectionDB.getConnection()) {
            ResultSet rsDisc = conn.createStatement().executeQuery("SELECT disciplina FROM Disciplini");
            while (rsDisc.next()) {
                cbDiscipline.getItems().add(rsDisc.getString("disciplina"));
            }

            ResultSet rsElevi = conn.createStatement().executeQuery(
                    "SELECT prenumeElev, numeElev, patronimicElev FROM Elevi"
            );
            while (rsElevi.next()) {
                String fullName = rsElevi.getString("prenumeElev") + " " +
                        rsElevi.getString("numeElev") + " " +
                        rsElevi.getString("patronimicElev");
                cbStudent.getItems().add(fullName);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // Populare cbOldGrade în funcție de discipline și elev
        ChangeListener<Object> populateOldGrade = (obs, oldVal, newVal) -> {
            cbOldGrade.getItems().clear();
            String discipline = cbDiscipline.getValue();
            String studentFullName = cbStudent.getValue();
            if (discipline == null || studentFullName == null) return;

            String[] names = studentFullName.split(" ");
            try (Connection conn = ConnectionDB.getConnection()) {
                // idElev
                PreparedStatement pstElev = conn.prepareStatement(
                        "SELECT idElev FROM Elevi WHERE prenumeElev=? AND numeElev=? AND patronimicElev=?"
                );
                pstElev.setString(1, names[0]);
                pstElev.setString(2, names[1]);
                pstElev.setString(3, names[2]);
                ResultSet rsElev = pstElev.executeQuery();
                if (!rsElev.next()) return;
                int idElev = rsElev.getInt("idElev");

                // idDisciplina
                PreparedStatement pstDisc = conn.prepareStatement(
                        "SELECT idDisciplina FROM Disciplini WHERE disciplina=?"
                );
                pstDisc.setString(1, discipline);
                ResultSet rsDisc = pstDisc.executeQuery();
                if (!rsDisc.next()) return;
                int idDisciplina = rsDisc.getInt("idDisciplina");

                // Note existente
                PreparedStatement pstNote = conn.prepareStatement(
                        "SELECT nota FROM Note WHERE idElev=? AND idDisciplina=?"
                );
                pstNote.setInt(1, idElev);
                pstNote.setInt(2, idDisciplina);
                ResultSet rsNote = pstNote.executeQuery();
                while (rsNote.next()) {
                    cbOldGrade.getItems().add(rsNote.getInt("nota"));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        };

        cbDiscipline.valueProperty().addListener(populateOldGrade);
        cbStudent.valueProperty().addListener(populateOldGrade);

        // Buton Update
        Button btnUpdate = new Button("Update Grade");
        btnUpdate.setFont(Font.font("Roboto Mono", 16));
        btnUpdate.setStyle("-fx-background-color: #FFA500; -fx-text-fill: white; -fx-background-radius: 10;");
        btnUpdate.setPrefWidth(200);

        btnUpdate.setOnAction(e -> {
            String discipline = cbDiscipline.getValue();
            String studentFullName = cbStudent.getValue();
            Integer oldGrade = cbOldGrade.getValue();
            Integer newGrade = cbNewGrade.getValue();

            if (discipline == null || studentFullName == null || oldGrade == null || newGrade == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select discipline, student, old grade and new grade.");
                alert.showAndWait();
                return;
            }

            String[] names = studentFullName.split(" ");

            try (Connection conn = ConnectionDB.getConnection()) {
                // idElev
                PreparedStatement pstElev = conn.prepareStatement(
                        "SELECT idElev FROM Elevi WHERE prenumeElev=? AND numeElev=? AND patronimicElev=?"
                );
                pstElev.setString(1, names[0]);
                pstElev.setString(2, names[1]);
                pstElev.setString(3, names[2]);
                ResultSet rsElev = pstElev.executeQuery();
                if (!rsElev.next()) return;
                int idElev = rsElev.getInt("idElev");

                // idDisciplina
                PreparedStatement pstDisc = conn.prepareStatement(
                        "SELECT idDisciplina FROM Disciplini WHERE disciplina=?"
                );
                pstDisc.setString(1, discipline);
                ResultSet rsDisc = pstDisc.executeQuery();
                if (!rsDisc.next()) return;
                int idDisciplina = rsDisc.getInt("idDisciplina");

                // Update nota
                PreparedStatement pstUpdate = conn.prepareStatement(
                        "UPDATE Note SET nota=? WHERE idElev=? AND idDisciplina=? AND nota=?"
                );
                pstUpdate.setInt(1, newGrade);
                pstUpdate.setInt(2, idElev);
                pstUpdate.setInt(3, idDisciplina);
                pstUpdate.setInt(4, oldGrade);

                int result = pstUpdate.executeUpdate();
                if (result > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Grade updated successfully!");
                    alert.showAndWait();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "No matching grade found to update.");
                    alert.showAndWait();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        // Aranjare în Grid
        grid.add(lblDiscipline, 0, 0);
        grid.add(cbDiscipline, 1, 0);
        grid.add(lblStudent, 0, 1);
        grid.add(cbStudent, 1, 1);
        grid.add(lblOldGrade, 0, 2);
        grid.add(cbOldGrade, 1, 2);
        grid.add(lblNewGrade, 0, 3);
        grid.add(cbNewGrade, 1, 3);
        grid.add(btnUpdate, 1, 4);

        pane.getChildren().addAll(lblTitle, grid);
        return pane;
    }


    private AnchorPane createDailyAttendancePane() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(700, 600);
        pane.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);

        Label lblTitle = new Label("Daily Attendance");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #272A31;");
        AnchorPane.setTopAnchor(lblTitle, 40.0);
        AnchorPane.setLeftAnchor(lblTitle, 220.0);

        // Select discipline
        ComboBox<String> cbDiscipline = new ComboBox<>();
        cbDiscipline.setPromptText("Select discipline");
        cbDiscipline.setPrefWidth(300);
        AnchorPane.setTopAnchor(cbDiscipline, 100.0);
        AnchorPane.setLeftAnchor(cbDiscipline, 200.0);

        try (Connection conn = ConnectionDB.getConnection();
             ResultSet rs = conn.createStatement().executeQuery("SELECT disciplina FROM Disciplini")) {
            while (rs.next()) cbDiscipline.getItems().add(rs.getString("disciplina"));
        } catch (SQLException ex) { ex.printStackTrace(); }

        TableView<Elev> table = new TableView<>();
        table.setPrefWidth(600);
        table.setPrefHeight(350);
        AnchorPane.setTopAnchor(table, 160.0);
        AnchorPane.setLeftAnchor(table, 50.0);

        TableColumn<Elev, String> colNume = new TableColumn<>("Nume");
        colNume.setCellValueFactory(new PropertyValueFactory<>("numeElev"));

        TableColumn<Elev, String> colPrenume = new TableColumn<>("Prenume");
        colPrenume.setCellValueFactory(new PropertyValueFactory<>("prenumeElev"));

        TableColumn<Elev, Boolean> colPrezenta = new TableColumn<>("Present");
        colPrezenta.setCellValueFactory(cellData -> cellData.getValue().presentProperty());
        colPrezenta.setCellFactory(CheckBoxTableCell.forTableColumn(colPrezenta));
        colPrezenta.setEditable(true);

        table.setEditable(true);

        // 🔹 Colorare dinamică a rândurilor în funcție de prezență
        table.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Elev elev, boolean empty) {
                super.updateItem(elev, empty);
                if (elev == null || empty) {
                    setStyle("");
                } else if (elev.isPresent()) {
                    setStyle("-fx-background-color: rgba(56,142,60,0.2);"); // verde deschis
                } else {
                    setStyle("-fx-background-color: rgba(211,47,47,0.15);"); // roșu deschis
                }

                // 🔹 Ascultă modificările de stare în timp real
                if (elev != null) {
                    elev.presentProperty().addListener((obs, oldVal, newVal) -> {
                        if (newVal) {
                            setStyle("-fx-background-color: rgba(56,142,60,0.2);");
                        } else {
                            setStyle("-fx-background-color: rgba(211,47,47,0.15);");
                        }
                    });
                }
            }
        });



        table.getColumns().addAll(colNume, colPrenume, colPrezenta);

        cbDiscipline.setOnAction(e -> {
            ObservableList<Elev> elevi = FXCollections.observableArrayList();
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement("SELECT * FROM Elevi");
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    elevi.add(new Elev(
                            rs.getInt("idElev"),
                            rs.getString("numeElev"),
                            rs.getString("prenumeElev"),
                            rs.getString("patronimicElev"),
                            rs.getDate("DataNasterii"),
                            rs.getString("IDNP"),
                            rs.getString("email"),
                            rs.getString("telefon")
                    ));
                }
            } catch (SQLException ex) { ex.printStackTrace(); }
            table.setItems(elevi);
        });

        Button btnSave = new Button("Save Attendance");
        btnSave.setPrefSize(200, 40);
        btnSave.setStyle("-fx-background-color: #398585; -fx-text-fill: white; -fx-background-radius: 10;");
        AnchorPane.setBottomAnchor(btnSave, 40.0);
        AnchorPane.setLeftAnchor(btnSave, 250.0);

        btnSave.setOnAction(e -> {
            String selectedDisc = cbDiscipline.getValue();
            if (selectedDisc == null) return;
            int idDisc = -1;
            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement("SELECT idDisciplina FROM Disciplini WHERE disciplina=?")) {
                ps.setString(1, selectedDisc);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) idDisc = rs.getInt(1);
            } catch (SQLException ex) { ex.printStackTrace(); }

            LocalDate today = LocalDate.now();
            for (Elev elev : table.getItems()) {
                boolean prez = elev.isPresent(); // dacă ai Checkbox model, poți obține valoarea reală
                try (Connection conn = ConnectionDB.getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "INSERT INTO Prezente (idElev, idDisciplina, data, prezent) VALUES (?, ?, ?, ?)")) {
                    ps.setInt(1, elev.getIdElev());
                    ps.setInt(2, idDisc);
                    ps.setDate(3, Date.valueOf(today));
                    ps.setBoolean(4, prez);
                    ps.executeUpdate();
                } catch (SQLException ex) { ex.printStackTrace(); }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Attendance saved successfully!");
            alert.showAndWait();
        });

        pane.getChildren().addAll(lblTitle, cbDiscipline, table, btnSave);
        return pane;
    }

    private AnchorPane createAttendanceReportPane() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(700, 600);
        pane.setStyle("""
        -fx-background-color: white;
        -fx-background-radius: 20;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-border-radius: 20;
    """);

        Label lblTitle = new Label("Attendance Report");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #272A31;");
        AnchorPane.setTopAnchor(lblTitle, 40.0);
        AnchorPane.setLeftAnchor(lblTitle, 220.0);

        TableView<AttendanceReport> table = new TableView<>();
        table.setPrefWidth(600);
        table.setPrefHeight(400);
        AnchorPane.setTopAnchor(table, 120.0);
        AnchorPane.setLeftAnchor(table, 50.0);

        TableColumn<AttendanceReport, String> colName = new TableColumn<>("Student");
        colName.setCellValueFactory(new PropertyValueFactory<>("studentName"));

        TableColumn<AttendanceReport, Long> colPresent = new TableColumn<>("Days Present");
        colPresent.setCellValueFactory(new PropertyValueFactory<>("daysPresent"));

        TableColumn<AttendanceReport, Long> colAbsent = new TableColumn<>("Days Absent");
        colAbsent.setCellValueFactory(new PropertyValueFactory<>("daysAbsent"));

        table.getColumns().addAll(colName, colPresent, colAbsent);

        ObservableList<AttendanceReport> list = FXCollections.observableArrayList();

        String sql = """
        SELECT e.prenumeElev + ' ' + e.numeElev AS nume,
               SUM(CASE WHEN p.prezent = 1 THEN 1 ELSE 0 END) AS prezente,
               SUM(CASE WHEN p.prezent = 0 THEN 1 ELSE 0 END) AS absente
        FROM Prezente p
        JOIN Elevi e ON p.idElev = e.idElev
        GROUP BY e.prenumeElev, e.numeElev
    """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new AttendanceReport(
                        rs.getString("nume"),
                        rs.getLong("prezente"),
                        rs.getLong("absente")
                ));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }

        table.setItems(list);
        pane.getChildren().addAll(lblTitle, table);
        return pane;
    }


    private AnchorPane createAveragePerSubjectPane() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(900, 600);
        pane.setStyle("-fx-background-color: white; -fx-background-radius: 20;");

        Label lblTitle = new Label("Average Grade per Subject");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #272A31;");
        AnchorPane.setTopAnchor(lblTitle, 30.0);
        AnchorPane.setLeftAnchor(lblTitle, 200.0);

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Subject");
        xAxis.setTickLabelRotation(-25);

        NumberAxis yAxis = new NumberAxis(0, 10, 1);
        yAxis.setLabel("Average Grade");

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setLegendVisible(false);
        chart.setAnimated(false);
        chart.setCache(false);
        chart.setStyle("-fx-background-color: transparent;");
        chart.setCategoryGap(25);
        chart.setBarGap(8);
        chart.setPrefSize(800, 450);

        AnchorPane.setTopAnchor(chart, 100.0);
        AnchorPane.setLeftAnchor(chart, 50.0);
        AnchorPane.setRightAnchor(chart, 50.0);
        AnchorPane.setBottomAnchor(chart, 50.0);

        pane.getChildren().addAll(lblTitle, chart);

        // 🔹 SQL Query
        String sql = """
        SELECT 
            d.disciplina AS Disciplina,
            CAST(AVG(CAST(n.nota AS FLOAT)) AS DECIMAL(4,2)) AS Medie
        FROM Note n
        JOIN Disciplini d ON n.idDisciplina = d.idDisciplina
        GROUP BY d.disciplina
        ORDER BY Medie DESC
        """;

        List<XYChart.Data<String, Number>> dataList = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String disciplina = rs.getString("Disciplina");
                double medie = rs.getDouble("Medie");
                dataList.add(new XYChart.Data<>(disciplina, medie));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // ✅ Fix final – Așteptăm până când panoul este adăugat în scenă
        pane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Platform.runLater(() -> {
                    if (dataList.isEmpty()) {
                        System.out.println("⚠️ No data found for AveragePerSubjectPane");
                        return;
                    }

                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    series.getData().addAll(dataList);
                    chart.getData().add(series);

                    // Adăugăm etichete + stil
                    for (XYChart.Data<String, Number> data : series.getData()) {
                        Node node = data.getNode();
                        if (node == null) continue;

                        // Culoare albastru mai închis spre deschis
                        node.setStyle("-fx-background-color: linear-gradient(to top, #5494da, #86cefa);");

                        // Eticheta numerică
                        Label label = new Label(String.format("%.2f", data.getYValue().doubleValue()));
                        label.setStyle("-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #1B1F29;");
                        label.setTranslateY(-20);

                        if (node.getParent() instanceof StackPane parent && !parent.getChildren().contains(label)) {
                            parent.getChildren().add(label);
                            StackPane.setAlignment(label, Pos.TOP_CENTER);
                        }
                    }
                });
            }
        });

        return pane;
    }


    private AnchorPane createTopStudentsPane() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(800, 600);
        pane.setStyle("-fx-background-color: white; -fx-background-radius: 20;");

        Label lblTitle = new Label("Top Students per Subject");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #272A31;");
        AnchorPane.setTopAnchor(lblTitle, 30.0);
        AnchorPane.setLeftAnchor(lblTitle, 200.0);

        // 🔹 ComboBox pentru alegerea disciplinei
        ComboBox<String> cbDiscipline = new ComboBox<>();
        cbDiscipline.setPromptText("Select Subject");
        cbDiscipline.setPrefWidth(250);
        AnchorPane.setTopAnchor(cbDiscipline, 90.0);
        AnchorPane.setLeftAnchor(cbDiscipline, 270.0);

        // 🔹 Axele și graficul
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Students");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Average Grade");

        BarChart<String, Number> chart = new BarChart<>(xAxis, yAxis);
        chart.setPrefSize(700, 400);
        chart.setLegendVisible(false);
        AnchorPane.setTopAnchor(chart, 150.0);
        AnchorPane.setLeftAnchor(chart, 50.0);

        // 🔹 Încarcă lista de discipline din baza de date
        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT disciplina FROM Disciplini");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                cbDiscipline.getItems().add(rs.getString("disciplina"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        // 🔹 Eveniment: schimbare disciplină => actualizează graficul
        cbDiscipline.setOnAction(e -> {
            String disciplinaSelectata = cbDiscipline.getValue();
            if (disciplinaSelectata == null) return;

            chart.getData().clear();

            String sql = """
            SELECT TOP 10
                e.prenumeElev + ' ' + e.numeElev AS Nume,
                AVG(CAST(n.nota AS FLOAT)) AS Medie
            FROM Note n
            JOIN Elevi e ON n.idElev = e.idElev
            JOIN Disciplini d ON n.idDisciplina = d.idDisciplina
            WHERE d.disciplina = ?
            GROUP BY e.prenumeElev, e.numeElev
            ORDER BY Medie DESC
            """;

            try (Connection conn = ConnectionDB.getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, disciplinaSelectata);
                ResultSet rs = ps.executeQuery();

                XYChart.Series<String, Number> serie = new XYChart.Series<>();
                serie.setName(disciplinaSelectata);

                while (rs.next()) {
                    serie.getData().add(new XYChart.Data<>(
                            rs.getString("Nume"), rs.getDouble("Medie")));
                }

                chart.getData().add(serie);

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        pane.getChildren().addAll(lblTitle, cbDiscipline, chart);
        ScrollPane scroll = new ScrollPane(pane);
        scroll.setFitToWidth(true);
        scroll.setFitToHeight(true);
        scroll.setStyle("-fx-background-color: transparent;");
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        AnchorPane root = new AnchorPane(scroll);
        AnchorPane.setTopAnchor(scroll, 0.0);
        AnchorPane.setLeftAnchor(scroll, 0.0);
        AnchorPane.setRightAnchor(scroll, 0.0);
        AnchorPane.setBottomAnchor(scroll, 0.0);

        return root;

    }



    private AnchorPane createGradeDistributionPane() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(800, 600);
        pane.setStyle("-fx-background-color: white; -fx-background-radius: 20;");

        Label lblTitle = new Label("Grades Distribution");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #272A31;");
        AnchorPane.setTopAnchor(lblTitle, 30.0);
        AnchorPane.setLeftAnchor(lblTitle, 250.0);

        PieChart chart = new PieChart();
        chart.setPrefSize(700, 400);
        AnchorPane.setTopAnchor(chart, 100.0);
        AnchorPane.setLeftAnchor(chart, 50.0);

        String sql = "SELECT nota, COUNT(*) AS Count FROM Note GROUP BY nota";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                chart.getData().add(new PieChart.Data(
                        "Grade " + rs.getInt("nota"),
                        rs.getInt("Count")
                ));
            }
        } catch (SQLException ex) { ex.printStackTrace(); }

        pane.getChildren().addAll(lblTitle, chart);
        return pane;
    }


    private AnchorPane createExportReportsPane() {
        AnchorPane pane = new AnchorPane();
        pane.setPrefSize(900, 600);
        pane.setStyle("-fx-background-color: white; -fx-background-radius: 20;");

        // 🔹 Titlu
        Label lblTitle = new Label("Export Reports");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #1B1F29;");
        AnchorPane.setTopAnchor(lblTitle, 40.0);
        AnchorPane.setLeftAnchor(lblTitle, 310.0);

        // 🔹 Subtitlu
        Label lblDesc = new Label("Choose your preferred format to export student grades:");
        lblDesc.setStyle("-fx-font-size: 15px; -fx-text-fill: #555;");
        AnchorPane.setTopAnchor(lblDesc, 100.0);
        AnchorPane.setLeftAnchor(lblDesc, 240.0);

        // 🔹 Linie decorativă sub titlu
        Line line = new Line(180, 80, 720, 80);
        line.setStroke(Color.web("#2b6cb0"));
        line.setStrokeWidth(2);

        // 🔹 Secțiune progres vizual
        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(400);
        progressBar.setStyle("-fx-accent: #77ccff;");
        AnchorPane.setTopAnchor(progressBar, 350.0);
        AnchorPane.setLeftAnchor(progressBar, 250.0);

        Label lblProgress = new Label("Ready to export");
        lblProgress.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;");
        AnchorPane.setTopAnchor(lblProgress, 380.0);
        AnchorPane.setLeftAnchor(lblProgress, 410.0);

        // 🔹 Container pentru butoane
        HBox btnBox = new HBox(30);
        btnBox.setAlignment(Pos.CENTER);
        AnchorPane.setTopAnchor(btnBox, 200.0);
        AnchorPane.setLeftAnchor(btnBox, 100.0);
        AnchorPane.setRightAnchor(btnBox, 100.0);

        // Stil de bază pentru butoane
        String baseStyle = """
    -fx-background-color: #2b6cb0;
    -fx-text-fill: white;
    -fx-font-size: 15px;
    -fx-font-weight: bold;
    -fx-background-radius: 8;
    -fx-cursor: hand;
    -fx-padding: 12 30 12 30;
    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 6, 0, 0, 2);
    """;

        String hoverStyle = """
    -fx-background-color: #1e4f82;
    -fx-text-fill: white;
    -fx-font-size: 15px;
    -fx-font-weight: bold;
    -fx-background-radius: 8;
    -fx-cursor: hand;
    -fx-padding: 12 30 12 30;
    -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.2), 10, 0, 0, 3);
    """;

        // 🔹 Buton CSV
        Button btnCSV = new Button("Export Grades to CSV");
        btnCSV.setStyle(baseStyle);
        btnCSV.setOnMouseEntered(e -> btnCSV.setStyle(hoverStyle));
        btnCSV.setOnMouseExited(e -> btnCSV.setStyle(baseStyle));
        btnCSV.setOnAction(e -> {
            progressBar.setProgress(0.3);
            lblProgress.setText("Exporting data...");
            exportToCSV();
            progressBar.setProgress(1.0);
            lblProgress.setText("Export complete!");
        });

        // 🔹 Buton PDF
        Button btnPDF = new Button("Export Grades to PDF");
        btnPDF.setStyle(baseStyle);
        btnPDF.setOnMouseEntered(e -> btnPDF.setStyle(hoverStyle));
        btnPDF.setOnMouseExited(e -> btnPDF.setStyle(baseStyle));
        btnPDF.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "PDF export coming soon!");
            alert.showAndWait();
        });

        // 🔹 Buton Excel
        Button btnExcel = new Button("Export Grades to Excel");
        btnExcel.setStyle(baseStyle);
        btnExcel.setOnMouseEntered(e -> btnExcel.setStyle(hoverStyle));
        btnExcel.setOnMouseExited(e -> btnExcel.setStyle(baseStyle));
        btnExcel.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Excel export coming soon!");
            alert.showAndWait();
        });

        btnBox.getChildren().addAll(btnCSV, btnPDF, btnExcel);

        pane.getChildren().addAll(lblTitle, lblDesc, line, btnBox, progressBar, lblProgress);
        return pane;
    }


    private void exportToCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Grades Report");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (PrintWriter pw = new PrintWriter(file)) {
                pw.println("Student, Subject, Grade");

                try (Connection conn = ConnectionDB.getConnection();
                     PreparedStatement ps = conn.prepareStatement(
                             "SELECT e.numeElev, d.disciplina, n.nota " +
                                     "FROM Note n " +
                                     "JOIN Elevi e ON n.idElev = e.idElev " +
                                     "JOIN Disciplini d ON n.idDisciplina = d.idDisciplina")) {
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        pw.printf("%s,%s,%d%n",
                                rs.getString("numeElev"),
                                rs.getString("disciplina"),
                                rs.getInt("nota"));
                    }
                }

                Alert alert = new Alert(Alert.AlertType.INFORMATION, "✅ Report exported successfully!");
                alert.showAndWait();

            } catch (Exception ex) {
                ex.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "❌ Failed to export CSV file!");
                alert.showAndWait();
            }
        }
    }





}
