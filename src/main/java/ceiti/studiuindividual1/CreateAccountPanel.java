package ceiti.studiuindividual1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static ceiti.studiuindividual1.Main.AVATARS_DIR;

public class CreateAccountPanel extends VBox {

    private LogInScreen parent;

    public CreateAccountPanel(LogInScreen parent) {
        this.parent = parent;

        setPrefSize(800, 550);
        setMaxSize(800, 550);
        setMinSize(800, 550);
        setStyle("-fx-background-color:#F2F2F2 ; -fx-background-radius: 20;");

        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(10);
        shadow.setOffsetY(10);
        shadow.setRadius(20);
        shadow.setColor(Color.rgb(213, 203, 203, 0.25));
        setEffect(shadow);

        // Title
        Label Title = new Label("Create");
        Title.setFont(Fonts.RobotoMonoBold45);
        Title.setStyle("-fx-text-fill: #272A31;");
        VBox titleContainer = new VBox(Title);
        titleContainer.setAlignment(Pos.TOP_CENTER);
        titleContainer.setPadding(new Insets(40, 0, 0, 0));

        // Fields left column
        Label lblUsername = new Label("Username");
        TextField txtUser = createStyledTextField();
        lblUsername.setFont(Fonts.RobotoMonoSemiBold24);
        txtUser.setFont(Fonts.RobotoMonoSemiBold24);

        Label lblFirstName = new Label("First Name");
        TextField txtFirstName = createStyledTextField();
        lblFirstName.setFont(Fonts.RobotoMonoSemiBold24);
        txtFirstName.setFont(Fonts.RobotoMonoSemiBold24);



        Label lblLastName = new Label("Last Name");
        TextField txtLastName = createStyledTextField();
        lblLastName.setFont(Fonts.RobotoMonoSemiBold24);
        txtLastName.setFont(Fonts.RobotoMonoSemiBold24);


        Label lblEmail = new Label("Email");
        TextField txtEmail = createStyledTextField();
        lblEmail.setFont(Fonts.RobotoMonoSemiBold24);
        txtEmail.setFont(Fonts.RobotoMonoSemiBold24);

        VBox container1 = new VBox(lblUsername, txtUser, lblFirstName, txtFirstName,
                lblLastName, txtLastName, lblEmail, txtEmail);
        container1.setAlignment(Pos.CENTER_LEFT);
        container1.setPadding(new Insets(10, 34, 0, 55));
        addMargin(container1, lblUsername, txtUser, lblFirstName, txtFirstName,
                lblLastName, txtLastName, lblEmail, txtEmail);

        // Fields right column
        Label lblPassword = new Label("Password");
        TextField txtPsw = createStyledTextField();
        lblPassword.setFont(Fonts.RobotoMonoSemiBold24);
        txtPsw.setFont(Fonts.RobotoMonoSemiBold24);

        Label lblDOB = new Label("Date of Birth");
        TextField txtDOB = createStyledTextField();
        lblDOB.setFont(Fonts.RobotoMonoSemiBold24);
        txtDOB.setFont(Fonts.RobotoMonoSemiBold24);

        VBox container2 = new VBox(lblPassword, txtPsw, lblDOB, txtDOB);
        container2.setAlignment(Pos.CENTER_LEFT);
        container2.setPadding(new Insets(10, 55, 0, 34));
        addMargin(container2, lblPassword, txtPsw, lblDOB, txtDOB);

        // Avatar
        Image defaultImage = new Image(getClass().getResource("/images/userDefault.png").toExternalForm());
        ImageView avatarView = new ImageView(defaultImage);
        avatarView.setFitWidth(60);
        avatarView.setFitHeight(60);
        avatarView.setPreserveRatio(false);
        avatarView.setSmooth(true);
        avatarView.setStyle("-fx-cursor: hand;");

        avatarView.setOnMouseClicked(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Alege o fotografie de profil");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Imagini", "*.png", "*.jpg", "*.jpeg")
            );

