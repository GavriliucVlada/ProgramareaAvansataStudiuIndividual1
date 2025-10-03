package ceiti.studiuindividual1;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        LogInScreen loginScreen = new LogInScreen(primaryStage);

        Scene scene = new Scene(loginScreen, 1200, 800);
        primaryStage.setTitle("Educational Management Aplication");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
