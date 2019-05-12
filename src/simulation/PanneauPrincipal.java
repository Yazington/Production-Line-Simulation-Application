package simulation;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.*;
import java.util.stream.Collectors;

import javax.swing.JPanel;

import dataForSimulation.*;
import dataForSimulation.UsineIntermediaires.*;
import observerPattern.IObservable;
import xmlUtility.XMLSourcer;
import observerPattern.IObserver;

public class PanneauPrincipal extends JPanel implements IObserver {

	private static final long serialVersionUID = 1L;
	private static final int numberOfUsineSimulationElements = 4;

//	// Variables temporaires de la demonstration:
	private Point position = new Point(0,0);
	private Point vitesse = new Point(1,1);
	private int taille = 32;
	private List<String> metadonneesD;
	private List<String> simulationD;
	private MenuFenetre menuFenetre;
	private List<Usine> usines;
	private List<Point> pointsUsine;
	private List<Chemin> chemins;

	public PanneauPrincipal(MenuFenetre menuFenetre) {
		super();
		this.menuFenetre = menuFenetre;
	}

//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		// On ajoute a la position le delta x et y de la vitesse
//		position.translate(vitesse.x, vitesse.y);
//		g.fillRect(position.x, position.y, taille, taille);
//		
//	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		if(this.pointsUsine != null) 
		{
			for(var usinePosition: this.pointsUsine)
			{
//				usinePosition.x = 
//				this.add(usinePosition)
				g2d.fillRect(usinePosition.x, usinePosition.y, taille, taille);
				
				
			}
		}
		
//		if(this.usines != null)
//		{
//			for(var usine:this.usines)
//			{
//				usine.setIcon(icon);
//			}
//		}
		
		if(this.chemins != null)
		{
			for(var cheminID: this.chemins)
			{
				Usine usine1 = 
						this.usines.stream()
								   .filter(u-> u.getId() == cheminID.getDe())
								   .findFirst()
								   .get();
				Usine usine2 = 
						this.usines.stream()
								   .filter(u-> u.getId() == cheminID.getVers())
								   .findFirst()
								   .get();
				g2d.drawLine(usine1.getPosition()[0], usine1.getPosition()[1], usine2.getPosition()[0], usine2.getPosition()[1]);
				
			}
		}

	}

	public void createNetwork(XMLSourcer xmlInfo) {
		this.metadonneesD = xmlInfo.getMetaList();
		this.simulationD = xmlInfo.getSimList();

		List<Point> pointsUsine = new ArrayList<Point>();
		List<Integer> pointsChemins = new ArrayList<Integer>();
		
		// Dessiner usines
		var usines = createInstances(this.metadonneesD, this.simulationD);
		this.usines = usines;
		for(var usine : usines)
		{
			Point point = new Point(usine.getPosition()[0], usine.getPosition()[1]);
			pointsUsine.add(point);
		}
		this.pointsUsine = pointsUsine;
		
		// Dessiner chemins
		var chemins = getChemins(this.simulationD);
		this.chemins = chemins;
		
		Graphics g = this.getGraphics();
		paintComponent(g);
		
		
	}

	private List<Usine> createInstances(List<String> metadonneesD, List<String> simulationD) {
		// chercher les indexes des usines dans le tableau
		List<Integer> indexesSim = new ArrayList<Integer>();
		for (int i = 0; i < simulationD.size(); i++) 
		{
			if (simulationD.get(i) == "usine")
				indexesSim.add(i);

		}

		// chercher les indexes des usines dans le tableau
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
				List<Icone> icones = getIcones(metadonneesD, parameters.get(1));
				var sortieAndInterval = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				
				Usine usineMatiere = new UsineMatiere(Integer.parseInt(parameters.get(0)), position, parameters.get(1), icones, 
													  sortieAndInterval[0], Integer.parseInt(sortieAndInterval[1]));
				usines.add(usineMatiere);
			}
			else if(parameters.get(1).equals("usine-aile"))
			{

				var extremeValues = getExtremeIndexesValues(metadonneesD.indexOf("type:" + parameters.get(1)), indexesMeta);
				int[] position = { Integer.parseInt(parameters.get(2)), Integer.parseInt(parameters.get(3)) };
				List<Icone> icones = getIcones(metadonneesD, parameters.get(1));
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
				List<Icone> icones = getIcones(metadonneesD, parameters.get(1));
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
				List<Icone> icones = getIcones(metadonneesD, parameters.get(1));
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
				List<Icone> icones = getIcones(metadonneesD, parameters.get(1));
				var sortieIntervalEntree = getSortieIntervalEntree(metadonneesD, extremeValues[0], extremeValues[1]);
				ProductionItem entree = new ProductionItem(sortieIntervalEntree[3], Integer.parseInt(sortieIntervalEntree[2]));
				List<ProductionItem> entrees = new LinkedList<ProductionItem>();
				entrees.add(entree);
				
				Usine entrepot = new Entrepot(Integer.parseInt(parameters.get(0)), position, parameters.get(1), icones, entrees);
				usines.add(entrepot);
				
			}

		}

		return usines;
	}
	
	private List<Chemin> getChemins(List<String> simulationD)
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
		
		return chemins;
	}
	
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

	private List<Icone> getIcones(List<String> metadonneesD, String type)
	{
		List<Icone> icones = new LinkedList<Icone>();
		
		// chercher les indexes des usines dans le tableau
		for(int k = 0; k< metadonneesD.size(); k++)
		{
			var metaType = metadonneesD.get(k).replace("type:", "");
			if(metaType.equals(type))
			{
				for(int i = k; i< k + 12 ; i++ )
				{
					if(metadonneesD.get(i).equals("icone")) 
					{
						Icone icone = new Icone(metadonneesD.get(k+2).replace("type:", ""), metadonneesD.get(k+1).replace("path:", ""));
						icones.add(icone);
					}
					
				}
			}
		}
		return icones;
	}
	
	/**
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
	


	@Override
	public void UpdateObserver() {
		var xmlSourcer = this.menuFenetre.getXmlSourcer();
		createNetwork(xmlSourcer);
	}

}