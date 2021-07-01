package model;
import model.action.Action;
import model.entity.Entity;
import view.MainView;

import java.util.LinkedList;

/**
 * La classe Player représente un joueur
 * Elle reçoit directement les inputs de la View, et
 * est responsable de transmettre l'état du jeu à la View
 * */
public class Player {
    private final MainView view;
    protected Game game;

    private Player2 player2=null;

    // vérifie que le joueur est prêt à commencer une partie
    private boolean isReady;
    protected int money=10; //TODO hard codé, à changer

    public Player() {
        view=null;
        game=null;
    }

    public Player(MainView view) {
        this.view=view;
        game=null;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    protected void setPlayer2(Player2 p2) {
        player2=p2;
    }

    protected void setReady(boolean b) {
        isReady=b;
    }

    protected Player2 getPlayer2() {
        return player2;
    }

    protected boolean isReady() {
        return isReady;
    }

    public int getMoney() {
        return money;
    }

    protected void changeAmountOfMoney(int amount) {
        money += amount;
        if (view != null) view.updateMoneyView(money);
    }

    // utile uniquement pour le multi local
    protected void changeAmoutOfMoneyInViewOnly(int amount) {
        if (view != null) view.updateMoneyView(amount);
    }

    // ---------------------------------
    //  fonctions qui modifient le jeu :
    // ---------------------------------

    //fonction qui charge le toujours le niveau 1 du jeu
    //public void loadLevel() {
    //    Level level = new Level();
    //    new Game(level.showLevel("src/main/Levels/level1"), this);
    //}

    //fonction qui charge le jeu en fonction des arguments passés en paramètre
    //"option" indique le mode du jeu (multijoueur, joueur contre l'ordinateur, éditeur de niveau)
    //"level_filename" contient le chemin du fichier contenant le niveau 
    public void loadLevel(String option, String level_filename) {
        Level level = new Level();
        new Game(option, level.loadLevel(level_filename), this);
    }

    /*public void initBotPlayer() {
        PlayerBot pb = new PlayerBot();
        game.addPlayer(pb);
        pb.initEntities();
    }*/

    public void toggleReady() {
        if (game.isLocalMultiplayer() && view!=null) {
            if (!isReady) {
                changeAmoutOfMoneyInViewOnly(player2.money);
                canPressReadyButton(false);
            } else{
                player2.toggleReady();
                return;
            }
        }
        isReady=!isReady;
        if (isReady) game.start();
    }
    /*public int getnbEntity() {
    	return game.nb;
    }*/

    public void endTurn() {
        game.nextRound(this);
    }

    public void makePath(int x, int y) {
        byte[] path = game.makePath(x,y);
        view.drawPath(path);
    }

    // transmet au modèle un mouvement associé au joueur courant 
    public void move(byte[] path) {
        game.move(this, path);
    }

    // transmet au modèle une action associée au joueur courant
    // l'action va être effectée sur la case x, y si elle est valide
    // l'entité avec laquelle l'action va être effectuée est l'entité "currentEntity" associée au joueur courant
    public void doAction(int actionNb, int x, int y) {
        game.doAction(this, actionNb, x, y);
    }

    // sélection d'une action dans la "barre de tâches"
    public void selectAction(int actionNb) {
        game.selectAction(this, actionNb);
    }

    // annule l'action
    public void cancelAction() {
        game.cancelAction(this);
    }

    public void addEntityToGame(int x, int y, int entity_type) {
        if (game.isLocalMultiplayer() && isReady) {
            game.tryToAddEntityToGame(player2, x,y,entity_type);
        }
        else game.tryToAddEntityToGame(this, x,y,entity_type);
    }

    public void deleteEntity(int x, int y) {
        if (game.isLocalMultiplayer() && isReady) {
            game.tryToDeleteEntity(player2, x, y);
        }
        else game.tryToDeleteEntity(this, x, y);
    }

    // ---------------------------------
    //  fonctions qui modifient la vue :
    // ---------------------------------

    // permet d'envoyer à la view les paramètres necessaires
    // pour construire une grille de jeu
    public byte[][] getHeightGrid() {
        Grid grid = game.getGrid();
        int h=grid.getHeight();
        int w=grid.getWidth();
        byte[][] heightGrid = new byte[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                heightGrid[i][j]=grid.getCell(i,j).getHeight();
            }
        }
        return heightGrid;
    }

    protected void addEntityToView(Entity e) {
        Action[] actions = e.getActions();
        String[][] array = new String[actions.length][3];
        for (int i = 0; i < actions.length; i++) {
            array[i][0]=actions[i].getName();
            array[i][1]=actions[i].getDescription();
            array[i][2]=String.valueOf(actions[i].getRoundCooldown());
        }
        view.addEntity(e.getX(), e.getY(), e.getPlayer()==this,e.toString(), e.getHp(), e.getMp(), e.getArmor() ,array);
    }

    //place un sphère verte au dessus de la première entité
    protected void focusFirstEntity(int i, boolean isCurrentPlayer) {
        view.focusFirstEntity(i);
        view.allowGridViewControls(isCurrentPlayer);
        view.showActionButtons(isCurrentPlayer);
        view.resetAction();
    }

    //place un sphère verte au dessus de la prochaine entité
    protected void focusNextEntity(int i, boolean isCurrentPlayer) {
        view.focusNextEntity(i);
        view.allowGridViewControls(isCurrentPlayer);
        view.showActionButtons(isCurrentPlayer);
        view.resetAction();
    }

    protected void moveEntityInView(byte direction) {
        view.moveViewEntity(direction);
    }

    protected void updateStatView(int i, int newHp, int newArmor, int newPoisonStatut, int newRootStatut) {
        view.updateStat(i, newHp, newArmor, newPoisonStatut, newRootStatut);
    }

    protected void resetAction() {
        view.resetAction();
    }

    protected void updateActionRangeView(LinkedList<int[]> coordList) {
        int[][] coords = new int[coordList.size()][2];
        for (int i = 0; i < coords.length; i++) {
            coords[i][0]=coordList.get(i)[0];
            coords[i][1]=coordList.get(i)[1];
        }
        view.updateActionRange(coords);
    }

    protected void removeEntity(int i) {
        view.removeEntity(i);
    }

    protected void endGame(boolean hasWon) {
        view.endGame(hasWon, game.isLocalMultiplayer());
    }

    protected void canPressReadyButton(boolean b) {
        view.canPressReadyButton(b);
    }

    public void updateAllEntityViews() {
        game.updateAllEntityViews(this);
    }

    public void updateHPofTarget(int i) {
        view.updateStatsOfTarget(i);
    }
}