package dataForSimulation;

import java.awt.Image;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import dataForSimulation.UsineIntermediaires.UsineAile;
import dataForSimulation.UsineIntermediaires.UsineAssemblage;
import dataForSimulation.UsineIntermediaires.UsineMoteur;
import simulation.RefreshManager;
import strategyPattern.IntervalSell;
import strategyPattern.RandomProbabilitySell;

public class Network {

	private static final int numberOfUsineSimulationElements = 4;
	
	private List<String> metadonneesD;
	private List<String> simulationD;
	private List<Usine> usines;
	private List<Chemin> chemins;
	private List<ProductionItem> productionItems;
	private List<Point> usinesPositions;
	private List<Image> usinesImages;
	private List<Image> produitsImages;
	private List<Point> produitsVitesses;
	private List<Point> produitsPositions;
	private RefreshManager _refreshManager;
	private Entrepot IObservableEntrepot;

	public Network(List<String> metadonneesD, List<String> simulationD)
	{
		this.metadonneesD = metadonneesD;
		this.simulationD = simulationD;
		this.productionItems = new ArrayList<ProductionItem>();
		this.usinesPositions = new ArrayList<Point>();
		this.usinesImages = new ArrayList<Image>();
		this.produitsImages = new ArrayList<Image>();
		this.produitsVitesses = new ArrayList<Point>();
		this.produitsPositions = new ArrayList<Point>();
		this.IObservableEntrepot = null;
		
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
				int[] extremeValues = getExtremeIndexesValues(metadonneesD.indexOf("type:" + parameters.get(1)), indexesMeta);
				Point position = new Point( Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)) );
				List<Image> images = getUsinesImages(metadonneesD, parameters.get(1));
				String[] sortieAndInterval = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				
				Usine usineMatiere = new UsineMatiere(Integer.parseInt(parameters.get(0)), position, parameters.get(1), images, 
													  sortieAndInterval[0], Integer.parseInt(sortieAndInterval[1]));
				
				usineMatiere.setCurrentImage(usineMatiere.getImageByType("vide"));
				usines.add(usineMatiere);
			}
			else if(parameters.get(1).equals("usine-aile"))
			{

				int[] extremeValues = getExtremeIndexesValues(metadonneesD.indexOf("type:" + parameters.get(1)), indexesMeta);
				Point position = new Point( Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)) );
				List<Image> images = getUsinesImages(metadonneesD, parameters.get(1));
				String[] sortieIntervalEntree = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				ProductionItem entree = new ProductionItem(sortieIntervalEntree[3], Integer.parseInt(sortieIntervalEntree[2]));
				List<ProductionItem> entrees = new LinkedList<ProductionItem>();
				entrees.add(entree);
				
				Usine usineAile = new UsineAile(Integer.parseInt(parameters.get(0)), position, parameters.get(1), images,
												sortieIntervalEntree[0], entrees, Integer.parseInt(sortieIntervalEntree[1]));
				usines.add(usineAile);
			}
			else if(parameters.get(1).equals("usine-assemblage"))
			{
				int[]  extremeValues = getExtremeIndexesValues(metadonneesD.indexOf("type:" + parameters.get(1)), indexesMeta);
				Point position = new Point( Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)) );
				List<Image> images = getUsinesImages(metadonneesD, parameters.get(1));
				String[] sortieIntervalEntree = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				ProductionItem entree = new ProductionItem(sortieIntervalEntree[3], Integer.parseInt(sortieIntervalEntree[2]));
				ProductionItem entree2 = new ProductionItem(sortieIntervalEntree[5], Integer.parseInt(sortieIntervalEntree[4]));
				List<ProductionItem> entrees = new LinkedList<ProductionItem>();
				entrees.add(entree);
				entrees.add(entree2);
				
				Usine usineAssemblage = new UsineAssemblage(Integer.parseInt(parameters.get(0)), position, parameters.get(1), images,
															sortieIntervalEntree[0], entrees, Integer.parseInt(sortieIntervalEntree[1]));
				usines.add(usineAssemblage);
			}
			else if(parameters.get(1).equals("usine-moteur"))
			{
				int[] extremeValues = getExtremeIndexesValues(metadonneesD.indexOf("type:" + parameters.get(1)), indexesMeta);
				Point position = new Point( Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)) );
				List<Image> images = getUsinesImages(metadonneesD, parameters.get(1));
				String[] sortieIntervalEntree = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				ProductionItem entree = new ProductionItem(sortieIntervalEntree[3], Integer.parseInt(sortieIntervalEntree[2]));
				List<ProductionItem> entrees = new LinkedList<ProductionItem>();
				entrees.add(entree);
				
				Usine usineMoteur = new UsineMoteur(Integer.parseInt(parameters.get(0)), position, parameters.get(1), images,
													sortieIntervalEntree[0], entrees, Integer.parseInt(sortieIntervalEntree[1]));
				usines.add(usineMoteur);
			}
			else if(parameters.get(1).equals("entrepot"))
			{
				int[] extremeValues = getExtremeIndexesValues(metadonneesD.indexOf("type:" + parameters.get(1)), indexesMeta);
				Point position = new Point( Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)) );
				List<Image> images = getUsinesImages(metadonneesD, parameters.get(1));
				String[] sortieIntervalEntree = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				ProductionItem entree = new ProductionItem(sortieIntervalEntree[3], Integer.parseInt(sortieIntervalEntree[2]));
				List<ProductionItem> entrees = new LinkedList<ProductionItem>();
				entrees.add(entree);
				RandomProbabilitySell randomSell = new RandomProbabilitySell();
				IntervalSell intervalSell = new IntervalSell();
				
				
				Usine entrepot = new Entrepot(Integer.parseInt(parameters.get(0)), position, parameters.get(1), images, entrees,
											  intervalSell, randomSell);
				this.IObservableEntrepot = (Entrepot)entrepot;
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
	private List<Image> getUsinesImages(List<String> metadonneesD, String type)
	{
		List<Image> images = new LinkedList<Image>();
		
		// chercher les indexes des usines dans le tableau
		for(int k = 0; k< metadonneesD.size(); k++)
		{
			String metaType = metadonneesD.get(k).replace("type:", "");
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

						Image image = null;
						try 
						{
							image = ImageIO.read(new File(path));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						images.add(image);
					}
					
				}
			}
		}
		return images;
	}
	
	public List<Image> getCurrentImages()
	{
		List<Image> usinesImages = new ArrayList<Image>();
		for(int i = 0; i < this.usines.size(); i++)
		{
			Usine usine = this.usines.get(i);
			Image img = usine.getCurrentImage();
			usinesImages.add(img);
		}
		return usinesImages;	
	}
	
	public List<Point> getCurrentPositions()
	{
		List<Point> positions = new ArrayList<Point>();
		for(int i = 0; i < this.usines.size(); i++)
		{
			Usine usine = this.usines.get(i);
			Point position = usine.getPosition();
			positions.add(position);
		}
		return positions;	
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


	public void refresh(int currentTime) 
	{
		if(this.usines == null || this._refreshManager == null) return;
		
		for(int i = 0; i < this.usines.size(); i++)
		{
			this._refreshManager.changeImages(currentTime, this.usines.get(i));
			this.usinesImages = this.getCurrentImages();
			this._refreshManager.handleCollisions();
			ProductionItem produit = this._refreshManager.createProduct(currentTime, this.usines.get(i), this.getChemins());
			if(produit == null) continue;
			
			this.productionItems.add(produit);
			try 
			{
				this.produitsImages.add(ImageIO.read(new File(produit.getImagePath())));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			this.produitsPositions.add(produit.getPosition());
			this.produitsVitesses.add(produit.getVitesse());
			
		}

	}


	public List<Image> getUsinesImages() {
		return usinesImages;
	}


	public List<Image> getProduitsImages() {
		return produitsImages;
	}


	public List<Point> getProduitsVitesses() {
		return produitsVitesses;
	}


	public List<Point> getProduitsPositions() {
		return produitsPositions;
	}


	public void createRefreshManager() {
		this._refreshManager = new RefreshManager(this.getUsines(), this.productionItems, this.produitsImages, this.produitsPositions, this.produitsVitesses);
		
	}


	public void createObserverPattern(int strategie) {
		
		Entrepot entrepot = (Entrepot)this.usines.stream()
				.filter(u -> u.getType().equals("entrepot")).findFirst().get();
		if(entrepot == null) return;
		
		List<Usine> usinesWithoutEntrepot = this.usines.stream().filter(u->!u.getType().equals("entrepot")).collect(Collectors.toList());
		for(int i =0; i< usinesWithoutEntrepot.size();i++)
		{
			entrepot.registerUsine(usinesWithoutEntrepot.get(i));
		}
		

		for(int i = 0;i<this.usines.size(); i++)
		{
			Usine usine = this.usines.get(i);
			
			if(usine.getType().equals("usine-matiere"))
			{
				((UsineMatiere) usine).setEntrepot(entrepot);
			}
			else if (usine.getType().equals("usine-aile"))
			{
				((UsineAile) usine).setEntrepot(entrepot);
			}
			else if (usine.getType().equals("usine-assemblage"))
			{
				((UsineAssemblage) usine).setEntrepot(entrepot);
			}
			else if (usine.getType().equals("usine-moteur"))
			{
				((UsineMoteur)usine).setEntrepot(entrepot);
			}
		}
		
		entrepot.setStrategy(strategie);
		
	}	
}
