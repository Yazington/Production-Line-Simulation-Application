package simulation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dataForSimulation.*;
import dataForSimulation.UsineIntermediaires.*;
import observerPattern.IObservable;
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
	private List<Chemin> chemins;


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
		super.paintComponent(g);
	}
	
	private void paintUsines()
	{
		// Dessiner usines si elles existent
		if(this.reseau == null) throw new NullPointerException("Pas d usine");
		for(var usine:this.reseau.getUsines())
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
	
	private void paintChemins(Graphics2D g2d)
	{
		var chemins = this.reseau.getChemins();
		// Dessiner chemins siles existent
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
				g2d.drawLine(usine1.getPosition()[0], usine1.getPosition()[1], usine2.getPosition()[0], usine2.getPosition()[1]);
//				this.repaint();
			}
		}
	}
	
	public void createNetwork(XMLSourcer xmlInfo)
	{
		if(this.menuFenetre == null) throw new NullPointerException("pas de source xml");
		
		Network reseau = new Network(xmlInfo.getMetaList(), xmlInfo.getSimList());
		this.reseau = reseau;
		var usines = this.reseau.createInstances(this.reseau.getMetadonneesD(), this.reseau.getSimulationD());
		
		
		List<Point> pointsUsine = new ArrayList<Point>();
		
		// Dessiner usines
		for(var usine : usines)
		{
			Point point = new Point(usine.getPosition()[0], usine.getPosition()[1]);
			pointsUsine.add(point);
		}
		
		
		Graphics g = this.getGraphics();
//		paintComponent(g);
		
	}

	@Override
	public void UpdateObserver() {
		var xmlSourcer = this.menuFenetre.getXmlSourcer();
		createNetwork(xmlSourcer);
		paintUsines();
		paintChemins((Graphics2D)this.getGraphics());
		
	}
	


}