package simulation;

import java.util.List;

import javax.swing.SwingWorker;

import dataForSimulation.Usine;


public class Environnement extends SwingWorker<Object, String>{
	private boolean actif = true;
	private static final int DELAI = 100;

	@Override
	protected Object doInBackground() throws Exception {
		while(actif) 
		{
			Thread.sleep(DELAI);
			/**
			 * C'est ici que vous aurez a faire la gestion de la notion de tour.
			 */
			firePropertyChange("change images", null, "-changing imgs-");
			firePropertyChange("creating products", null, "-creating-");
			firePropertyChange("move objects", null, "-moving-");
			firePropertyChange("handle collisions", null, "-handling collisions-");
			firePropertyChange("paint everything", null, "-painting-");
			
		}
		return null;
	}
}