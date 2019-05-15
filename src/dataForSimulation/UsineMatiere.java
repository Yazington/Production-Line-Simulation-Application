package dataForSimulation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import dataForSimulation.ProductionItems.Metal;
import observerPattern.IObserver;

public class UsineMatiere extends Usine implements PropertyChangeListener, IObserver{

	private String sortie;
	private int intervalProduction;
	private boolean entrepotIsFull;
	
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
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("Create products") && !this.entrepotIsFull) {
			try {
				evt.wait(100 - this.intervalProduction);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			var produit = faitProduit();
		}
	}

	@Override
	public void UpdateObserver() {
		// TODO Auto-generated method stub
		
	}
}
