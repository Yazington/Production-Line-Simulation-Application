package simulation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.util.*;

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
	private List<Point> produitsPositions;
	

	public PanneauPrincipal(MenuFenetre menuFenetre) {
		super();
		this.menuFenetre = menuFenetre;
		this.produitsImages = new ArrayList<Image>();
		this.produitsVitesses = new ArrayList<Point>();
		this.produitsPositions = new ArrayList<Point>();
	}
	
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
				try 
				{
					Point point1 = this.cheminsPoints1.get(i);
					Point point2 = this.cheminsPoints2.get(i);
					g2d.drawLine(point1.x + 16, point1.y + 16, point2.x + 16 , point2.y + 16);
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
				try 
				{
				g.drawImage(this.usinesImages.get(i), this.usinesPositions.get(i).x, this.usinesPositions.get(i).y, null);
				}
				catch(Exception e)
				{
					System.err.println("-----------------usinesPositions and usines images-------------------------------");
				}
			}
			
		}
		
		// dessine les produits
		if(this.produitsPositions!=null && this.produitsImages != null )
		{
			for(int i = 0; i<this.produitsPositions.size();i++)
			{
				try 
				{
					g.drawImage(this.produitsImages.get(i), this.produitsPositions.get(i).x, this.produitsPositions.get(i).y, null);
				}
				catch(Exception e)
				{
					System.err.println("----------------- MOVING POINTS ERROR-------------------");
				}
				
				
			}

		}
		
		// donne des vitesses aux produits
		if(this.produitsPositions!=null)
		{
			for(int i = 0; i < this.produitsPositions.size();i++)
			{
				try 
				{
					this.produitsPositions.get(i).translate(this.produitsVitesses.get(i).x, this.produitsVitesses.get(i).y);
				}
				catch(Exception e) {
					System.err.println("--moving items--");
				}
				
			}
		}
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

	public void refreshNetwork(int currentTime) {
		if(this.reseau == null) return;
		this.reseau.refresh(currentTime);
		this.usinesImages = this.reseau.getUsinesImages();
		this.produitsImages = this.reseau.getProduitsImages();
		this.produitsPositions = this.reseau.getProduitsPositions();
		this.produitsVitesses = this.reseau.getProduitsVitesses();
		repaint();
	}

	public Network getNetwork() {
		return reseau;
	}

	
	@Override
	public void UpdateObserver() {
		XMLSourcer xmlSourcer = this.menuFenetre.getXmlSourcer();
		createNetwork(xmlSourcer);
		this.reseau.createRefreshManager();
		this.reseau.createObserverPattern(this.menuFenetre.getStrategie());
		this.usinesPositions = this.reseau.getCurrentPositions();
		paintChemins(this.getGraphics());
		
	}


}