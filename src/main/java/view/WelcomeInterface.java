package view;

import custom.GameMenu;
import custom.MainMenu;
import custom.LevelMenu;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import custom.GameButton;


public class WelcomeInterface extends Pane {

    private GameButton option;
    private GameMenu optionMenu;
    private GameButton quitter;
	private Controller ctrl;
    private GameButton play;
    private MainMenu menu;
    private LevelMenu levelMenu;

    int width;
    int height;

    int nbOfButtons=0;

    WelcomeInterface(int width, int height, Controller controller){
     
        this.width=width;
        this.height=height;
        ctrl=controller;

        play = makeButton("Lancer");
        addButton(play);
        play.setTranslateX(width/2 - 85);

        menu = new MainMenu(width, height);

        levelMenu = new LevelMenu(width, height);

        getChildren().add(menu);

        optionMenu = new GameMenu(300,400);
        createBackground();
        initOptionButton();
        initOptionMenu();
        initButtonListeners();

        ctrl.getStage().show();
    }
    
    private void addButton(Button b) {
        getChildren().add(b);
    }

    private GameButton makeButton(String name){
        GameButton b = new GameButton(name);
        b.setFont(new Font(30));
        b.setTranslateY(height-100);
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

    private void initButtonListeners(){
        play.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            /*ctrl.getMainView().setChosenAction(-2);
            ctrl.view.setMainGroup( ( Group)new BuyEntityInterface(width, height, ctrl));
            Scene buyScene= new Scene(ctrl.getMainGroup(),width,height);
            ctrl.getStage().setScene(buyScene);

            ctrl.initializePlayer();
            ctrl.loadLevel();
            ctrl.mkGameGrid();
            ctrl.initBotPlayer();
            ctrl.getMainView().allowGridViewControls(true);*/

            menu.animation();
            //on enlève le bouton "options" qui se trouve en haut à gauche de l'écran
            option.setVisible(false);
            //on fait disparaître le menu "options" si il est ouvert
            optionMenu.fadeOutScene();

            // ces lignes ont été ajoutés pour éviter que le fond d'écran apparaisse pendant les chargements
            Image backgroundImage = new Image(menu.BG_IMAGE_1, width, height, false, true);
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
            setBackground(new Background(background));

        });

        menu.getMulti().setOnMouseClicked(e -> {
            levelMenu.setGameMode("localMultiplayer");
            getChildren().clear();
            getChildren().add(levelMenu);
            levelMenu.animation(true);
            //launchGame("localMultiplayer");
        });

        menu.getBot().setOnMouseClicked(e -> {
            levelMenu.setGameMode("vsBot");
            getChildren().clear();
            getChildren().add(levelMenu);
            levelMenu.animation(true);
            //launchGame("vsBot");
        });

        option.setOnMouseClicked(e -> {
            optionMenu.animation();
        });

        quitter.setOnAction(e -> {
            ctrl.getMainView().getPrimaryStage().close();
        });
        menu.getQuit().setOnAction(e -> {
            ctrl.getMainView().getPrimaryStage().close();
        });

        levelMenu.getMap1().setOnMouseClicked(e -> {
            launchGame(levelMenu.getGameMode(), "level1");
        });

        levelMenu.getMap2().setOnMouseClicked(e -> {
            launchGame(levelMenu.getGameMode(), "level2");
        });

        levelMenu.getMap3().setOnMouseClicked(e -> {
            launchGame(levelMenu.getGameMode(), "level3");
        });

        levelMenu.getMap4().setOnMouseClicked(e -> {
            launchGame(levelMenu.getGameMode(), "level4");
        });

        levelMenu.getBackButton().setOnMouseClicked(e -> {
            /*menu.animation();
            //on enlève le bouton "options" qui se trouve en haut à gauche de l'écran
            option.setVisible(false);
            //on fait disparaître le menu "options" si il est ouvert
            optionMenu.fadeOutScene();

            // ces lignes ont été ajoutés pour éviter que le fond d'écran apparaisse pendant les chargements
            Image backgroundImage = new Image(menu.BG_IMAGE_1, width, height, false, true);
            BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
            setBackground(new Background(background));*/
            getChildren().clear();
            getChildren().add(menu);
        });
    }

    private void launchGame(String option, String level_filename) {
        ctrl.getMainView().setChosenAction(-2);
        ctrl.view.setMainGroup( ( Group)new BuyEntityInterface(width, height, ctrl));
        Scene buyScene= new Scene(ctrl.getMainGroup(),width,height);
        ctrl.getStage().setScene(buyScene);

        ctrl.initializePlayer();
        ctrl.loadLevel(option, level_filename);
        ctrl.mkGameGrid();
        ctrl.getMainView().allowGridViewControls(true);
        menu.animation();
    }

    private void createBackground(){
        Image backgroundImage = new Image("icons/Welcome-Page-BG.png", width, height, false, true);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, null);
        setBackground(new Background(background));
    }

    public MainMenu getMenu() {
        return menu;
    }

    public void resetAll() {
        getChildren().clear();
        menu.animation(false);
        levelMenu.animation(false);
    }

    public void showMainMenu() {
        getChildren().add(menu);
        menu.animation(true);
    }
}
