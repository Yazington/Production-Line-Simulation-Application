package dataForSimulation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import observerPattern.IObserver;

public abstract class Usine extends JLabel{

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

	public Icone getCurrentIcone() {
		return currentIcone;
	}

	public void setCurrentIcone(Icone currentIcone) {
		this.currentIcone = currentIcone;
	}
	
	public Icone getIconeByType(String type)
	{
		if(type.equals("vide"))
		{
			return this.icones.get(0);
		}
		else if(type.equals("un-tiers"))
		{
			return this.icones.get(1);
		}
		else if(type.equals("deux-tiers"))
		{
			return this.icones.get(2);
		}
		else if(type.equals("plein"))
		{
			return this.icones.get(3);
		}
		return null;
	}

	
}
