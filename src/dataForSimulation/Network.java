package dataForSimulation;

import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import dataForSimulation.UsineIntermediaires.UsineAile;
import dataForSimulation.UsineIntermediaires.UsineAssemblage;
import dataForSimulation.UsineIntermediaires.UsineMoteur;

public class Network {

	private static final int numberOfUsineSimulationElements = 4;
	
	private List<String> metadonneesD;
	private List<String> simulationD;
	private List<Usine> usines;
	private List<Chemin> chemins;
	private List<ProductionItem> productionItems;

	public Network(List<String> metadonneesD, List<String> simulationD)
	{
		this.metadonneesD = metadonneesD;
		this.simulationD = simulationD;
	}
	
	public void updateItems()
	{
		var usines = new LinkedList<Usine>();
		for(int i = 0; i< this.usines.size();i++)
		{
			if(this.usines.get(i).getType().equals("usine-matiere"))
				usines.add(this.usines.get(i));
		}
		
		List<ProductionItem> produits = new ArrayList<ProductionItem>();
		for(int i = 0; i< usines.size(); i++)
		{
			var produit = usines.get(i).faitProduit();
			int[] position2 = null;
			for(int j = 0; j < this.chemins.size(); j++)
			{
				var currentIndex = j;
				if(this.chemins.get(j).getDe() == usines.get(i).getId())
				{
					
					var usine2 = this.usines.stream()
											.filter(u -> u.getId() == this.chemins.get(currentIndex).getVers())
											.findFirst().get();

					position2 = usine2.getPosition();
				}
			}
			
			int xTranslate;
			if(usines.get(i).getPosition()[0] < position2[0]) 
			{
				xTranslate = 1;
			}
			else if (usines.get(i).getPosition()[0]>position2[0])
			{
				xTranslate = -1;
			}
			else
			{
				xTranslate = 0;
			}
			
			int yTranslate;
			if(usines.get(i).getPosition()[1] < position2[1]) 
			{
				yTranslate = 1;
			}
			else if (usines.get(i).getPosition()[1]>position2[1])
			{
				yTranslate = -1;
			}
			else
			{
				yTranslate = 0;
			}
			produit.setPosition(usines.get(i).getPosition());
			produit.setVitesse(new Point(xTranslate, yTranslate));
			produits.add(produit);
		}
		
		this.productionItems = produits;
		
	}
	
