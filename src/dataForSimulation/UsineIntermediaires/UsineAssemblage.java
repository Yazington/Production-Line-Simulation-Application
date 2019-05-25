package dataForSimulation.UsineIntermediaires;

import java.awt.Image;
import java.util.List;
import dataForSimulation.*;

public class UsineAssemblage extends UsineIntermediaire {
	
	private int currentAileQty;
	private int currentMoteurQty;
	
	public UsineAssemblage(int id, int[] position, String type, List<Image> images, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		super(id, position, type, images, sortie, entree, intervalProduction);
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
		if(aileQTYIsMet && moteurQTYIsMet) return new ProductionItem(this.sortie);
		return null;
	}
}
