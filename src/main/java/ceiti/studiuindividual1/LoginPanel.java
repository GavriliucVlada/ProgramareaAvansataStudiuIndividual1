package ceiti.studiuindividual1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class LoginPanel extends VBox {

    private LogInScreen parent;

    public LoginPanel(LogInScreen parent) {
        this.parent = parent;
        setPrefSize(400, 500);
        setMaxSize(400, 500);
        setMinSize(400, 500);
        setStyle("-fx-background-color:#F2F2F2 ; -fx-background-radius: 20;");

        DropShadow shadow = new DropShadow();
        shadow.setOffsetX(10);
        shadow.setOffsetY(10);
        shadow.setRadius(20);
        shadow.setColor(Color.rgb(213, 203, 203, 0.25));
        setEffect(shadow);

        Label Title = new Label("LogIn");
        Title.setFont(Fonts.RobotoMonoBold45);
        Title.setStyle("-fx-text-fill: #272A31;");

        Label lblUsername = new Label("Username");
        lblUsername.setFont(Fonts.RobotoMonoBold30);
        lblUsername.setStyle("-fx-text-fill: #272A31;");

        TextField txtUser = new TextField();
        txtUser.setPrefSize(301, 45);
        txtUser.setFont(Fonts.RobotoMonoSemiBold24);
        txtUser.setStyle(
                "-fx-text-fill: #272A31;" +
                        "-fx-background-color: transparent;" +
                        "-fx-border-color: #272A31;" +
                        "-fx-border-width: 1;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );

        Label lblPassword = new Label("Password");
        lblPassword.setFont(Fonts.RobotoMonoBold30);
        lblPassword.setStyle("-fx-text-fill: #272A31;");

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

        Label lblMessage = new Label();
        lblMessage.setFont(Fonts.RobotoMonoSemiBold24);
        lblMessage.setTextFill(Color.RED);

        Button btnCreate = new Button("Create");
        btnCreate.setPrefSize(145, 45);
        btnCreate.setFont(Fonts.RobotoMonoSemiBold24);
        btnCreate.setStyle(
                "-fx-text-fill: #F2F2F2;" +
                        "-fx-background-color: #363844;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );
        btnCreate.setOnAction(e -> parent.switchToPanel(parent.getCreatePanel()));
        Hovers.applyButtonHoverBackground(btnCreate, "#363844", "#272A31", "#F2F2F2");

        Button btnLogIn = new Button("LogIn");
        btnLogIn.setPrefSize(145, 45);
        btnLogIn.setFont(Fonts.RobotoMonoSemiBold24);
        btnLogIn.setStyle(
                "-fx-text-fill: #F2F2F2;" +
                        "-fx-background-color: #D4737A;" +
                        "-fx-border-radius: 5;" +
                        "-fx-background-radius: 5;"
        );
        Hovers.applyButtonHoverBackground(btnLogIn, "#D4737A", "#F27C84", "#F2F2F2");

        btnLogIn.setOnAction(e -> {
            String username = txtUser.getText().trim();
            String password = txtPsw.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                lblMessage.setText("Completați toate câmpurile!");
                return;
            }

            User user = UserDAO.getUser(username, password);

            if (user != null) {
                lblMessage.setTextFill(Color.GREEN);
                lblMessage.setText("Login reușit!");

                ManagementEducational mainPane = new ManagementEducational(user);
                Scene mainScene = new Scene(mainPane, 1200, 700);

                parent.getStage().setScene(mainScene);
                parent.getStage().setTitle("Schooling Management");
                parent.getStage().setMaximized(true);
                parent.getStage().show();
            } else {
                lblMessage.setTextFill(Color.RED);
                lblMessage.setText("Username sau parola greșită!");
            }
        });


        VBox titleContainer = new VBox(Title);
        titleContainer.setAlignment(Pos.TOP_CENTER);
        titleContainer.setPadding(new Insets(70, 0, 0, 0));

        VBox container = new VBox(lblUsername, txtUser, lblPassword, txtPsw, lblMessage);
        container.setAlignment(Pos.CENTER_LEFT);
        container.setPadding(new Insets(10, 45, 0, 45));
        VBox.setMargin(lblUsername, new Insets(0, 0, 3, 0));
        VBox.setMargin(txtUser, new Insets(0, 0, 15, 0));
        VBox.setMargin(lblPassword, new Insets(0, 0, 3, 0));
        VBox.setMargin(txtPsw, new Insets(0, 0, 30, 0));

        HBox btnsContainer = new HBox(btnCreate, btnLogIn);
        btnsContainer.setAlignment(Pos.CENTER_LEFT);
        btnsContainer.setPadding(new Insets(0, 45, 0, 45));
        btnsContainer.setSpacing(11);

        getChildren().addAll(titleContainer, container, btnsContainer);
    }



}
