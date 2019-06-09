package simulation;

import java.awt.Image;
import java.awt.Point;
import java.util.List;
import java.util.stream.Collectors;

import dataForSimulation.Chemin;
import dataForSimulation.Entrepot;
import dataForSimulation.ProductionItem;
import dataForSimulation.Usine;
import dataForSimulation.UsineMatiere;
import dataForSimulation.ProductionItems.Aile;
import dataForSimulation.ProductionItems.Avion;
import dataForSimulation.ProductionItems.Metal;
import dataForSimulation.ProductionItems.Moteur;
import dataForSimulation.UsineIntermediaires.UsineAile;
import dataForSimulation.UsineIntermediaires.UsineAssemblage;
import dataForSimulation.UsineIntermediaires.UsineMoteur;

public class RefreshManager {

	private static final int CURRENT_COMPONENTS_SPEED = 7;
	private List<Usine> usines;
	private List<ProductionItem> productionItems;
	private List<Point> produitsPositions;
	private List<Image> produitsImages;
	private List<Point> produitsVitesses;
	private int usineAileTimer;
	private int usineMoteurTimer;
	private int usineAssemblageTimer;
	
	public RefreshManager(List<Usine> usines, List<ProductionItem> productionItems, List<Image> produitsImages, 
						  List<Point> produitsPositions, List<Point> produitsVitesses) 
	{
		this.usines = usines;
		this.productionItems = productionItems;
		this.produitsPositions = produitsPositions;
		this.produitsImages = produitsImages;
		this.produitsVitesses = produitsVitesses;
		this.usineAileTimer = 0;
		this.usineMoteurTimer = 0;
		this.usineAssemblageTimer = 0;
	}

