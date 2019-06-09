package dataForSimulation.UsineIntermediaires;

import java.awt.Image;
import java.awt.Point;
import java.util.List;

import dataForSimulation.*;
import dataForSimulation.ProductionItems.Aile;
import observerPattern.IObserver;
public class UsineAile extends UsineIntermediaire implements IObserver{

	private int currentMetalQty;
	private int neededMetalQty;
	private boolean entrepotIsFull;
	private Entrepot entrepot;
	
	public UsineAile(int id, Point position, String type, List<Image> images, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		super(id, position, type, images, sortie, entree, intervalProduction);
		this.currentMetalQty = 0;
		this.neededMetalQty = entree.get(0).getNeededQuantity();
		this.entrepotIsFull = false;
	} 
	
	
	@Override
	public ProductionItem faitProduit()
	{
		if(this.currentMetalQty != this.neededMetalQty || this.entrepotIsFull) return null;
		return new Aile(this.sortie);
	}
	
	
	public void addOneEntree()
	{
		this.currentMetalQty++;
	}

	public int getCurrentMetalQty() 
	{
		return currentMetalQty;
	}

	public int getNeededMetalQty() {
		return neededMetalQty;
	}

	public void setCurrentMetalQty(int currentMetalQty) {
		this.currentMetalQty = currentMetalQty;
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
