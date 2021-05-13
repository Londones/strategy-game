package model.entity;

import model.Player;
import model.action.Action;
import model.action.Attack;
import model.action.Heal;

public class Knight extends Entity {

    public Knight(int x, int y, Player player) {
        super(player, 12, 6, 3, 3);
        super.x=x;
        super.y=y;
        super.actions = new Action[2];
        actions[0]=new Attack("attaque de mélée", "physique",1,2,8,0,1);
        actions[1]=new Heal("auto soin", "heal",0,0,2,0,2);
    }

    public Knight(Player player) {
        this(-1,-1,player);
    }

    @Override
    public String toString() {
        return "Chevalier";
    }

    @Override
    public Entity copy() {
        return new Knight(x,y,player);
    }
}

