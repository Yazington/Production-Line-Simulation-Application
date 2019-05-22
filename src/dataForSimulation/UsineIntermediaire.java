package dataForSimulation;

import java.util.List;

public class UsineIntermediaire extends Usine{
	
	protected List<ProductionItem> entree;
	protected String sortie;
	protected int intervalProduction;
	protected ProductionItem produitFinal;
	
	
	public UsineIntermediaire(int id, int[] position, String type, List<Icone> icones, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		this.id = id;
		this.position = position;
		this.type = type;
		this.icones = icones;
		this.entree = entree;
		this.sortie = sortie;
		this.intervalProduction = intervalProduction;
		this.currentIcone = this.getIconeByType("vide");
	}

	@Override
	public ProductionItem faitProduit() {
		return new ProductionItem(this.sortie);
	}

	public ProductionItem getProduitFinal() {
		return produitFinal;
	}
	
	
}
