package simulation;

import java.util.List;
import java.util.concurrent.TimeUnit;

import dataForSimulation.Usine;
import dataForSimulation.UsineMatiere;

public class UsineImageChanger {

	public UsineMatiere changeImageByProductionUsine(Usine usine, int loopIterator) {
		
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
		
		if(loopIterator >= 0 && loopIterator < 33)
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("vide"));
		}
		else if(loopIterator >= 33 && loopIterator < 66)
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("un-tiers"));
		}
		else if(loopIterator >= 66 && loopIterator < 100)
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("deux-tiers"));
		}
		else if(loopIterator == 100)
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("plein"));
			
		}

		return usineMatiere;
		
		
	}

}
