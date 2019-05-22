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

public class PanneauPrincipal extends JPanel implements IObserver {

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


	public PanneauPrincipal(MenuFenetre menuFenetre) {
		super();
		this.menuFenetre = menuFenetre;
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
	public synchronized void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		// dessine les chemins
		if(this.cheminsPoints1 != null)
		{
			for(int i = 0; i< this.cheminsPoints1.size();i++)
			{
				var point1 = this.cheminsPoints1.get(i);
				var point2 = this.cheminsPoints2.get(i);
				g2d.drawLine(point1.x, point1.y, point2.x, point2.y);
			}
		}
		// dessine les usines
		if(this.usinesPositions != null && this.usinesImages != null)
		{
			for(int i = 0; i< this.usinesPositions.size();i++)
			{
				g2d.drawImage(this.usinesImages.get(i), this.usinesPositions.get(i).x, this.usinesPositions.get(i).y, null);
			}
		}
		
		// dessine les produits
		if(this.movingPoints!=null)
			for(int i = 0; i < this.movingPoints.size();i++)
			{
				this.movingPoints.get(i).translate(this.produitsVitesses.get(i).x, this.produitsVitesses.get(i).y);
			}
			
		if(this.produitsImages!=null)
			for(int i = 0; i<this.produitsImages.size();i++)
			{
				g2d.drawImage(this.produitsImages.get(i), this.movingPoints.get(i).x, this.movingPoints.get(i).y, null);
			}
	}
	
	public void paintProducts()
	{
		//Dessiner produits si ils existent
		var produits = this.reseau.getProductionItems();
		if(produits == null) return;
		List<Point> produitsPositions = new ArrayList<Point>();
		List<Image> produitsImages = new ArrayList<Image>();
		List<Point> produitsVitesses = new ArrayList<Point>();
		for(var item: produits)
		{
			var position = item.getPosition();
			Point point = new Point(position[0]-16,position[1]-16);
			produitsPositions.add(point);
			try 
			{
				produitsImages.add(ImageIO.read(new File(item.getImagePath())));
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			var vitesse = item.getVitesse();
			produitsVitesses.add(vitesse);
		}
		this.produitsPositions = produitsPositions;
		this.produitsImages = produitsImages;
		this.produitsVitesses = produitsVitesses;
		
		// dessine les produits
		List<Point> produitsPoints = new LinkedList<Point>();
		if(this.produitsPositions != null && this.produitsImages != null)
		{
			for(int i = 0; i< this.produitsPositions.size();i++)
			{
				Point pointProduit = new Point(this.produitsPositions.get(i).x,this.produitsPositions.get(i).y);
				this.getGraphics().drawImage(this.produitsImages.get(i), pointProduit.x, pointProduit.y, null);	
				produitsPoints.add(pointProduit);
			}
			this.movingPoints = produitsPoints;
		}	
	}
	
	private void paintUsines(List<Usine> usines) throws IOException
	{
		//Dessiner usines si elles existent
		if(this.reseau == null) throw new NullPointerException("pas d usine");
		List<Point> usinesPositions = new ArrayList<Point>();
		List<Image> usinesImages = new ArrayList<Image>();
		for(var usine:usines)
		{
			var position = usine.getPosition();
			Point point = new Point(position[0] - 16,position[1] - 16);
			usinesPositions.add(point);
			var currentIcone = usine.getCurrentIcone();
			try {
				//TODO: change usines icones every production-interval time
				usinesImages.add(ImageIO.read(new File(currentIcone.getPath())));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		this.usinesPositions = usinesPositions;
		this.usinesImages = usinesImages;
	}
	
	private void paintChemins(Graphics g)
	{
		var chemins = this.reseau.getChemins();
		// Dessiner chemins siles existent
		this.cheminsPoints1 = new ArrayList<Point>();
		this.cheminsPoints2 = new ArrayList<Point>();
		if(chemins!= null)
		{
			for(var cheminID: chemins)
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
		var usines = this.reseau.createInstances(this.reseau.getMetadonneesD(), this.reseau.getSimulationD());
		this.reseau.execute();
		return usines;
	}

	
	public Network getNetwork() {
		return reseau;
	}

	@Override
	public synchronized void UpdateObserver() {
		var xmlSourcer = this.menuFenetre.getXmlSourcer();
		var usines = createNetwork(xmlSourcer);
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
		paintProducts();
	}

}