package custom;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class GameButton extends Button {

    private Label label;


    private final String BUTTON_FREE= "-fx-background-color: transparent;" +
            "-fx-background-image: url('buttons/button_ready_on.png'); -fx-background-size: 170 65;" +
            "-fx-background-position: center; -fx-font-family: 'Cinzel Decorative'; -fx-line-spacing: -7;" +
            "src: url('style/CinzelDecorative-Bold.ttf'); -fx-font-size: 16; -fx-padding: 0 0 6 0;";
    private final String BUTTON_PRESSED = "-fx-background-color: transparent;" +
            "-fx-background-image: url('buttons/button_ready_off.png'); -fx-background-size: 170 65;" +
            "-fx-background-position: center; -fx-font-family: 'Cinzel Decorative'; -fx-line-spacing: -7;" +
            "src: url('style/CinzelDecorative-Bold.ttf'); -fx-font-size: 15; ; -fx-padding: 0 0 6 0;";

    public GameButton(String s){
        super(s);
        initButtonFont();
        setEffect(buttonEffect());
        label = new Label(s);
        label.setEffect(labelEffect());
        label.setTextFill(Paint.valueOf("#fbc786"));
        label.setTextAlignment(TextAlignment.CENTER);
        setGraphic(label);
        setText("");
    }



    public GameButton(){
        super();
        initButtonFont();
    }

    public void initStyle(){
        setStyle(BUTTON_FREE);
        setPrefSize(170,65);

        setOnMouseEntered(e -> {
            setCursor(Cursor.HAND);
            setStyle(BUTTON_PRESSED);
        });

        setOnMouseExited(e -> {
            setCursor(Cursor.DEFAULT);
            setStyle(BUTTON_FREE);
        });

        setOnMousePressed(e-> setStyle(BUTTON_PRESSED));

        setOnMouseReleased(e-> setStyle(BUTTON_FREE));
    }

   private void initButtonFont(){
       setTextFill(Paint.valueOf("#140101"));
       Font.loadFont(getClass().getResourceAsStream("/style/CinzelDecorative-Bold.ttf"), 15);
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

    public Label getLabel() {
        return label;
    }
}