            File selectedFile = fileChooser.showOpenDialog(avatarView.getScene().getWindow());
            if (selectedFile != null) {
                try {
                    Path destination = AVATARS_DIR.resolve(selectedFile.getName());
                    Files.copy(selectedFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
                    avatarView.setImage(new Image(destination.toUri().toString()));
                    System.out.println("Imagine copiată în: " + destination.toAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Label lblProfilePhoto = new Label("Profile Photo");
        lblProfilePhoto.setFont(Fonts.RobotoMonoSemiBold24);
        lblProfilePhoto.setStyle("-fx-text-fill: #272A31;");

        HBox ppPanel = new HBox(avatarView, lblProfilePhoto);
        ppPanel.setAlignment(Pos.CENTER_LEFT);
        ppPanel.setSpacing(11);
        ppPanel.setPadding(new Insets(0, 0, 15, 34));

        VBox container3 = new VBox(ppPanel, container2);
        container3.setPadding(new Insets(20, 0, 10, 0));

        // Buttons
        Button btnCreate = new Button("Create");
        btnCreate.setPrefSize(145, 45);
        btnCreate.setFont(Fonts.RobotoMonoSemiBold24);
        btnCreate.setStyle("-fx-text-fill: #F2F2F2; -fx-background-color: #363844; -fx-border-radius: 5; -fx-background-radius: 5;");
        Hovers.applyButtonHoverBackground(btnCreate, "#363844", "#272A31", "#F2F2F2");


        btnCreate.setOnAction(event -> {
            // Preluăm datele introduse de utilizator
            String username = txtUser.getText();
            String firstName = txtFirstName.getText();
            String lastName = txtLastName.getText();
            String email = txtEmail.getText();
            String password = txtPsw.getText();
            String dob = txtDOB.getText(); // Format corect: yyyy-MM-dd
            String avatarPath = avatarView.getImage().getUrl(); // calea imaginii

            // Validăm datele (simplu)
            if(username.isEmpty() || firstName.isEmpty() || lastName.isEmpty() ||
                    email.isEmpty() || password.isEmpty() || dob.isEmpty() || avatarPath == null) {
                System.out.println("Completați toate câmpurile!");
                return;
            }

            try {
                // Apelăm UserDAO pentru a salva utilizatorul în baza de date
                UserDAO.createUser(username, password, avatarPath, firstName, lastName, email, dob);
                System.out.println("User creat cu succes!");

                // După ce s-a creat contul, revenim la pagina de logare
                parent.switchToPanel(parent.getLoginPanel());
            } catch (IllegalArgumentException ex) {
                System.out.println("Data introdusă nu este validă: " + dob);
            }
        });


        Button btnLogIn = new Button("LogIn");
        btnLogIn.setPrefSize(145, 45);
        btnLogIn.setFont(Fonts.RobotoMonoSemiBold24);
        btnLogIn.setStyle("-fx-text-fill: #F2F2F2; -fx-background-color: #D4737A; -fx-border-radius: 5; -fx-background-radius: 5;");
        btnLogIn.setOnAction(event -> parent.switchToPanel(parent.getLoginPanel()));
        Hovers.applyButtonHoverBackground(btnLogIn, "#D4737A", "#F27C84", "#F2F2F2");

        HBox btnsContainer = new HBox(btnCreate, btnLogIn);
        btnsContainer.setAlignment(Pos.CENTER_LEFT);
        btnsContainer.setSpacing(11);

        container2.getChildren().add(btnsContainer);

        // Combine containers
        HBox rowContainer = new HBox(container1, container3);
        rowContainer.setAlignment(Pos.CENTER);
        rowContainer.setSpacing(0);

        getChildren().addAll(titleContainer, rowContainer);
    }

    private TextField createStyledTextField() {
        TextField txt = new TextField();
        txt.setPrefSize(301, 45);
        txt.setMaxSize(301, 45);
        txt.setMinSize(301, 45);
        txt.setFont(Fonts.RobotoMonoSemiBold24);
        txt.setStyle("-fx-text-fill: #272A31; -fx-background-color: transparent; -fx-border-color: #272A31; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-radius: 5;");
        return txt;
    }

    private void addMargin(VBox container, javafx.scene.Node... nodes) {
        for (int i = 0; i < nodes.length; i += 2) {
            VBox.setMargin(nodes[i], new Insets(0, 0, 3, 0));
            VBox.setMargin(nodes[i + 1], new Insets(0, 0, 15, 0));
        }
        // Ultimul TextField să aibă margin special (pentru DOB)
        if (nodes.length >= 2) {
            VBox.setMargin(nodes[nodes.length - 1], new Insets(0, 0, 47, 0));
        }
    }
}
