package view;

import custom.MainMenu;
import javafx.application.Application;
import javafx.scene.*;
import javafx.stage.Stage;

import java.util.LinkedList;

public class MainView extends Application {

    private Controller ctrl;
    private Scene mainScene; // tout ce qui est en 2D : les boutons, les menus, etc
    private SubScene scene3D; // tout ce qui est en 3D est ici
    private MainMenu menu;
    private Group mainGroup;
    private Stage primaryStage;
    private WelcomeInterface welcomeinterface;

    private int height = 720;
    private int width = 1080;

    private final LinkedList<EntityView> entityViews= new LinkedList<>();
    private EntityView currentEntityView;
    private int unitInd;

    private GridView gridView =null;
    private UserInterface ui=null;

    int pointedX=-1;
    int pointedY=-1;
    int chosenAction=-1;
    int preGameAction = -1;

    byte[] path=null;

    @Override
    public void start(Stage primaryStage) throws Exception {
    	mainGroup = new Group();
    	this.primaryStage=primaryStage;
        //Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        //mainGroup = new Group();
        mainScene=new Scene(mainGroup,width,height);
        ctrl = new Controller(this);

        welcomeinterface = new WelcomeInterface(width, height, ctrl);
        //mainScene=new Scene(welcomeinterface,width,height); //what ??
        mainGroup.getChildren().add(welcomeinterface);
        //menu = new MainMenu(width,height);

        
        primaryStage.setTitle("Jeu de stratégie");
        primaryStage.setScene(mainScene);
        primaryStage.centerOnScreen();
        primaryStage.show();
       
    }

    

    public Controller getCtrl() {
    	return this.ctrl;
    }
    public void setChosenAction(int n) { //set l'action
    	this.chosenAction=n;
    }
    public void setMainGroup(Group g) {
    	this.mainGroup=g;
    }
    //Getter pour le mainGroup 
    public Group getMainGroup() {
    	return this.mainGroup;
    }
    //Getter pour le primaryStage du MainView
    
    public Stage getPrimaryStage() {
    	return this.primaryStage;
    }

    public int getChosenAction() {
        return chosenAction;
    }

    public void setPointedXY(int x, int y) {
        pointedX = x;
        pointedY = y;
    }

    public void setPreGameAction(int preGameAction) {
        this.preGameAction = preGameAction;
    }

    public int getPreGameAction() {
        return preGameAction;
    }

    public void makePath(int x, int y) {
        ctrl.makePath(x,y);
    }

    public void drawPath(byte[] newPath) {
        cleanPath();
        path=newPath;
        Hexagon h= gridView.getHexagon(currentEntityView.getX(), currentEntityView.getY());
        if (path!=null) {
            for (byte dir:path) {
                h= gridView.getAdjHexagon(h,dir);
                if(h!=null) h.color(1);
            }
        }
    }


    public void cleanPath() {
        if (path!=null && chosenAction==-1) {
            Hexagon h = gridView.getHexagon(currentEntityView.getX(), currentEntityView.getY());
            for (byte dir:path) {
                h= gridView.getAdjHexagon(h,dir);
                if(h!=null) h.color(0);
            }
        }
    }

    private void moveModelEntity() {
        cleanPath();
        ctrl.move(path);
        path=null;
    }

    public void moveViewEntity(byte direction) {
        gridView.moveEntity(currentEntityView, direction);
        currentEntityView.decreaseMp();
    }

    public void makeGameScene(byte[][] heightGrid) {

        gridView = new GridView(heightGrid, this);
        scene3D=new SubScene(gridView, width, height, true, SceneAntialiasing.BALANCED);
        GameCamera camera = new GameCamera();
        scene3D.setCamera(camera);
        camera.initialiseControls(scene3D);
        ui = new UserInterface(width, height, ctrl, this);

        mainGroup.getChildren().add(scene3D);
        mainGroup.getChildren().add(ui);

    }

    public void addEntity(int x, int y, boolean isAlly, String name, int hp, int mp, int armor, String[][] actions) {
        if (gridView==null) return;
        EntityView u = new EntityView(this, x,y,isAlly,name, hp, mp, armor, actions);
        entityViews.add(u);
        gridView.addEntity(u,x,y);
        u.allowActionOnClick(true);
        u.showInfoOnHover(true);
    }



