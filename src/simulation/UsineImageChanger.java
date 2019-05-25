package simulation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import dataForSimulation.Usine;
import dataForSimulation.UsineMatiere;

public class UsineImageChanger {

	public UsineMatiere changeImageByProductionUsine(Usine usine, long loopIterator) {
		
		UsineMatiere usineMatiere =(UsineMatiere) usine;
		long increment = usineMatiere.getIntervalProduction()/3;
		
		// Foreach usine matiere, change their icones every intervalProduction/3
//		try 
//		{
//			Thread.sleep(increment);
//			System.out.println("sleeping");
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		if(System.currentTimeMillis() - loopIterator >= 0  && System.currentTimeMillis() - loopIterator < 33)
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("vide"));
		}
		else if(System.currentTimeMillis() - loopIterator >= 33  && System.currentTimeMillis() - loopIterator < 66)
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("un-tiers"));
			System.out.println("Bam");
		}
		else if(System.currentTimeMillis() - loopIterator >= 66  && System.currentTimeMillis() - loopIterator < 100)
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("deux-tiers"));
			System.out.println("Bam");
		}
		else if(loopIterator == 100)
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("plein"));
			System.out.println("PAK");
		}

		return usineMatiere;
		
		
	}

}
