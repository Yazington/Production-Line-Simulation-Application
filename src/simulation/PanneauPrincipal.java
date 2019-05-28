package simulation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import dataForSimulation.*;
import dataForSimulation.ProductionItems.Aile;
import dataForSimulation.ProductionItems.Avion;
import dataForSimulation.ProductionItems.Metal;
import dataForSimulation.ProductionItems.Moteur;
import dataForSimulation.UsineIntermediaires.UsineAile;
import dataForSimulation.UsineIntermediaires.UsineAssemblage;
import dataForSimulation.UsineIntermediaires.UsineMoteur;
import xmlUtility.*;
import observerPattern.IObserver;

public class PanneauPrincipal extends JPanel implements IObserver {

	private static final long serialVersionUID = 1L;

	// Variables temporaires de la demonstration:
//	private Point position = new Point(0,0);
//	private Point vitesse = new Point(1,1);
//	private int taille = 32;
	private MenuFenetre menuFenetre;
	private Network reseau; 
	List<Point> cheminsPoints1;
	List<Point> cheminsPoints2;

	private List<Point> usinesPositions;
	private List<Image> usinesImages;
	private List<Image> produitsImages;
	private List<Point> produitsVitesses;
	private List<Point> movingPoints;
	private int currentTime;

//	private boolean usinesAreFull;
	


	public PanneauPrincipal(MenuFenetre menuFenetre) {
		super();
		this.menuFenetre = menuFenetre;
		this.produitsImages = new ArrayList<Image>();
		this.produitsVitesses = new ArrayList<Point>();
		this.movingPoints = new ArrayList<Point>();
//		this.usinesAreFull = false;
		
	}

//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		// On ajoute a la position le delta x et y de la vitesse
//		position.translate(vitesse.x, vitesse.y);
//		g.fillRect(position.x, position.y, taille, taille);
//		
//	}
	
	@Override
	public void paintComponent(Graphics g)
	{
//		this.getGraphics().dispose();
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		if(this.cheminsPoints1 != null)
		{
			for(int i = 0; i< this.cheminsPoints1.size();i++)
			{
				try {
				Point point1 = this.cheminsPoints1.get(i);
				Point point2 = this.cheminsPoints2.get(i);
				g.drawLine(point1.x + 16, point1.y + 16, point2.x + 16 , point2.y + 16);
				this.reseau.setUsinesAreLoaded(true);
				}
				catch(Exception e)
				{
					System.err.println("---------------------------CHEMINS ERROR---------------------------");
				}
			}
		}

		
		// dessine les usines
		if(this.usinesPositions != null && this.usinesImages != null)
		{
			for(int i = 0; i< this.usinesPositions.size();i++)
			{
				try {
				g.drawImage(this.usinesImages.get(i), this.usinesPositions.get(i).x, this.usinesPositions.get(i).y, null);
				}
				catch(Exception e)
				{
					System.err.println("-----------------usinesPositions and usines images-------------------------------");
				}
			}
			
		}
		
		// dessine les produits
		
		if(this.movingPoints!=null && this.produitsImages != null )
		{
			for(int i = 0; i<this.movingPoints.size();i++)
			{
				try {
					g.drawImage(this.produitsImages.get(i), this.movingPoints.get(i).x, this.movingPoints.get(i).y, null);
				}
				catch(Exception e)
				{
					System.err.println("----------------- MOVING POINTS ERROR-------------------");
				}
				
				
			}
//			this.usinesAreFull = false;
		}
		
		if(this.movingPoints!=null)
		{
			for(int i = 0; i < this.movingPoints.size();i++)
			{
				try {
					this.movingPoints.get(i).translate(this.produitsVitesses.get(i).x, this.produitsVitesses.get(i).y);
				}
				catch(Exception e) {
					System.err.println("--moving items--");
				}
				
			}
		}
	}
	

