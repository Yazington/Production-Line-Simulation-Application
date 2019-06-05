package dataForSimulation.UsineIntermediaires;

import java.awt.Image;
import java.awt.Point;
import java.util.List;
import dataForSimulation.*;
import dataForSimulation.ProductionItems.Aile;
import dataForSimulation.ProductionItems.Avion;
import dataForSimulation.ProductionItems.Moteur;
import observerPattern.IObserver;

public class UsineAssemblage extends UsineIntermediaire implements IObserver {
	
	private int currentAileQty;
	private int currentMoteurQty;
	private int neededAileQty;
	private int neededMoteurQty;
	private boolean entrepotIsFull;
	private Entrepot entrepot;
	
	public UsineAssemblage(int id, Point position, String type, List<Image> images, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		super(id, position, type, images, sortie, entree, intervalProduction);
		this.currentAileQty = 0;
		this.currentMoteurQty = 0;
		this.entrepotIsFull = false;
		for(int i = 0; i< entree.size(); i ++)
		{
			if(entree.get(i).getType().equals("aile"))
			{
				this.neededAileQty = entree.get(i).getNeededQuantity();
			}
			else if (entree.get(i).getType().equals("moteur")) 
			{
				this.neededMoteurQty =entree.get(i).getNeededQuantity();
			}
		}
	}
	
	@Override
	public ProductionItem faitProduit()
	{
		if(this.currentAileQty < this.neededAileQty && this.currentMoteurQty < this.neededMoteurQty || this.entrepotIsFull) return null;
		return new Avion(this.sortie);
	}

	public void addOneAile() {
		this.currentAileQty++;
	}
	
	public void addOneMoteur() {
		this.currentMoteurQty++;
	}

	public int getCurrentAileQty() {
		return currentAileQty;
	}

	public int getCurrentMoteurQty() {
		return currentMoteurQty;
	}

	public int getNeededAileQty() {
		return neededAileQty;
	}

	public int getNeededMoteurQty() {
		return neededMoteurQty;
	}
	
	public void setCurrentAileQty(int currentAileQty) {
		this.currentAileQty = currentAileQty;
	}

	public void setCurrentMoteurQty(int currentMoteurQty) {
		this.currentMoteurQty = currentMoteurQty;
	}

	@Override
	public void UpdateObserver() {
		if(this.entrepot.getCurrentAvionQTY() != this.entrepot.getMaxAvionQTY())
		{
			this.entrepotIsFull = false;
		}
		else
		{
			this.entrepotIsFull = true;
		}
	}

	public void setEntrepot(Entrepot entrepot) {
		this.entrepot = entrepot;
	}
	
	
}
