package simulation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

import dataForSimulation.Network;
import observerPattern.IObserver;

public class FenetrePrincipale extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private static final String TITRE_FENETRE = "Laboratoire 1 : LOG121 - Simulation";
	private static final Dimension DIMENSION = new Dimension(700, 700);
	private PanneauPrincipal panneau;


	public FenetrePrincipale() {
		MenuFenetre menuFenetre = new MenuFenetre();
		IObserver panneauPrincipal = new PanneauPrincipal(menuFenetre);
		menuFenetre.registerObserver(panneauPrincipal);
		add((PanneauPrincipal) panneauPrincipal);
		this.panneau = (PanneauPrincipal) panneauPrincipal;
		add(menuFenetre, BorderLayout.NORTH);
		// Faire en sorte que le X de la fenetre ferme la fenetre
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle(TITRE_FENETRE);
		setSize(DIMENSION);
		// Rendre la fenetre visible
		setVisible(true);
		// Mettre la fenetre au centre de l'ecran
		setLocationRelativeTo(null);
		// Empecher la redimension de la fenetre
		setResizable(false);
		
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("change images")) {
			repaint();
		}
		else if (evt.getPropertyName().equals("Create products"))
		{
			if(this.panneau.getNetwork()!= null) 
			{
				for(int i = 0; i < this.panneau.getNetwork().getUsines().size(); i ++)
				{
					var usine = this.panneau.getNetwork().getUsines().get(i);
					this.panneau.getNetwork().getProductionItems().add(usine.faitProduit());
				}
				repaint();
			}
				
		}
	}
	
}
