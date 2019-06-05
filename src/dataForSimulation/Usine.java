package dataForSimulation;

import java.awt.Image;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import observerPattern.IObserver;
import simulation.Environnement;

public abstract class Usine{

	protected int id;
	protected String type;
	protected Point position;
	protected List<Image> images;
	protected Image currentImage;
	protected int intervalProduction;
	
	
	public abstract ProductionItem faitProduit();

	public Point getPosition() {
		return position;
	}

	public int getId() {
		return id;
	}

	public List<Image> getIcones()
	{
		return images;
	}

	public String getType() {
		return this.type;
	}

	public Image getCurrentImage() {
		return currentImage;
	}

	public void setCurrentImage(Image currentImage) {
		this.currentImage = currentImage;
	}
	// TODO: change icone type to image type
	public Image getImageByType(String type)
	{
		if(type.equals("vide"))
		{
			return this.images.get(0);
		}
		else if(type.equals("un-tiers"))
		{
			return this.images.get(1);
		}
		else if(type.equals("deux-tiers"))
		{
			return this.images.get(2);
		}
		else if(type.equals("plein"))
		{
			return this.images.get(3);
		}
		return null;
	}


	public void updateCurrentImage(int currentTime) {
		if(this.intervalProduction != 0)
		{
			if( currentTime  >=0 && currentTime  < this.intervalProduction/3)
			{
				this.setCurrentImage(this.getImageByType("un-tiers"));
			}
			else if (currentTime  >=this.intervalProduction/3 && currentTime  < this.intervalProduction *2/3)
			{
				this.setCurrentImage(this.getImageByType("deux-tiers"));
			}
			else if (currentTime  >= this.intervalProduction *2/3 && currentTime  < this.intervalProduction)
			{
				this.setCurrentImage(this.getImageByType("plein"));
			}
			else if ( currentTime  == this.intervalProduction)
			{
				this.setCurrentImage(this.getImageByType("vide"));
			}
		}
		else
		{
			
		}
	}

	public Point getPositionPoint()
	{
		return this.position;
	}

	protected abstract void UpdateObserver();
	
}
