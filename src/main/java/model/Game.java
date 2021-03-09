package model;
import model.entity.Entity;
import model.entity.Soldier;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Timer;

public class Game {

    private Grid grid;
    private Player[] players;
    private LinkedList<Entity> playableEntities; // liste de toutes les entités en jeu
    private int[] entTeam; // nombre d'entités pour chaque équipe actuellement en jeu

    private Player currentPlayer=null; // le joueur dont c'est le tour
    private Entity currentEntity=null;

    private int gameState; // état de jeu

    private int entInd; // index de l'entité courante

    //byte[] path;

    //Timer timer; plus tard



    public Game(Grid grid, Player player1, Player player2) {
        //TODO : permettre l'initialisation pour un nombre quelconque de joueurs
        playableEntities = new LinkedList<>();
        this.grid=grid;
        players = new Player[2];
        players[0]=player1;
        players[1]=player2;
        entTeam=new int[2];
    }

    protected Grid getGrid() {
        return grid;
    }

    void start() {
        initPlayableEntities();
        firstRound();
    }

    private void firstRound() {
        entInd=0;
        currentEntity=playableEntities.get(entInd);
        currentPlayer=currentEntity.getPlayer();
        for (Player p:players) {
            p.focusFirstEntity(entInd);
        }
    }

    protected void nextRound(Player pp) {
        if (currentPlayer!=pp) return;
        entInd=(entInd+1)%playableEntities.size();
        currentEntity=playableEntities.get(entInd);
        currentPlayer=currentEntity.getPlayer();
        for (Player p:players) {
            p.focusNextEntity(entInd);
        }
    }

    // chaque joueur pose ses unités
    // TODO
    private void initPlayableEntities() {
        int h=grid.getHeight();
        int w=grid.getWidth();
        Entity e1 = new Soldier(1,1,players[0]);
        Entity e2 = new Soldier(h-2,w-2,players[1]);
        addEntityToGame(e1, 1,1,0);
        addEntityToGame(e2, h-2,w-2,1);
    }

    private void addEntityToGame(Entity e, int x, int y, int playerNb) {
        e.updateCoords(x, y);
        grid.getCell(x,y).setEntity(e);
        entTeam[playerNb]++;
        playableEntities.add(e);
        for (Player p : players) {
            p.addEntityToView(e);
        }
    }

    // renvoie true si le jeu est fini
    // vérifie que seule une équipe ait encore des unités en jeu
    private boolean gameIsOver() {
        boolean b=false;
        for (int i:entTeam) {
            if (b && i>0) return false;
            if (i>0) b=true;
        }
        return true;
    }

    // bouge l'entité e en suivant le chemin donné en paramètre
    protected void move(Player p, byte[] path) {
        if (p!=currentPlayer || path==null) return;
        for (byte dir: path) {
            grid.move(currentEntity, dir);
            for (Player pp:players) {
                pp.moveEntityInView(dir);
            }
        }

    }

    protected byte[] makePath(int x, int y) {
        return grid.getPath(currentEntity.getX(), currentEntity.getY(), x, y, currentEntity.getMp());
        //return path;
    }


}
