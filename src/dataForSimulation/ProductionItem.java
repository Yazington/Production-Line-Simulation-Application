package dataForSimulation;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ProductionItem{

	protected String type;
	protected int neededQuantity;
	protected Point vitesse;
	protected Point position;
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

	public void setPosition(Point position) {
		this.position = position;
	}

	public Point getVitesse() {
		return vitesse;
	}

	public Point getPosition() {
		return position;
	}

	public String getImagePath() {
		return imagePath;
	}

	public Image getImage() {
		try {
			return ImageIO.read(new File(this.imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
