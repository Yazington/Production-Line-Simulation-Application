package simulation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import dataForSimulation.*;
import xmlUtility.*;
import observerPattern.IObserver;

public class PanneauPrincipal extends JPanel implements PropertyChangeListener, IObserver {

	private static final long serialVersionUID = 1L;

	// Variables temporaires de la demonstration:
	private Point position = new Point(0,0);
	private Point vitesse = new Point(1,1);
	private int taille = 32;
	private MenuFenetre menuFenetre;
	private Network reseau; 
	List<Point> cheminsPoints1;
	List<Point> cheminsPoints2;

	private List<Point> usinesPositions;
	private List<Image> usinesImages;
	private List<Point> produitsPositions;
	private List<Image> produitsImages;
	private List<Point> produitsVitesses;
	private List<Point> movingPoints;
	private int incrementNumber;
	private boolean initialIsPainted;

	private boolean usinesAreFull;


	public PanneauPrincipal(MenuFenetre menuFenetre) {
		super();
		this.menuFenetre = menuFenetre;
		this.initialIsPainted = false;
		this.produitsImages = new ArrayList<Image>();
		this.produitsPositions = new ArrayList<Point>();
		this.produitsVitesses = new ArrayList<Point>();
		this.movingPoints = new ArrayList<Point>();
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
//		this.setDoubleBuffered(true);
//		this.getGraphics().dispose();
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
//		if(!this.initialIsPainted)
//		{
			// dessine les chemins
			if(this.cheminsPoints1 != null)
			{
				for(int i = 0; i< this.cheminsPoints1.size();i++)
				{
					Point point1 = this.cheminsPoints1.get(i);
					Point point2 = this.cheminsPoints2.get(i);
					g.drawLine(point1.x, point1.y, point2.x, point2.y);
					this.reseau.setUsinesAreLoaded(true);
					this.initialIsPainted = true;
				}
			}
//		}
		

		// dessine les usines
		if(this.usinesPositions != null && this.usinesImages != null)
		{
			for(int i = 0; i< this.usinesPositions.size();i++)
			{
				g.drawImage(this.usinesImages.get(i), this.usinesPositions.get(i).x, this.usinesPositions.get(i).y, null);
			}
			
		}
		
//		if(this.movingPoints!=null)
//			for(int i = 0; i < this.movingPoints.size();i++)
//			{
//				try {
//					this.movingPoints.get(i).translate(this.produitsVitesses.get(i).x, this.produitsVitesses.get(i).y);
//				}
//				catch(Exception e) {
//					System.err.println("--moving items--");
//				}
//				
//			}
		
//		// dessine les produits
//		if(this.produitsImages!=null && this.usinesAreFull)
//		{
//			for(int i = 0; i<this.movingPoints.size();i++)
//			{
//				g.drawImage(this.produitsImages.get(i), this.movingPoints.get(i).x, this.movingPoints.get(i).y, null);
//				
//			}
//		}
			
		
		
		
		
	}

	
	public void paintProducts()
	{
		//Dessiner produits si ils existent
		List<ProductionItem> produits = this.reseau.getProductionItems();
		if(produits == null) return;
		
		changeIcones(produits);
		
		if(this.usinesAreFull && this.incrementNumber == 2)
		{
			this.reseau.execute();
			this.usinesAreFull = false;
			this.incrementNumber = 3;
		}
		
	}
	
