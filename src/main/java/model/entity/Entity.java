package model.entity;

import model.Cell;
import model.action.Action;
import model.Player;

public abstract class Entity {
    protected int x;  //position x
    protected int y;  //position y
    protected int hp; //points de vie
    protected int armor;
    protected int maxHp; //points de vie max
    protected int mp; //points de mouvements
    protected int maxMp;//points de mouvements max
    protected Action[] actions;
    protected Player player;

    public Entity(Player player, int health, int movement, int armor) {
        this.player=player;
        maxHp = health;
        hp = health;
        maxMp = movement;
        mp = movement;
        this.armor = armor;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHp() {
        return hp;
    }

    public int getArmor() { return armor; }

    public int getMp() {
        return mp;
    }

    public Action getAction(int i) {
        return actions[i];
    }

    public Action[] getActions() {
        return actions;
    }

    public Player getPlayer() {
        return player;
    }

    public void damage(int dmg) {
        if (dmg>0) {
            hp=hp-(dmg-armor);
            if (hp<0) hp=0;
        }
    }

    public void magicDamage(int mdmg){
        if (mdmg>0) {
            hp-=mdmg;
            if (hp<0) hp=0;
        }
    }

    public void heal(int h) {
        if (h>0) {
            hp+=h;
            if (hp>maxHp) hp=maxHp;
        }
    }

    public void armorBuff(int a){
        armor+=a;
    }

    //sert à savoir si une entité quelconque appartient au même joueur que l'entité source
    public boolean isAlly(Entity e){
        return e.getPlayer() == player;
    }

    public abstract Entity copy();

    // update les coordonnées x et y
    public void updateCoords(int direction) {
        boolean odd = x%2==0;
        switch (direction) {
            case 0:
                x--;
                if (!odd) y++;
                break;
            case 1:
                y++;
                break;
            case 2:
                x++;
                if (!odd) y++;
                break;
            case 3:
                x++;
                if (odd) y--;
                break;
            case 4:
                y--;
                break;
            case 5:
                x--;
                if (odd) y--;
                break;
        }
    }

    public void updateCoords(int x, int y) {
        this.x=x;
        this.y=y;
    }

    public void decreaseMp() {
        mp--;
    }

    public void resetMp() {
        mp=maxMp;
    }

    // renvoie false si l'action échoue
    public boolean doAction(Player p, int i, Cell c) {
        return actions[i].doAction(p, c);
    }

}
