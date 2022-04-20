package graphResolution;

import java.util.ArrayList;

public class IndividualWithNGenes {
	
	private ArrayList<ArrayList<Integer>> listOfGenes = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Integer> scores = new ArrayList<Integer>();
	private double scoreTotal;
	
	public IndividualWithNGenes() {
		this(1); //Without n given, this one is taken as 1
	}
	
	public IndividualWithNGenes(int n) {
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
		calcScores();
	}
	
	public IndividualWithNGenes(IndividualWithNGenes clone) {
		this.listOfGenes=clone.listOfGenes;
		this.scores=clone.scores;
		this.scoreTotal=clone.scoreTotal;
	}
	
	public IndividualWithNGenes(ArrayList<ArrayList<Integer>> listOfGenes) {
//		System.out.println("Constructor with listOfList : "+listOfGenes);
		this.listOfGenes=listOfGenes;
		calcScores();
	}
	
	public ArrayList<ArrayList<Integer>> getAllGenes(){
		return listOfGenes;
	}
	
	public ArrayList<Integer> getGeneN(int n){
		if(n>listOfGenes.size()) throw new RuntimeException("Error : n is superior to the number of genes");
		return listOfGenes.get(n);
	}
	
	public ArrayList<Integer> getScores(){
		return scores;
	}
	
	public double getScoreTotal() {
		return scoreTotal;
	}
	
	public ArrayList<Integer> getFirstPartgenes(int geneNumber, int separator){
		if(geneNumber>listOfGenes.size()) throw new RuntimeException("The geneNumber is superior to the number of genes");
		ArrayList<Integer> genes;
		genes=listOfGenes.get(geneNumber);
		if(separator>genes.size()) throw new RuntimeException("The separator calcul is wrong !");
		ArrayList<Integer> firstPartGenes = new ArrayList<Integer>();
		for(int i=0; i<separator; i++) firstPartGenes.add(genes.get(i));
		return firstPartGenes;
	}
	
	public ArrayList<Integer> getLastPartgenes(int geneNumber, int separator){
		if(geneNumber>listOfGenes.size()) throw new RuntimeException("The geneNumber is superior to the number of genes");
		ArrayList<Integer> genes;
		genes=listOfGenes.get(geneNumber);
		if(separator>genes.size()) throw new RuntimeException("The separator calcul is wrong !");
		ArrayList<Integer> lastPartGenes = new ArrayList<Integer>();
		for(int i=separator; i<genes.size(); i++) lastPartGenes.add(genes.get(i));
		return lastPartGenes;
	}
	
	public void mutate() {
		//In this case, a mutation is the switching of two nodes between two genes (at the same position)
		//Not the first place wich is zero for the all of them
		int mutationPlace = (int) (Math.random()*(listOfGenes.get(0).size()-1))+1; //On the first because he is less longer or equals to the last
		if(mutationPlace==8) mutationPlace--; //It need to be solve an other way
		int firstGenePlace = (int) (Math.random()*listOfGenes.size());
		int secondGenePlace = (int) (Math.random()*listOfGenes.size());
		while(secondGenePlace == firstGenePlace) secondGenePlace = (int) (Math.random()*listOfGenes.size());
		ArrayList<Integer> firstGene = listOfGenes.get(firstGenePlace);
		ArrayList<Integer> secondGene = listOfGenes.get(secondGenePlace);
		int mut1 = firstGene.remove(mutationPlace);
		int mut2 = secondGene.remove(mutationPlace);
		if(mutationPlace >= firstGene.size()) firstGene.add(mut2);
		else firstGene.add(mutationPlace, mut2);
		if(mutationPlace >= secondGene.size()) secondGene.add(mut1);
		else secondGene.add(mutationPlace, mut1);
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
		
		//Now we check if every nodes are past 
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
}
