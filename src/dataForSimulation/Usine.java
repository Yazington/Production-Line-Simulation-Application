package dataForSimulation;

import java.util.List;

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
	
	/**
	 * Protected: set its own Icone using an Observable 
	 * class that will notify it
	 * @param currentIcone
	 */
	public void setIcone(Icone currentIcone)
	{
		this.setIcon(new ImageIcon(icones.get(0).getPath()));
	}
	
	public List<Icone> getIcones()
	{
		return icones;
	}
}
