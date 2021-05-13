package model.entity;

import model.Player;
import model.action.*;

public class Druid extends Entity {

    public Druid(int x, int y, Player player) {
        super(player, 8, 5, 1, 3);
        super.x=x;
        super.y=y;
        super.actions = new Action[3];
        actions[0]=new Attack("attaque de baton", "physique",1,2,2,0,1);
        actions[1]=new Poisoning("Empoisonnement", "statut",1,4,3,0,5);
        actions[2]=new Rooting("Enracinement", "statut",1,4,2,0,4);
    }

    public Druid(Player player) {
        this(-1,-1,player);
    }

    @Override
    public String toString() {
        return "Druide";
    }

    @Override
    public Entity copy() {
        return new Druid(x,y,player);
    }
}