	/**
	 * change images depending on quantity and time 
	 * @param currentTime
	 * @param usine
	 */
	public void changeImages(int currentTime, Usine usine) {
		if(usine.getType().equals("usine-matiere"))
		{
			UsineMatiere usineMatiere = (UsineMatiere) usine;
			if(currentTime == 0 || currentTime > usineMatiere.getIntervalProduction() || usineMatiere.entrepotIsFull())
			{
				usine.setCurrentImage(usine.getImageByType("vide"));
			}
			else if(currentTime > 0 && currentTime <= usineMatiere.getIntervalProduction()/3)
			{
				usine.setCurrentImage(usine.getImageByType("un-tiers"));
			}
			else if(currentTime > usineMatiere.getIntervalProduction()/3 && currentTime <= usineMatiere.getIntervalProduction()*2/3)
			{
				usine.setCurrentImage(usine.getImageByType("deux-tiers"));
			}
			else if(currentTime > usineMatiere.getIntervalProduction()*2/3 && currentTime <= usineMatiere.getIntervalProduction())
			{
				usine.setCurrentImage(usine.getImageByType("plein"));
			}
		}
		else if (usine.getType().equals("usine-aile"))
		{
			UsineAile usineAile = (UsineAile) usine;
			
			// Check if usine has the need quantity for production
			if(usineAile.getCurrentMetalQty() != usineAile.getNeededMetalQty()) return;
			this.usineAileTimer ++;
			
			if(this.usineAileTimer == 0 && this.usineAileTimer > usineAile.getIntervalProduction())
			{
				usine.setCurrentImage(usine.getImageByType("vide"));
			}
			else if(this.usineAileTimer > 0 && this.usineAileTimer <= usineAile.getIntervalProduction()/3)
			{
				usine.setCurrentImage(usine.getImageByType("un-tiers"));
			}
			else if(this.usineAileTimer > usineAile.getIntervalProduction()/3 && this.usineAileTimer <= usineAile.getIntervalProduction()*2/3)
			{
				usine.setCurrentImage(usine.getImageByType("deux-tiers"));
			}
			else if(this.usineAileTimer > usineAile.getIntervalProduction()*2/3 && this.usineAileTimer <= usineAile.getIntervalProduction())
			{
				usine.setCurrentImage(usine.getImageByType("plein"));
			}
		}
		else if (usine.getType().equals("usine-moteur"))
		{
			UsineMoteur usineMoteur = (UsineMoteur) usine;
			
			// Check if usine has the need quantity for production
			if(usineMoteur.getCurrentMetalQty() != usineMoteur.getNeededMetalQty()) return;
			this.usineMoteurTimer ++;
			
			if(this.usineMoteurTimer == 0 && this.usineMoteurTimer > usineMoteur.getIntervalProduction())
			{
				usine.setCurrentImage(usine.getImageByType("vide"));
			}
			else if(this.usineMoteurTimer > 0 && this.usineMoteurTimer <= usineMoteur.getIntervalProduction()/3)
			{
				usine.setCurrentImage(usine.getImageByType("un-tiers"));
			}
			else if(this.usineMoteurTimer > usineMoteur.getIntervalProduction()/3 && this.usineMoteurTimer <= usineMoteur.getIntervalProduction()*2/3)
			{
				usine.setCurrentImage(usine.getImageByType("deux-tiers"));
			}
			else if(this.usineMoteurTimer > usineMoteur.getIntervalProduction()*2/3 && this.usineMoteurTimer <= usineMoteur.getIntervalProduction())
			{
				usine.setCurrentImage(usine.getImageByType("plein"));
			}
		}
		else if (usine.getType().equals("usine-assemblage"))
		{
			UsineAssemblage usineAssemblage = (UsineAssemblage) usine;
			
			// Check if usine has the need quantity for production
			if(usineAssemblage.getCurrentAileQty() < usineAssemblage.getNeededAileQty() || 
			   usineAssemblage.getCurrentMoteurQty() < usineAssemblage.getNeededMoteurQty()) return;
			this.usineAssemblageTimer++;
			
			if(this.usineAssemblageTimer == 0 && this.usineAssemblageTimer > usineAssemblage.getIntervalProduction())
			{
				usine.setCurrentImage(usine.getImageByType("vide"));
			}
			else if(this.usineAssemblageTimer > 0 && this.usineAssemblageTimer <= usineAssemblage.getIntervalProduction()/3)
			{
				usine.setCurrentImage(usine.getImageByType("un-tiers"));
			}
			else if(this.usineAssemblageTimer > usineAssemblage.getIntervalProduction()/3 && 
					this.usineAssemblageTimer <= usineAssemblage.getIntervalProduction()*2/3)
			{
				usine.setCurrentImage(usine.getImageByType("deux-tiers"));
			}
			else if(this.usineAssemblageTimer > usineAssemblage.getIntervalProduction()*2/3 && this.usineAssemblageTimer <= usineAssemblage.getIntervalProduction())
			{
				usine.setCurrentImage(usine.getImageByType("plein"));
			}
		}
		else if (usine.getType().equals("entrepot"))
		{
			Entrepot entrepot = (Entrepot) usine;
			if(entrepot.getCurrentAvionQTY() == 0)
			{
				usine.setCurrentImage(usine.getImageByType("vide"));
			}
			else if(entrepot.getCurrentAvionQTY() > 0 && entrepot.getCurrentAvionQTY() <= entrepot.getMaxAvionQTY()/3)
			{
				usine.setCurrentImage(usine.getImageByType("un-tiers"));
			}
			else if(entrepot.getCurrentAvionQTY() > entrepot.getMaxAvionQTY()/3 && entrepot.getCurrentAvionQTY() <= entrepot.getMaxAvionQTY()*2/3)
			{
				usine.setCurrentImage(usine.getImageByType("deux-tiers"));
			}
			else if(entrepot.getCurrentAvionQTY() > entrepot.getMaxAvionQTY()*2/3 && entrepot.getCurrentAvionQTY() == entrepot.getMaxAvionQTY())
			{
				usine.setCurrentImage(usine.getImageByType("plein"));
			}
		}
	}

