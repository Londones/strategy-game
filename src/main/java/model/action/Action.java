package model.action;
import model.*;
import model.entity.Entity;

public abstract class Action {
    protected String name;
    protected String type; // type d'attaque magic ou physique, n'a pas d'influence sur les actions Heal et ArmorBuff
    protected int minRange; // 0 = peut être fait sur soi même
    protected int maxRange;
    protected int amount; // quantité de dégats, soins, etc
    protected int roundCooldown; // tours de récupération au tour 1 puis en temps réel
    protected int cooldown; // nombre de tours de récupération
    //protected int cost; // coût en point d'action


    // doit renvoyer false si l'action échoue
    public abstract boolean doAction(Player p, Cell c);

    public abstract String getDescription();

    public void startCooldown(int cd) {
        roundCooldown = cd;
    }

    public void reduceCooldown() {
        if(roundCooldown>0){
            roundCooldown-=1;
        }
    }

    public String getName() {
        return name;
    }

    public int getRoundCooldown() { return roundCooldown; }

    public int getMinRange() {
        return minRange;
    }

    public int getMaxRange() {
        return maxRange;
    }
}
