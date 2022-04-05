package graphResolution;

import java.util.ArrayList;

public class Individual {
	//An individual is one road possible it's encoding as a List of number wich represent each one point of the road 
	//Each point as a define number between 1 and the number of buildings 
	//The score is a double between 0 and 1 (1 is the perfect road and impossible
	
	//For now the size of an individual genes is the number of building but later it will have variation 
	//wich will depend of the number of team
	
	private ArrayList<Integer> genes = new ArrayList<Integer>();
	private double score;
	
	public Individual() {
		genes.add(0); //The start is always 0 
		for(int i=1; i<Constants.NUMBEROFBUILDINGS; i++) genes.add(Constants.GETANONALREADYCHOOSENNUMBER(genes));
		calcScore();
	}
	
	public Individual(Individual clone) {
		this.genes = clone.genes;
		this.score = clone.score;
	}
	
	public Individual(ArrayList<Integer> genes) {
		this.genes = genes;
		calcScore();
	}
	
	public ArrayList<Integer> getGenes(){
		return genes;
	}
	
	public double getScore() {
		return score;
	}
	
	public ArrayList<Integer> getFirstPartgenes(int separator){
		if(separator>genes.size()) throw new RuntimeException("The separator calcul is wrong !");
		ArrayList<Integer> firstPartGenes = new ArrayList<Integer>();
		for(int i=0; i<separator; i++) firstPartGenes.add(genes.get(i));
		return firstPartGenes;
	}
	
	public ArrayList<Integer> getLastPartgenes(int separator){
		if(separator>genes.size()) throw new RuntimeException("The separator calcul is wrong !");
		ArrayList<Integer> lastPartGenes = new ArrayList<Integer>();
		for(int i=separator; i<genes.size(); i++) lastPartGenes.add(genes.get(i));
		return lastPartGenes;
	}
	
	public void mutate() {
		//The mutation can't appears at the first point because he is the starting point
		int firstMutationPlace = (int) (Math.random()*(genes.size()-1))+1;
		int secondMutationPlace = (int) (Math.random()*(genes.size()-1))+1;
		while(firstMutationPlace == secondMutationPlace) secondMutationPlace = (int) (Math.random()*genes.size());
		int i = genes.get(firstMutationPlace);
		int j = genes.get(secondMutationPlace);
		genes.remove(firstMutationPlace);
		if(firstMutationPlace==genes.size()) genes.add(j);
		else genes.add(j, firstMutationPlace);
		genes.remove(secondMutationPlace);
		if(secondMutationPlace==genes.size()) genes.add(i);
		else genes.add(i, secondMutationPlace);
	}
	
	public String toString(){
		String s = "Le parcours : ";
		for(int i : genes) s+=""+i+";";
		return s;
	}
	
	private void calcScore() {
		String[] namesOrder = new String[genes.size()];
		for(int i=0; i<genes.size(); i++) {
			namesOrder[i] = genes.get(i).toString();
		}
//		for(int i=0; i<namesOrder.length; i++) System.out.print(namesOrder[i]);
//		System.out.println();
		try{
			int s = Constants.THEGRAPHTEST.calcTotalValue(namesOrder);
			score = (double) 1/s;//Higher the value of the way is, the lower his score became
		}catch(RuntimeException r) {
			score = 0;
		}
		boolean inDouble = false;
		for(int i=0; i<genes.size(); i++) for(int j=0; j<genes.size(); j++) if(i!=j && genes.get(i)==genes.get(j)) inDouble = true;
		if(inDouble) score=0;
	}
}
