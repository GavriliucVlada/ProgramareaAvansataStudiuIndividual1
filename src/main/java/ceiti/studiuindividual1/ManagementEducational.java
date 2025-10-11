package ceiti.studiuindividual1;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class ManagementEducational extends BorderPane {

    int numarUseri = UserDAO.getUserCount();
    int numarElevi = UserDAO.getElevCount();
    int numarDiscipline = UserDAO.getDisciplinaCount();
    int numarNote = UserDAO.getNoteCount();

    private User currentUser;

    public ManagementEducational(User user) {
        this.currentUser = user;
        initUI();
    }

    private void initUI() {
        // --- LEFT PANEL ---
        VBox leftPanel = new VBox(15);
        leftPanel.setPadding(new Insets(20));
        leftPanel.setPrefWidth(250);
        leftPanel.setStyle("-fx-background-color: rgba(54,56,68,1);");

        // --- TOP LEFT BOX ---
        StackPane topLeftBox = new StackPane();
        topLeftBox.setPrefSize(250, 40);
        topLeftBox.setStyle("-fx-background-color: rgba(212,115,122,1);");

        Label lblSchooling = new Label("Schooling");
        lblSchooling.setTextFill(Color.WHITE);
        lblSchooling.setFont(Font.font("Roboto Mono", 18));
        topLeftBox.getChildren().add(lblSchooling);

        // --- TOP BAR ---
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

        // --- MAIN PANEL (centru) ---
        VBox mainPanel = new VBox(20);
        mainPanel.setStyle("-fx-background-color: rgba(242,242,242,1);");
        mainPanel.setPadding(new Insets(20, 20, 20, 20));




        // === DASHBOARD: 4 CARDURI DE STATISTICI ===
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

        // === NOUA SECȚIUNE: 4 CARDURI DE ACȚIUNE (mai mici, fără panou alb mare) ===
        GridPane actionCards = new GridPane();
        actionCards.setHgap(10);
        actionCards.setVgap(10);
        actionCards.setAlignment(Pos.CENTER_RIGHT);
        actionCards.setPadding(new Insets(0, 20, 0, 0));
        actionCards.setStyle("-fx-background-color: transparent;");

        actionCards.add(createActionCard("Add Student", "/images/addS.png"), 0, 0);
        actionCards.add(createActionCard("Add Employee", "/images/addE.png"), 1, 0);
        actionCards.add(createActionCard("Plan Academic Calendar", "/images/addC.png"), 0, 1);
        actionCards.add(createActionCard("Send Announcement", "/images/addM.png"), 1, 1);



        AnchorPane container1 = new AnchorPane();
        VBox container2 = new VBox(actionCards);

        container1.setMaxSize(700, 600);
        container1.setPrefSize(700, 600);
        container1.setMinSize(700, 600);


        HBox container = new HBox(container1, container2);
        container2.setPadding(new Insets(0,10,0,20));


        // === ADAUGĂ ÎN MAIN PANEL ===
        mainPanel.getChildren().addAll(statsBox, container);

        // --- TOP CONTAINER ---
        BorderPane topContainer = new BorderPane();
        topContainer.setLeft(topLeftBox);
        topContainer.setCenter(topBar);

        // --- LEFT MENU ---
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
        RoundedButton btnModificare = new RoundedButton("Modify", null);
        btnModificare.setStyle("-fx-background-color: #4E5665; -fx-background-radius: 10;");

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

        RoundedButton btnEmployee = new RoundedButton("Employee", "/images/admin.png");

        addHoverEffect(btnStudent, "rgba(39,42,49,1)", "#6C7A89");
        addHoverEffect(btnEmployee, "rgba(39,42,49,1)", "#6C7A89");
        addHoverEffect(btnAfisare, "#4E5665", "#6C7A89");
        addHoverEffect(btnAdaugare, "#4E5665", "#6C7A89");
        addHoverEffect(btnStergere, "#4E5665", "#6C7A89");
        addHoverEffect(btnModificare, "#4E5665", "#6C7A89");


        RoundedButton btnClose = new RoundedButton("Close Application", null);
        addHoverEffect(btnClose, "rgba(39,42,49,1)", "#6C7A89");
        btnClose.setOnAction(e -> Platform.exit());


        leftPanel.getChildren().addAll(btnStudent, studentSubMenu, btnEmployee, btnClose);

        // --- FINAL ROOT ---
        setLeft(leftPanel);
        setTop(topContainer);
        setCenter(mainPanel);
    }

    // === EFECT HOVER ===
    private void addHoverEffect(RoundedButton button, String normalColor, String hoverColor) {
        button.setStyle("-fx-background-color: " + normalColor + "; -fx-background-radius: 15; -fx-text-fill: white;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + hoverColor + "; -fx-background-radius: 15; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + normalColor + "; -fx-background-radius: 15; -fx-text-fill: white;"));
    }

    // === CARD STATISTIC ===
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

    // === CARD DE ACȚIUNE ===
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
        pane.setPrefSize(600, 550);
        pane.setStyle("""
            -fx-background-color: #F2F2F2;
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
        AnchorPane.setLeftAnchor(Title, 194.0);


        // Etichete
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




        // Câmpuri
        TextField txtNume = createStyledTextField();

        TextField txtPrenume = createStyledTextField();

        TextField txtPatronimic = createStyledTextField();

        TextField dateDataNasterii = createStyledTextField();

        TextField txtIDNP = createStyledTextField();

        TextField txtEmail = createStyledTextField();

        TextField txtTelefon = createStyledTextField();

        // Buton Adaugă
        Button btnAdd = new Button("Add");
        btnAdd.setStyle("-fx-background-color: #363844; -fx-text-fill: white; -fx-background-radius: 5; -fx-border-radius: 5;");
        btnAdd.setFont(Fonts.RobotoMonoMedium20);
        btnAdd.setPrefSize(250, 45);
        btnAdd.setMinSize(250, 45);
        btnAdd.setMaxSize(250, 45);

        // Poziționare
        AnchorPane.setTopAnchor(lblNume, 124.0);
        AnchorPane.setLeftAnchor(lblNume, 40.0);
        AnchorPane.setTopAnchor(txtNume, 161.0);
        AnchorPane.setLeftAnchor(txtNume, 40.0);

        AnchorPane.setTopAnchor(lblPrenume, 211.0);
        AnchorPane.setLeftAnchor(lblPrenume, 40.0);
        AnchorPane.setTopAnchor(txtPrenume, 248.0);
        AnchorPane.setLeftAnchor(txtPrenume, 40.0);

        AnchorPane.setTopAnchor(lblPatronimic, 298.0);
        AnchorPane.setLeftAnchor(lblPatronimic, 40.0);
        AnchorPane.setTopAnchor(txtPatronimic, 335.0);
        AnchorPane.setLeftAnchor(txtPatronimic, 40.0);

        AnchorPane.setTopAnchor(lblData, 385.0);
        AnchorPane.setLeftAnchor(lblData, 40.0);
        AnchorPane.setTopAnchor(dateDataNasterii, 422.0);
        AnchorPane.setLeftAnchor(dateDataNasterii, 40.0);

        AnchorPane.setTopAnchor(lblIDNP, 124.0);
        AnchorPane.setLeftAnchor(lblIDNP, 318.0);
        AnchorPane.setTopAnchor(txtIDNP, 161.0);
        AnchorPane.setLeftAnchor(txtIDNP, 318.0);

        AnchorPane.setTopAnchor(lblEmail, 211.0);
        AnchorPane.setLeftAnchor(lblEmail, 318.0);
        AnchorPane.setTopAnchor(txtEmail, 248.0);
        AnchorPane.setLeftAnchor(txtEmail, 318.0);

        AnchorPane.setTopAnchor(lblTelefon, 298.0);
        AnchorPane.setLeftAnchor(lblTelefon, 318.0);
        AnchorPane.setTopAnchor(txtTelefon, 335.0);
        AnchorPane.setLeftAnchor(txtTelefon, 318.0);

        AnchorPane.setTopAnchor(btnAdd, 422.0);
        AnchorPane.setLeftAnchor(btnAdd, 318.0);

        pane.getChildren().addAll(Title, lblNume, txtNume, lblPrenume, txtPrenume, lblPatronimic, txtPatronimic,
                lblData, dateDataNasterii, lblIDNP, txtIDNP, lblEmail, txtEmail, lblTelefon, txtTelefon,
                btnAdd);

        // Event pentru butonul Adaugă
        btnAdd.setOnAction(e -> {
            String nume = txtNume.getText().trim();
            String prenume = txtPrenume.getText().trim();
            String patronimic = txtPatronimic.getText().trim();
            String dataNasterii = dateDataNasterii.getText().trim();
            String idnp = txtIDNP.getText().trim();
            String email = txtEmail.getText().trim();
            String telefon = txtTelefon.getText().trim();

            // Verificare câmpuri goale
            if (nume.isEmpty() || prenume.isEmpty() || dataNasterii.isEmpty() ||
                    idnp.isEmpty() || email.isEmpty() || telefon.isEmpty()) {
                System.out.println("Completați toate câmpurile obligatorii!");
                return;
            }

            // Conversie dată din text
            Date dateOfBirth;
            try {
                // Aici poți schimba formatul, de ex. "dd.MM.yyyy"
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate localDate = LocalDate.parse(dataNasterii, formatter);
                dateOfBirth = Date.valueOf(localDate);
            } catch (Exception ex) {
                System.out.println("Formatul datei este invalid! Folosiți formatul yyyy-MM-dd (ex: 2006-08-13)");
                return;
            }

            // Inserare în baza de date
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

                    // Curățare câmpuri
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
        pane.setPrefSize(700, 600);
        pane.setStyle("""
            -fx-background-color: #F2F2F2;
            -fx-background-radius: 20;
            -fx-border-color: #B0B0B0;
            -fx-border-width: 1;
            -fx-border-radius: 20;
        """);

        // Titlu
        Label lblTitle = new Label("Student's List");
        lblTitle.setFont(Fonts.RobotoMonoBold32);
        lblTitle.setStyle("-fx-text-fill: #272A31;");
        AnchorPane.setTopAnchor(lblTitle, 30.0);
        AnchorPane.setLeftAnchor(lblTitle, 194.0);

        // Creare tabel
        TableView<Elev> table = new TableView<>();
        table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.setPrefHeight(450);
        table.setStyle("""
        -fx-background-color: #F8F9FA;
        -fx-border-color: #B0B0B0;
        -fx-border-width: 1;
        -fx-table-cell-border-color: #B0B0B0;
        -fx-font-size: 13px;
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
        colData.setPrefWidth(80);

        TableColumn<Elev, String> colIDNP = new TableColumn<>("IDNP");
        colIDNP.setCellValueFactory(new PropertyValueFactory<>("idnp"));
        colIDNP.setPrefWidth(100);

        TableColumn<Elev, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colEmail.setPrefWidth(160);

        TableColumn<Elev, String> colTelefon = new TableColumn<>("Telefon");
        colTelefon.setCellValueFactory(new PropertyValueFactory<>("telefon"));
        colTelefon.setPrefWidth(80);

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

        // Date din baza de date
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

        // Adăugăm componentele în AnchorPane
        pane.getChildren().addAll(lblTitle, table);
        AnchorPane.setTopAnchor(table, 100.0);
        AnchorPane.setBottomAnchor(table, 20.0);
        AnchorPane.setLeftAnchor(table, 20.0);
        AnchorPane.setRightAnchor(table, 20.0);

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

}
