package ceiti.studiuindividual1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static ceiti.studiuindividual1.Fonts.RobotoMonoSemiBold24;
import static ceiti.studiuindividual1.Main.AVATARS_DIR;


public class LogInScreen extends BorderPane {

    // ----------------- CONSTANTS -----------------

    private Stage primaryStage;
    StackPane mainPanel;










    public LogInScreen(Stage stage) {
        this.primaryStage = stage;


        setTop(createTopBar());


        mainPanel = new StackPane();
        mainPanel.setStyle("-fx-background-color: #363844;");
        mainPanel.getChildren().add(createLoginPanel());


        setCenter(mainPanel);


    }

    // ----------------- METODE -----------------

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #272A31;");
        topBar.setPrefHeight(40);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(10);


        ImageView logo = new ImageView(new Image(getClass().getResourceAsStream("/images/Logo1.png")));
        logo.setFitHeight(30);
        logo.setPreserveRatio(true);
        HBox.setMargin(logo, new Insets(0, 0, 0, 20));
        topBar.getChildren().add(logo);


        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        Button closeButton = createCloseButton();
        Hovers.applyButtonHover(closeButton, "#F2F2F2", "#D4737A");
        topBar.getChildren().addAll(spacer, closeButton);

        return topBar;
    }

    private Button createCloseButton() {
        Button closeButton = new Button("x");
        setButtonStyle(closeButton, "#F2F2F2");

        closeButton.setOnAction(e -> primaryStage.close());

        return closeButton;
    }

    private void setButtonStyle(Button button, String textColor) {
        button.setStyle(
                "-fx-background-color: transparent;" +
                        " -fx-text-fill: " + textColor + ";" +
                        " -fx-font-size: 24px;" +
                        " -fx-font-family: 'Roboto Mono';" +
                        " -fx-font-weight: bold;"
        );
    }

    private VBox createLoginPanel() {
        VBox log = new VBox();
        log = new VBox();
        log.setPrefSize(400, 500);
        log.setMaxSize(400, 500);
        log.setMinSize(400, 500);

        log.setStyle("-fx-background-color:#F2F2F2 ; -fx-background-radius: " + 20 + ";");

        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(10);
        shadow.setOffsetY(10);
        shadow.setRadius(20);
        shadow.setColor(Color.rgb(213, 203, 203, 0.25));
        log.setEffect(shadow);

        Label Title = new Label("LogIn");
        Title.setFont(Fonts.RobotoMonoBold45);
        Title.setStyle("-fx-text-fill: #272A31;");

        Label lblUsername = new Label("Username");
        lblUsername.setFont(Fonts.RobotoMonoBold30);
        lblUsername.setStyle("-fx-text-fill: #272A31;");

        Label lblPassword = new Label("Password");
        lblPassword.setFont(Fonts.RobotoMonoBold30);
        lblPassword.setStyle("-fx-text-fill: #272A31;");


        TextField txtUser = new TextField();
        txtUser.setPrefSize(301, 45);
        txtUser.setFont(RobotoMonoSemiBold24);
        txtUser.setStyle(
                "-fx-text-fill: #272A31;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: #272A31;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );

        PasswordField txtPsw = new PasswordField();
        txtPsw.setPrefSize(301, 45);
        txtPsw.setStyle(
                "-fx-text-fill: #272A31;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: #272A31;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;" +
                        "-fx-font-size: 24px"
        );

        Button btnCreate = new Button("Create");
        btnCreate.setPrefSize(145, 45);
        btnCreate.setFont(Fonts.RobotoMonoSemiBold24);
        btnCreate.setStyle(
                "-fx-text-fill: #F2F2F2;" +
                        "-fx-background-color: #363844;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );




        btnCreate.setOnAction(event ->
            switchToPanel(createCreatePanel())
        );
        Hovers.applyButtonHoverBackground(btnCreate, "#363844", "#272A31", "#F2F2F2");


        Button btnLogIn = new Button("LogIn");
        btnLogIn.setPrefSize(145, 45);
        btnLogIn.setFont(RobotoMonoSemiBold24);
        btnLogIn.setStyle(
                "-fx-text-fill: #F2F2F2;" +
                        "-fx-background-color: #D4737A;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );

        Hovers.applyButtonHoverBackground(btnLogIn, "#D4737A", "#F27C84", "#F2F2F2");

        VBox titleContainer = new VBox(Title);
        titleContainer.setAlignment(Pos.TOP_CENTER);
        titleContainer.setPadding(new Insets(70,0,0,0));

        VBox Container = new VBox(lblUsername, txtUser, lblPassword, txtPsw);
        Container.setAlignment(Pos.CENTER_LEFT);
        Container.setPadding(new Insets(10,45,0,45));

        VBox.setMargin(lblUsername, new Insets(0, 0, 3, 0));
        VBox.setMargin(txtUser, new Insets(0, 0, 15, 0));
        VBox.setMargin(lblPassword, new Insets(0, 0, 3, 0));
        VBox.setMargin(txtPsw, new Insets(0, 0, 30, 0));

        HBox btnsContainer = new HBox(btnCreate, btnLogIn);
        btnsContainer.setAlignment(Pos.CENTER_LEFT);
        btnsContainer.setPadding(new Insets(0,45,0,45));
        btnsContainer.setSpacing(11);

        log.getChildren().add(titleContainer);
        log.getChildren().add(Container);
        log.getChildren().add(btnsContainer);


        return log;
    }

    private VBox createCreatePanel(){
        VBox create = new VBox();
        create.setPrefSize(800, 550);
        create.setMaxSize(800, 550);
        create.setMinSize(800, 550);

        create.setStyle("-fx-background-color:#F2F2F2 ; -fx-background-radius: " + 20 + ";");

        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(10);
        shadow.setOffsetY(10);
        shadow.setRadius(20);
        shadow.setColor(Color.rgb(213, 203, 203, 0.25));
        create.setEffect(shadow);

        Label Title = new Label("Create");
        Title.setFont(Fonts.RobotoMonoBold45);
        Title.setStyle("-fx-text-fill: #272A31;");




        Label lblUsername = new Label("Username");
        lblUsername.setFont(RobotoMonoSemiBold24);
        lblUsername.setStyle("-fx-text-fill: #272A31;");

        TextField txtUser = new TextField();
        txtUser.setPrefSize(301, 45);
        txtUser.setMaxSize(301, 45);
        txtUser.setMinSize(301, 45);
        txtUser.setFont(RobotoMonoSemiBold24);
        txtUser.setStyle(
                "-fx-text-fill: #272A31;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: #272A31;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );





        Label lblFirstName = new Label("First Name");
        lblFirstName.setFont(RobotoMonoSemiBold24);
        lblFirstName.setStyle("-fx-text-fill: #272A31;");

        TextField txtFirstName = new TextField();
        txtFirstName.setPrefSize(301, 45);
        txtFirstName.setMaxSize(301, 45);
        txtFirstName.setMinSize(301, 45);
        txtFirstName.setFont(RobotoMonoSemiBold24);
        txtFirstName.setStyle(
                "-fx-text-fill: #272A31;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: #272A31;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );






        Label lblLastName = new Label("Last Name");
        lblLastName.setFont(RobotoMonoSemiBold24);
        lblLastName.setStyle("-fx-text-fill: #272A31;");

        TextField txtLastName = new TextField();
        txtLastName.setPrefSize(301, 45);
        txtLastName.setMaxSize(301, 45);
        txtLastName.setMinSize(301, 45);
        txtLastName.setFont(RobotoMonoSemiBold24);
        txtLastName.setStyle(
                "-fx-text-fill: #272A31;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: #272A31;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );







        Label lblEmail = new Label("Email");
        lblEmail.setFont(RobotoMonoSemiBold24);
        lblEmail.setStyle("-fx-text-fill: #272A31;");

        TextField txtEmail = new TextField();
        txtEmail.setPrefSize(301, 45);
        txtEmail.setMaxSize(301, 45);
        txtEmail.setMinSize(301, 45);
        txtEmail.setFont(RobotoMonoSemiBold24);
        txtEmail.setStyle(
                "-fx-text-fill: #272A31;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: #272A31;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );



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
        lblProfilePhoto.setFont(RobotoMonoSemiBold24);
        lblProfilePhoto.setStyle("-fx-text-fill: #272A31;");






        Label lblPassword = new Label("Password");
        lblPassword.setFont(RobotoMonoSemiBold24);
        lblPassword.setStyle("-fx-text-fill: #272A31;");

        TextField txtPsw = new TextField();
        txtPsw.setPrefSize(301, 45);
        txtPsw.setMaxSize(301, 45);
        txtPsw.setMinSize(301, 45);
        txtPsw.setFont(RobotoMonoSemiBold24);
        txtPsw.setStyle(
                "-fx-text-fill: #272A31;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: #272A31;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );







        Label lblDOB = new Label("Date of Birth");
        lblDOB.setFont(RobotoMonoSemiBold24);
        lblDOB.setStyle("-fx-text-fill: #272A31;");

        TextField txtDOB = new TextField();
        txtDOB.setPrefSize(301, 45);
        txtDOB.setMaxSize(301, 45);
        txtDOB.setMinSize(301, 45);
        txtDOB.setFont(RobotoMonoSemiBold24);
        txtDOB.setStyle(
                "-fx-text-fill: #272A31;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: #272A31;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );






        Button btnCreate = new Button("Create");
        btnCreate.setPrefSize(145, 45);
        btnCreate.setFont(Fonts.RobotoMonoSemiBold24);
        btnCreate.setStyle(
                "-fx-text-fill: #F2F2F2;" +
                        "-fx-background-color: #363844;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );


        Hovers.applyButtonHoverBackground(btnCreate, "#363844", "#272A31", "#F2F2F2");


        Button btnLogIn = new Button("LogIn");
        btnLogIn.setPrefSize(145, 45);
        btnLogIn.setFont(RobotoMonoSemiBold24);
        btnLogIn.setStyle(
                "-fx-text-fill: #F2F2F2;" +
                        "-fx-background-color: #D4737A;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );

        btnLogIn.setOnAction(event ->
                switchToPanel(createLoginPanel())
        );

        Hovers.applyButtonHoverBackground(btnLogIn, "#D4737A", "#F27C84", "#F2F2F2");






        VBox titleContainer = new VBox(Title);
        titleContainer.setAlignment(Pos.TOP_CENTER);
        titleContainer.setPadding(new Insets(40,0,0,0));

        VBox Container1 = new VBox(lblUsername, txtUser, lblFirstName, txtFirstName, lblLastName, txtLastName, lblEmail, txtEmail);
        Container1.setAlignment(Pos.CENTER_LEFT);
        Container1.setPadding(new Insets(10,34,0,55));

        VBox.setMargin(lblUsername, new Insets(0, 0, 3, 0));
        VBox.setMargin(txtUser, new Insets(0, 0, 15, 0));
        VBox.setMargin(lblFirstName, new Insets(0, 0, 3, 0));
        VBox.setMargin(txtFirstName, new Insets(0, 0, 15, 0));
        VBox.setMargin(lblLastName, new Insets(0, 0, 3, 0));
        VBox.setMargin(txtLastName, new Insets(0, 0, 15, 0));
        VBox.setMargin(lblEmail, new Insets(0, 0, 3, 0));
        VBox.setMargin(txtEmail, new Insets(0, 0, 15, 0));



        VBox Container2 = new VBox(lblPassword, txtPsw, lblDOB, txtDOB);
        Container2.setAlignment(Pos.CENTER_LEFT);
        Container2.setPadding(new Insets(10,55,0,34));

        VBox.setMargin(lblPassword, new Insets(0, 0, 3, 0));
        VBox.setMargin(txtPsw, new Insets(0, 0, 15, 0));
        VBox.setMargin(lblDOB, new Insets(0, 0, 3, 0));
        VBox.setMargin(txtDOB, new Insets(0, 0, 47, 0));


        HBox btnsContainer = new HBox(btnCreate, btnLogIn);
        btnsContainer.setAlignment(Pos.CENTER_LEFT);
        btnsContainer.setSpacing(11);

        HBox ppPanel = new HBox(avatarView, lblProfilePhoto);
        ppPanel.setAlignment(Pos.CENTER_LEFT);
        ppPanel.setSpacing(11);
        ppPanel.setPadding(new Insets(0,0,15,34));

        VBox Container3 = new VBox(ppPanel, Container2);
        Container3.setPadding(new Insets(20,0,10,0));



        Container2.getChildren().add(btnsContainer);






        create.getChildren().add(titleContainer);

        HBox rowContainer = new HBox(Container1, Container3);
        rowContainer.setAlignment(Pos.CENTER);
        rowContainer.setSpacing(0);

        create.getChildren().add(rowContainer);
        return create;

    }


    // ---------------- SWITCH PANEL ----------------
    private void switchToPanel(VBox newPanel) {
        mainPanel.getChildren().clear();
        mainPanel.getChildren().add(newPanel);
    }

}
