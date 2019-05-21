package simulation;

import java.awt.Dimension;

import javax.swing.JFrame;

public class FenetreStrategie extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final String TITRE_FENETRE = "Selectionnez votre strategie de vente";
	private static final Dimension DIMENSION = new Dimension(250, 100);
	private PanneauStrategie panneauStrategie = new PanneauStrategie();

	public FenetreStrategie() {
		add(panneauStrategie);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(TITRE_FENETRE);
		setSize(DIMENSION);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}
}