	private void paintUsines(List<Usine> usines) throws IOException
	{
		//Dessiner usines si elles existent
		if(this.reseau == null) throw new NullPointerException("pas d usine");
		List<Point> usinesPositions = this.reseau.getCurrentPositions();
		List<Image> usinesImages = this.reseau.getCurrentImages();

		this.usinesPositions = usinesPositions;
		this.usinesImages = usinesImages;
	}
	
	
	private void paintChemins(Graphics g)
	{
		List<Chemin> chemins = this.reseau.getChemins();
		// Dessiner chemins siles existent
		this.cheminsPoints1 = new ArrayList<Point>();
		this.cheminsPoints2 = new ArrayList<Point>();
		if(chemins!= null)
		{
			for(Chemin cheminID: chemins)
			{
				Usine usine1 = 
						this.reseau.getUsines().stream()
								   .filter(u-> u.getId() == cheminID.getDe())
								   .findFirst()
								   .get();
				Usine usine2 = 
						this.reseau.getUsines().stream()
								   .filter(u-> u.getId() == cheminID.getVers())
								   .findFirst()
								   .get();
				
				Point point = usine1.getPosition();
				Point point2 = usine2.getPosition();

				this.cheminsPoints1.add(point);
				this.cheminsPoints2.add(point2);
			}
		}
	}
	
	
	public List<Usine> createNetwork(XMLSourcer xmlInfo)
	{
		if(this.menuFenetre == null) throw new NullPointerException("pas de source xml");
		
		Network reseau = new Network(xmlInfo.getMetaList(), xmlInfo.getSimList());
		this.reseau = reseau;
		List<Usine> usines = this.reseau.createInstances(this.reseau.getMetadonneesD(), this.reseau.getSimulationD());
		return usines;
	}

	
	public Network getNetwork() {
		return reseau;
	}

	
	@Override
	public void UpdateObserver() {
		XMLSourcer xmlSourcer = this.menuFenetre.getXmlSourcer();
		List<Usine> usines = createNetwork(xmlSourcer);
		paintChemins(this.getGraphics());
		try 
		{
			paintUsines(usines);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}

	
	public void changeImages(int current) {
		this.currentTime = current;
		try {
			updateUsinesMatiereImages();
			updateOtherUsinesImages();
		}
		catch (Exception e)
		{
			System.err.println("-------CHANGING USINES IMAGES ERROR--------");
		}
		
	}
	
	/**
	 * Change les images des usines matieres
	 * @param startTime
	 */
	private void updateUsinesMatiereImages() 
	{
		for (int i = 0; i < this.reseau.getUsines().size(); i++)
		{
			Usine usine = this.reseau.getUsines().get(i);
			if(usine.getType().equals("usine-matiere"))
			{
				try {
					usine.updateCurrentImage(this.currentTime);
					createProducts();
				}
				catch(Exception e)
				{
					System.err.println("oooooooooooooooo UPDATING CURRENT IMAGE ERROR ooooooooooooooo");
				}
			}
		}
		this.usinesImages = this.reseau.getCurrentImages();
	}

	/**
	 * Changes les images des autres usines que les usines matieres
	 */
	private void updateOtherUsinesImages() {
		
		for(int i = 0; i< this.reseau.getUsines().size(); i++)
		{
			
			Usine usine = this.reseau.getUsines().get(i);
			if(usine.getType().equals("usine-aile"))
			{
				UsineAile usineAile = (UsineAile) usine;
				if(usineAile.getCurrentMetalQty() == usineAile.getNeededMetalQty())
				{
					try 
					{
						usineAile.updateCurrentImage(this.currentTime);
						createProducts();
					}
					catch(Exception e)
					{
						System.err.println("oooooooo  UPDATE other usines images  ooooooo");
					}
				}

			}
			else if (usine.getType().equals("usine-assemblage"))
			{
				UsineAssemblage usineAssemblage = (UsineAssemblage) usine;
				if(usineAssemblage.getCurrentAileQty() >= usineAssemblage.getNeededAileQty() && 
				   usineAssemblage.getCurrentMoteurQty() >= usineAssemblage.getNeededMoteurQty())
				{
					try 
					{
						usineAssemblage.updateCurrentImage(this.currentTime);
						createProducts();
						
					}
					catch(Exception e)
					{
						System.err.println("oooooooo  UPDATE other usines images  ooooooo");
					}
				}
			}
			else if (usine.getType().equals("usine-moteur"))
			{
				UsineMoteur usineMoteur = (UsineMoteur) usine;
				if(usineMoteur.getCurrentMetalQty() >= usineMoteur.getNeededMetalQty())
				{
					try 
					{
						usineMoteur.updateCurrentImage(this.currentTime);
						createProducts();
					}
					catch(Exception e)
					{
						System.err.println("oooooooo  UPDATE other usines images  ooooooo");
					}
				}
			}
			else if (usine.getType().equals("entrepot"))
			{
				Entrepot entrepot = (Entrepot) usine;
				if(entrepot.getCurrentAvionQTY() != entrepot.getMaxAvionQTY())
				{
					try 
					{
						entrepot.updateCurrentImage(this.currentTime);
						stopProduction();
						
					}
					catch(Exception e)
					{
						System.err.println("oooooooo  UPDATE other usines images  ooooooo");
					}
				}				
			}
		}
		this.usinesImages = this.reseau.getCurrentImages();
		
	}
	
	private void stopProduction() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * creer les produits dependamment des entrees si on en a besoin
	 */
	public void createProducts()
	{	
		for(int i = 0; i < this.reseau.getUsines().size();i++)
		{
			Usine usine = this.reseau.getUsines().get(i);
			
			if(usine.getType().equals("usine-matiere"))
			{
				if(this.currentTime == ((UsineMatiere) usine).getIntervalProduction())
				{
					ProductionItem produit = usine.faitProduit();
					Point position2 = null;
					for(int j = 0; j < this.reseau.getChemins().size(); j++)
					{
						int currentIndex = j;
						if(this.reseau.getChemins().get(j).getDe() == usine.getId())
						{
							
							Usine usine2 = this.reseau.getUsines().stream()
													.filter(u -> u.getId() == this.reseau.getChemins().get(currentIndex).getVers())
													.findFirst().get();

							position2 = usine2.getPosition();
						}
					}
					
					int xTranslate;
					if(usine.getPosition().x < position2.x) 
					{
						xTranslate = 10;
					}
					else if (usine.getPosition().x>position2.x)
					{
						xTranslate = -10;
					}
					else
					{
						System.err.println("-no speed1-");
						xTranslate = 0;
					}
					
					int yTranslate;
					if(usine.getPosition().y < position2.y) 
					{
						yTranslate = 10;
					}
					else if (usine.getPosition().y>position2.y)
					{
						yTranslate = -10;
					}
					else
					{
						System.err.println("-no speed1-");
						yTranslate = 0;
					}
					try {
						produit.setPosition(new Point(usine.getPosition().x, usine.getPosition().y));
						produit.setVitesse(new Point(xTranslate, yTranslate));
						this.reseau.addProductionItem((Metal)produit);
					}
					catch (Exception e)
					{
						System.err.println("Error setting composant speed");
					}
				}
				
			}
			else if (usine.getType().equals("usine-aile"))
			{
				ProductionItem produit = usine.faitProduit();
				if(produit!= null)
				{
					System.err.println("new aile");
					Point position2 = null;
					for(int j = 0; j < this.reseau.getChemins().size(); j++)
					{
						int currentIndex = j;
						if(this.reseau.getChemins().get(j).getDe() == usine.getId())
						{
							
							Usine usine2 = this.reseau.getUsines().stream()
													.filter(u -> u.getId() == this.reseau.getChemins().get(currentIndex).getVers())
													.findFirst().get();

							position2 = usine2.getPosition();
						}
					}
					
					int xTranslate;
					if(usine.getPosition().x < position2.x) 
					{
						xTranslate = 10;
					}
					else if (usine.getPosition().x>position2.x)
					{
						xTranslate = -10;
					}
					else
					{
						xTranslate = 0;
						System.err.println("-no speed2-");
					}
					
					int yTranslate;
					if(usine.getPosition().y < position2.y) 
					{
						yTranslate = 10;
					}
					else if (usine.getPosition().y>position2.y)
					{
						yTranslate = -10;
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
						this.reseau.addProductionItem((Aile)produit);
					}
					catch (Exception e)
					{
						System.err.println("error setting componsant infos");
					}
					((UsineAile)usine).setIsFull(false);
					((UsineAile)usine).setCurrentMetalQty(0);
				}
			}
			else if (usine.getType().equals("usine-moteur"))
			{
				ProductionItem produit = usine.faitProduit();
				if(produit!= null)
				{
					System.err.println("new moteur");
					Point position2 = null;
					for(int j = 0; j < this.reseau.getChemins().size(); j++)
					{
						int currentIndex = j;
						if(this.reseau.getChemins().get(j).getDe() == usine.getId())
						{
							
							Usine usine2 = this.reseau.getUsines().stream()
													.filter(u -> u.getId() == this.reseau.getChemins().get(currentIndex).getVers())
													.findFirst().get();

							position2 = usine2.getPosition();
						}
					}
					
					int xTranslate;
					if(usine.getPosition().x < position2.x) 
					{
						xTranslate = 10;
					}
					else if (usine.getPosition().x>position2.x)
					{
						xTranslate = -10;
					}
					else
					{
						xTranslate = 0;
						System.err.println("-no speed2-");
					}
					
					int yTranslate;
					if(usine.getPosition().y < position2.y) 
					{
						yTranslate = 10;
					}
					else if (usine.getPosition().y>position2.y)
					{
						yTranslate = -10;
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
						this.reseau.addProductionItem((Moteur)produit);
					}
					catch (Exception e)
					{
						System.err.println("error setting componsant infos");
					}
					((UsineMoteur)usine).setIsFull(false);
				}
			}
			else if (usine.getType().equals("usine-assemblage"))
			{
				ProductionItem produit = usine.faitProduit();
				if(produit!= null)
				{
					System.err.println("new avion");
					Point position2 = null;
					for(int j = 0; j < this.reseau.getChemins().size(); j++)
					{
						int currentIndex = j;
						if(this.reseau.getChemins().get(j).getDe() == usine.getId())
						{
							
							Usine usine2 = this.reseau.getUsines().stream()
													.filter(u -> u.getId() == this.reseau.getChemins().get(currentIndex).getVers())
													.findFirst().get();

							position2 = usine2.getPosition();
						}
					}
					
					int xTranslate;
					if(usine.getPosition().x < position2.x) 
					{
						xTranslate = 10;
					}
					else if (usine.getPosition().x>position2.x)
					{
						xTranslate = -10;
					}
					else
					{
						xTranslate = 0;
						System.err.println("-no speed2-");
					}
					
					int yTranslate;
					if(usine.getPosition().y < position2.y) 
					{
						yTranslate = 10;
					}
					else if (usine.getPosition().y>position2.y)
					{
						yTranslate = -10;
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
						this.reseau.addProductionItem((Avion)produit);
					}
					catch (Exception e)
					{
						System.err.println("error setting componsant infos");
					}
					((UsineAssemblage)usine).setIsFull(false);
				}
			}
			
			
		}
	}

	public void updateAfterCollisions() {
		
		// Create products
//		createProducts();
		
		// Handle from usines matiere to other usines (usine aile, moteur)

		updateUsinesAM();
		
		// Handle usine assemblage collisions
		
		updateUsinesAssemblage();
		
		// Handle entrepot collisions
		
		updateEntrepots();
		
		// Create new materials if needed and update images
		
		updateOtherUsinesImages();
		
	}


	private void updateEntrepots() {
		List<Usine> entrepots = this.reseau.getUsines()
				.stream()
				.filter(u -> u.getType().equals("entrepot"))
				.collect(Collectors.toList());
		
		for(int i = 0; i< this.reseau.getProductionItems().size(); i++)
		{
			Point movingPoint = this.reseau.getProductionItems().get(i).getPosition();
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
						this.reseau.removeProductionItem(i);
						this.movingPoints.remove(i);
						((Entrepot) entrepot).addOneEntree();
					}
					catch (Exception e)
					{
						System.err.println("Erro with entrepot components");
					}
					
				}
			}
		}
	}

	private void updateUsinesAssemblage() {
		List<Usine> usinesAssemblage = this.reseau.getUsines()
				.stream()
				.filter(u -> u.getType().equals("usine-assemblage"))
				.collect(Collectors.toList());
		
		for(int i = 0; i< this.reseau.getProductionItems().size(); i++)
		{
			ProductionItem productionItem = this.reseau.getProductionItems().get(i);
			if(productionItem == null) continue;
			Point movingPoint = this.reseau.getProductionItems().get(i).getPosition();
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
								this.reseau.removeProductionItem(i);
								this.movingPoints.remove(i);
								if(usineAssemblage.getCurrentMoteurQty()> usineAssemblage.getNeededMoteurQty())
								{
									usineAssemblage.setCurrentMoteurQty(0);
								}
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
					if(usineAssemblage.getPosition().distanceSq(movingPoint)<=10 && 
					  usineAssemblage!= null)
					{
						try {
							this.produitsImages.remove(i);
							this.produitsVitesses.remove(i);
							this.reseau.removeProductionItem(i);
							this.movingPoints.remove(i);
							if(usineAssemblage.getCurrentMoteurQty()> usineAssemblage.getNeededMoteurQty())
							{
								usineAssemblage.setCurrentMoteurQty(0);
							}
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
		
		List<Usine> usinesAM = this.reseau.getUsines()
				.stream()
				.filter(u -> u.getType().equals("usine-aile")|| u.getType().equals("usine-moteur"))
				.collect(Collectors.toList());
		
		for(int i = 0; i< this.reseau.getProductionItems().size(); i++)
		{
			Point movingPoint = this.reseau.getProductionItems().get(i).getPosition();
			if(movingPoint == null) continue;
			String movingItemType = this.reseau.getProductionItems().get(i).getType();
			
			for(int j = 0 ; j< usinesAM.size();j++)
			{
				Usine usineAM = usinesAM.get(j);
				// if metals collide with usines other than usines matieres
				if(usineAM.getPosition().distanceSq(movingPoint)<=100 && 
				   !movingItemType.equals("aile") && 
				   !movingItemType.equals("moteur") && 
				   movingPoint!= null &&
				   this.movingPoints!= null &&
				   this.produitsImages!= null &&
				   this.produitsVitesses!= null &&
				   this.reseau.getProductionItems()!= null)
				{
					try {
						this.produitsImages.remove(i);
						this.produitsVitesses.remove(i);
						this.reseau.removeProductionItem(i);
						this.movingPoints.remove(i);
					}
					catch(Exception e)
					{
						System.err.println("------------------------COLLISION ERROR AAMMM--------------------------");
					}
					
					if(usineAM.getType().equals("usine-aile"))
					{
						if(((UsineAile) usineAM).getCurrentMetalQty() > ((UsineAile) usineAM).getNeededMetalQty())
						{
							((UsineAile) usineAM).setCurrentMetalQty(0);
						}
						((UsineAile) usineAM).addOneEntree();
					}
					else if (usineAM.getType().equals("usine-moteur"))
					{
						if(((UsineMoteur) usineAM).getCurrentMetalQty() > ((UsineMoteur) usineAM).getNeededMetalQty())
						{
							((UsineMoteur) usineAM).setCurrentMetalQty(0);
						}
						((UsineMoteur) usineAM).addOneEntree();
					}
				}
			}
		}
	}

	public void fillPanelInformation() {
		if(this.reseau.getProductionItems()!= null && 
		   this.movingPoints.size()< this.reseau.getProductionItems().size())
		{
			List<Point> vitesses = new ArrayList<Point>();
			List<Point> positions = new ArrayList<Point>();
			List<Image> images = new ArrayList<Image>();
			for(int i = 0; i < this.reseau.getProductionItems().size();i++)
			{
				try
				{
					ProductionItem productionItem = this.reseau.getProductionItems().get(i);
					positions.add(productionItem.getPosition());
					vitesses.add(productionItem.getVitesse());
					images.add(productionItem.getImage());
				}
				catch (Exception e)
				{
					System.err.println("--------------------------ERROR----------------------------");
				}
			}
			this.movingPoints = positions;
			this.produitsVitesses = vitesses;
			this.produitsImages = images;
			if(this.movingPoints.size() != this.reseau.getProductionItems().size()) 
				System.err.println("Size of moving points is different than production items size");
		}
		
		
	}



}