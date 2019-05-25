package simulation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.*;

import java.util.Timer;


import javax.swing.JPanel;

import dataForSimulation.*;
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
	private long startTime;
	private long difference = System.currentTimeMillis() - startTime;
	


	public PanneauPrincipal(MenuFenetre menuFenetre) {
		super();
		this.menuFenetre = menuFenetre;
		this.produitsImages = new ArrayList<Image>();
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
		this.getGraphics().dispose();
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		if(this.cheminsPoints1 != null)
		{
			for(int i = 0; i< this.cheminsPoints1.size();i++)
			{
				Point point1 = this.cheminsPoints1.get(i);
				Point point2 = this.cheminsPoints2.get(i);
				g.drawLine(point1.x, point1.y, point2.x, point2.y);
				this.reseau.setUsinesAreLoaded(true);
			}
		}

		// dessine les usines
		if(this.usinesPositions != null && this.usinesImages != null)
		{
			for(int i = 0; i< this.usinesPositions.size();i++)
			{
				g.drawImage(this.usinesImages.get(i), this.usinesPositions.get(i).x-16, this.usinesPositions.get(i).y-16, null);
			}
			
		}
		
		if(this.movingPoints!=null && this.difference>=100)
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
			
		
		// dessine les produits
		if(this.produitsImages!=null && this.difference>=100)
		{
			for(int i = 0; i<this.movingPoints.size();i++)
			{
				g.drawImage(this.produitsImages.get(i), this.movingPoints.get(i).x, this.movingPoints.get(i).y, null);
				
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
		try 
		{
			paintUsines(usines);
		}
		catch (IOException e) 
		{
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
	
	public void changeImages(long startTime) {
		this.startTime = startTime;
		updateUsinesMatiereImages(startTime);
		updateOtherUsinesImages();
		
	}
	


//	private void updateUsinesMatiereImages() {
//		for(int i = 0; i< this.reseau.getUsines().size(); i++)
//		{
//			if(this.reseau.getUsines().get(i).getType().equals("usine-matiere"))
//				this.reseau.getUsines().get(i).updateCurrentImage();
//		}
//		this.usinesImages = this.reseau.getCurrentImages();
//		
//		
//	}
	
	/**
	 * Change les images des usines matieres
	 * @param startTime
	 */
	private void updateUsinesMatiereImages(long startTime) 
	{
		for (int i = 0; i < this.reseau.getUsines().size(); i++)
		{
			Usine usine = this.reseau.getUsines().get(i);
			if(usine.getType().equals("usine-matiere"))
			{
					usine.updateCurrentImage(startTime);
					
			}
		}
//		repaint();
	}

	/**
	 * Changes les images des autres usines que les usines matieres
	 */
	private void updateOtherUsinesImages() {
		
		for(int i = 0; i< this.reseau.getUsines().size(); i++)
		{
			if(!this.reseau.getUsines().get(i).getType().equals("usine-matiere"))
			{
				
			}
				
		}
		this.usinesImages = this.reseau.getCurrentImages();
		
	}
	
	/**
	 * creer les produits dependamment des entrees si on en a besoin
	 */
	public void createProducts()
	{	
		for(int i = 0; i < this.reseau.getUsines().size();i ++)
		{
			Usine usine = this.reseau.getUsines().get(i);
			if(usine.getType().equals("usine-matiere"))
			{
				if(usine.getCurrentImage().equals(usine.getImageByType("plein")))
				{
					ProductionItem produit = usine.faitProduit();
					int[] position2 = null;
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
					if(usine.getPosition()[0] < position2[0]) 
					{
						xTranslate = 5;
					}
					else if (usine.getPosition()[0]>position2[0])
					{
						xTranslate = -5;
					}
					else
					{
						xTranslate = 5;
					}
					
					int yTranslate;
					if(usine.getPosition()[1] < position2[1]) 
					{
						yTranslate = 5;
					}
					else if (usine.getPosition()[1]>position2[1])
					{
						yTranslate = -5;
					}
					else
					{
						yTranslate = 0;
					}
					produit.setPosition(usine.getPosition());
					produit.setVitesse(new Point(xTranslate, yTranslate));
					
					this.produitsImages.add(produit.getImage());
					if(produit.getImage() == null)
						System.err.println("no product image");
					
					// Add initial point
					this.movingPoints.add(new Point(usine.getPosition()[0] - 16,usine.getPosition()[1] - 16));

					// Give them speed
					this.produitsVitesses.add(produit.getVitesse());

				}
				
			}
			
			
		}
	}

	public void updateAfterCollisions() {
		// TODO Auto-generated method stub
		
	}



}