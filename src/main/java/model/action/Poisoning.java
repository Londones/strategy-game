package model.action;
import model.*;
import model.entity.Entity;

public class Poisoning extends Action {

    public Poisoning(String name, String type, int min, int max, int nbrofturn, int roundCD, int cooldown) {
        super.name=name;
        super.type=type;
        super.minRange=min;
        super.maxRange=max;
        super.amount =nbrofturn;
        super.roundCooldown=roundCD;
        super.cooldown=cooldown;
        //super.cost=cost;
    }

    @Override
    public boolean doAction(Player p, Cell c) {
        Entity e = c.getEntity();
        if (e==null || e.getPlayer()!=p || roundCooldown != 0) return false;
        e.poisoning(amount);
        startCooldown(cooldown);
        return true;
    }

    @Override
    public String getDescription() {
        StringBuilder bld = new StringBuilder();
        bld.append("nombre de dégat/tour: ").append(1).append("pendant ").append(amount).append(" tours").append("\n");
        bld.append("portée: ");
        if (minRange==maxRange) bld.append(minRange).append("\n");
        else bld.append(minRange).append("-").append(maxRange).append("\n");
        bld.append("temps de récupération : ").append(cooldown).append(" tours \n");
        bld.append("temps restant avant utilisation :");
        return bld.toString();
    }

}

