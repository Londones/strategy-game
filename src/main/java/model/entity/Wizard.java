package model.entity;

import model.action.Action;
import model.action.ArmorBuff;
import model.Player;
import model.action.Attack;

public class Wizard extends Entity {

    public Wizard(int x, int y, Player player) {
        super(player, 6, 6, 0, 4);
        super.x=x;
        super.y=y;
        super.actions = new Action[2];
        actions[0]=new Attack("Boule de feu", "magic",1,6,8,0,1);
        actions[1]=new ArmorBuff("Protection", "buff",1,4,2,0,3);
    }

    public Wizard(Player player) {
        this(-1,-1,player);
    }

    @Override
    public String toString() {
        return "Magicien";
    }

    @Override
    public Entity copy() {
        return new Soldier(x,y,player);
    }

}
