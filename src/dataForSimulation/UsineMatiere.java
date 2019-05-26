package dataForSimulation;

import java.awt.Image;
import java.awt.Point;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dataForSimulation.ProductionItems.Metal;
import observerPattern.IObserver;

public class UsineMatiere extends Usine implements IObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortie;
	private int intervalProduction;
	private boolean entrepotIsFull;
	private ProductionItem produitFinal;
	
	public UsineMatiere(int id, Point position, String type, List<Image> images, String sortie, int intervalProduction)
	{
		this.id = id;
		this.position = position;
		this.type = type;
		this.images = images;
		this.sortie = sortie;
		this.intervalProduction = intervalProduction;
		this.currentImage = this.getImageByType("vide");
		
	}
	
	@Override
	public ProductionItem faitProduit() {
		this.setCurrentImage(this.getImageByType("vide"));
		return new Metal(this.sortie);
	}

	@Override
	public void UpdateObserver() {
		// TODO Auto-generated method stub
		
	}

	public int getIntervalProduction() {
		return intervalProduction;
	}

	
	
}
