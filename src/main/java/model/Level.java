
package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Level  implements Serializable {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = -1092342944633849329L;
	private Grid grid;

	/*
	 * Le Constructeur
	 */
	Level() {
	  
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
	
	public Grid showLevel(String str) {
		//desérialiser le niveau 
	    this.SetGrid(null);
		try {
			// String str ="src/main/Levels/level1";
			FileInputStream fis = new FileInputStream(str);
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

