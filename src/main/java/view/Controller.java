package view;

import javafx.scene.Group;
import javafx.stage.Stage;
import model.Player;


public class Controller {

    Player player;
    MainView view;

    public Controller() {}

    public Controller(MainView view) {
        this.view = view;
    }

    public void initializePlayer() {
        player=new Player(view);
    }

    public void loadLevel() {
        player.loadLevel();
    }

    public void loadLevel(String option, String level_filename) {
        player.loadLevel(option, level_filename);
    }

    public void mkGameGrid() {
        byte[][] heightGrid = player.getHeightGrid();
        view.makeGameScene(heightGrid);
        view.updateMoneyView(player.getMoney());
        player.updateAllEntityViews();
    }

    public void toggleReady() {
        player.toggleReady();
    }

    public void endTurn() {
        player.endTurn();
    }

    public void makePath(int x, int y) {
        player.makePath(x,y);
    }

    public void move(byte[] path) {
        player.move(path);
    }

    public void doAction(int actionNb, int x, int y) {
        player.doAction(actionNb, x, y);
    }

    //l'action est sélectionnée depuis la barre d'actions
    public void selectAction(int actionNb) {
        player.selectAction(actionNb);
    }

    //l'action est déselectionnée
    public void cancelAction() {
        player.cancelAction();
    }

    //ajout d'une entité de type "entity_type" au jeu 
    public void addEntityToGame(int x, int y, int entity_type) {
        player.addEntityToGame(x, y, entity_type);
    }

    /*public int nbEntity() {
    	return player.getnbEntity();
    }*/
    //on récupere le primaryStage de mainView
    public Stage getStage() {
        return this.view.getPrimaryStage();
    }
    //On récupere le mainGroup dans le quel on va make la grid
    public Group getMainGroup() {
        return this.view.getMainGroup();
    }

    public MainView getMainView() {
        return this.view;
    }

    //public void initBotPlayer() {
    //    player.initBotPlayer();
    //}

    public void deleteEntity(int x, int y) {
        player.deleteEntity(x,y);
    }
}
