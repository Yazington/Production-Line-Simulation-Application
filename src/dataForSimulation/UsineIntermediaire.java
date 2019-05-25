package dataForSimulation;

import java.awt.Image;
import java.util.List;

public class UsineIntermediaire extends Usine{
	
	protected List<ProductionItem> entree;
	protected String sortie;
	protected int intervalProduction;
	protected ProductionItem produitFinal;
	
	
	public UsineIntermediaire(int id, int[] position, String type, List<Image> images, String sortie, List<ProductionItem> entree, int intervalProduction)
	{
		this.id = id;
		this.position = position;
		this.type = type;
		this.images = images;
		this.entree = entree;
		this.sortie = sortie;
		this.intervalProduction = intervalProduction;
		this.currentImage = this.getImageByType("vide");
	}

	@Override
	public ProductionItem faitProduit() {
		return new ProductionItem(this.sortie);
	}

	public ProductionItem getProduitFinal() {
		return produitFinal;
	}

	
	
}
