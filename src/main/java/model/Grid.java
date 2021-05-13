package model;
import java.util.LinkedList;

import model.entity.Entity;
import java.io.Serializable;
import java.util.LinkedList;


public class Grid implements Serializable {

    private static final long serialVersionUID = -9029502583635790499L;

    private final Cell[][] cells;

    private final int height;
    private final int width;

    private LinkedList<int[]> coordList;

    final double offset=0.8660254037844386; // = (racine 3) / 2

    public Grid(int h, int w) {
        height=h;
        width=w;
        cells=new Cell[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                cells[i][j]=new Cell();
            }
        }
        coordList = new LinkedList<>();
    }

    public Cell getCell(int h, int w) {
        return cells[h][w];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    // vérifie que les coordonées sont dans la grille
    private boolean isInBounds(int x, int y) {
        return (x>=0 && y>=0 && x<height && y<width);
    }

    private boolean isInBounds(int[] coords) {
        return (coords[0]>=0 && coords[1]>=0 && coords[0]<height && coords[1]<width);
    }

    /* renvoie la cellule adjascente aux coordonnées entrées, en fonction de l'entier direction, ou null si celà ferait sortir de la grille
       direction prends une valeur entre 1 et 6. représentation vusielle de la cellule renvoyée :
         5   0
        4  x  1
         3   2
    */
    public Cell getAdjCell(int h, int w, int direction) {
        boolean odd = h%2==0;
        switch (direction) {
            case 0:
                if (h<=0 || !odd && w>=width-1) return null;
                if (odd) return cells[h-1][w];
                else return cells[h-1][w+1];
            case 1:
                if (w>=width-1) return null;
                return cells[h][w+1];
            case 2:
                if (h>=height-1 || !odd && w>=width-1) return null;
                if (odd) return cells[h+1][w];
                else return cells[h+1][w+1];
            case 3:
                if (h>=height-1 || odd && w<=0) return null;
                if (odd) return cells[h+1][w-1];
                else return cells[h+1][w];
            case 4:
                if (w<=0) return null;
                return cells[h][w-1];
            case 5:
                if (h<=0 || odd && w<=0) return null;
                if (odd) return cells[h-1][w-1];
                else return cells[h-1][w];

            default:
                break;
        }
        return null;
    }

    // meme chose que getAdjCell mais a la place d'envoyer une cellule elle envoie les
    // coordonnees de la cellule adjacente
    // 5   0
    //4  x  1
    // 3   2
    public int[] getAdjCellCoordinates(int h, int w, int direction) {
        boolean odd = h % 2 == 0;
        int[] result = new int[2];
        switch (direction) {
            case 0:
                if (h <= 0 || !odd && w >= width - 1) return null;
                if (odd) {
                    // return cells[h-1][w];
                    result[0] = h - 1;
                    result[1] = w;
                    return result;
                } else {
                    // return cells[h-1][w+1];
                    result[0] = h - 1;
                    result[1] = w + 1;
                    return result;
                }
            case 1:
                if (w >= width - 1) return null;
                // return cells[h][w+1];
                result[0] = h;
                result[1] = w + 1;
                return result;
            case 2:
                if (h >= height - 1 || !odd && w >= width - 1) return null;
                if (odd) {
                    // return cells[h+1][w];
                    result[0] = h + 1;
                    result[1] = w;
                    return result;
                } else {
                    // return cells[h+1][w+1];
                    result[0] = h + 1;
                    result[1] = w + 1;
                    return result;
                }
            case 3:
                if (h >= height - 1 || odd && w <= 0) return null;
                if (odd) {
                    // return cells[h+1][w-1];
                    result[0] = h + 1;
                    result[1] = w - 1;
                    return result;
                } else {
                    // return cells[h+1][w];
                    result[0] = h + 1;
                    result[1] = w;
                    return result;
                }
            case 4:
                if (w <= 0) return null;
                // return cells[h][w-1];
                result[0] = h;
                result[1] = w - 1;
                return result;
            case 5:
                if (h <= 0 || odd && w <= 0) return null;
                if (odd) {
                    // return cells[h-1][w-1];
                    result[0] = h - 1;
                    result[1] = w - 1;
                    return result;
                } else {
                    // return cells[h-1][w];
                    result[0] = h - 1;
                    result[1] = w;
                    return result;
                }
            default:
                break;
        }
        return null;
    }

    // même chose que getAdjCellCoordinates dans le code de Matyas, mais renvoie toujours des coordonnées,
    // jamais null, même si elles seraient en dehors de la grille
    public int[] getAdjCellCoordinates_2(int h, int w, int direction) {
        boolean odd = h % 2 == 0;
        int[] result = new int[2];
        switch (direction) {
            case 0:
                if (odd) {
                    result[0] = h - 1;
                    result[1] = w;
                    return result;
                } else {
                    result[0] = h - 1;
                    result[1] = w + 1;
                    return result;
                }
            case 1:
                result[0] = h;
                result[1] = w + 1;
                return result;
            case 2:
                if (odd) {
                    result[0] = h + 1;
                    result[1] = w;
                    return result;
                } else {
                    result[0] = h + 1;
                    result[1] = w + 1;
                    return result;
                }
            case 3:
                if (odd) {
                    result[0] = h + 1;
                    result[1] = w - 1;
                    return result;
                } else {
                    result[0] = h + 1;
                    result[1] = w;
                    return result;
                }
            case 4:
                result[0] = h;
                result[1] = w - 1;
                return result;
            case 5:
                if (odd) {
                    result[0] = h - 1;
                    result[1] = w - 1;
                    return result;
                } else {
                    result[0] = h - 1;
                    result[1] = w;
                    return result;
                }

            default:
                break;
        }
        return null;
    }


    //Vérifie si un mouvement depuis la cellule x, y dans la direction i est possible
    private boolean isMovePossible(int x, int y, int orientation) {
        // Vérifie si les coordonnées sont correctes
        if(x < 0 || y < 0 || x > height || y > width){
            return false;
        }
        Cell c = getAdjCell(x, y, orientation);
        // Renvoie "false" si il n'y a pas de cellule voisine dans la direction "i"
        if (c==null) return false;

        // Vérifie que la différence de hauteur n'est pas trop grande
        int delta = cells[x][y].getHeight() - c.getHeight();
        return (c.getEntity()==null && delta <= 1 && delta >=-1 );
    }

    //Vérifie si un mouvement depuis le cellule x1, y1 vers la cellule x2, y2 est possible
    private boolean isMovePossible(int x1, int y1, int x2, int y2) {
        // Vérifie si les coordonnées sont correctes
        if(x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || x1 > height || y1 > width || x2 > height || y2 > width){
            return false;
        }

        // Vérifie si les deux cellules sont les unes à côté des autres (ne fonctionne pas)
        /*int result[];
        boolean voisin = false;
        for(int i = 0; i < 6; i++){
            result = getAdjCellCoordinates(x1, y1, i);
            if(result[0] == x2 && result[1] == y2){
                voisin = true;
            }
        }
        if(!voisin){
            return false;
        }*/

        // Vérifie que la différence de hauteur n'est pas trop grande
        int delta = cells[x1][y1].getHeight() - cells[x2][y2].getHeight();

        //On vérifie si il y a une entité à x2, y2 et si la différence de hauteur n'est pas trop grande entre x1, y1 et x2, y2
        return (cells[x2][y2].getEntity() == null && delta <= 1 && delta >=-1 );
    }

    //Vérifie si une attaque depuis la cellule x1, y1 vers la cellule x2, y2 est possible
    //Même chose que isMovePossible mais ne vérifie pas si il y a une entité à la cellule de destination
    private boolean isAttackPossible(int x1, int y1, int x2, int y2) {
        // Vérifie si les coordonnées sont correctes
        if(x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || x1 > height || y1 > width || x2 > height || y2 > width){
            return false;
        }

        // Vérifie que la différence de hauteur n'est pas trop grande
        int delta = cells[x1][y1].getHeight() - cells[x2][y2].getHeight();
        return (delta <= 1 && delta >=-1 );
    }

    // Calcule la distance estimée au point final
    double heuristicDistance(int x1, int y1, int x2, int y2){
        int distX = Math.abs(x1-x2);
        int distY = Math.abs(y1-y2);
        return Math.sqrt(distX*distX+distY*distY);
    }

    byte meilleureDirection(int x1, int y1, int x2, int y2){
        byte meilleureDirection;
        double distanceMinimale;

        meilleureDirection = -1;
        distanceMinimale = Double.MAX_VALUE;

        for(byte i = 0; i < 6; i++){
            int [] adjCellCoordinates = getAdjCellCoordinates(x1, x2, i);
            if(heuristicDistance(adjCellCoordinates[0], adjCellCoordinates[1], x2, y2) < distanceMinimale &&
                    isMovePossible(adjCellCoordinates[0], adjCellCoordinates[1], i)){
                meilleureDirection = i;
                distanceMinimale = heuristicDistance(adjCellCoordinates[0], adjCellCoordinates[1], x2, y2);
            }
        }
        return meilleureDirection;
    }

    // Directions "retournées"
    // 5   0       2   3
    //4  x  1 --> 1  x  4
    // 3   2       0   5
    byte retournerDirection(byte direction){
        int result = direction-3;
        if(result < 0){
            result = 6 + result;
        }
        return (byte)Math.abs(result);
    }

    // Parcourt le tableau "open" et renvoie "true" si elle trouve une valeur "true" à l'intèrieur
    boolean existeOuvert(boolean open[][]){
        for(int i = 0; i < open.length; i++){
            for(int j = 0; j < open[i].length; j++){
                if(open[i][j] == true)
                    return true;
            }
        }
        return false;
    }

    // Algorithme A*
    // renvoie une tableau d'entiers, chaque entier représente la cellule adjascente
    // qu'il faut prendre pour avancer dans le chemin
    // renvoie null si le chemin n'existe pas, ou s'il demande plus de maxlength mouvements
    public byte[] getPath(int x1, int y1, int x2, int y2, int maxLength) {
        if (x1<0 || x2<0) return null;
        if (maxLength<=0) return null;

        //distance estimée au point final
        double heuristic[][] = new double[this.height][this.width];
        //distance au point d'origine
        int distance[][] = new int[this.height][this.width];
        // direction qu'il faut prendre pour arriver à la cellule de laquelle on vient
        byte origin[][] = new byte[this.height][this.width];
        // cellules qu'on a pas encore empruntées mais pour lesquelles on a déjà colculé les valeurs
        boolean open[][] = new boolean [this.height][this.width];
        // cellules par lesquelles on est déjà passés
        boolean closed[][] = new boolean [this.height][this.width];

        // Initialisation du tableau indiquant la direction de laquelle on est venus pour arriver à chaque cellule
        for(int i = 0; i < distance.length; i++){
            for(int j = 0; j < distance[i].length; j++){
                origin[i][j] = -1;
            }
        }

        // Initialisation du tableau des distances au point de départ
        for(int i = 0; i < distance.length; i++){
            for(int j = 0; j < distance[i].length; j++){
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        distance[x1][y1] = 0;

        // Initialisation des valeurs estimées au point final
        for(int i = 0; i < heuristic.length; i++){
            for(int j = 0; j < heuristic[i].length; j++){
                heuristic[i][j] = heuristicDistance(i, j, x2, y2);
            }
        }

        open[x1][y1] = true;

        int xCourant = x1;
        int yCourant = y1;

        while(existeOuvert(open) && (xCourant !=x2 || yCourant != y2)){

            // On sélectionne la cellule qui a la plus petite valeur distance+heuristic
            // En cas d'égalité, on prend celle qui a la plus petite valeur heuristique
            double min = Double.MAX_VALUE;
            double minHeuristic = Double.MAX_VALUE;
            for(int i = 0; i < this.height; i++){
                for(int j = 0; j < this.width; j++){
                    if(open[i][j] && ( (distance[i][j] + heuristic[i][j] < min) || (distance[i][j] + heuristic[i][j] == min && minHeuristic < heuristic[i][j]) ) ){
                        xCourant = i;
                        yCourant = j;
                        min = distance[i][j] + heuristic[i][j];
                        minHeuristic = heuristic[i][j];
                    }
                }
            }

            open[xCourant][yCourant] = false;
            closed[xCourant][yCourant] = true;

            // On calcule les distances pour les cellules voisines à celle sélectionnée
            if(xCourant !=x2 || yCourant != y2){
                for(byte i = 0; i < 6; i++){

                    if(getAdjCellCoordinates(xCourant, yCourant, i) != null){
                        int xAdjCell = getAdjCellCoordinates(xCourant, yCourant, i)[0];
                        int yAdjCell = getAdjCellCoordinates(xCourant, yCourant, i)[1];

                        if(isMovePossible(xCourant, yCourant, i) && !closed[xAdjCell][yAdjCell]){

                            // On vérifie si il existe un raccourci à chaque cellule voisine en passant par la cellule sélectionnée
                            if((distance[xCourant][yCourant] + 1 < distance[xAdjCell][yAdjCell] ) || !open[xAdjCell][yAdjCell]){
                                distance[xAdjCell][yAdjCell] = distance[xCourant][yCourant] + 1;
                                origin[xAdjCell][yAdjCell] = retournerDirection(i);
                                if(!open[xAdjCell][yAdjCell]){
                                    open[xAdjCell][yAdjCell] = true;
                                }
                            }
                        }
                    }

                }
            }


        }

        // Le cas où on ne peut pas atteindre la cellule d'arrivée
        if(origin[x2][y2] == -1){
            return null;
        }

        // On crée une liste contenant toutes les directions menant du point d'arrivée au point de départ
        LinkedList<Byte> path = new LinkedList<Byte>();

        // On parcourt le chemin entre le point d'arrivée et le point de départ en ajoutant les directions dans une liste
        while(xCourant != x1 || yCourant != y1){
            path.add(origin[xCourant][yCourant]);
            int parentCoordinates[] = getAdjCellCoordinates(xCourant, yCourant, origin[xCourant][yCourant]);
            xCourant = parentCoordinates[0];
            yCourant = parentCoordinates[1];
        }

        // Nous avons les directions pour aller du point d'arrivée au point de départ mais on a besoin des directions dans l'autre sens
        // On met les éléments de la liste dans un tableau et on retourne chaque direction
        if(path.size() > maxLength){
            return null;
        }
        else{
            byte[] b = new byte[path.size()];
            // On ajoute les directions "retournées" et à l'envers dans un tableau de type byte qu'on renvoie
            // Directions "retournées"
            // 5   0       2   3
            //4  x  1 --> 1  x  4
            // 3   2       0   5
            for(int i = path.size() - 1; i >= 0; i--){
                b[path.size() - i - 1] = retournerDirection(path.get(i));
            }
            return b;
        }

    }

    boolean isFull( boolean processed[][]){
        //teste s'il reste encore des sommets à visiter
        for(int i = 0; i < processed.length; i++){
            for(int j= 0; j < processed[i].length; j++){
                if(processed[i][j] == false)
                    return false;
            }
        }

        return true;
    }

    int[] minDistance(int distance[][], boolean processed[][]){
        int min = Integer.MAX_VALUE;
        int[] result = new int[2];
        result[0] = -1;
        result[1] = -1;

        for(int i = 0; i < distance.length; i++){
            for(int j = 0; j < distance[i].length; j++){
                if(distance[i][j] < min && !processed[i][j]){
                    min = distance[i][j];
                    result[0] = i;
                    result[1] = j;
                }
            }
        }

        return result;
    }

    // Algorithme de Dijkstra
    /*public byte[] getPath(int x1, int y1, int x2, int y2, int maxLength) {
        if (x1<0 || x2<0) return null;
        if (maxLength<=0) return null;

        //distance au point d'origine
        int distance[][] = new int[this.height][this.width];
        // direction qu'il faut prendre pour arriver à la cellule de laquelle on vient
        byte origin[][] = new byte[this.height][this.width];
        // cellules pour lesquelles on a colculé les valeurs
        boolean processed[][] = new boolean[this.height][this.width];

        // Initialisation du tableau indiquant la direction de laquelle on est venus pour arriver à chaque cellule
        for(int i = 0; i < distance.length; i++){
            for(int j = 0; j < distance[i].length; j++){
                origin[i][j] = -1;
            }
        }

        // Initialisation du tableau des distances au point de départ
        for(int i = 0; i < distance.length; i++){
            for(int j = 0; j < distance[i].length; j++){
                distance[i][j] = Integer.MAX_VALUE;
            }
        }
        distance[x1][y1] = 0;

        // Initialisation du tableau des cellules pour lesquelles on a calculé les valeurs
        for(int i = 0; i < processed.length; i++){
            for(int j = 0; j < processed[i].length; j++){
                processed[i][j] = false;
            }
        }

        int[] celluleCourante = {x1, y1};

        while(!isFull(processed)){
            // On choisit la cellule qui a la plus petite distance du point de départ
            celluleCourante = minDistance(distance, processed);

            // On vérifie si on a fini de parcourir le tableau
            if(celluleCourante[0] == -1 || celluleCourante[1] == -1 || distance[celluleCourante[0]][celluleCourante[1]] == Integer.MAX_VALUE){
                break;
            }

            // On marque le cellule qu'on vient de choisir comme évaluée
            processed[celluleCourante[0]][celluleCourante[1]] = true;

            // On calcule les distances des cellules voisines à la cellule courante
            for(byte i = 0; i < 6; i++){
                if(getAdjCellCoordinates(celluleCourante[0], celluleCourante[1], i) != null && isMovePossible(celluleCourante[0], celluleCourante[1], i)){
                    int xAdjCell = getAdjCellCoordinates(celluleCourante[0], celluleCourante[1], i)[0];
                    int yAdjCell = getAdjCellCoordinates(celluleCourante[0], celluleCourante[1], i)[1];

                    if(isMovePossible(celluleCourante[0], celluleCourante[1], i) && !processed[xAdjCell][yAdjCell]){

                        // On vérifie si il existe un raccourci à chaque cellule voisine en passant par la cellule sélectionnée
                        if((distance[celluleCourante[0]][celluleCourante[1]] + 1 < distance[xAdjCell][yAdjCell] && isMovePossible(celluleCourante[0], celluleCourante[1], xAdjCell, yAdjCell ))){
                            distance[xAdjCell][yAdjCell] = distance[celluleCourante[0]][celluleCourante[1]] + 1;
                            origin[xAdjCell][yAdjCell] = retournerDirection(i);
                        }
                    }
                }

            }

        }

        // Le cas où on ne peut pas atteindre la cellule d'arrivée
        if(origin[x2][y2] == -1){
            return null;
        }

        celluleCourante[0] = x2;
        celluleCourante[1] = y2;

        // On crée une liste contenant toutes les directions menant du point d'arrivée au point de départ
        LinkedList<Byte> path = new LinkedList<Byte>();

        // On retourne à la cellule de départ depuis la cellule d'arrivée en ajoutant la direction qu'on prend à chaque fois
        while(celluleCourante[0] != x1 || celluleCourante[1] != y1){
            path.add(origin[celluleCourante[0]][celluleCourante[1]]);
            int parentCoordinates[] = getAdjCellCoordinates(celluleCourante[0], celluleCourante[1], origin[celluleCourante[0]][celluleCourante[1]]);
            celluleCourante[0] = parentCoordinates[0];
            celluleCourante[1] = parentCoordinates[1];
        }

        // Nous avons les directions pour aller du point d'arrivée au point de départ mais on a besoin des directions dans l'autre sens
        // On met les éléments de la liste dans un tableau et on retourne chaque direction
        if(path.size() > maxLength){
            return null;
        }
        else{
            byte[] b = new byte[path.size()];
            // On ajoute les directions "retournées" et à l'envers dans un tableau de type byte qu'on renvoie
            // Directions "retournées"
            // 5   0       2   3
            //4  x  1 --> 1  x  4
            // 3   2       0   5
            for(int i = path.size() - 1; i >= 0; i--){
                b[path.size() - i - 1] = retournerDirection(path.get(i));
            }
            return b;
        }

    }*/

    // Bouge l'entité d'une case, et update ses coordoonées internes
    public void move(Entity e, int direction) {
        int x=e.getX();
        int y=e.getY();
        if (!isMovePossible(x,y,direction) || e.getMp()<=0) return;
        getAdjCell(x,y,direction).setEntity(e);
        getCell(x,y).setEntity(null);
        e.updateCoords(direction);
        e.decreaseMp();

    }

    public void selectCellsWithinRange(int x, int y, int minRange, int maxRange) {
        clearCoordList();
        for (int i = minRange; i <= maxRange; i++) {
            addCellsAtDistance(x,y,i);
        }
    }

    // ajoute le cercle de distance dist à coordList
    private void addCellsAtDistance(int x, int y, int dist) {
        int[] coord={x,y+dist};
        if (isInBounds(coord) && isVisible(x, y, coord[0], coord[1])) coordList.add(coord);
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < dist; j++) {
                coord= getAdjCellCoordinates_2(coord[0],coord[1],(i+3)%6);
                // TODO : vérifier que la cible est directement visible (pas d'obstacle)
                if (isInBounds(coord) && isVisible(x, y, coord[0], coord[1])) {
                    coordList.add(coord);
                }
            }
        }
    }

    public LinkedList<int[]> getCoordList() {
        return coordList;
    }

    public void clearCoordList() {
        if (coordList==null) coordList = new LinkedList<>();
        else coordList.clear();
    }

    // renvoie les coordonnées cartésiennes de la position de cells[x][y];
    // utile pour calculer des distances
    private double[] getEffectiveXY(int x, int y) {
        double[] result = new double[2];
        result[0]=x*offset;
        result[1]=(x%2==0)? y:y+0.5;
        return result;
    }

    // calcule la distance entière entre deux cellules
    // je sais pas comment ça marche mais je prie petit jésus
    // allègrement copié depuis https://www.redblobgames.com/grids/hexagons/#distances
    private int distance(int x1, int y1, int x2, int y2) {
        int a1 = y1 - (x1 - ((x1%2==0)?0:1))/2;
        int c1 = x1;
        int b1 = -a1-c1;

        int a2 = y2 - (x2 - ((x2%2==0)?0:1))/2;;
        int c2 = x2;
        int b2 = -a2-c2;

        return (Math.abs(a1-a2)+Math.abs(b1-b2)+Math.abs(c1-c2)) / 2;
    }

    // distance réelle entre deux cellules
    // prend en argument des coordonnées matricielles
    private double distanceR(int x1, int y1, int x2, int y2) {
        double[] c1=getEffectiveXY(x1,y1);
        double[] c2=getEffectiveXY(x2,y2);
        return Math.sqrt((c1[0]-c2[0])*(c1[0]-c2[0])+(c1[1]-c2[1])*(c1[1]-c2[1]));
    }

    public boolean isInCoordList(int x, int y) {
        if (coordList==null) return false;
        for (int[] coord: coordList) {
            if (x==coord[0] && y==coord[1]) return true;
        }
        return false;
    }

    // retourne un tableau avec les cellules (en format matriciel) présentes sur la ligne entre a et b
    public static int[][] ligne_matrice(int x1, int y1, int x2, int y2){
        int[] _a = {x1, y1};
        Cube a = Cube.matrice_cube(_a);
        int[] _b = {x2, y2};
        Cube b = Cube.matrice_cube(_b);
        int N = Cube.distance(a, b);
        LinkedList<Cube> list = new LinkedList<Cube>();
        //System.out.println(list.size());
        for(int i = 0; i <= N; i++){
            list.add(Cube.interpolation(a, b, 1.0 / N * i).arrondir());
            // System.out.println(list.get(list.size() - 1));
        }
        int[][] result = new int [list.size()][2];
        for(int i = 0; i < list.size(); i++){
            result[i][0] = list.get(i).cube_matrice()[0];
            result[i][1] = list.get(i).cube_matrice()[1];
        }
        return result;
    }

    public boolean isVisible(int x1, int y1, int x2, int y2){
        int[][] ligne = ligne_matrice(x1, y1, x2, y2);
        /*for(int i = 0; i < ligne.length; i++){
            System.out.println(ligne[i][0] + " " + ligne[i][1]);
        }
        System.out.println();*/
        for(int i = 1; i < ligne.length-1; i++){
            if(!isDirectlyVisible(ligne[i-1][0], ligne[i-1][1], ligne[i][0], ligne[i][1]))
                return false;
            /*if(cells[ligne[i][0]][ligne[i][1]].entity != null)
                return false;*/
        }
        return true;
    }

    //Vérifie si la cellule x2 y2 adjacente à x1 y1 est directement visible
    private boolean isDirectlyVisible(int x1, int y1, int x2, int y2) {
        // Vérifie si les coordonnées sont correctes
        if(x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 || x1 > height || y1 > width || x2 > height || y2 > width){
            return false;
        }

        // Vérifie que la différence de hauteur n'est pas trop grande
        int delta = cells[x1][y1].getHeight() - cells[x2][y2].getHeight();

        //On vérifie si la cible n'est pas plus haute que le départ
        return (cells[x2][y2].getEntity() == null && delta >= -1);
    }

}