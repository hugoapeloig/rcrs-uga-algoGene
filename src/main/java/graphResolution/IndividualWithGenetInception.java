package graphResolution;

import java.util.ArrayList;

public class IndividualWithGenetInception {
	//One individual has a list of genes, for each genes we find the best trail 
	
	private int SIZEOFTHESUBPOPULATION = 100; //We will test with a population of 100
	private int NUMBEROFSUBGENERATION = 1000;
	
	private ArrayList<ArrayList<Integer>> listOfGenes = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	private double scoreTotal;
	
	public IndividualWithGenetInception() {
		this(1); //Without n given, this one is taken as 1
	}
	
	public IndividualWithGenetInception(int n) {
		for(int i=0; i<n; i++) listOfGenes.add(new ArrayList<Integer>());
		ArrayList<Integer> total = new ArrayList<Integer>();
		for(int i=1; i<Constants.NUMBEROFBUILDINGS; i++) total.add(Constants.GETANONALREADYCHOOSENNUMBER(total));
		for(ArrayList<Integer> gene : listOfGenes) gene.add(0); //For each of them the start is 0
		int geneNumber=0;
		for(int i=0; i<total.size(); i++) {
			if(i!=0 && n!=1 && i%(Constants.NUMBEROFBUILDINGS/n)==0 && geneNumber<n-1) geneNumber++; //If the rest is'nt 0 : the last gene will be longer than the others
			listOfGenes.get(geneNumber).add(total.get(i));
		}
//		System.out.println("Constructor basic : "+listOfGenes);
//		System.out.println(scores);
		calcGenesAndScores();
	}
	
	public IndividualWithGenetInception(IndividualWithGenetInception clone) {
		this.listOfGenes=clone.listOfGenes;
		this.scores=clone.scores;
		this.scoreTotal=clone.scoreTotal;
	}
	
	public IndividualWithGenetInception(ArrayList<ArrayList<Integer>> listOfGenes) {
//		System.out.println("Constructor with listOfList : "+listOfGenes);
		this.listOfGenes=listOfGenes;
		calcGenesAndScores();
	}
	
	public ArrayList<ArrayList<Integer>> getListOfGenes(){
		return listOfGenes;
	}
	
	public ArrayList<Integer> getScores(){
		return scores;
	}
	
	public double getScoreTotal() {
		return scoreTotal;
	}
	
	public String toString() {
		String s = new String();
		for(int i=0; i<listOfGenes.size(); i++) {
			s += "Le parcours "+(i+1)+" : ";
			for(int j : listOfGenes.get(i)) s+= j+";";
			s += " et son score est : "+scores.get(i)+"\n";
		}
		return s;
	}
	
	private void calcGenesAndScores() {
		for(ArrayList<Integer> gene : listOfGenes) gene = calcTheBestForOneGene(gene);
		calcScores();
	}
	
	private void calcScores() {
		String[][] namesOrders = new String[listOfGenes.size()][];
		for(int i=0; i<listOfGenes.size(); i++) {
			ArrayList<Integer> gene = listOfGenes.get(i);
			String[] namesOrder = new String[gene.size()];
			for(int j=0; j<gene.size(); j++) namesOrder[j] = ""+gene.get(j); 
			namesOrders[i] = namesOrder;
		}
		
		try {
			for(String[] namesOrder : namesOrders) {
				int score = Constants.THEGRAPHTEST.calcTotalValue(namesOrder);
				scores.add(score);
			}
			scoreTotal = -1;
			for(int score : scores) if(score>scoreTotal) scoreTotal = score;
			scoreTotal = 1/scoreTotal;
		}catch(RuntimeException r) {
			scoreTotal=0;
//			System.err.println(r.getMessage());
		}
		//We check if there is double inside the teams trails
		boolean inDouble = false; 
		for(ArrayList<Integer> gene : listOfGenes) {
			for(int i=0; i<gene.size(); i++) for(int j=0; j<gene.size(); j++) if(i!=j && gene.get(i)==gene.get(j)) inDouble = true;
		}		
		
		//Now we take care about all the nodes being passed
		ArrayList<Integer> allNodes = new ArrayList<Integer>();
		for(int i=0; i<Constants.NUMBEROFBUILDINGS; i++) allNodes.add(i);
		boolean notAllIn = false;
		for(int i : allNodes) {
			boolean in = false;
			for(ArrayList<Integer> gene : listOfGenes) for(int j : gene) if(i==j) in=true;
			if(!in) {
				notAllIn = true;
				break;
			}
		}
				
		if(inDouble || notAllIn) scoreTotal=0;
	
	}
	
	private ArrayList<Integer> calcTheBestForOneGene(ArrayList<Integer> gene) {
		ArrayList<ArrayList<Integer>> possibilities = new ArrayList<ArrayList<Integer>>(); 
		for(int i=0; i<SIZEOFTHESUBPOPULATION; i++) possibilities.add(new ArrayList<Integer>());
		ArrayList<Individual> subIndividuals = new ArrayList<Individual>();
		for(ArrayList<Integer> possibility : possibilities) {
			possibility.add(0); //They all start with 0
			for(int i=1; i<gene.size(); i++) possibility.add(getANonAlreadyChoosenNumber(gene, possibility));
			subIndividuals.add(new Individual(possibility));
		}
		SubPopulation subPopulation = new SubPopulation(subIndividuals); 
		subPopulation.start(NUMBEROFSUBGENERATION);
		return subPopulation.getTheBest().getGenes();
	}
	
	private  int getANonAlreadyChoosenNumber(ArrayList<Integer> listToTake,ArrayList<Integer> listToAdd) {
		boolean in = false;
		int thePlace = (int) (Math.random()*listToTake.size()-1)+1; //0 can't be taken
		int theNumber = listToTake.get(thePlace);
		for(Integer i : listToAdd) if (theNumber == i) in=true;
		while(in) {
			in=false;
			thePlace = (int) (Math.random()*listToTake.size()-1)+1;
			theNumber = listToTake.get(thePlace);
			for(Integer i : listToAdd) if (theNumber == i) in=true;
		}
		return theNumber;
	}
	
}
