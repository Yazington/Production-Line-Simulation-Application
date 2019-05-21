package dataForSimulation;

import java.util.List;

import dataForSimulation.ProductionItems.Metal;
import observerPattern.IObserver;

public class UsineMatiere extends Usine implements IObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sortie;
	private int intervalProduction;
	private boolean entrepotIsFull;
	private ProductionItem produitFinal;
	
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
		return new Metal(this.sortie);
	}

	@Override
	public void UpdateObserver() {
		// TODO Auto-generated method stub
		
	}

}
