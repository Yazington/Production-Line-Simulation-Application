package dataForSimulation.UsineIntermediaires;

import java.awt.Image;
import java.util.List;

import dataForSimulation.*;

public class UsineAile extends UsineIntermediaire {

	
	public UsineAile(int id, int[] position, String type, List<Image> images, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		super(id, position, type, images, sortie, entree, intervalProduction);
	}
	
	@Override
	public ProductionItem faitProduit() {
		return new ProductionItem(this.sortie);
	}

}
