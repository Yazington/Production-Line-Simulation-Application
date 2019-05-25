package simulation;

import java.util.List;

import javax.swing.SwingWorker;

import dataForSimulation.Usine;


public class Environnement extends SwingWorker<Object, String>{
	private boolean actif = true;
	private static final long DELAI = 100;
	private long start = System.currentTimeMillis();

	@Override
	protected Object doInBackground() throws Exception {
		while(actif) 
		{
			long difference = System.currentTimeMillis() - this.start;
			if(difference >= DELAI  )
			{
				this.start = System.currentTimeMillis();
			}
			Thread.sleep(1);
			/**
			 * C'est ici que vous aurez a faire la gestion de la notion de tour.
			 */
			this.start++;
			firePropertyChange("change images", null, start);
			if(System.currentTimeMillis() - start >= DELAI && System.currentTimeMillis() - start <= 102) 
				firePropertyChange("create products", null, start);
			firePropertyChange("handle collisions", null, start);
			firePropertyChange("paint everything", null, start);
			
		}
		return null;
	}
}