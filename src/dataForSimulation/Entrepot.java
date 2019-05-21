package dataForSimulation;

import java.util.List;

import observerPattern.IObservable;
import observerPattern.IObserver;

public class Entrepot extends Usine implements IObservable{
	
	private List<ProductionItem> entree;
	
	public Entrepot(int id,int[] position , String type, List<Icone> icones, List<ProductionItem> entree)
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

	@Override
	public void registerObserver(IObserver o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregisterObserver(IObserver o) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObserver() {
		// TODO Auto-generated method stub
		
	}

	

}
