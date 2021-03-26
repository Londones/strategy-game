package view;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

/**
 * L'interface utilisateur est tout les éléments
 * (interactifs ou non) en 2D lors du jeu, comme les
 * boutons clickables
 */
public class UserInterface extends Group {
    MainView view;
    Controller ctrl;
    Button endTurn;
    Group actions;
    ActionButton[] actionButtons;
    Label entityDetails;

    int width;
    int height;

    int nbOfButtons=0;

    private class ActionButton extends Button {
        int actionNb;
        Label description;
        boolean isSelected=false;
        ActionButton(String name, String desc, int actionNb) {
            super(name);
            setFont(new Font(20));
            setTranslateX(10+(actionNb+1)*300);
            setTranslateY(height-100);
            description=new Label(desc);
            description.setFont(new Font(20));
            description.setVisible(false);
            description.setTranslateX(10+(actionNb+1)*300);
            description.setTranslateY(height-200);
            this.actionNb=actionNb;
            allowMouseListeners();
        }

        public Label getDescription() {
            return description;
        }

        public void allowMouseListeners() {
            setOnMouseEntered(event -> {
                description.setVisible(true);
            });
            setOnMouseExited(event -> {
                description.setVisible(false);
            });
            setOnMouseClicked(event -> {
                toggleSelected();
            });
        }

        private void toggleSelected() {
            if (isSelected) {
                view.resetAction();
                isSelected=false;
            } else {
                for (ActionButton ab: actionButtons) {
                    ab.isSelected=false;
                }
                isSelected=true;
                view.setAction(actionNb);
            }
        }

    }

    UserInterface(int width, int height, Controller controller, MainView view) {
        super();
        this.width=width;
        this.height=height;
        ctrl=controller;
        this.view=view;

        endTurn = makeButton("Fin du tour", 0);
        endTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ctrl.endTurn();
        });

        actions=new Group();
        showActionButtons(false);
        getChildren().add(actions);
        initEntityDetails();
    }

    private void initEntityDetails() {
        entityDetails = new Label();
        entityDetails.setVisible(false);
        entityDetails.setFont(new Font(20));
        getChildren().add(entityDetails);
    }

    public void updateEntityDetails(EntityView e) {
        entityDetails.setText("pv: "+e.getHp()+"/"+e.getMaxHp());
        entityDetails.setTranslateX(width/2-entityDetails.getWidth()/2);
    }

    public void showEntityDetails(boolean bool) {
        entityDetails.setVisible(bool);
    }

    private Button makeButton(String name, int i) {
        Button b = new Button(name);
        b.setFont(new Font(20));
        b.setTranslateY(height-100);
        b.setTranslateX(120+i*100);
        return b;
    }

    public void updateActionButtons(EntityView e) {
        actions.getChildren().clear();
        actions.getChildren().add(endTurn);
        int nbOfActions=e.getActionNames().length;
        actionButtons=new ActionButton[nbOfActions];
        for (int i = 0; i < nbOfActions; i++) {
            ActionButton b = new ActionButton(e.getActionNames()[i], e.getActionDesc()[i],i);
            actionButtons[i]=b;
            actions.getChildren().addAll(b,b.getDescription());
        }
    }

    public void showActionButtons(boolean bool) {
        actions.setVisible(bool);
    }

    public void resetActionButtons() {
        for (ActionButton ab: actionButtons) {
            ab.isSelected=false;
        }
    }
}
