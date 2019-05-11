package dataForSimulation;

import java.util.List;

public class UsineIntermediaire extends Usine{
	
	private String type;
	private Entree entree;
	private String sortie;
	private int intervalProduction;
	
	public UsineIntermediaire(int id, int[] position, String type, List<Icone> icones, String sortie, Entree entree, int intervalProduction)
	{
		this.id = id;
		this.position = position;
		this.type = type;
		this.icones = icones;
		this.entree = entree;
		this.sortie = sortie;
		this.intervalProduction = intervalProduction;
	}

	@Override
	public ObjetProduit faitProduit() {
		return new ObjetProduit(this.sortie);
	}
}
