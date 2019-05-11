package dataForSimulation;

import java.util.List;

public abstract class Entrepot extends Usine{

	private String type;
	private Entree entree;
	
	public Entrepot(int id,int[] position , String type, List<Icone> icones, Entree entree)
	{
		this.id = id;
		this.position = position;
		this.type = type;
		this.icones = icones;
		this.entree = entree;
	}

}
