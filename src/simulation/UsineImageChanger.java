package simulation;

import java.util.List;

import dataForSimulation.Usine;
import dataForSimulation.UsineMatiere;

public class UsineImageChanger {

	public UsineMatiere changeImageByProductionUsine(Usine usine) {
		
		UsineMatiere usineMatiere =(UsineMatiere) usine;
		long increment = usineMatiere.getIntervalProduction()/3;
		
		// Foreach usine matiere, change their icones every intervalProduction/3
		try 
		{
			wait(increment);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(usineMatiere.getCurrentImage().equals(usineMatiere.getImageByType("vide")))
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("un-tiers"));
		}
		else if(usineMatiere.getCurrentImage().equals(usineMatiere.getImageByType("un-tiers")))
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("deux-tiers"));
		}
		else if(usineMatiere.getCurrentImage().equals(usineMatiere.getImageByType("deux-tiers")))
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("plein"));
		}
		else if(usineMatiere.getCurrentImage().equals(usineMatiere.getImageByType("plein")))
		{
			usineMatiere.setCurrentImage(usineMatiere.getImageByType("vide"));
		}

		return usineMatiere;
		
		
	}

}
