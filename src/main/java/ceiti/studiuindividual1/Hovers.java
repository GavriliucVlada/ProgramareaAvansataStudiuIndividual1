package ceiti.studiuindividual1;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;


public class Hovers {


    public static void applyButtonHover(Button button, String textColorStr, String hoverColorStr) {

        Color textColor = Color.web(textColorStr);
        Color hoverColor = Color.web(hoverColorStr);

        button.setOnMouseEntered(e -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.5),
                            new KeyValue(button.textFillProperty(), hoverColor))
            );
            timeline.play();
        });

        button.setOnMouseExited(e -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(0.5),
                            new KeyValue(button.textFillProperty(), textColor))
            );
            timeline.play();
        });
    }



    public static void applyButtonHoverBackground(Button button, String bgColor, String hoverBgColor, String textColor) {
        button.setOnMouseEntered(e -> button.setStyle( "-fx-background-color: " +
                hoverBgColor + ";" + " -fx-text-fill: " + textColor + ";" +
                " -fx-font-size: 24px;" + " -fx-font-family: 'Roboto Mono';" +
                " -fx-font-weight: bold;" + " -fx-background-radius: 5;" +
                " -fx-border-radius: 5;" ));

        button.setOnMouseExited(e -> button.setStyle( "-fx-background-color: " +
                bgColor + ";" + " -fx-text-fill: " + textColor + ";" +
                " -fx-font-size: 24px;" + " -fx-font-family: 'Roboto Mono';" +
                " -fx-font-weight: bold;" + " -fx-background-radius: 5;" +
                " -fx-border-radius: 5;" ));
    }
}

