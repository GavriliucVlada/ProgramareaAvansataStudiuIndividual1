package ceiti.studiuindividual1;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class RoundedButton extends Button {

    public RoundedButton(String text, String iconPath) {
        super(text);

        setPrefSize(200, 40);
        setFont(Font.font("Roboto Mono", 14));
        setTextFill(Color.WHITE);

        setStyle(
                "-fx-background-color: rgba(39,42,49,1);" +
                        "-fx-background-radius: 15;" +
                        "-fx-cursor: hand;"
        );

        if (iconPath != null) {
            Image img = new Image(getClass().getResourceAsStream(iconPath));
            ImageView iv = new ImageView(img);
            iv.setFitWidth(18);
            iv.setFitHeight(18);
            setGraphic(iv);
            setGraphicTextGap(10);
        }
    }
}
