package dataForSimulation;

import java.awt.Image;
import java.util.List;

import observerPattern.IObservable;
import observerPattern.IObserver;

public class Entrepot extends Usine implements IObservable{
	
	private List<ProductionItem> entree;
	
	public Entrepot(int id,int[] position , String type, List<Image> images, List<ProductionItem> entree)
	{

		this.id = id;
		this.position = position;
		this.type = type;
		this.images = images;
		this.entree = entree;
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

	

}
