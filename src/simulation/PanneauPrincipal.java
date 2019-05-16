package simulation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.*;
import javax.swing.JPanel;

import dataForSimulation.*;
import xmlUtility.*;
import observerPattern.IObserver;

public class PanneauPrincipal extends JPanel implements IObserver {

	private static final long serialVersionUID = 1L;

//	// Variables temporaires de la demonstration:
//	private Point position = new Point(0,0);
//	private Point vitesse = new Point(1,1);
	private int taille = 32;
	private MenuFenetre menuFenetre;
	private Network reseau; 
	List<Point> cheminsPoints1;
	List<Point> cheminsPoints2;


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
	
	
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D) g;
		super.paintComponent(g2d);
		
		if(this.cheminsPoints1 != null)
		{
			for(int i = 0; i< this.cheminsPoints1.size();i++)
			{
				var point1 = this.cheminsPoints1.get(i);
				var point2 = this.cheminsPoints2.get(i);
				g2d.drawLine(point1.x, point1.y, point2.x, point2.y);
			}
		}
		
		
	}
	
	private void paintUsines(List<Usine> usines)
	{
		// Dessiner usines si elles existent
		if(this.reseau == null) throw new NullPointerException("Pas d usine");
		for(var usine:usines)
		{
			usine.setIcone(usine.getIcones().get(0));
			usine.setVisible(true);
			usine.setLocation(11, 11);
			usine.setSize(taille, taille);
			Point p = new Point(usine.getPosition()[0] - 16, usine.getPosition()[1] - 16);
			usine.setLocation(p);
			this.add(usine);
		}
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
		

		return usines;
	}

	@Override
	public void UpdateObserver() {
		var xmlSourcer = this.menuFenetre.getXmlSourcer();
		var usines = createNetwork(xmlSourcer);
		paintChemins(this.getGraphics());
		paintComponent(this.getGraphics());
		paintUsines(usines);

	}
}