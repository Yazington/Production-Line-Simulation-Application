package dataForSimulation.UsineIntermediaires;

import java.awt.Image;
import java.util.List;

import dataForSimulation.*;
public class UsineAile extends UsineIntermediaire {

	private int currentMetalQty;
	
	public UsineAile(int id, int[] position, String type, List<Image> images, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		super(id, position, type, images, sortie, entree, intervalProduction);
		this.currentMetalQty = 0;
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
				return new ProductionItem(this.sortie);
			}
		}
		return null;
	}
}
