package dataForSimulation;

import javax.swing.JLabel;

public class ProductionItem extends JLabel{

	private String type;
	private int quantite;
	
	public ProductionItem(String type)
	{
		this.type = type;
	}
	
	public ProductionItem(String type, int quantite)
	{
		this.type = type;
		this.quantite = quantite;
	}
}
