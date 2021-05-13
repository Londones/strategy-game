package model.entity;

import model.Player;
import model.action.*;

public class Clerk extends Entity {

    public Clerk(int x, int y, Player player) {
        super(player, 6, 4, 3, 2);
        super.x=x;
        super.y=y;
        super.actions = new Action[3];
        actions[0]=new Attack("orion sacrée", "magique",1,2,2,0,1);
        actions[1]=new Heal("Premiers soins", "heal",1,2,2,0,3);
        actions[2]=new Cleans("Purge", "cleans",1,2,0,0,5);
    }

    public Clerk(Player player) {
        this(-1,-1,player);
    }

    @Override
    public String toString() {
        return "Clerc";
    }

    @Override
    public Entity copy() {
        return new Clerk(x,y,player);
    }
}
