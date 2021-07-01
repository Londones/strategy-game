
package model;

import java.io.*;

public class Level  implements Serializable {

	private static final long serialVersionUID = -1092342944633849329L;
	private Grid grid;

	Level() {
	  // uwu
	}
	
	
	public Grid GetGrid() {
		return this.grid;
	}
	
	
	public void SetGrid(Grid grid) {
		this.grid=grid;
	}
	
	
	public void createLevel(String filename) {
		//pour sérialiser la grille de départ dans un fichier "filename" dans le dossier Levels
        try {
			FileOutputStream fos = new FileOutputStream(filename);
		    ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(this.GetGrid());
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * @return la grille (la tableau de cellules qu'on a sérialisé
	 */

	//desérialise le niveau
	public Grid loadLevel(String level_filename) {

	    this.SetGrid(null);
		try {
			InputStream fis = getClass().getResourceAsStream("/Levels/" + level_filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			this.SetGrid((Grid)ois.readObject());
			ois.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return this.GetGrid();
	}

}

// code pour construire les niveaux :

/*niveau 1*/
        /*Grid grid = new Grid(10, 15);
        grid.getCell(3,4).setHeight((byte)1);
        grid.getCell(5,5).setHeight((byte)1);
        grid.getCell(5,6).setHeight((byte)2);

        //sérialisation de la grille du niveau
        level.SetGrid(grid);
        level.createLevel("src/main/Levels/level1");*/

/*niveau 2*/
        /*Grid grid = new Grid(11, 15);
        grid.getCell(1,3).setHeight((byte)3);
        grid.getCell(2,3).setHeight((byte)3);
        grid.getCell(3,2).setHeight((byte)3);
        grid.getCell(4,2).setHeight((byte)3);

        grid.getCell(6,2).setHeight((byte)3);
        grid.getCell(7,2).setHeight((byte)3);
        grid.getCell(8,3).setHeight((byte)3);
        grid.getCell(9,3).setHeight((byte)3);

        grid.getCell(1,10).setHeight((byte)3);
        grid.getCell(2,11).setHeight((byte)3);
        grid.getCell(3,11).setHeight((byte)3);
        grid.getCell(4,12).setHeight((byte)3);

        grid.getCell(9,10).setHeight((byte)3);
        grid.getCell(7,11).setHeight((byte)3);
        grid.getCell(8,11).setHeight((byte)3);
        grid.getCell(6,12).setHeight((byte)3);

        //sérialisation de la grille du niveau
        level.SetGrid(grid);
        level.createLevel("src/main/Levels/level2");*/

/*niveau 3*/
        /*Grid grid = new Grid(7, 16);

        grid.getCell(3,2).setHeight((byte)1);
        grid.getCell(3,3).setHeight((byte)2);
        grid.getCell(3,4).setHeight((byte)3);
        grid.getCell(3,5).setHeight((byte)3);
        grid.getCell(3,6).setHeight((byte)3);
        grid.getCell(3,7).setHeight((byte)3);
        grid.getCell(3,8).setHeight((byte)3);
        grid.getCell(3,9).setHeight((byte)3);
        grid.getCell(3,10).setHeight((byte)3);
        grid.getCell(3,11).setHeight((byte)3);
        grid.getCell(3,12).setHeight((byte)2);
        grid.getCell(3,13).setHeight((byte)1);

        //sérialisation de la grille du niveau
        level.SetGrid(grid);
        level.createLevel("src/main/Levels/level3");*/

/*niveau 4*/
        /*Grid grid = new Grid(9, 15);

        grid.getCell(1,3).setHeight((byte)1);
        grid.getCell(2,4).setHeight((byte)2);
        grid.getCell(3,4).setHeight((byte)2);
        grid.getCell(4,5).setHeight((byte)2);
        grid.getCell(5,5).setHeight((byte)1);

        grid.getCell(1,5).setHeight((byte)1);
        grid.getCell(2,5).setHeight((byte)2);
        grid.getCell(4,4).setHeight((byte)2);
        grid.getCell(5,3).setHeight((byte)1);

        grid.getCell(1,8).setHeight((byte)1);
        grid.getCell(2,9).setHeight((byte)1);
        grid.getCell(3,8).setHeight((byte)2);
        grid.getCell(4,9).setHeight((byte)1);
        grid.getCell(5,8).setHeight((byte)1);

        grid.getCell(1,9).setHeight((byte)1);
        grid.getCell(1,10).setHeight((byte)1);
        grid.getCell(2,11).setHeight((byte)1);
        grid.getCell(3,11).setHeight((byte)2);
        grid.getCell(4,11).setHeight((byte)1);
        grid.getCell(5,10).setHeight((byte)1);
        grid.getCell(5,9).setHeight((byte)1);


        //sérialisation de la grille du niveau
        level.SetGrid(grid);
        level.createLevel("src/main/Levels/level4");*/

