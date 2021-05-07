package model.entity;
import model.action.Action;
import model.action.Attack;
import model.Player;

public class Mage extends Entity {

    public Mage(int x, int y, Player player) {
        super(player, 10, 4, 2);
        super.x=x;
        super.y=y;
        super.actions = new Action[1];
        actions[0]=new Attack("Attaque\n ligne de mire", 1, 69420,8,0);
    }

    public Mage(Player player) {
        this(-1,-1,player);
    }

    @Override
    public String toString() {
        return "Mage";
    }

    @Override
    public Entity copy() {
        return new Mage(x,y,player);
    }

}

