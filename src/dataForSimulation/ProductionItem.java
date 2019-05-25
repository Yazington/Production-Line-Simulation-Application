package dataForSimulation;

import java.awt.Point;


import javax.swing.JLabel;

public class ProductionItem{

	protected String type;
	protected int neededQuantity;
	protected Point vitesse;
	protected int[] position;
	protected String imagePath;
	
	public ProductionItem(String type)
	{
		this.type = type;
	}
	
	public ProductionItem(String type, int neededQuantity)
	{
		this.type = type;
		this.neededQuantity = neededQuantity;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNeededQuantity() {
		return neededQuantity;
	}

	public void setNeededQuantity(int neededQuantity) {
		this.neededQuantity = neededQuantity;
	}	
	
	
}
