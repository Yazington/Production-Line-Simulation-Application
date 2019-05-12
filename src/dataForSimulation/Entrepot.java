package dataForSimulation;

import java.util.List;

public class Entrepot extends Usine{

	private String type;
	private List<Entree> entree;
	
	public Entrepot(int id,int[] position , String type, List<Icone> icones, List<Entree> entree)
	{

		this.id = id;
		this.position = position;
		this.type = type;
		this.icones = icones;
		this.entree = entree;
	}

	@Override
	public ProductionItem faitProduit() {
		return null;

	}

}
