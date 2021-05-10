package view;

import custom.GameButton;
import custom.GameMenu;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import custom.GameLabel;


/**
 * L'interface utilisateur est tout les éléments
 * (interactifs ou non) en 2D lors du jeu, comme les
 * boutons clickables
 */
public class UserInterface extends Group {
    private MainView view;
    private Controller ctrl;
    private GameButton endTurn;
    private GameButton start;
    private Group actions;
    private List<ActionButton> actionButtons;
    private List<BuyButton> buyButtons;
    private GameLabel entityDetails;
    private GameButton option;
    private GameMenu optionMenu;
    private GameButton quitter;
    private GameLabel money;
    private int height = 720;
    private int width = 1080;
    //private final String[] listOfPossibleEntities = {"Soldat", "Chevalier"};

    private final static String BUTTON_FREE = "-fx-background-color: transparent; " +
            "-fx-background-image: url('buttons/button_ready_on.png'); -fx-background-size: 170 65;" +
            "-fx-background-position: center; -fx-font-family: 'Cinzel Decorative';" +
            "src: url('src/main/resources/style/CinzelDecorative-Bold.ttf'); -fx-font-size: 14; -fx-padding: 0 0 6 0;";

    private final static String BUTTON_PRESSED = "-fx-background-color: transparent; " +
            "-fx-background-image: url('buttons/button_ready_off.png'); -fx-background-size: 170 65;" +
            "-fx-background-position: center; -fx-font-family: 'Cinzel Decorative';" +
            "src: url('src/main/resources/style/CinzelDecorative-Bold.ttf'); -fx-font-size: 13; -fx-padding: 0 0 6 0;";

    private final int START_BUTTON_X = 100;
    private final int START_BUTTON_Y = 100;

    private int nbOfButtons=0;

    private final String[] listOfPossibleEntities = {"soldat", "chevalier"};

    private class ActionButton extends GameButton {
        int actionNb;
        GameLabel description;
        boolean isSelected=false;
        ActionButton(String name, String desc, int actionNb) {
            super(name);
            setFont(new Font(14));
            setTranslateX(START_BUTTON_X + endTurn.getTranslateX() * 2 + actionButtons.size()*200);
            setTranslateY(height-100);
            description=new GameLabel(desc);
            description.setFont(new Font(20));
            description.setVisible(false);
            description.setTranslateX(START_BUTTON_X + endTurn.getTranslateX()*2 + actionButtons.size()*200);
            description.setTranslateY(height-220);
            description.initstyle();
            this.actionNb=actionNb;
            allowMouseListeners();
        }

        public Label getDescription() {
            return description;
        }

        public void allowMouseListeners() {

            setPrefSize(170,65);
            setStyle(BUTTON_FREE);
            setOnMouseEntered(event -> {
                description.setVisible(true);
                setCursor(Cursor.HAND);
                setStyle(BUTTON_PRESSED);
            });
            setOnMouseExited(event -> {
                description.setVisible(false);
                setCursor(Cursor.DEFAULT);
                setStyle(BUTTON_FREE);
            });
            setOnMouseClicked(event -> {
                toggleSelected();
            });

            setOnMousePressed(e-> setStyle(BUTTON_PRESSED));

            setOnMouseReleased(e-> setStyle(BUTTON_FREE));
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

    private class BuyButton extends GameButton{
        private final int entityNb;

        BuyButton(String s, int i){
            super(s);
            entityNb = i;
            initStyle();
            setTranslateX(START_BUTTON_X + nbOfButtons * 200);
            nbOfButtons ++;
            setTranslateY(height - 100);
            setOnMouseClicked(e -> view.setPreGameAction(entityNb));
        }
    }

    UserInterface(int width, int height, Controller controller, MainView view) {
        super();
        this.width=width;
        this.height=height;
        ctrl=controller;
        this.view=view;

        initOptionButton();
        endTurn = makeButton("Fin du tour",0);
        start = makeButton("Commencer",0);
        addButton(start);
        start.setVisible(true);
        start.setDisable(true);

        optionMenu = new GameMenu(300,400);

        actions=new Group();
        showActionButtons(false);
        getChildren().add(actions);
        initEntityDetails();

        buttonsMouseListeners();

        initOptionMenu();

        buyButtons = new LinkedList<>();
        buyButtons.add(0,new BuyButton("Supprimer", -1));
        getChildren().add(buyButtons.get(0));
        for (int i = 1; i < listOfPossibleEntities.length + 1; i++){
            buyButtons.add(i, new BuyButton(listOfPossibleEntities[i - 1], i - 1));
            getChildren().add(buyButtons.get(i));
        }

        moneyLabelInit();

    }

    public void moneyLabelInit(){
        money = new GameLabel("");
        money.initstyle2();
        getChildren().add(money);
        money.setVisible(true);
    }

    public void buttonsMouseListeners(){
        endTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            ctrl.endTurn();
        });

