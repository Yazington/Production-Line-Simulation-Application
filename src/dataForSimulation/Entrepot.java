package dataForSimulation;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import observerPattern.IObservable;
import observerPattern.IObserver;
import strategyPattern.IVenteStrategy;
import strategyPattern.IntervalSell;
import strategyPattern.RandomProbabilitySell;

public class Entrepot extends Usine implements IObservable{
	
	private int currentAvionQTY;
	private int maxAvionQTY;
	private boolean isFull;
	private List<Usine> usinesObservers;
	private IVenteStrategy intervalSell;
	private IVenteStrategy randomSell;
	private int strategie;
	
	public Entrepot(int id,Point position , String type, List<Image> images, List<ProductionItem> entree, 
					IntervalSell intervalSell, RandomProbabilitySell randomSell)
	{
		
		this.id = id;
		this.position = position;
		this.type = type;
		this.images = images;
		this.currentAvionQTY = 0;
		this.currentImage = this.getImageByType("vide");
		this.maxAvionQTY = entree.get(0).getNeededQuantity();
		this.intervalProduction = 0;
		this.isFull = false;
		this.usinesObservers = new ArrayList<Usine>();
		this.intervalSell = intervalSell;
		this.randomSell = randomSell;
	}

	@Override
	public ProductionItem faitProduit() {
		return null;
	}
	
	public void stopIfFull()
	{
		notifyObserver();
	}

	@Override
	public void registerObserver(IObserver o) {
		
	}
	
	public void registerUsine(Usine usine) {
		this.usinesObservers.add(usine);
	}

	@Override
	public void unregisterObserver(IObserver o) {
		for(int i = 0; i< this.usinesObservers.size(); i++)
		{
			if(this.usinesObservers.get(i) == o)
			{
				this.usinesObservers.remove(i);
			}
		}
	}

	/**
	 * should be called notifyObservers but because many classes use it, its stays Observer
	 */
	@Override
	public void notifyObserver() {
		if(this.usinesObservers==null) return;
		for(int i = 0; i<this.usinesObservers.size(); i++)
		{
			this.usinesObservers.get(i).UpdateObserver();
		}
	}

	public void addOneEntree() {
		this.currentAvionQTY++;
		
	}

	public int getCurrentAvionQTY() {
		return currentAvionQTY;
	}

	public void setCurrentAvionQTY(int currentAvionQTY) {
		this.currentAvionQTY = currentAvionQTY;
	}

	public int getMaxAvionQTY() {
		return maxAvionQTY;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	@Override
	protected void UpdateObserver() {
		// TODO Auto-generated method stub
		
	}

	public void continueIfNotFull() {
		// TODO Auto-generated method stub
		
	}


	public boolean sellProduct()
	{
		boolean isSelling = false;
		
		if(this.strategie == 2)
		{
			isSelling = this.randomSell.sell();
		}
		else if (this.strategie == 1)
		{
			this.intervalSell.setInterval(this.currentAvionQTY);
			isSelling = this.intervalSell.sell();
		}
		return isSelling;
	}

	public void setStrategy(int strategie) {
		this.strategie = strategie;
		
		
	}

	public void removeOneEntree() {
		this.currentAvionQTY = this.currentAvionQTY - 4 ;
	}
}