	public List<Usine> createInstances(List<String> metadonneesD, List<String> simulationD) {
		// chercher les indexes des usines dans le tableau(sim)
		List<Integer> indexesSim = new ArrayList<Integer>();
		for (int i = 0; i < simulationD.size(); i++) 
		{
			if (simulationD.get(i) == "usine")
				indexesSim.add(i);

		}

		// chercher les indexes des usines dans le tableau(meta)
		List<Integer> indexesMeta = new ArrayList<Integer>();
		for (int i = 0; i < metadonneesD.size(); i++) 
		{
			if (metadonneesD.get(i) == "usine")
				indexesMeta.add(i);

		}
		
		List<Usine> usines = new LinkedList<Usine>();
		
		// Instancier les classes et associer les parametres correspondants
		for (int j = 0; j < indexesSim.size(); j++) {
			List<String> parameters = new LinkedList<String>();
			for (int w = indexesSim.get(j) + 1; w < indexesSim.get(j) + 1 + numberOfUsineSimulationElements; w++) {
				// 1
				if (simulationD.get(w).contains("type:"))
					parameters.add(simulationD.get(w).replace("type:", ""));
				// 0
				else if (simulationD.get(w).contains("id:"))
					parameters.add(simulationD.get(w).replace("id:", ""));
				// 2
				else if (simulationD.get(w).contains("x:"))
					parameters.add(simulationD.get(w).replace("x:", ""));
				// 3
				else if (simulationD.get(w).contains("y:"))
					parameters.add(simulationD.get(w).replace("y:", ""));
				else
					throw new NullPointerException("pas d'information a traite");
			}

			
			
			if (parameters.get(1).equals("usine-matiere"))
			{
				var extremeValues = getExtremeIndexesValues(metadonneesD.indexOf("type:" + parameters.get(1)), indexesMeta);
				int[] position = { Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)) };
				List<Icone> icones = getUsinesIcones(metadonneesD, parameters.get(1));
				var sortieAndInterval = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				
				Usine usineMatiere = new UsineMatiere(Integer.parseInt(parameters.get(0)), position, parameters.get(1), icones, 
													  sortieAndInterval[0], Integer.parseInt(sortieAndInterval[1]));
				usines.add(usineMatiere);
			}
			else if(parameters.get(1).equals("usine-aile"))
			{

				var extremeValues = getExtremeIndexesValues(metadonneesD.indexOf("type:" + parameters.get(1)), indexesMeta);
				int[] position = { Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)) };
				List<Icone> icones = getUsinesIcones(metadonneesD, parameters.get(1));
				var sortieIntervalEntree = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				ProductionItem entree = new ProductionItem(sortieIntervalEntree[3], Integer.parseInt(sortieIntervalEntree[2]));
				List<ProductionItem> entrees = new LinkedList<ProductionItem>();
				entrees.add(entree);
				
				Usine usineAile = new UsineAile(Integer.parseInt(parameters.get(0)), position, parameters.get(1), icones,
												sortieIntervalEntree[0], entrees, Integer.parseInt(sortieIntervalEntree[2]));
				usines.add(usineAile);
			}
			else if(parameters.get(1).equals("usine-assemblage"))
			{
				var extremeValues = getExtremeIndexesValues(metadonneesD.indexOf("type:" + parameters.get(1)), indexesMeta);
				int[] position = { Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)) };
				List<Icone> icones = getUsinesIcones(metadonneesD, parameters.get(1));
				var sortieIntervalEntree = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				ProductionItem entree = new ProductionItem(sortieIntervalEntree[3], Integer.parseInt(sortieIntervalEntree[2]));
				ProductionItem entree2 = new ProductionItem(sortieIntervalEntree[5], Integer.parseInt(sortieIntervalEntree[4]));
				List<ProductionItem> entrees = new LinkedList<ProductionItem>();
				entrees.add(entree);
				entrees.add(entree2);
				
				Usine usineAssemblage = new UsineAssemblage(Integer.parseInt(parameters.get(0)), position, parameters.get(1), icones,
															sortieIntervalEntree[0], entrees, Integer.parseInt(sortieIntervalEntree[2]));
				usines.add(usineAssemblage);
			}
			else if(parameters.get(1).equals("usine-moteur"))
			{
				var extremeValues = getExtremeIndexesValues(metadonneesD.indexOf("type:" + parameters.get(1)), indexesMeta);
				int[] position = { Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)) };
				List<Icone> icones = getUsinesIcones(metadonneesD, parameters.get(1));
				var sortieIntervalEntree = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				ProductionItem entree = new ProductionItem(sortieIntervalEntree[3], Integer.parseInt(sortieIntervalEntree[2]));
				List<ProductionItem> entrees = new LinkedList<ProductionItem>();
				entrees.add(entree);
				
				Usine usineMoteur = new UsineMoteur(Integer.parseInt(parameters.get(0)), position, parameters.get(1), icones,
													sortieIntervalEntree[0], entrees, Integer.parseInt(sortieIntervalEntree[2]));
				usines.add(usineMoteur);
			}
			else if(parameters.get(1).equals("entrepot"))
			{
				var extremeValues = getExtremeIndexesValues(metadonneesD.indexOf("type:" + parameters.get(1)), indexesMeta);
				int[] position = { Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)) };
				List<Icone> icones = getUsinesIcones(metadonneesD, parameters.get(1));
				var sortieIntervalEntree = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				ProductionItem entree = new ProductionItem(sortieIntervalEntree[3], Integer.parseInt(sortieIntervalEntree[2]));
				List<ProductionItem> entrees = new LinkedList<ProductionItem>();
				entrees.add(entree);
				
				Usine entrepot = new Entrepot(Integer.parseInt(parameters.get(0)), position, parameters.get(1), icones, entrees);
				usines.add(entrepot);
				
			}

		}
		this.chemins = getChemins(this.simulationD);
		this.usines = usines;
		return usines;
	}
	
	/**
	 * parcours la liste de simulation xml et prend les informations des chemins
	 * @param simulationD
	 * @return
	 */
	public List<Chemin> getChemins(List<String> simulationD)
	{
		List<Chemin> chemins = new ArrayList<Chemin>();
		simulationD = new ArrayList<String>();
		simulationD.addAll(this.simulationD);
		List<String> cheminsEnString = 
				simulationD.stream()
						   .filter(sim -> sim.contains("chemin") || sim.contains("de:") || sim.contains("vers:"))
						   .collect(Collectors.toList());
		
		for(int i = 0; i< cheminsEnString.size();i++)
		{
			if(cheminsEnString.get(i).equals("chemin"))
			{
				int de = Integer.parseInt(cheminsEnString.get(i+1).replace("de:", ""));
				int vers = Integer.parseInt(cheminsEnString.get(i+2).replace("vers:", ""));
				Chemin chemin = new Chemin(de, vers);
				chemins.add(chemin);
			}
		}
		this.chemins = chemins;
		return chemins;
	}
	
	/**
	 * prend les valeurs extremes des sous listes de chaque usine
	 * exemple:
	 * @param indexe
	 * @param indexes
	 * @return
	 */
	private int[] getExtremeIndexesValues(int indexe, List<Integer> indexes)
	{
		int[] extremeValues = new int[2];
		for(int i = 0; i< indexes.size();i++)
		{
			if(indexe - indexes.get(i).intValue() > 0)
			{
				extremeValues[0] = indexes.get(i).intValue();
				
			}
		}
		
		for(int i = indexes.size() - 1; i>0 ;i--)
		{
			
			if(indexes.get(i).intValue() - indexe > 0)
			{
				extremeValues[1] = indexes.get(i).intValue();
			}
			
		}
		
		if(extremeValues[1] < extremeValues[0])
		{
			extremeValues[1] = this.metadonneesD.size() - 1;
		}
		
		return extremeValues;
		
	}
	

	/**
	 * prend les icones des usines
	 * @param metadonneesD
	 * @param type
	 * @return
	 */
	private List<Icone> getUsinesIcones(List<String> metadonneesD, String type)
	{
		List<Icone> icones = new LinkedList<Icone>();
		
		// chercher les indexes des usines dans le tableau
		for(int k = 0; k< metadonneesD.size(); k++)
		{
			var metaType = metadonneesD.get(k).replace("type:", "");
			if(metaType.equals(type))
			{
				for(int i = k+1; i< k + 11 ; i++ )
				{
					if(metadonneesD.get(i).equals("icone")) 
					{
						String iconeType = "" ;
						String path = "";
						for(int j = i; j< i+3;j++)
						{
							if(metadonneesD.get(j).contains("type:"))
							{
								iconeType = metadonneesD.get(j).replace("type:","");
							}
							else if(metadonneesD.get(j).contains("path:"))
							{
								path = metadonneesD.get(j).replace("path:","");
							}
						}
						Icone icone = new Icone(iconeType, path);
						icones.add(icone);
					}
					
				}
			}
		}
		return icones;
	}
	
	/**
	 * prend les parametre sortie, interval et entrees des metadonnees
	 * results:
	 * 		
	 * 		0:type
	 * 		1:interval-production
	 * 		2:quantite
	 * 		3:type
	 * 
	 * @param metadonneesD
	 * @param usineIndex
	 * @param nextUsineIndex
	 * @return
	 */
	private String[] getSortieIntervalEntree(List<String> metadonneesD, int usineIndex, int nextUsineIndex)
	{
		String[] results = new String[6];
		for(int i=0; i<results.length;i++)
		{
			results[i] = "";
		}
		
		for(int i = usineIndex; i< usineIndex + (nextUsineIndex - usineIndex); i++)
		{
			if(metadonneesD.get(i).contains("sortie"))
				results[0] = metadonneesD.get(i + 1).replace("type:", "");
			else if(metadonneesD.get(i).contains("interval-production:"))
				results[1] = metadonneesD.get(i).replace("interval-production:", "");
			else if(metadonneesD.get(i).contains("entree") && results[2] == "" && results[3] =="")
			{
				results[2] = metadonneesD.get(i+1).substring(metadonneesD.get(i+1).indexOf(":")+1);
				results[3] = metadonneesD.get(i+2).substring(metadonneesD.get(i+2).indexOf(":")+1);
			}
			else if (metadonneesD.get(i).contains("entree") && results[2] != "" && results[3] != "")
			{
				results[4] = metadonneesD.get(i+1).substring(metadonneesD.get(i+1).indexOf(":")+1);
				results[5] = metadonneesD.get(i+2).substring(metadonneesD.get(i+2).indexOf(":")+1);
			}
		}
		return results;
	}
	


	public List<String> getMetadonneesD() {
		return metadonneesD;
	}


	public List<String> getSimulationD() {
		return simulationD;
	}



	public List<Usine> getUsines() {
		return this.usines;
	}


	public List<Chemin> getChemins() {
		return chemins;
	}


	public List<ProductionItem> getProductionItems() {
		return productionItems;
	}

}
