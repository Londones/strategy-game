package model;
import model.entity.*;

import java.io.Serializable;
import java.util.LinkedList;



public class Game implements Serializable {

	private static final long serialVersionUID = 7373047453891668295L;
	private final Grid grid;
    private final LinkedList<Player> players;
    private LinkedList<Entity> playableEntities; // liste de toutes les entités en jeu
    private int[] entTeam; // nombre d'entités pour chaque équipe actuellement en jeu
	int nb=0;

    private Player currentPlayer=null; // le joueur dont c'est le tour
    private Entity currentEntity=null;

    private int gameState; // état de jeu : 0 : phase de selection des entités, 1 : phase de jeu
    private int entInd; // index de l'entité courante


    public Game(Grid grid, Player ... playerList) {
        playableEntities = new LinkedList<>();
        this.grid=grid;

        players = new LinkedList<>();
        for (Player p : playerList){
            addPlayer(p);
        }
    }

    public void addPlayer(Player p) {
        if (!players.contains(p)) players.add(p);
        p.setGame(this);
    }

    protected Grid getGrid() {
        return grid;
    }

    // un bouton dit si le joueur a fini de poser ses entités une fois que les joueurs ont cliqué
    void start() {
        if (allPlayersAreReady()) {
            gameState = 1;
            firstRound();
        }

    }

    // premier tour de jeu
    // current player et current entity sont correctement initialisée
    private void firstRound() {
        entInd=0;
        currentEntity=playableEntities.get(entInd);
        currentPlayer=currentEntity.getPlayer();
        for (Player p:players) {
            p.focusFirstEntity(entInd, p==currentPlayer);
        }
    }

    // vérifie qu'un joueur ait le droit de jouer, càd que c'est son tour
    // et que gamestate == 1
    private boolean canPlay(Player p) {
        return (currentPlayer==p && gameState==1);
    }

    // permet de passer au tour de l'entité suivante
    protected void nextRound(Player player) {
        if (!canPlay(player)) return; // seul le joueur courant peut effectuer l'action
        grid.clearCoordList();
        entInd=(entInd+1)%playableEntities.size();
        currentEntity=playableEntities.get(entInd);
        currentPlayer=currentEntity.getPlayer();
        if(currentEntity.getRoot()>0){
            currentEntity.setMp(0);
        }
        else{
            currentEntity.resetMp();
        }
        if(currentEntity.getPoison()>0){
            currentEntity.magicDamage(1);
            for (int i = 0; i < playableEntities.size(); i++) {
                for (Player p : players) {
                    p.updateStatView(i, playableEntities.get(i).getHp(), playableEntities.get(i).getArmor(), playableEntities.get(i).getPoison(), playableEntities.get(i).getRoot());
                }
                removeIfDead(i);
            }
        }
        currentEntity.decreaseAllCooldowns();
        for (Player p:players) {
            p.focusNextEntity(entInd, p==currentPlayer);
        }
    }


    // permet d'ajouter un entité au model et à la view de tous les joueurs
    private boolean addEntityToGame(Entity e, int x, int y) {
        if (grid.getCell(x,y).getEntity()!=null || e==null) return false; //yeet
        e.updateCoords(x, y);
        grid.getCell(x,y).setEntity(e);
        playableEntities.add(e);
        for (Player p : players) {
            p.addEntityToView(e);
        }
        return true;
    }

   
    //un joueur essaie de poser une entité
    public void tryToAddEntityToGame(Player player, int x, int y, int entity_type) {
        if(!canAddEntity(player) || gameState!=0) return;
        Entity e = null;
        //entity_type c'est pour indiquer quel type d'entité à ajouter (par exemple 0 pour soldier 1 pour Knight)
        switch (entity_type) {
            case 0:
                e = new Soldier(player);
                break;
            case 1:
                e = new Knight(player);
                break;
            case 2:
                e = new Wizard(player);
                break;
            case 3:
                e = new Druid(player);
                break;
            case 4:
                e = new Clerk(player);
                break;
            default:
                break;
        }
        if (e!=null && e.getCost()<=player.getMoney()) {
            if (addEntityToGame(e, x, y)) player.changeAmountOfMoney(-e.getCost());
            player.setReady(false);
            // le joueur n'as le droit de dire qu'il est prêt à jouer que s'il a au moins une entité
            player.canPressReadyButton(hasAtLeastOneEntityPlaced(player));
        }
    }

