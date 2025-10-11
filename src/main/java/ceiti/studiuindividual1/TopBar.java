

package ceiti.studiuindividual1;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class TopBar {

    public static HBox createTopBar(Stage stage) {
        HBox topBar = new HBox();
        topBar.setStyle("-fx-background-color: #272A31;");
        topBar.setPrefHeight(40);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setSpacing(10);

        ImageView logo = new ImageView(new Image(TopBar.class.getResourceAsStream("/images/Logo1.png")));
        logo.setFitHeight(30);
        logo.setPreserveRatio(true);
        HBox.setMargin(logo, new Insets(0, 0, 0, 20));
        topBar.getChildren().add(logo);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button closeButton = new Button("x");
        closeButton.setStyle(
                "-fx-background-color: transparent;" +
                        " -fx-text-fill: #F2F2F2;" +
                        " -fx-font-size: 24px;" +
                        " -fx-font-family: 'Roboto Mono';" +
                        " -fx-font-weight: bold;"
        );
        closeButton.setOnAction(e -> stage.close());
        Hovers.applyButtonHover(closeButton, "#F2F2F2", "#D4737A");

        topBar.getChildren().addAll(spacer, closeButton);
        return topBar;
    }
}
