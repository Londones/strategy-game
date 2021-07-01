package custom;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class GameSpell extends Button {
    private String ICON_FREE;
    private String ICON_PRESSED;
    private String name;
    private GameLabel description;

    private final String LABEL_BG = "boxes/barmid_ready.png";
    private final String LABEL_STYLE = "-fx-font-family: 'Cinzel Decorative';" +
            "src: url('style/CinzelDecorative-Bold.ttf'); -fx-font-size: 10;";

    private Label label;

    public GameSpell(String name, String desc){
        super(name);
        this.name = name;
        description = new GameLabel(desc);
        setPrefSize(50, 50);
        initBG(name);
        setEffect(buttonEffect());
        label = new GameLabel();
        label.setText(name);
        setLabelImage();
        initLabelFont();
        mouseListeners();
    }

    private void initBG(String name){
        switch (name){
            case "Frappe courte" :
                ICON_FREE = "icons/spells/Soldier_melee_free.png";
                ICON_PRESSED = "icons/spells/Soldier_melee_pressed.png";
                break;
            case "Frappe longue" :
                ICON_FREE = "icons/spells/Soldier_ranged_free.png";
                ICON_PRESSED = "icons/spells/Soldier_ranged_pressed.png";
                break;
            case "Boule de feu" :
                ICON_FREE = "icons/spells/Wizard_fireball_free.png";
                ICON_PRESSED = "icons/spells/Wizard_fireball_pressed.png";
                break;
            case "Protection" :
                ICON_FREE = "icons/spells/Wizard_protect_free.png";
                ICON_PRESSED = "icons/spells/Wizard_protect_pressed.png";
                break;
            case "Orion sacré" :
                ICON_FREE = "icons/spells/Cleric_magicattack_free.png";
                ICON_PRESSED = "icons/spells/Cleric_magicattack_pressed.png";
                break;
            case "Premiers soins" :
                ICON_FREE = "icons/spells/Cleric_allyheal_free.png";
                ICON_PRESSED = "icons/spells/Cleric_allyheal_pressed.png";
                break;
            case "Purge" :
                ICON_FREE = "icons/spells/Cleric_cleanse_free.png";
                ICON_PRESSED = "icons/spells/Cleric_cleanse_pressed.png";
                break;
            case "Coup décisif" :
                ICON_FREE = "icons/spells/Knight_melee_free.png";
                ICON_PRESSED = "icons/spells/Knight_melee_pressed.png";
                break;
            case "Auto soin" :
                ICON_FREE = "icons/spells/Knight_selfheal_free.png";
                ICON_PRESSED = "icons/spells/Knight_selfheal_pressed.png";
                break;
            case "Attaque de baton" :
                ICON_FREE = "icons/spells/Druid_attack_free.png";
                ICON_PRESSED = "icons/spells/Druid_attack_pressed.png";
                break;
            case "Empoisonnement" :
                ICON_FREE = "icons/spells/Druid_poison_free.png";
                ICON_PRESSED = "icons/spells/Druid_poison_pressed.png";
                break;
            case "Enracinement" :
                ICON_FREE = "icons/spells/Druid_root_free.png";
                ICON_PRESSED = "icons/spells/Druid_root_pressed.png";
                break;
        }
    }

    public void setBackgroundFree(){
        BackgroundImage image = new BackgroundImage(new Image(ICON_FREE, 50, 50, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        setBackground(new Background(image));
    }

    public void setBackgroundPressed(){
        BackgroundImage image = new BackgroundImage(new Image(ICON_PRESSED, 50, 50, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        setBackground(new Background(image));
    }

    private void setLabelImage() {
        label.setTranslateY(getTranslateY() + 35);
        label.setTranslateX(getTranslateX());
        BackgroundImage image = new BackgroundImage(new Image(LABEL_BG, 150, 30, false, true), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, null);
        label.setBackground(new Background(image));
        label.setEffect(labelEffect());
        setGraphic(label);
    }

    public void mouseListeners(){
        label.setVisible(false);
        setBackgroundFree();
        setOnMouseEntered(e ->{
            description.setVisible(true);
            setBackgroundPressed();
            setCursor(Cursor.HAND);
            label.setVisible(true);
        });

        setOnMouseExited(e ->{
            description.setVisible(false);
            setBackgroundFree();
            setCursor(Cursor.DEFAULT);
            label.setVisible(false);
        });

        setOnMousePressed(e -> setBackgroundPressed());

        setOnMouseReleased(e -> setBackgroundFree());
    }

    private void initLabelFont(){
        label.setStyle(LABEL_STYLE);
        label.setTextFill(Paint.valueOf("white"));
        label.setAlignment(Pos.CENTER);
    }

    private DropShadow labelEffect(){
        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.GAUSSIAN);
        ds.setColor(Color.valueOf("#140101"));
        ds.setRadius(2);
        ds.setWidth(3);
        ds.setSpread(0.8);
        ds.setOffsetY(1);
        return ds;
    }

    private DropShadow buttonEffect(){
        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.GAUSSIAN);
        ds.setColor(Color.valueOf("black"));
        ds.setRadius(4);
        ds.setOffsetY(2);
        ds.setOffsetX(2);
        return ds;
    }

    public GameLabel getDescription() {
        return description;
    }
}