        start.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            start.setVisible(false);
            ctrl.toggleReady();
        });

        option.setOnMouseClicked(e -> {
               optionMenu.animation();
        });
    }

    public void canPressReadyButton(boolean b) {
        start.setDisable(!b);
    }

    private void initEntityDetails() {
        entityDetails = new GameLabel();
        entityDetails.setVisible(false);
        entityDetails.setFont(new Font(20));
        entityDetails.initstyle2();
        getChildren().add(entityDetails);
    }

    public void updateEntityDetails(EntityView e) {
        entityDetails.setText(e.getName()+"\npv: "+e.getHp()+"/"+e.getMaxHp());
        entityDetails.setTranslateX(width/2-entityDetails.getWidth()/2);
    }

    public void showEntityDetails(boolean bool) {
        entityDetails.setVisible(bool);
    }

    private GameButton makeButton(String name, int i) {
        GameButton b = new GameButton(name);
        b.setFont(new Font(20));
        b.setTranslateY(height-100);
        b.setTranslateX(START_BUTTON_X + i * 100);
        b.initStyle();
        return b;
    }

    private void initOptionButton(){
        option = new GameButton("Options");
        getChildren().add(option);
        option.setTranslateY(0);
        option.setTranslateX(width-170);
        option.initStyle();
    }

    private void initOptionMenu(){
        getChildren().add(optionMenu);
        optionMenu.fadeOutScene();


        quitter = new GameButton("Quitter");
        quitter.initStyle();
        quitter.setLayoutX(65);
        quitter.setLayoutY(300);
        optionMenu.getPane().getChildren().add(quitter);
    }

    public void updateActionButtons(EntityView e) {
        actions.getChildren().clear();
        actions.getChildren().add(endTurn);
        int nbOfActions=e.getActionNames().length;
        actionButtons= new LinkedList<>();
        for (int i = 0; i < nbOfActions; i++) {
            ActionButton b = new ActionButton(e.getActionNames()[i], e.getActionDesc()[i],i);
            actionButtons.add(i,b);
            actions.getChildren().addAll(b,b.getDescription());
        }
    }

    public void showActionButtons(boolean bool) {
        actions.setVisible(bool);
    }

    public void resetActionButtons() {
        if (actionButtons!=null) {
            for (ActionButton ab : actionButtons) {
                ab.isSelected = false;
            }
        }
    }

    private void addButton(Button b) {
        getChildren().add(b);
        b.setTranslateX(START_BUTTON_X + nbOfButtons*200);
        nbOfButtons++;
    }

    // à appeler quand le jeu commence
    public void hidePreGameButtons() {
        getChildren().remove(start);
        getChildren().remove(money);
        for (Button b:buyButtons) {
            getChildren().remove(b);
        }
    }

    // à appeler à la fin du jeu
    public void hideAllGameButtons() {
        actions.setVisible(false);
    }

    public void setMoneyValue(int money) {
        this.money.setText(money+" £");
    }

}
