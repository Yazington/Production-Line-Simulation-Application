package simulation;

import java.util.List;

import javax.swing.SwingWorker;

import dataForSimulation.Usine;


public class Environnement extends SwingWorker<Object, String>{
	private boolean actif = true;
	private static final long DELAI = 100;
	private long currentTime = 0;

	@Override
	protected Object doInBackground() throws Exception {
		while(actif) 
		{
//			long difference = System.currentTimeMillis() - this.start;
//			if(difference == DELAI)
//			{
//				this.start = System.currentTimeMillis();
//			}
			
			if(this.currentTime >= DELAI)
			{
				this.currentTime = 0;
			}
			Thread.sleep(3);
			/**
			 * C'est ici que vous aurez a faire la gestion de la notion de tour.
			 */
			this.currentTime = this.currentTime + 1;
			firePropertyChange("change images", null, currentTime);
			if(this.currentTime == DELAI) 
				firePropertyChange("create products", null, currentTime);
			firePropertyChange("handle collisions", null, null);
			firePropertyChange("paint everything", null, null);
			
		}
		return null;
	}
}