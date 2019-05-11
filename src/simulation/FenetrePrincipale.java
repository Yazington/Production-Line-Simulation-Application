package simulation;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JFrame;

public class FenetrePrincipale extends JFrame implements PropertyChangeListener {

	private static final long serialVersionUID = 1L;
	private static final String TITRE_FENETRE = "Laboratoire 1 : LOG121 - Simulation";
	private static final Dimension DIMENSION = new Dimension(700, 700);

	public FenetrePrincipale() {
		PanneauPrincipal panneauPrincipal = new PanneauPrincipal();
		MenuFenetre menuFenetre = new MenuFenetre();
		add(panneauPrincipal);
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
		System.out.println("hi");
		if (evt.getPropertyName().equals("TEST")) {
			repaint();
			System.out.println(evt.getNewValue());
			
		}
	}
}
