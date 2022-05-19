package sampleagent;
import java.util.ArrayList;
import convertisseur_map_to_graph.*;
import graphResolution.*;

public class CentrePreDisaster {
	public class Agent{
		//Will be replace by the super type of the differents agent's types	
		private ArrayList<Integer> path;
		
		public Agent(ArrayList<Integer> path) {
			this.path=path;
		}
	}
	//It's the central object which calculate the bests roads and, then the scenario is launched, will allocate
	//each agent his path
	private Map m;
	private Graph g;
	private int nbPoliceForceAvailable, nbAmbulanceTeamAvailable, nbFireBrigadeAvailable;
	private boolean sameNumberOfAgents; //For each category, true if there is the same number available
	private ArrayList<ArrayList<ArrayList<Integer>>> bestPaths; //If sameNum[...] is true, there is only
	//The bestPaths are : {AmbulancePaths, FirePaths, PolicePaths}
	
	public CentrePreDisaster(Map m) {
		this(m,1,1,1); //If there is no information on the number of agent available, we will make as if there were one of each
	}
	
	public CentrePreDisaster(Map m, int numberOfTeam) {
		//If there is a numberOfTeam given, it means there is the same number of agent available for each category
		this(m,numberOfTeam,numberOfTeam,numberOfTeam);
	}
	
	public CentrePreDisaster(Map m,int nbPFAvailable, int nbATAvailable, int nbFBAvailable){
		this.m=m;
		nbPoliceForceAvailable=nbPFAvailable;
		nbAmbulanceTeamAvailable=nbATAvailable;
		nbFireBrigadeAvailable=nbFBAvailable;
		if(nbPFAvailable==nbATAvailable && nbATAvailable==nbFBAvailable) sameNumberOfAgents=true;
		else sameNumberOfAgents=false;
		
		calcGraph();
		calcBestPaths();
	}
	
	private void calcGraph() {
		Convertisseur c = new Convertisseur(m);
		g=c.getGraph();
	}
	
	public ArrayList<ArrayList<Integer>> getPathsForAmbulance(){
		return bestPaths.get(0);
	}
	public ArrayList<ArrayList<Integer>> getPathsForFireFighter(){
		if(sameNumberOfAgents) return bestPaths.get(0);
		return bestPaths.get(1);
	}
	public ArrayList<ArrayList<Integer>> getPathsForPoliceForce(){
		if(sameNumberOfAgents) return bestPaths.get(0);
		return bestPaths.get(2);
	}
	
	private void calcBestPaths() {
		int numberOfBuildings = g.getNodes().size();
		bestPaths = new ArrayList<ArrayList<ArrayList<Integer>>>();
		if(sameNumberOfAgents) {
			PopulationWithNGenes p = new PopulationWithNGenes(nbAmbulanceTeamAvailable,g); //For now the half separator is undefined here 
			p.start(numberOfBuildings);
			IndividualWithNGenes i = p.getTheBest();
			bestPaths.add(i.getAllGenes());
		}
		else {
			PopulationWithNGenes pAmbulance = new PopulationWithNGenes(nbAmbulanceTeamAvailable, g);
			PopulationWithNGenes pFire = new PopulationWithNGenes(nbFireBrigadeAvailable, g);
			PopulationWithNGenes pPolice = new PopulationWithNGenes(nbPoliceForceAvailable, g);
			pAmbulance.start(numberOfBuildings);
			pFire.start(numberOfBuildings);
			pPolice.start(numberOfBuildings);
			IndividualWithNGenes iAmbulance = pAmbulance.getTheBest();
			IndividualWithNGenes iFire = pFire.getTheBest();
			IndividualWithNGenes iPolice = pPolice.getTheBest();
			bestPaths.add(iAmbulance.getAllGenes());
			bestPaths.add(iFire.getAllGenes());
			bestPaths.add(iPolice.getAllGenes());
		}
		
	}
	
	}
	

}
