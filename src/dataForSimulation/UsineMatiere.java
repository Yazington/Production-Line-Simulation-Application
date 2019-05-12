package dataForSimulation;

import java.util.List;

public class UsineMatiere extends Usine{

	private String type;
	private String sortie;
	private int intervalProduction;
	
	public UsineMatiere(int id, int[] position, String type, List<Icone> icones, String sortie, int intervalProduction)
	{
		this.id = id;
		this.position = position;
		this.type = type;
		this.icones = icones;
		this.sortie = sortie;
		this.intervalProduction = intervalProduction;
		
	}
	
	@Override
	public ProductionItem faitProduit() {
		return new ProductionItem(this.sortie);
	}

}
