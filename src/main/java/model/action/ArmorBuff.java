package model.action;
import model.Cell;
import model.Player;
import model.entity.Entity;

public class ArmorBuff extends Action {
    public ArmorBuff(String name, String type, int min, int max, int dmg, int roundCD, int cooldown) {
        super.name=name;
        super.type=type;
        super.minRange=min;
        super.maxRange=max;
        super.amount =dmg;
        super.roundCooldown=roundCD;
        super.cooldown=cooldown;
    }

    @Override
    public boolean doAction(Player p, Cell c) {
        Entity e = c.getEntity();
        if (e==null || e.getPlayer()!=p || roundCooldown != 0) return false;
        e.armorBuff(amount);
        startCooldown(cooldown);
        return true;
    }

    @Override
    public String getDescription() {
        StringBuilder bld = new StringBuilder();
        bld.append("bonus d'armure : ").append(amount).append("\n");
        bld.append("portée: ");
        if (minRange==maxRange) bld.append(minRange).append("\n");
        else bld.append(minRange).append("-").append(maxRange).append("\n");
        bld.append("temps de récupération : ").append(cooldown).append(" tours \n");
        bld.append("temps restant avant utilisation :");
        return bld.toString();
    }

}