	public ProductionItem createProduct(int currentTime, Usine usine, List<Chemin> chemins) {
		
		
		if(usine.getType().equals("usine-matiere"))
		{
			if(currentTime == ((UsineMatiere) usine).getIntervalProduction())
			{
				ProductionItem produit = usine.faitProduit();
				Point position2 = null;
				for(int j = 0; j < chemins.size(); j++)
				{
					int currentIndex = j;
					if(chemins.get(j).getDe() == usine.getId())
					{
						
						Usine usine2 = this.usines.stream()
												.filter(u -> u.getId() == chemins.get(currentIndex).getVers())
												.findFirst().get();

						position2 = usine2.getPosition();
					}
				}
				
				int xTranslate;
				if(usine.getPosition().x < position2.x) 
				{
					xTranslate = CURRENT_COMPONENTS_SPEED;
				}
				else if (usine.getPosition().x>position2.x)
				{
					xTranslate = -CURRENT_COMPONENTS_SPEED;
				}
				else
				{
					System.err.println("-no speed1-");
					xTranslate = 0;
				}
				
				int yTranslate;
				if(usine.getPosition().y < position2.y) 
				{
					yTranslate = CURRENT_COMPONENTS_SPEED;
				}
				else if (usine.getPosition().y>position2.y)
				{
					yTranslate = -CURRENT_COMPONENTS_SPEED;
				}
				else
				{
					System.err.println("-no speed1-");
					yTranslate = 0;
				}
				try {
					produit.setPosition(new Point(usine.getPosition().x, usine.getPosition().y));
					produit.setVitesse(new Point(xTranslate, yTranslate));
					return (Metal)produit;
				}
				catch (Exception e)
				{
					System.err.println("Error setting composant speed");
				}
			}
			
		}
		else if (usine.getType().equals("usine-aile"))
		{
			
			if(usine.getCurrentImage().equals(usine.getImageByType("plein")) && 
			   this.usineAileTimer == ((UsineAile)usine).getIntervalProduction())
			{
				ProductionItem produit = usine.faitProduit();
				if(produit == null) return null;
				System.err.println("new aile");
				Point position2 = null;
				for(int j = 0; j < chemins.size(); j++)
				{
					int currentIndex = j;
					if(chemins.get(j).getDe() == usine.getId())
					{
						
						Usine usine2 = this.usines.stream()
												.filter(u -> u.getId() == chemins.get(currentIndex).getVers())
												.findFirst().get();

						position2 = usine2.getPosition();
					}
				}
				
				int xTranslate;
				if(usine.getPosition().x < position2.x) 
				{
					xTranslate = CURRENT_COMPONENTS_SPEED;
				}
				else if (usine.getPosition().x>position2.x)
				{
					xTranslate = -CURRENT_COMPONENTS_SPEED;
				}
				else
				{
					xTranslate = 0;
					System.err.println("-no speed2-");
				}
				
				int yTranslate;
				if(usine.getPosition().y < position2.y) 
				{
					yTranslate = CURRENT_COMPONENTS_SPEED;
				}
				else if (usine.getPosition().y>position2.y)
				{
					yTranslate = -CURRENT_COMPONENTS_SPEED;
				}
				else
				{
					yTranslate = 0;
					System.err.println("-no speed2-");
				}
				
				try 
				{
					produit.setPosition(new Point(usine.getPosition().x, usine.getPosition().y));
					produit.setVitesse(new Point(xTranslate, yTranslate));
					
				}
				catch (Exception e)
				{
					System.err.println("error setting componsant infos");
				}

				usine.setCurrentImage(usine.getImageByType("vide"));
				((UsineAile) usine).setCurrentMetalQty(0);
				this.usineAileTimer = 0;
				return (Aile)produit;
			}
		}
		else if (usine.getType().equals("usine-moteur"))
		{
			
			if(usine.getCurrentImage().equals(usine.getImageByType("plein")) && 
			   this.usineMoteurTimer == ((UsineMoteur)usine).getIntervalProduction())
			{
				ProductionItem produit = usine.faitProduit();
				System.err.println("new moteur");
				Point position2 = null;
				for(int j = 0; j < chemins.size(); j++)
				{
					int currentIndex = j;
					if(chemins.get(j).getDe() == usine.getId())
					{
						
						Usine usine2 = this.usines.stream()
												.filter(u -> u.getId() == chemins.get(currentIndex).getVers())
												.findFirst().get();

						position2 = usine2.getPosition();
					}
				}
				
				int xTranslate;
				if(usine.getPosition().x < position2.x) 
				{
					xTranslate = CURRENT_COMPONENTS_SPEED;
				}
				else if (usine.getPosition().x>position2.x)
				{
					xTranslate = -CURRENT_COMPONENTS_SPEED;
				}
				else
				{
					xTranslate = 0;
					System.err.println("-no speed2-");
				}
				
				int yTranslate;
				if(usine.getPosition().y < position2.y) 
				{
					yTranslate = CURRENT_COMPONENTS_SPEED;
				}
				else if (usine.getPosition().y>position2.y)
				{
					yTranslate = -CURRENT_COMPONENTS_SPEED;
				}
				else
				{
					yTranslate = 0;
					System.err.println("-no speed2-");
				}
				
				try 
				{
					produit.setPosition(new Point(usine.getPosition().x, usine.getPosition().y));
					produit.setVitesse(new Point(xTranslate, yTranslate));
					
				}
				catch (Exception e)
				{
					System.err.println("error setting componsant infos");
				}
				usine.setCurrentImage(usine.getImageByType("vide"));
				((UsineMoteur)usine).setCurrentMetalQty(0);
				this.usineMoteurTimer = 0;
				return (Moteur)produit;
			}
		}
		else if (usine.getType().equals("usine-assemblage"))
		{
			
			if(usine.getCurrentImage().equals(usine.getImageByType("plein")) && 
			   this.usineAssemblageTimer == ((UsineAssemblage)usine).getIntervalProduction())
			{
				ProductionItem produit = usine.faitProduit();
				System.err.println("new avion");
				Point position2 = null;
				for(int j = 0; j < chemins.size(); j++)
				{
					int currentIndex = j;
					if(chemins.get(j).getDe() == usine.getId())
					{
						
						Usine usine2 = this.usines.stream()
												.filter(u -> u.getId() == chemins.get(currentIndex).getVers())
												.findFirst().get();

						position2 = usine2.getPosition();
					}
				}
				
				int xTranslate;
				if(usine.getPosition().x < position2.x) 
				{
					xTranslate = CURRENT_COMPONENTS_SPEED;
				}
				else if (usine.getPosition().x>position2.x)
				{
					xTranslate = -CURRENT_COMPONENTS_SPEED;
				}
				else
				{
					xTranslate = 0;
					System.err.println("-no speed2-");
				}
				
				int yTranslate;
				if(usine.getPosition().y < position2.y) 
				{
					yTranslate = CURRENT_COMPONENTS_SPEED;
				}
				else if (usine.getPosition().y>position2.y)
				{
					yTranslate = -CURRENT_COMPONENTS_SPEED;
				}
				else
				{
					yTranslate = 0;
					System.err.println("-no speed2-");
				}
				
				try 
				{
					produit.setPosition(new Point(usine.getPosition().x, usine.getPosition().y));
					produit.setVitesse(new Point(xTranslate, yTranslate));
					
				}
				catch (Exception e)
				{
					System.err.println("error setting componsant infos");
				}
				usine.setCurrentImage(usine.getImageByType("vide"));
				this.usineAssemblageTimer = 0;
				((UsineAssemblage)usine).setCurrentAileQty(0);
				((UsineAssemblage)usine).setCurrentMoteurQty(0);
				return (Avion)produit;
			}
		}
		else if (usine.getType().equals("entrepot"))
		{
			Entrepot entrepot = (Entrepot) usine;
			stopIfFull(entrepot);
			continueIfNotFull(entrepot);
			if(entrepot.getCurrentAvionQTY() == entrepot.getMaxAvionQTY() - 1)
			{
				boolean sellOne = entrepot.sellProduct();
				if(sellOne) entrepot.removeOneEntree();
			}
		}
		return null;
		
	}
	
