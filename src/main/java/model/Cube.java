package model;

import java.util.LinkedList;

// méthode pour stocker les cellules de la grille avec 3 coordonnées chacune
// facilite beaucoup la manipulation des coordonnées dans certains algorithmes (notamment celui de la ligne de mire)
// plus d'informations ici: https://www.redblobgames.com/grids/hexagons/#coordinates-cube

public class Cube {
    double x, y, z;

    public Cube (double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Cube (int x, int y, int z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString(){
        return "x: " + this.x + ", y: " + this.y + ", z: " + this.z;
    }

    // arrondit les coordonnées de a vers des entiers afin qu'on puisse décider quelle cellule inclure sur la ligne
    public Cube arrondir(){
        int rx = (int) this.x;
        int ry = (int) this.y;
        int rz = (int) this.z;

        double x_diff = Math.abs(rx - this.x);
        double y_diff = Math.abs(ry - this.y);
        double z_diff = Math.abs(rz - this.z);

        if(x_diff > y_diff && x_diff > z_diff){
            rx = -ry-rz;
        }
        else if(y_diff > z_diff){
            ry = -rx-rz;
        }
        else{
            rz = -rx-ry;
        }

        this.x = rx;
        this.y = ry;
        this.z = rz;

        return this;
    }

    // convertit des coordonnées matricielles vers un objet "Cube"
    public static Cube matrice_cube(int[] a){
        int x = a[1] - (a[0] - (a[0]&1)) / 2;
        int z = a[0];
        int y = -x-z;
        return new Cube(x, y, z);
    }

    // convertit des coordonnées d'un objet de type "Cube" vers des coordonnées matricielles
    public int[] cube_matrice(){
        int[] result = new int[2];
        result[1] = (int)this.x + ((int)this.z - ((int)this.z & 1))/2;
        result[0] = (int)this.z;
        return result;
    }

    // distance entière entre deux cellules données avec des coordonnées "Cube"
    public static int distance(Cube a, Cube b){
        return (int)(Math.abs(a.x - b.x) + Math.abs(a.y - b.y) + Math.abs(a.z - b.z)) / 2;
    }

    // renvoie un nombre à t pourcent du chemin entre a et b (t est un nombre entre 0 et 1)
    // par exemple, si t vaut 0,5 le nombre renvoyé sera au milieu entre a et b (à 50% du chemin)
    public static double interpolation_lineaire(double a, double b, double t){
        return a + (b - a) * t;
    }

    public static Cube interpolation(Cube a, Cube b, double t){
        return new Cube(interpolation_lineaire(a.x, b.x, t),
                        interpolation_lineaire(a.y, b.y, t),
                        interpolation_lineaire(a.z, b.z, t));
    }
    
    public static LinkedList<Cube> ligne_cube(Cube a, Cube b){
        int N = distance(a, b);
        LinkedList<Cube> list = new LinkedList<Cube>();
        //System.out.println(list.size());
        for(int i = 0; i <= N; i++){
            list.add(Cube.interpolation(a, b, 1.0 / N * i).arrondir());
        }
        return list;
    }

}