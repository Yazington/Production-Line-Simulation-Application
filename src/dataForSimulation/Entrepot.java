package dataForSimulation;

import java.awt.Image;
import java.awt.Point;
import java.util.List;

import observerPattern.IObservable;
import observerPattern.IObserver;

public class Entrepot extends Usine implements IObservable{
	
	private int currentAvionQTY;
	private int maxAvionQTY;
	private boolean isFull;
	
	public Entrepot(int id,Point position , String type, List<Image> images, List<ProductionItem> entree)
	{
		
		this.id = id;
		this.position = position;
		this.type = type;
		this.images = images;
		this.currentAvionQTY = 0;
		this.currentImage = this.getImageByType("vide");
		this.maxAvionQTY = entree.get(0).getNeededQuantity();
		this.intervalProduction = 0;
		this.isFull = false;
	}

	@Override
	public ProductionItem faitProduit() {
		return null;
	}

	@Override
	public void registerObserver(IObserver o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterObserver(IObserver o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObserver() {
		// TODO Auto-generated method stub
		
	}

	public void addOneEntree() {
		this.currentAvionQTY++;
		
	}

	public int getCurrentAvionQTY() {
		return currentAvionQTY;
	}

	public void setCurrentAvionQTY(int currentAvionQTY) {
		this.currentAvionQTY = currentAvionQTY;
	}

	public int getMaxAvionQTY() {
		return maxAvionQTY;
	}

	
	@Override
	public void updateCurrentImage(int currentTime) {
		
		if( this.currentAvionQTY  > 0 && this.currentAvionQTY  < this.maxAvionQTY/3)
		{
			this.setCurrentImage(this.getImageByType("un-tiers"));
		}
		else if (this.currentAvionQTY  >= this.maxAvionQTY/3 && this.currentAvionQTY  < this.maxAvionQTY*2/3)
		{
			this.setCurrentImage(this.getImageByType("deux-tiers"));
		}
		else if (this.currentAvionQTY  >= this.maxAvionQTY*2/3 && this.currentAvionQTY  < this.maxAvionQTY)
		{
			this.setCurrentImage(this.getImageByType("plein"));
			this.isFull = true;
			
		}
		else if ( this.currentAvionQTY == 0)
		{
			this.setCurrentImage(this.getImageByType("vide"));
		}
		
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}
	
	

}
