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
		for(int i=0; i<Constants.NUMBEROFBUILDINGS; i++) genes.add(Constants.GETANONALREADYCHOSENNUMBER(genes));
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
		int firstMutationPlace = (int) (Math.random()*genes.size());
		int secondMutationPlace = (int) (Math.random()*genes.size());
		while(firstMutationPlace == secondMutationPlace) secondMutationPlace = (int) (Math.random()*genes.size());
		int i = genes.remove(firstMutationPlace);
		genes.add(genes.remove(secondMutationPlace), firstMutationPlace);
		genes.add(i, secondMutationPlace);
	}
	
	private void calcScore() {
		String[] namesOrder = new String[genes.size()];
		for(int i=0; i<genes.size(); i++) {
			namesOrder[i] = genes.get(i).toString();
		}
		score = 1/Constants.THEGRAPHTEST.calcTotalValue(namesOrder); //Higher the value of the way is, the lower his score became
	}
}
