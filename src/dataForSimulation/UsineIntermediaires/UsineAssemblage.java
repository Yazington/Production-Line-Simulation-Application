package dataForSimulation.UsineIntermediaires;

import java.awt.Image;
import java.awt.Point;
import java.util.List;
import dataForSimulation.*;
import dataForSimulation.ProductionItems.Aile;
import dataForSimulation.ProductionItems.Avion;

public class UsineAssemblage extends UsineIntermediaire {
	
	private int currentAileQty;
	private int currentMoteurQty;
	private int neededAileQty;
	private int neededMoteurQty;
	
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
	
//	@Override
//	public ProductionItem faitProduit() {
//		return new ProductionItem(this.sortie);
//	}
	
	@Override
	public ProductionItem faitProduit()
	{
		boolean aileQTYIsMet = false;
		boolean moteurQTYIsMet = false;
		for(int i = 0; i< this.entree.size(); i++)
		{
			if(this.entree.get(i).getType() == "aile")
			{
				if(this.currentAileQty == this.entree.get(i).getNeededQuantity())
				{
					aileQTYIsMet = true;
				}
			}
			else if(this.entree.get(i).getType() == "moteur")
			{
				if(this.currentMoteurQty == this.entree.get(i).getNeededQuantity())
				{
					moteurQTYIsMet = true;
				}
			}
			
		}
		if(aileQTYIsMet && moteurQTYIsMet) 
		{
			this.setCurrentImage(this.getImageByType("vide"));
			return new Avion(this.sortie);
		}
		return null;
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
	
	
}
