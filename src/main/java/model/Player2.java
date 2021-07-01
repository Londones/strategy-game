package model;

import model.entity.Entity;
import view.MainView;

// joueur 2 des parties locales : sera de fait contrôlée par le joueur principal
public class Player2 extends Player {
    Player player1;
    public Player2(Player p) {
        super(null);
        player1=p;
    }

    @Override
    public void addEntityToView(Entity e) {}
    @Override
    protected void focusFirstEntity(int i, boolean isCurrentPlayer) {}
    @Override
    protected void focusNextEntity(int i, boolean isCurrentPlayer) {}
    @Override
    protected void moveEntityInView(byte direction) {}
    @Override
    protected void updateStatView(int i, int newHp, int newArmor, int newPoisonStatut, int newRootStatut) {}
    @Override
    protected void removeEntity(int i) {}
    @Override
    protected void endGame(boolean hasWon) {}
    @Override
    protected void canPressReadyButton(boolean b) {
        player1.canPressReadyButton(b);
    }

    @Override
    protected void resetAction() {}

    protected void changeAmountOfMoney(int amount) {
        money += amount;
        player1.changeAmoutOfMoneyInViewOnly(money);
    }

    @Override
    public void updateHPofTarget(int i) {
        player1.updateHPofTarget(i);
    }

    @Override
    public void permuteEntities(int[] perm) {}
}