	private void continueIfNotFull(Entrepot entrepot) {
		entrepot.continueIfNotFull();
		
	}

	private void stopIfFull(Entrepot entrepot) {
		entrepot.stopIfFull();
		
	}

	public void handleCollisions() {
		updateUsinesAM();
		updateUsinesAssemblage();
		updateEntrepots();
		
	}

	private void updateEntrepots() {
		List<Usine> entrepots = this.usines
				.stream()
				.filter(u -> u.getType().equals("entrepot"))
				.collect(Collectors.toList());
		
		for(int i = 0; i< this.productionItems.size(); i++)
		{
			Point movingPoint = this.productionItems.get(i).getPosition();
			if(movingPoint == null) continue;
			
			for(int j = 0 ; j< entrepots.size();j++)
			{
				Usine entrepot = entrepots.get(j);
				// if metals collide with usines other than usines matieres
				if(((Entrepot)entrepot).getPosition().distanceSq(movingPoint) <= 100 )
				{
					try {
						this.produitsImages.remove(i);
						this.produitsVitesses.remove(i);
						this.productionItems.remove(i);
						this.produitsPositions.remove(i);
						((Entrepot) entrepot).addOneEntree();
					}
					catch (Exception e)
					{
						System.err.println("Error with entrepot components");
					}
					
				}
			}
		}
	}

