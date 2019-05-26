package dataForSimulation.UsineIntermediaires;

import java.awt.Image;
import java.awt.Point;
import java.util.List;

import dataForSimulation.*;
import dataForSimulation.ProductionItems.Aile;
import dataForSimulation.ProductionItems.Metal;
public class UsineAile extends UsineIntermediaire {

	private int currentMetalQty;
	private int neededMetalQty;
	
	public UsineAile(int id, Point position, String type, List<Image> images, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		super(id, position, type, images, sortie, entree, intervalProduction);
		this.currentMetalQty = 0;
		this.neededMetalQty = entree.get(0).getNeededQuantity();
	} 
	
//	@Override
//	public ProductionItem faitProduit() {
//		return new ProductionItem(this.sortie);
//	}

	public ProductionItem faitProduit()
	{
		for(int i = 0; i< this.entree.size(); i++)
		{
			if(this.currentMetalQty == this.entree.get(i).getNeededQuantity())
			{
				this.setCurrentImage(this.getImageByType("vide"));
				return new Aile(this.sortie);
			}
		}
		this.currentMetalQty = 0;
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
