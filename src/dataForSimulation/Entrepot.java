package dataForSimulation;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import observerPattern.IObservable;
import observerPattern.IObserver;

public class Entrepot extends Usine implements IObservable{
	
	private int currentAvionQTY;
	private int maxAvionQTY;
	private boolean isFull;
	private List<Usine> usinesObservers;
	
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
		this.usinesObservers = new ArrayList<Usine>();
	}

	@Override
	public ProductionItem faitProduit() {
		return null;
	}
	
	public void stopIfFull()
	{
		notifyObserver();
	}

	@Override
	public void registerObserver(IObserver o) {
		
	}
	
	public void registerUsine(Usine usine) {
		this.usinesObservers.add(usine);
	}

	@Override
	public void unregisterObserver(IObserver o) {
		for(int i = 0; i< this.usinesObservers.size(); i++)
		{
			if(this.usinesObservers.get(i) == o)
			{
				this.usinesObservers.remove(i);
			}
		}
	}

	/**
	 * should be called notifyObservers but because many classes use it, its stays Observer
	 */
	@Override
	public void notifyObserver() {
		if(this.usinesObservers==null) return;
		for(int i = 0; i<this.usinesObservers.size(); i++)
		{
			this.usinesObservers.get(i).UpdateObserver();
		}
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

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	@Override
	protected void UpdateObserver() {
		// TODO Auto-generated method stub
		
	}
}
