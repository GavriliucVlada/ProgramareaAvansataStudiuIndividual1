package ceiti.studiuindividual1;

import javafx.scene.text.Font;

public class Fonts {

    // --- FONTURI ROBOTO MONO ---
    public static final Font RobotoMonoBold45;
    public static final Font RobotoMonoBold30;
    public static final Font RobotoMonoBold24;
    public static final Font RobotoMonoSemiBold24;
    public static final Font RobotoMonoRegular42;
    public static final Font RobotoMonoRegular30;
    public static final Font RobotoMonoRegular20;
    public static final Font RobotoMonoRegular13;
    public static final Font RobotoMonoRegular14;
    public static final Font RobotoMonoBold32;
    public static final Font RobotoMonoMedium20;
    public static final Font RobotoMonoMedium16;
    public static final Font RobotoMonoMedium14;


    static {
        // --- BOLD ---
        RobotoMonoBold45 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Bold.ttf"), 45);
        RobotoMonoBold30 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Bold.ttf"), 30);
        RobotoMonoBold24 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Bold.ttf"), 24);
        RobotoMonoBold32 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Bold.ttf"), 32);

        // --- SEMIBOLD ---
        RobotoMonoSemiBold24 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-SemiBold.ttf"), 24);


        // --- MEDIUM ---
        RobotoMonoMedium20 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Medium.ttf"), 20);
        RobotoMonoMedium16 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Medium.ttf"), 16);
        RobotoMonoMedium14 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Medium.ttf"), 14);


        // --- REGULAR ---
        RobotoMonoRegular42 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Regular.ttf"), 42);
        RobotoMonoRegular30 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Regular.ttf"), 30);
        RobotoMonoRegular20 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Regular.ttf"), 20);
        RobotoMonoRegular14 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Regular.ttf"), 14);
        RobotoMonoRegular13 = Font.loadFont(Fonts.class.getResourceAsStream("/fonts/RobotoMono-Regular.ttf"), 13);
    }
}
