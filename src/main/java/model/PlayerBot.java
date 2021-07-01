package model;

import model.entity.Entity;
import view.MainView;

// pour le moment cette classe ne sert que à override les fonctions
// qui causeraient un NullPointerException
public class PlayerBot extends Player {
    public PlayerBot() {
        super(null);
    }

    @Override
    public void addEntityToView(Entity e) {}
    @Override
    protected void focusFirstEntity(int i, boolean isCurrentPlayer) { if (isCurrentPlayer) endTurn(); }
    @Override
    protected void focusNextEntity(int i, boolean isCurrentPlayer) {
        if (isCurrentPlayer) endTurn();
    }
    @Override
    protected void moveEntityInView(byte direction) {}
    @Override
    protected void updateStatView(int i, int newHp, int newArmor, int newPoisonStatut, int newRootStatut) {}
    @Override
    protected void removeEntity(int i) {}
    @Override
    protected void endGame(boolean hasWon) {
        if (hasWon) System.out.println("git gud casul");
    }
    @Override
    protected void canPressReadyButton(boolean b) {}

    protected void initEntities() {
        game.tryToAddEntityToGame(this, 1, 1, 0);
        game.tryToAddEntityToGame(this, 4, 3, 1);
        setReady(true);
    }

    @Override
    protected void changeAmountOfMoney(int amount) {}

    @Override
    protected void resetAction() {}

    @Override
    public void updateHPofTarget(int i) {}
}