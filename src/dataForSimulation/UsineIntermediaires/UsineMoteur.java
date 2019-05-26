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
	
	public UsineMoteur(int id, Point position, String type, List<Image> images, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		super(id, position, type, images, sortie, entree, intervalProduction);
		this.neededMetalQty = entree.get(0).getNeededQuantity();
		
	}
	
	@Override
	public ProductionItem faitProduit()
	{
		for(int i = 0; i< this.entree.size(); i++)
		{
			if(this.currentMetalQty == this.entree.get(i).getNeededQuantity())
			{
				this.setCurrentImage(this.getImageByType("vide"));
				return new Moteur(this.sortie);
			}
		}
		return null;
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
	
	
}
