package graphResolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class PopulationWithNGenes {
	private ArrayList<IndividualWithNGenes> populationWithNGenes = new ArrayList<IndividualWithNGenes>();
	private boolean halfSeparation;
	private Graph g;
	
	public PopulationWithNGenes() {
		this(1, Constants.THEGRAPHTEST);
	}
	
	public PopulationWithNGenes(int n) {
		this(n, Constants.THEGRAPHTEST);
	}
	
	public PopulationWithNGenes(int n, Graph g) {
		for(int i=0; i<Constants.POPULATIONSIZE; i++) populationWithNGenes.add(new IndividualWithNGenes(n,g));
		halfSeparation = true;
	}
	
	public PopulationWithNGenes(int n, Graph g, boolean halfSeparation) {
		this(n,g);
		this.halfSeparation=halfSeparation;
	}
	
	public PopulationWithNGenes(PopulationWithNGenes previousPopulationWithNGenes) {
		this.populationWithNGenes = previousPopulationWithNGenes.populationWithNGenes;
		this.halfSeparation=previousPopulationWithNGenes.halfSeparation;
		this.g=previousPopulationWithNGenes.g;
	}
	
	public ArrayList<IndividualWithNGenes> getPopulationWithNGenes(){
		return populationWithNGenes;
	}
	
	public IndividualWithNGenes getTheBest() {
		IndividualWithNGenes best = populationWithNGenes.get(0);
		for(IndividualWithNGenes i : populationWithNGenes) if(i.getScoreTotal()>best.getScoreTotal()) best = i;
		return best;
	}
	
	public void start(int numberOfGeneration) {
		for(int i=0; i<numberOfGeneration; i++) oneCycle();
	}
	
	private void oneCycle() {
		reproductionOfTheGeneration();
		naturalSelection();
	}
	
	private void reproductionOfTheGeneration() {
		ArrayList<IndividualWithNGenes> newGeneration = new ArrayList<IndividualWithNGenes>();
		
		for(int i=0; i<populationWithNGenes.size()-1; i++) {
			//All the couples are taking unless the couple : last and first IndividualWithNGenes
			IndividualWithNGenes father = populationWithNGenes.get(i);
			IndividualWithNGenes mother = populationWithNGenes.get(i+1);
			IndividualWithNGenes[] sonAndDaughter = oneCoupleReproduction(father, mother);
			
			//A mutation append if we are in the probability space
			double m = Math.random();
			if(m<Constants.MUTATIONPROBABILITY) sonAndDaughter[0].mutate();

			newGeneration.add(sonAndDaughter[0]);
			newGeneration.add(sonAndDaughter[1]);
		}
		//We make the reproduction of the first and last one
		IndividualWithNGenes father = populationWithNGenes.get(0);
		IndividualWithNGenes mother = populationWithNGenes.get(populationWithNGenes.size()-1);
		IndividualWithNGenes[] sonAndDaughter = oneCoupleReproduction(father, mother);
		
		//A mutation append if we are in the probability space
		double m = Math.random();
		if(m<Constants.MUTATIONPROBABILITY) sonAndDaughter[0].mutate();

		newGeneration.add(sonAndDaughter[0]);
		newGeneration.add(sonAndDaughter[1]);
		
		populationWithNGenes = newGeneration;
	}
	
	private void naturalSelection() {
		ArrayList<IndividualWithNGenes> bestHalfOfTheGeneration = new ArrayList<IndividualWithNGenes>();
		HashMap<IndividualWithNGenes, Double> map = new HashMap<IndividualWithNGenes, Double>();
		for(int i=0; i<populationWithNGenes.size(); i++) map.put(populationWithNGenes.get(i), (Double)populationWithNGenes.get(i).getScoreTotal());
		while(bestHalfOfTheGeneration.size()<populationWithNGenes.size()/2) {
			double score = -1;
			IndividualWithNGenes theBest = new IndividualWithNGenes();
			for(Entry<IndividualWithNGenes, Double> anIndividualWithNGenes : map.entrySet()) {
				if(anIndividualWithNGenes.getValue()>score) {
					score=anIndividualWithNGenes.getValue();
					theBest=anIndividualWithNGenes.getKey();
				}
			}
			map.remove(theBest);
			bestHalfOfTheGeneration.add(theBest);
		}
		populationWithNGenes = bestHalfOfTheGeneration;
	}
	
	private IndividualWithNGenes[] oneCoupleReproduction(IndividualWithNGenes father, IndividualWithNGenes mother) {
		IndividualWithNGenes[] children = new IndividualWithNGenes[2];
		//For now the sizes of all the PopulationWithNGenes member will be taken as the same
		int genesSize = father.getGeneN(father.getAllGenes().size()-1).size(); //The last gene has the minimum size
		int[] separator = new int[father.getAllGenes().size()];
		if(halfSeparation) for(int i=0; i<separator.length; i++) separator[i]=genesSize/2;
		else for(int i=0; i<separator.length; i++) separator[i] = (int) (Math.random()*genesSize);
		
		ArrayList<ArrayList<Integer>> sonsGenes = new ArrayList<ArrayList<Integer>>();
		ArrayList<ArrayList<Integer>> daughtersGenes = new ArrayList<ArrayList<Integer>>();
		
		for(int i=0; i<father.getAllGenes().size(); i++) {
			ArrayList<Integer> firstPartGeneFather = father.getFirstPartgenes(i, separator[i]);
			ArrayList<Integer> lastPartGeneFather = father.getLastPartgenes(i, separator[i]);
			ArrayList<Integer> firstPartGeneMother = mother.getFirstPartgenes(i, separator[i]);
			ArrayList<Integer> lastPartGeneMother = mother.getLastPartgenes(i, separator[i]);
			

			ArrayList<Integer> sonsGene = new ArrayList<Integer>();
			ArrayList<Integer> daughtersGene = new ArrayList<Integer>();
			sonsGene.addAll(firstPartGeneFather);
			daughtersGene.addAll(firstPartGeneMother);
			sonsGene.addAll(lastPartGeneMother);
			daughtersGene.addAll(lastPartGeneFather);
			sonsGenes.add(sonsGene);
			daughtersGenes.add(daughtersGene);
		}
//		System.out.println("In reproduction : "+sonsGenes);
		IndividualWithNGenes son = new IndividualWithNGenes(sonsGenes, g);
		IndividualWithNGenes daughter = new IndividualWithNGenes(daughtersGenes, g);
		children[0] = son;
		children[1] = daughter;
		return children;
	}
}
