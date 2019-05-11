package dataForSimulation.UsineIntermediaires;

import java.util.List;
import dataForSimulation.*;

public class UsineMoteur extends UsineIntermediaire {

	private String type;
	private Entree entree;
	private String sortie;
	private int intervalProduction;
	
	public UsineMoteur(int id, int[] position, String type, List<Icone> icones, String sortie, Entree entree, int intervalProduction)
	{
		super(id, position, sortie, icones, sortie, entree, intervalProduction);
	}
	
	@Override
	public ObjetProduit faitProduit() {
		return new ObjetProduit(this.sortie);
	}
	
}
