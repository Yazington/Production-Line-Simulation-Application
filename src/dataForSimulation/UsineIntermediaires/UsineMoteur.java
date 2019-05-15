package dataForSimulation.UsineIntermediaires;

import java.util.List;
import dataForSimulation.*;

public class UsineMoteur extends UsineIntermediaire {

	private String type;
	private List<ProductionItem> entree;
	private String sortie;
	private int intervalProduction;
	
	public UsineMoteur(int id, int[] position, String type, List<Icone> icones, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		super(id, position, sortie, icones, sortie, entree, intervalProduction);
	}
	
	@Override
	public ProductionItem faitProduit() {
		return new ProductionItem(this.sortie);
	}
	
	
}
