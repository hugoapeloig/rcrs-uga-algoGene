package graphResolution;

import java.util.ArrayList;

public class IndividualWithGenetInception {
	//One individual has a list of genes, for each genes we find the best trail 
	
	private int SIZEOFTHESUBPOPULATION = 100;
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
			if(i!=0 && n!=1 && i%(n-1)==0 && geneNumber<n-1) geneNumber++; //If the rest is'nt 0 : the last gene will be longer than the others
			listOfGenes.get(geneNumber).add(total.get(i));
		}
//		System.out.println("Constructor basic : "+listOfGenes);
//		System.out.println(scores);
		calcScores();
	}
	
	public IndividualWithGenetInception(IndividualWithGenetInception clone) {
		this.listOfGenes=clone.listOfGenes;
		this.scores=clone.scores;
		this.scoreTotal=clone.scoreTotal;
	}
	
	public IndividualWithGenetInception(ArrayList<ArrayList<Integer>> listOfGenes) {
//		System.out.println("Constructor with listOfList : "+listOfGenes);
		this.listOfGenes=listOfGenes;
		calcScores();
	}
	
	private void calcScores() {
		
	}
	
	private int calcTheBestForOneGene(int geneNumber) {
		if(geneNumber>listOfGenes.size()) throw new RuntimeException("GeneNumber is superior at listOfGenes.size()");
		int score = 0;
		ArrayList<Integer> gene = listOfGenes.get(geneNumber);
		ArrayList<ArrayList<Integer>> possibilities = new ArrayList<ArrayList<Integer>>(); //We will test with a population of 100
		for(int i=0; i<SIZEOFTHESUBPOPULATION; i++) possibilities.add(new ArrayList<Integer>());
		for(ArrayList<Integer> possibility : possibilities) possibility.add(0); //They all start with 0
		return score;
	}
}
