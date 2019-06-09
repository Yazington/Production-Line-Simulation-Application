package simulation;

import javax.swing.SwingWorker;

public class Environnement extends SwingWorker<Object, String>{
	private boolean actif = true;
	public static final long DELAI = 150;
	private int currentTime = 0;

	
	
	@Override
	protected Object doInBackground() throws Exception {
		while(actif) 
		{
			if(this.currentTime >= (int) DELAI)
			{
				this.currentTime = 0;
			}
			Thread.sleep(3);
			/**
			 * C'est ici que vous aurez a faire la gestion de la notion de tour.
			 */
			
			this.currentTime = this.currentTime + 1;
			firePropertyChange("change images", null, currentTime);
			
		}
		
		return null;
	}
}