package dataForSimulation;

import java.util.List;

public abstract class Usine{

	protected int id;
	protected int[] position;
	protected List<Icone> icones;
	
	public abstract ObjetProduit faitProduit();

}