	private void updateUsinesAssemblage() {
		List<Usine> usinesAssemblage = this.usines
				.stream()
				.filter(u -> u.getType().equals("usine-assemblage"))
				.collect(Collectors.toList());
		
		for(int i = 0; i< this.productionItems.size(); i++)
		{
			ProductionItem productionItem = this.productionItems.get(i);
			if(productionItem == null) continue;
			Point movingPoint = this.productionItems.get(i).getPosition();
			if(movingPoint == null) continue;
			if(productionItem.getType().equals("aile"))
			{
				for(int j = 0 ; j< usinesAssemblage.size();j++)
				{

						UsineAssemblage usineAssemblage = (UsineAssemblage) usinesAssemblage.get(j);
						// if aile collides with usines other than usines usinesMatiere, usines Aile. usines moteur and entrepots
						if(usineAssemblage.getPosition().distanceSq(movingPoint)<=100 && 
						  usineAssemblage!= null)
						{
							try {
								this.produitsImages.remove(i);
								this.produitsVitesses.remove(i);
								this.productionItems.remove(i);
								this.produitsPositions.remove(i);
//								if(usineAssemblage.getCurrentMoteurQty()> usineAssemblage.getNeededMoteurQty())
//								{
//									usineAssemblage.setCurrentMoteurQty(0);
//								}
								((UsineAssemblage) usineAssemblage).addOneAile();
							}
							catch (Exception e)
							{
								System.err.println("error with usine assemblage");
							}
						}
				}
			}
			else if(productionItem.getType().equals("moteur"))
			{
				for(int j = 0 ; j< usinesAssemblage.size();j++)
				{
					UsineAssemblage usineAssemblage = (UsineAssemblage)usinesAssemblage.get(j);
					// if moteur collides with usines other than usines usinesMatiere, usines Aile, usines moteur and entrepots
					if(usineAssemblage.getPosition().distanceSq(movingPoint)<=100 && 
					  usineAssemblage!= null)
					{
						try {
							this.produitsImages.remove(i);
							this.produitsVitesses.remove(i);
							this.productionItems.remove(i);
							this.produitsPositions.remove(i);
//							if(usineAssemblage.getCurrentMoteurQty()> usineAssemblage.getNeededMoteurQty())
//							{
//								usineAssemblage.setCurrentMoteurQty(0);
//							}
							usineAssemblage.addOneMoteur();
						}
						catch (Exception e)
						{
							System.err.println("error with usine assemblage");
						}
					}
				}
			}
		}
		
	}

	private void updateUsinesAM() {
		List<Usine> usinesAM = this.usines
				.stream()
				.filter(u -> u.getType().equals("usine-aile")|| u.getType().equals("usine-moteur"))
				.collect(Collectors.toList());
		
		for(int i = 0; i< productionItems.size(); i++)
		{
			Point movingPoint = productionItems.get(i).getPosition();
			if(movingPoint == null) continue;
			String movingItemType = productionItems.get(i).getType();
			
			for(int j = 0 ; j< usinesAM.size();j++)
			{
				Usine usineAM = usinesAM.get(j);
				// if metals collide with usines other than usines matieres
				if(usineAM.getPosition().distanceSq(movingPoint)<=100 && 
				   !movingItemType.equals("aile") && 
				   !movingItemType.equals("moteur") && 
				   movingPoint!= null &&
				   this.produitsPositions!= null &&
				   this.produitsImages!= null &&
				   this.produitsVitesses!= null &&
				   productionItems!= null)
				{
					try {
						this.produitsImages.remove(i);
						this.produitsVitesses.remove(i);
						this.productionItems.remove(i);
						this.produitsPositions.remove(i);
					}
					catch(Exception e)
					{
						System.err.println("------------------------COLLISION ERROR AMMM--------------------------");
					}
					
					if(usineAM.getType().equals("usine-aile"))
					{
						((UsineAile) usineAM).addOneEntree();
					}
					else if (usineAM.getType().equals("usine-moteur"))
					{
							((UsineMoteur) usineAM).addOneEntree();
					}
				}
			}
		}
		
	}
}