    public void tryToDeleteEntity(Player player, int x, int y) {
        Entity e = grid.getCell(x,y).getEntity();
        if (gameState!=0 || e==null || e.getPlayer()!=player) return;
        player.changeAmountOfMoney(e.getCost());
        removeEntity(e);
        player.setReady(false);
        player.canPressReadyButton(hasAtLeastOneEntityPlaced(player));
    }


    private boolean hasAtLeastOneEntityPlaced(Player player) {
        for (Entity e:playableEntities) {
            if (e.getPlayer()==player) return true;
        }
        return false;
    }

    private boolean allPlayersAreReady() {
        for (Player p:players) {
            if (!p.isReady()) return false;
        }
        return true;
    }


    // vérifie que le joueur a au plus 4 entités en jeu
    private boolean canAddEntity(Player player) {
        int i=0;
        for (Entity e:playableEntities) {
            if (e.getPlayer()==player) i++;
        }
        return i<64;
    }


    // renvoie true si le jeu est fini
    // vérifie que seule une équipe ait encore des unités en jeu
    private boolean gameIsOver() {
        if (playableEntities.size()<=1) return true;
        Player p = playableEntities.get(0).getPlayer();
        for (int i = 1; i < playableEntities.size(); i++) {
            if (p != playableEntities.get(i).getPlayer()) return false;
        }
        return true;
    }

    // bouge l'entité e en suivant le chemin donné en paramètre
    // met à jour la vue de tous les joueurs
    protected void move(Player player, byte[] path) {
        if ((!canPlay(player)) || path==null) return;
        for (byte dir: path) {
            grid.move(currentEntity, dir);
            for (Player p:players) {
                p.moveEntityInView(dir);
            }
        }

    }
    // renvoie le chemin menant de la position de l'entité en cours et les coords x y
    // renvoie null si le chemin n'exsite pas
    protected byte[] makePath(int x, int y) {
        return grid.getPath(currentEntity.getX(), currentEntity.getY(), x, y, currentEntity.getMp());
    }


    public void doAction(Player player, int action, int x, int y) {
        if (!canPlay(player)) return;
        Cell c = grid.getCell(x,y);
        if (grid.isInCoordList(x,y) && currentEntity.doAction(player, action, c)) {
            // pour l'instant on update les points de vie de toutes les entités, ce n'est pas idéal
            for (int i = 0; i < playableEntities.size(); i++) {
                for (Player p : players) {
                    p.updateStatView(i, playableEntities.get(i).getHp(), playableEntities.get(i).getArmor(), playableEntities.get(i).getPoison(), playableEntities.get(i).getRoot());
                }
                removeIfDead(i);
            }
        }
        grid.clearCoordList();
        player.resetAction();
        if (gameIsOver()) {
            gameState=2;
            for (Player p : players) {
                boolean hasWon = playableEntities.get(0).getPlayer()==p;
                p.endGame(hasWon);
            }
        }
    }

    public void selectAction(Player player, int actionNb) {
        if (!canPlay(player)) return;
        System.out.println(currentEntity.getClass().getName());
        /*if(currentEntity.getClass().getName() != "model.entity.Mage"){

        }
        else{
            grid.selectLineOfSight(int x, int y, byte direction);
        }*/
        int minRange=currentEntity.getAction(actionNb).getMinRange();
        int maxRange=currentEntity.getAction(actionNb).getMaxRange();
        grid.selectCellsWithinRange(currentEntity.getX(), currentEntity.getY(), minRange, maxRange);
        player.updateActionRangeView(grid.getCoordList());
    }

    public void cancelAction(Player player) {
        if (!canPlay(player)) return;
        grid.clearCoordList();
    }

    // s'occupe de "tuer" l'entité playableEntities[i] si ses pv == 0
    private void removeIfDead(int i) {
        if (playableEntities.get(i).getHp()<=0) {
            removeEntity(i);
        }
    }

    private void removeEntity(Entity e) {
        int i = playableEntities.indexOf(e);
        removeEntity(i);
    }


    private void removeEntity(int i) {
        grid.getCell(playableEntities.get(i).getX(),playableEntities.get(i).getY()).setEntity(null);
        playableEntities.remove(i);
        if (i<=entInd) entInd--; // on fait attention à ne pas changer l'ordre de jeu des entités
        for (Player p: players) {
            p.removeEntity(i);
        }
    }

}
