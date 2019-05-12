package dataForSimulation;

import java.util.List;

import javax.swing.JLabel;

public abstract class Usine extends JLabel{

	protected int id;
	protected int[] position;
	protected List<Icone> icones;
	
	public abstract ProductionItem faitProduit();

	public int[] getPosition() {
		return position;
	}

	public int getId() {
		return id;
	}
	
	
}
