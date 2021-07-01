package custom;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class GameLabel extends Label {

    private Label label;

    private final String BACKGROUND = "-fx-background-image: url('boxes/bar_ready.png'); " +
            "-fx-background-position: center; -fx-background-size: 300 135; -fx-font-family: 'Cinzel Decorative';" +
            "src: url('style/CinzelDecorative-Bold.ttf'); -fx-font-size: 12;" +
            "-fx-line-spacing: 0.7; -fx-padding: 0 0 5 0;";
    private final String BACKGROUND2 = "-fx-background-image: url('boxes/name_bar2.png'); " +
            "-fx-background-position: center; -fx-background-size: 180 90; -fx-font-family: 'Cinzel Decorative';" +
            "src: url('style/CinzelDecorative-Bold.ttf'); -fx-font-size: 15;" +
            "-fx-line-spacing: 0.7; -fx-padding: 0 0 5 0;";
    private final String FONT = "style/CinzelDecorative-Bold.ttf";

    public GameLabel(String s, int fontsize){
        super(s);
        label = new Label(s);
        label.setEffect(labelEffect());
        initLabelFont(fontsize);
        setEffect(labelEffect());
    }

    public GameLabel(){
        super();
        initLabelFont(10);
    }

    public GameLabel(String s){
        this(s, 10);

    }

    public void initstyle2(){
        setPrefSize(180,90);
        setAlignment(Pos.CENTER);
        setStyle(BACKGROUND2);
    }

    public void initstyle(){
        setPrefSize(300,140);
        setAlignment(Pos.CENTER);
        setStyle(BACKGROUND);
    }

    private void initLabelFont(int fontSize){
        setTextFill(Paint.valueOf("white"));
        setLineSpacing(0.7);
        setFont(Font.loadFont(getClass().getResourceAsStream("/"+FONT), fontSize));
    }

    private DropShadow labelEffect(){
        DropShadow ds = new DropShadow();
        ds.setBlurType(BlurType.GAUSSIAN);
        ds.setColor(Color.valueOf("black"));
        ds.setRadius(4);
        ds.setOffsetY(2);
        ds.setOffsetX(2);
        return ds;
    }


}
