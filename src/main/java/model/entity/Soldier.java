package model.entity;
import model.action.Action;
import model.action.Attack;
import model.Player;

public class Soldier extends Entity {

    public Soldier(int x, int y, Player player) {
        super(player, 10, 4);
        super.x=x;
        super.y=y;
        super.actions = new Action[2];
        actions[0]=new Attack("attaque de mélée", 1,1,4,0);
        actions[1]=new Attack("attaque à distance", 2,6,6,0);
    }

    public Soldier(Player player) {
        this(-1,-1,player);
    }

    @Override
    public String toString() {
        return "Soldat";
    }

    @Override
    public Entity copy() {
        return new Soldier(x,y,player);
    }

}

