package dataForSimulation;

import java.awt.Image;
import java.awt.Point;
import java.util.List;

import observerPattern.IObservable;
import observerPattern.IObserver;

public class Entrepot extends Usine implements IObservable{
	
	private int currentAvionQTY;
	
	public Entrepot(int id,Point position , String type, List<Image> images, List<ProductionItem> entree)
	{
		
		this.id = id;
		this.position = position;
		this.type = type;
		this.images = images;
		this.currentAvionQTY = 0;
		this.currentImage = this.getImageByType("vide");
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

	

}
