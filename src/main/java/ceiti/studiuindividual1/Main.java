package ceiti.studiuindividual1;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    static final Path AVATARS_DIR = Paths.get(System.getProperty("user.home"), "StudiuIndividual1", "avatars");

    public static void initAvatarsFolder() {
        try {
            Files.createDirectories(AVATARS_DIR);
            System.out.println("Folder creat sau deja exista: " + AVATARS_DIR.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        initAvatarsFolder();

        LogInScreen loginScreen = new LogInScreen(primaryStage);

        Scene scene = new Scene(loginScreen, 1200, 800);
        primaryStage.setTitle("Educational Management Aplication");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
