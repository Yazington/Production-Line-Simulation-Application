package dataForSimulation;

import java.awt.Point;


import javax.swing.JLabel;

public class ProductionItem{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String type;
	protected int quantite;
	protected Point vitesse;
	protected int[] position;
	protected String imagePath;
	
	public ProductionItem(String type)
	{
		this.type = type;
	}
	
	public ProductionItem(String type, int quantite)
	{
		this.type = type;
		this.quantite = quantite;
	}

	public void setVitesse(Point vitesse) {
		this.vitesse = vitesse;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public Point getVitesse() {
		return vitesse;
	}

	public int[] getPosition() {
		return position;
	}

	public String getImagePath() {
		return imagePath;
	}	
	
}
