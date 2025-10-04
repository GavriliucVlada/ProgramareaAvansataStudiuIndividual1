package ceiti.studiuindividual1;

import javafx.scene.text.Font;

public class Fonts {

    public static final Font RobotoMonoBold45;
    public static final Font RobotoMonoBold30;
    public static final Font RobotoMonoSemiBold24;

    static {
        RobotoMonoBold45 = Font.loadFont(
                Fonts.class.getResourceAsStream("/fonts/RobotoMono-Bold.ttf"), 45
        );
        RobotoMonoBold30 = Font.loadFont(
                Fonts.class.getResourceAsStream("/fonts/RobotoMono-Bold.ttf"), 30
        );
        RobotoMonoSemiBold24 = Font.loadFont(
                Fonts.class.getResourceAsStream("/fonts/RobotoMono-SemiBold.ttf"), 24
        );
    }
}