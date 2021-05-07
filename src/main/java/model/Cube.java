package model;

// méthode pour stocker les cellules de la grille avec 3 coordonnées chacune
// facilite beaucoup la manipulation des coordonnées dans certains algorithmes (notamment celui de la ligne de mire)
// plus d'informations ici: https://www.redblobgames.com/grids/hexagons/#coordinates-cube

public class Cube {
    double x, y, z;

    Cube (double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    Cube (int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
