package dataForSimulation;

import java.awt.Image;
import java.awt.Point;
import java.util.List;

import dataForSimulation.ProductionItems.Metal;
import observerPattern.IObserver;

public class UsineMatiere extends Usine implements IObserver{

	private String sortie;
	private Entrepot entrepot;
	private boolean entrepotIsFull;
	
	public UsineMatiere(int id, Point position, String type, List<Image> images, String sortie, int intervalProduction)
	{
		this.id = id;
		this.position = position;
		this.type = type;
		this.images = images;
		this.sortie = sortie;
		this.intervalProduction = intervalProduction;
		this.currentImage = this.getImageByType("vide");
		this.entrepotIsFull = false;
	}
	
	public boolean entrepotIsFull() {
		return entrepotIsFull;
	}

	@Override
	public ProductionItem faitProduit() {
		if(this.entrepotIsFull) 
		{
			return null;
		}
//		this.setCurrentImage(this.getImageByType("vide"));
		return new Metal(this.sortie);
	}

	@Override
	public void UpdateObserver() {
		if(this.entrepot.getCurrentAvionQTY() != this.entrepot.getMaxAvionQTY())
		{
			this.entrepotIsFull = false;
		}
		else
		{
			this.entrepotIsFull = true;
			
		}
	}

	public int getIntervalProduction() {
		return intervalProduction;
	}

	public void setEntrepot(Entrepot entrepot) {
		this.entrepot = entrepot;
	}	
	
	
}