	private void changeIcones(List<ProductionItem> producionItem)
	{
		UsineImageChanger uIC = new UsineImageChanger();
		
		for(int i = 0 ; i<this.reseau.getUsines().size(); i++)
		{
			Usine usine = this.reseau.getUsines().get(i);
			if(usine.getType().equals("usine-matiere")) uIC.changeImageByProductionUsine(usine);
		}
		
		
	}
	
	
//	private void changeIcones(List<ProductionItem> produits) {
//		// get usines matieres
//				List<Usine> usinesMatiere = this.reseau.getUsines()
//												.stream()
//												.filter(u -> u.getType().equals("usine-matiere"))
//												.collect(Collectors.toList());
//				
//				//Pour les usines matieres en meme temps
//				for(int i = 0; i < usinesMatiere.size();i++)
//				{
//					UsineMatiere usineMatiere = (UsineMatiere) usinesMatiere.get(i);
//					int increment = usineMatiere.getIntervalProduction()/3;
//					
//					if(this.incrementNumber == 0)
//					{
//						synchronized(this) 
//						{
//							try 
//							{
//								wait(increment);
//								usineMatiere.setCurrentIcone(usineMatiere.getIconeByType("un-tiers"));
//								Image image = ImageIO.read(new File(usineMatiere.getCurrentIcone().getPath()));
//								for(int j = 0; j< this.reseau.getUsines().size();j++)
//								{
//									if(this.reseau.getUsines().get(j).getType().equals("usine-matiere"))
//										this.usinesImages.set(j, image);
//										
//								}
//								this.usinesAreFull = false;
//								this.incrementNumber = 1;
//							}
//							catch(InterruptedException e)
//							{
//								e.printStackTrace();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
////						paintComponent(this.getGraphics());
//						return;
//					}
//					
//					if(this.incrementNumber == 1)
//					{
//						synchronized(this) 
//						{
//							try 
//							{
//								wait(increment);
//								usineMatiere.setCurrentIcone(usineMatiere.getIconeByType("deux-tiers"));
//								Image image = ImageIO.read(new File(usineMatiere.getCurrentIcone().getPath()));
//								for(int j = 0; j< this.reseau.getUsines().size();j++)
//								{
//									if(this.reseau.getUsines().get(j).getType().equals("usine-matiere"))
//										this.usinesImages.set(j, image);
//										
//								}
//								this.usinesAreFull = false;
//								this.incrementNumber = 2;
//							}
//							catch(InterruptedException e)
//							{
//								e.printStackTrace();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
////						paintComponent(this.getGraphics());
//						return;
//					}
//					
//					
//					if(this.incrementNumber == 3)
//					{
//						synchronized(this) 
//						{
//							try 
//							{
//								wait(increment);
//								usineMatiere.setCurrentIcone(usineMatiere.getIconeByType("vide"));
//								Image image = ImageIO.read(new File(usineMatiere.getCurrentIcone().getPath()));
//								for(int j = 0; j< this.reseau.getUsines().size();j++)
//								{
//									if(this.reseau.getUsines().get(j).getType().equals("usine-matiere"))
//										this.usinesImages.set(j, image);
//										
//								}
//								this.usinesAreFull = false;
//							}
//							catch(InterruptedException e)
//							{
//								e.printStackTrace();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
//						
//					}
//					
//					if(this.incrementNumber == 2)
//					{
//						synchronized(this) 
//						{
//							try 
//							{
//								wait(increment);
//								usineMatiere.setCurrentIcone(usineMatiere.getIconeByType("plein"));
//								Image image = ImageIO.read(new File(usineMatiere.getCurrentIcone().getPath()));
//								for(int j = 0; j< this.reseau.getUsines().size();j++)
//								{
//									if(this.reseau.getUsines().get(j).getType().equals("usine-matiere"))
//										this.usinesImages.set(j, image);
//										
//								}
//								this.usinesAreFull = true;
//								
//							}
//							catch(InterruptedException e)
//							{
//								e.printStackTrace();
//							} catch (IOException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//						}
////						paintComponent(this.getGraphics());
//						
//					}
//					
//					
//				}
//				
//				if(this.incrementNumber == 3)
//				{
//
//					int additionalItems = 0;
//					for(ProductionItem item: this.reseau.getProductionItems())
//					{
//						int[] position = item.getPosition();
//						Point point = new Point(position[0]-16,position[1]-16);
//						this.produitsPositions.add(point);
//						try 
//						{
//							this.produitsImages.add(ImageIO.read(new File(item.getImagePath())));
//						} 
//						catch (Exception e) 
//						{
//							e.printStackTrace();
//						}
//						Point vitesse = item.getVitesse();
//						this.produitsVitesses.add(vitesse);
//						additionalItems++;
//					}
//					
//					
//					
//					
//					List<Point> produitsPoints = new LinkedList<Point>();
//					if(this.produitsPositions != null && this.produitsImages != null && this.produitsPositions.size() != this.movingPoints.size())
//					{
//						for(int i = 0; i< additionalItems;i++)
//						{
//							Point pointProduit = new Point(this.produitsPositions.get(i).x,this.produitsPositions.get(i).y);
////							this.getGraphics().drawImage(this.produitsImages.get(i), pointProduit.x, pointProduit.y, null);	
//							produitsPoints.add(pointProduit);
//							if(this.movingPoints==null)
//							{
//								this.movingPoints = new ArrayList<Point>();
//							}
//							this.movingPoints.add(pointProduit);
//						}
//						this.usinesAreFull = true;
////						paintComponent(this.getGraphics());
//						this.incrementNumber = 0;
//						
//					}	
//				}
//		
//	}

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
				
				Point point = new Point(usine1.getPosition()[0], usine1.getPosition()[1]);
				Point point2 = new Point(usine2.getPosition()[0], usine2.getPosition()[1]);

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
		paintComponent(this.getGraphics());
		try 
		{
			paintUsines(usines);
		}
		catch (IOException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void moveObjects() {
		if(this.movingPoints!=null)
			for(int i = 0; i < this.produitsVitesses.size();i++)
			{
				try {
					this.movingPoints.get(i).translate(this.produitsVitesses.get(i).x, this.produitsVitesses.get(i).y);
				}
				catch(Exception e) {
					System.err.println("--moving items--");
				}
				
			}
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName().equals("move"))
		{
//			paintComponent(this.getGraphics());
		}
		
	}

}