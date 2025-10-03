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
import javafx.stage.Stage;
import javafx.scene.text.Font;


public class LogInScreen extends BorderPane {

    // ----------------- CONSTANTS -----------------
    private static final Color TOPBAR_COLOR = Color.web("#272A31");
    private static final Color BACKGROUND_COLOR = Color.web("#363844");
    private static final Color LOGIN_PANEL_COLOR = Color.web("#F2F2F2");

    private static final double TOPBAR_HEIGHT = 40;
    private static final double LOGIN_PANEL_WIDTH = 400;
    private static final double LOGIN_PANEL_HEIGHT = 500;
    private static final double LOGIN_PANEL_RADIUS = 20;

    private Stage primaryStage;


    Font RobotoMonoBold45 = Font.loadFont(
            getClass().getResourceAsStream("/fonts/RobotoMono-Bold.ttf"),
            45
    );
    Font RobotoMonoBold30 = Font.loadFont(
            getClass().getResourceAsStream("/fonts/RobotoMono-Bold.ttf"),
            30
    );
    Font RobotoMonoBold24 = Font.loadFont(
            getClass().getResourceAsStream("/fonts/RobotoMono-SemiBold.ttf"),
            25
    );



    public LogInScreen(Stage stage) {
        this.primaryStage = stage;


        setTop(createTopBar());


        StackPane mainPanel = new StackPane();
        mainPanel.setStyle("-fx-background-color: #363844;");
        mainPanel.getChildren().add(createLoginPanel());
        setCenter(mainPanel);


    }

    // ----------------- METODE -----------------

    private HBox createTopBar() {
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #272A31;");
        topBar.setPrefHeight(TOPBAR_HEIGHT);
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
        topBar.getChildren().addAll(spacer, closeButton);

        return topBar;
    }

    private Button createCloseButton() {
        Button closeButton = new Button("x");
        setButtonStyle(closeButton, "#F2F2F2");

        closeButton.setOnAction(e -> primaryStage.close());
        closeButton.setOnMouseEntered(e -> setButtonStyle(closeButton, "#D4737A"));
        closeButton.setOnMouseExited(e -> setButtonStyle(closeButton, "#F2F2F2"));

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
        log.setPrefSize(LOGIN_PANEL_WIDTH, LOGIN_PANEL_HEIGHT);
        log.setMaxSize(LOGIN_PANEL_WIDTH, LOGIN_PANEL_HEIGHT);
        log.setMinSize(LOGIN_PANEL_WIDTH, LOGIN_PANEL_HEIGHT);

        log.setStyle("-fx-background-color:#F2F2F2 ; -fx-background-radius: " + LOGIN_PANEL_RADIUS + ";");

        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(10);
        shadow.setOffsetY(10);
        shadow.setRadius(20);
        shadow.setColor(Color.rgb(213, 203, 203, 0.25));
        log.setEffect(shadow);

        Label Title = new Label("LogIn");
        Title.setFont(RobotoMonoBold45);
        Title.setStyle("-fx-text-fill: #272A31;");

        Label lblUsername = new Label("Username");
        lblUsername.setFont(RobotoMonoBold30);
        lblUsername.setStyle("-fx-text-fill: #272A31;");

        Label lblPassword = new Label("Password");
        lblPassword.setFont(RobotoMonoBold30);
        lblPassword.setStyle("-fx-text-fill: #272A31;");


        TextField txtUser = new TextField();
        txtUser.setPrefSize(301, 45);
        txtUser.setFont(RobotoMonoBold24);
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
        btnCreate.setFont(RobotoMonoBold24);
        btnCreate.setStyle(
                "-fx-text-fill: #F2F2F2;" +
                        "-fx-background-color: #363844;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );

        Button btnLogIn = new Button("LogIn");
        btnLogIn.setPrefSize(145, 45);
        btnLogIn.setFont(RobotoMonoBold24);
        btnLogIn.setStyle(
                "-fx-text-fill: #F2F2F2;" +
                        "-fx-background-color: #D4737A;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );



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
}
