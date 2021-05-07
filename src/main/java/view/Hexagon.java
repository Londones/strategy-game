package view;

import custom.GameTile;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.Cylinder;

public class Hexagon extends Group {

    GameTile cylinder;
    Cylinder outerCylinder;
    private final Image tileFill = new Image("tiles/dirt.png");
    int x, y, height;

    MainView view;
    public Hexagon(int i, int j, int h, MainView view) {
        super();
        this.view=view; 
        x=i;
        y=j;
        height=h;
        cylinder = new GameTile(8, 1+h*3, 6);
        initOuterCylinder(h);
        getChildren().add(cylinder);
        float f = (i%2==0) ? 0 : 7.5f;
        setTranslateZ(i*-13);
        setTranslateX(f+j*15);
        setTranslateY(h*-1.5);
        color(0);
    }

    //TODO FAIRE UNE FONCTION QUI VERIFIE QUE L'HEXAGONE NE POSSEDE PAS D'ENTITÉ!
    private void initOuterCylinder(int h) {
        outerCylinder = new Cylinder(9, 2+h*3, 6);
        outerCylinder.setVisible(false);
        PhongMaterial m = new PhongMaterial();
        m.setDiffuseColor(Color.YELLOW);
        try {
            m.setSelfIlluminationMap(new Image("yellowPixel.jpg"));
        } catch (Exception e) {
            System.out.println(e);
        }
        outerCylinder.setMaterial(m);
        outerCylinder.setCullFace(CullFace.FRONT);
        getChildren().add(outerCylinder);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    // 0: par défaut  1: chemin  2: portée
    public void color(int i) {
        PhongMaterial material = new PhongMaterial();
        switch (i) {
            case 0:
                material.setDiffuseMap(tileFill);
                break;
            case 1:
                material.setDiffuseColor(Color.LAWNGREEN);
                break;
            case 2:
                material.setDiffuseColor(Color.ORANGE);
                break;
            default:
                material.setDiffuseColor(Color.PINK);
                break;
        }
        material.setSpecularColor(Color.BLACK);
        cylinder.setMaterial(material);
    }

    public void setHighlight(boolean bool) {
        outerCylinder.setVisible(bool);
    }

    public void allowHighlight(boolean bool) {
        if (bool) {
            setOnMouseEntered(event -> {
                setHighlight(true);
                view.setPointedXY(x,y);
                hoverAction();
            });
            setOnMouseExited(event -> {
                setHighlight(false);
                view.cleanPath();
            });
        } else {
            setOnMouseEntered( event -> {});
            setOnMouseExited( event -> {});
            //color(0);
            setHighlight(false);
        }
    }

    public void allowClickAction(boolean bool) {
        if (bool) {
            setOnMouseClicked( event -> {
                clickAction();
            });
        } else {
            setOnMouseClicked( event -> {});
        }
    }

    private void hoverAction() {
        if (view.getChosenAction()==-1) view.makePath(x,y);
    }

    void clickAction() {

        view.doAction();
    }

}
