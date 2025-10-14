package ceiti.studiuindividual1;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class LogInScreen extends BorderPane {

    private Stage primaryStage;
    private StackPane mainPanel;

    private LoginPanel loginPanel;
    private CreateAccountPanel createPanel;


    public LogInScreen(Stage stage) {
        this.primaryStage = stage;

        setTop(TopBar.createTopBar(primaryStage));

        mainPanel = new StackPane();
        mainPanel.setStyle("-fx-background-color: #363844;");

        loginPanel = new LoginPanel(this);
        createPanel = new CreateAccountPanel(this);

        mainPanel.getChildren().add(loginPanel);

        setCenter(mainPanel);
    }

    public void switchToPanel(Object panel) {
        mainPanel.getChildren().clear();
        if (panel instanceof javafx.scene.layout.VBox vbox) {
            mainPanel.getChildren().add(vbox);
        } else if (panel instanceof javafx.scene.layout.Pane pane) {
            mainPanel.getChildren().add(pane);
        } else {
            System.err.println("⚠️ Tip necunoscut de panou: " + panel.getClass().getSimpleName());
        }
    }

    public LoginPanel getLoginPanel() {
        return loginPanel;
    }

    public CreateAccountPanel getCreatePanel() {
        return createPanel;
    }

    public Stage getStage() {
        return primaryStage;
    }

    public void show() {
        Scene scene = new Scene(this, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login - Schooling App");
        primaryStage.show();
    }
}
