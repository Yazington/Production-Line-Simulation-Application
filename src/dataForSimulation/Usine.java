package dataForSimulation;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import observerPattern.IObserver;

public abstract class Usine{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected int id;
	protected String type;
	protected int[] position;
	protected List<Icone> icones;
	protected Icone currentIcone;
	
	
	public abstract ProductionItem faitProduit();

	public int[] getPosition() {
		return position;
	}

	public int getId() {
		return id;
	}

	public List<Icone> getIcones()
	{
		return icones;
	}

	public String getType() {
		return this.type;
	}
	
}
