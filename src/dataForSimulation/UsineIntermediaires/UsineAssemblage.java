package dataForSimulation.UsineIntermediaires;

import java.util.List;
import dataForSimulation.*;

public class UsineAssemblage extends UsineIntermediaire {
	
	
	public UsineAssemblage(int id, int[] position, String type, List<Icone> icones, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		super(id, position, type, icones, sortie, entree, intervalProduction);
	}
	
	@Override
	public ProductionItem faitProduit() {
		return new ProductionItem(this.sortie);
	}
	
}