    public void focusFirstEntity(int i) {
        ui.hidePreGameButtons();
        currentEntityView = entityViews.get(i);
        currentEntityView.highlight(true);
    }


    public void focusNextEntity(int i) {
        currentEntityView.highlight(false);
        currentEntityView = entityViews.get(i);
        currentEntityView.highlight(true);
    }

    public void showActionButtons(boolean bool) {
        if (bool) ui.updateActionButtons(currentEntityView);
        ui.showActionButtons(bool);
    }

    public void allowGridViewControls(boolean bool) {
        gridView.allowControls(bool);
        for (EntityView e: entityViews) {
            e.showInfoOnHover(bool);
        }
    }

    public void highlightHexagon(int x, int y, boolean b) {
        gridView.getHexagon(x,y).setHighlight(b);
    }

    public void showEntityDetails(EntityView e) {
        ui.updateEntityDetails(e);
        ui.showEntityDetails(true);
    }

    public void hideEntityDetails() {
        ui.showEntityDetails(false);
    }

    //fonction main du programme
    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void setAction(int actionNb) { //permet d'initialiser l'action
        chosenAction=actionNb;
        ctrl.selectAction(actionNb);
        allowActionOnEntities(true);
    }

    public void resetAction() {
        ctrl.cancelAction();
        chosenAction=-1;
        allowActionOnEntities(false);
        ui.resetActionButtons();
        gridView.clearSelectedHex();
    }

    public void doAction() {
        if (chosenAction==-2) {
            addOrDeleteEntity(pointedX, pointedY);
        } else if (chosenAction==-1) {
            moveModelEntity();
        } else if (chosenAction>=0) {
            ctrl.doAction(chosenAction, pointedX, pointedY);
        }
    }

    public void updateStat(int i, int newHp, int newArmor, int newPoisonStatut, int newRootStatut) {
        entityViews.get(i).setHp(newHp);
        entityViews.get(i).setArmor(newArmor);
        entityViews.get(i).setPoisonStatut(newPoisonStatut);
        entityViews.get(i).setRootStatut(newRootStatut);
        //ui.updateEntityDetails(entityViews.get(i));
    }

    public void updateStatsOfTarget(int i) {
        ui.updateEntityDetails(entityViews.get(i));
    }

    private void allowActionOnEntities(boolean bool) {
        for (EntityView e:entityViews) {
            e.allowActionOnClick(bool);
        }
    }

    public void updateActionRange(int[][] newCoords) {
        gridView.clearSelectedHex();
        gridView.setCoords(newCoords);
        gridView.updateSelectedHex();
    }

    public void removeEntity(int i) {
        gridView.getChildren().remove(entityViews.get(i));
        entityViews.remove(i);
    }

    public void endGame(boolean hasWon, boolean localMP) {
        resetAction();
        allowGridViewControls(false);
        allowActionOnEntities(false);
        currentEntityView.highlight(false);
        ui.hideAllGameButtons();
        ui.showEndScreen(hasWon, localMP);
    }

    public void addOrDeleteEntity(int x, int y) {
        if (preGameAction==-1) ctrl.deleteEntity(x,y);
        else ctrl.addEntityToGame(x,y,preGameAction);
    }


    public void canPressReadyButton(boolean b) {
        ui.canPressReadyButton(b);
    }

    public void updateMoneyView(int money) {
        ui.setMoneyValue(money);
    }

    public WelcomeInterface getWelcomeinterface() {
        return welcomeinterface;
    }

    public void returnToMainMenu() {
        resetAll();
        mainGroup.getChildren().add(welcomeinterface);
        welcomeinterface.showMainMenu();
    }

    private void resetAll() {
        scene3D=null;
        mainGroup.getChildren().clear();
        gridView=null;
        entityViews.clear();
        currentEntityView=null;
        ui=null;
        pointedX=-1;
        pointedY=-1;
        chosenAction=-1;
        preGameAction = -1;
        path=null;
        welcomeinterface.resetAll();
    }
}

	


       