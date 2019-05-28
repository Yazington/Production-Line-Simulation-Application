package dataForSimulation.UsineIntermediaires;

import java.awt.Image;
import java.awt.Point;
import java.util.List;
import dataForSimulation.*;
import dataForSimulation.ProductionItems.Aile;
import dataForSimulation.ProductionItems.Moteur;

public class UsineMoteur extends UsineIntermediaire {

	private int currentMetalQty;
	private int neededMetalQty;
	private boolean isFull;
	
	public UsineMoteur(int id, Point position, String type, List<Image> images, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		super(id, position, type, images, sortie, entree, intervalProduction);
		this.neededMetalQty = entree.get(0).getNeededQuantity();
		this.isFull = false;
		
	}
	
	@Override
	public ProductionItem faitProduit()
	{
		if(!this.isFull) return null;	
				return new Moteur(this.sortie);
	}
	
	@Override
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
				if(Math.abs(this.intervalProduction - currentTime) == 1)
				{
					this.isFull = true;
				}
				
			}
			else if ( currentTime == this.intervalProduction)
			{
				this.setCurrentImage(this.getImageByType("vide"));
				
			}
		}
		else
		{
			
		}
	}
	
	
	public void addOneEntree()
	{
		this.currentMetalQty++;
	}

	public int getCurrentMetalQty() {
		return currentMetalQty;
	}

	public int getNeededMetalQty() {
		return neededMetalQty;
	}

	public void setCurrentMetalQty(int currentMetalQty) {
		this.currentMetalQty = currentMetalQty;
	}

	public void setIsFull(boolean b) {
		this.isFull = b;
		
	}
	
	
	
}
