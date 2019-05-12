package dataForSimulation.UsineIntermediaires;

import java.util.List;

import dataForSimulation.*;

public class UsineAile extends UsineIntermediaire {

	private String type;
	private List<Entree> entree;
	private String sortie;
	private int intervalProduction;
	
	public UsineAile(int id, int[] position, String type, List<Icone> icones, String sortie, List<Entree> entree, int intervalProduction)
	{
		super(id, position, sortie, icones, sortie, entree, intervalProduction);
	}
	
	@Override
	public ProductionItem faitProduit() {
		return new ProductionItem(this.sortie);
	}
	
}
