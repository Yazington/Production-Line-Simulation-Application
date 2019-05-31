package dataForSimulation.UsineIntermediaires;

import java.awt.Image;
import java.awt.Point;
import java.util.List;
import dataForSimulation.*;
import dataForSimulation.ProductionItems.Aile;
import dataForSimulation.ProductionItems.Avion;
import dataForSimulation.ProductionItems.Moteur;

public class UsineAssemblage extends UsineIntermediaire {
	
	private int currentAileQty;
	private int currentMoteurQty;
	private int neededAileQty;
	private int neededMoteurQty;
	private boolean isFull;
	
	public UsineAssemblage(int id, Point position, String type, List<Image> images, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		super(id, position, type, images, sortie, entree, intervalProduction);
		this.currentAileQty = 0;
		this.currentMoteurQty = 0;
		for(int i = 0; i< entree.size(); i ++)
		{
			if(entree.get(i).getType().equals("aile"))
			{
				this.neededAileQty = entree.get(i).getNeededQuantity();
			}
			else if (entree.get(i).getType().equals("moteur")) 
			{
				this.neededMoteurQty =entree.get(i).getNeededQuantity();
			}
		}
	}

	@Override
	public void updateCurrentImage(int currentTime) {
		if(this.intervalProduction != 0)
		{
			if( currentTime  > 0 && currentTime  < this.intervalProduction/3)
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
				if(Math.abs(this.intervalProduction - currentTime) <= 25)
				{
					this.isFull = true;
				}
				
			}
			else if ( currentTime == 0 || currentTime >= this.intervalProduction)
			{
				this.setCurrentImage(this.getImageByType("vide"));
				
			}
		}
	}
	
	@Override
	public ProductionItem faitProduit()
	{
		if(!this.isFull) return null;	
				return new Avion(this.sortie);
	}

	public void addOneAile() {
		this.currentAileQty++;
		
	}
	
	public void addOneMoteur() {
		this.currentMoteurQty++;
	}

	public int getCurrentAileQty() {
		return currentAileQty;
	}

	public int getCurrentMoteurQty() {
		return currentMoteurQty;
	}

	public int getNeededAileQty() {
		return neededAileQty;
	}

	public int getNeededMoteurQty() {
		return neededMoteurQty;
	}
	
	public void setCurrentAileQty(int currentAileQty) {
		this.currentAileQty = currentAileQty;
	}

	public void setCurrentMoteurQty(int currentMoteurQty) {
		this.currentMoteurQty = currentMoteurQty;
	}

	public void setIsFull(boolean b) {
		this.isFull = b;
		
	}
	
}
