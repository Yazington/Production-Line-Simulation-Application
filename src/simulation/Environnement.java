package simulation;

import javax.swing.SwingWorker;


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
			firePropertyChange("creating products", null, "products created");
			
		}
		return null;
	}
}