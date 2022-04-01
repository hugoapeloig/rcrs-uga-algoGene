package graphResolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Population {
	
	private ArrayList<Individual> population = new ArrayList<Individual>();
	private boolean halfSeparation;
	
	public Population() {
		for(int i=0; i<Constants.POPULATIONSIZE; i++) population.add(new Individual());
		halfSeparation = true;
	}
	
	public Population(boolean halfSeparation) {
		this();
		this.halfSeparation=halfSeparation;
	}
	
	public Population(Population previousPopulation) {
		this.population = previousPopulation.population;
		this.halfSeparation=previousPopulation.halfSeparation;
	}
	
	public ArrayList<Individual> getPopulation(){
		return population;
	}
	
	public Individual getTheBest() {
		Individual best = population.get(0);
		for(Individual i : population) if(i.getScore()>best.getScore()) best = i;
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
		ArrayList<Individual> newGeneration = new ArrayList<Individual>();
		
		for(int i=0; i<population.size()-1; i++) {
			//All the couples are taking unless the couple : last and first individual
			Individual father = population.get(i);
			Individual mother = population.get(i+1);
			Individual[] sonAndDaughter = oneCoupleReproduction(father, mother);
			
			//A mutation append if we are in the probability space
			double m = Math.random();
			if(m<Constants.MUTATIONPROBABILITY) sonAndDaughter[0].mutate();

			newGeneration.add(sonAndDaughter[0]);
			newGeneration.add(sonAndDaughter[1]);
		}
		//We make the reproduction of the first and last one
		Individual father = population.get(population.size()-1);
		Individual mother = population.get(0);
		Individual[] sonAndDaughter = oneCoupleReproduction(father, mother);
		
		//A mutation append if we are in the probability space
		double m = Math.random();
		if(m<Constants.MUTATIONPROBABILITY) sonAndDaughter[0].mutate();

		newGeneration.add(sonAndDaughter[0]);
		newGeneration.add(sonAndDaughter[1]);
		
		population = newGeneration;
	}
	
	private void naturalSelection() {
		ArrayList<Individual> bestHalfOfTheGeneration = new ArrayList<Individual>();
		HashMap<Individual, Double> map = new HashMap<Individual, Double>();
		for(int i=0; i<population.size(); i++) map.put(population.get(i), (Double)population.get(i).getScore());
		while(bestHalfOfTheGeneration.size()<population.size()/2) {
			double score = -1;
			Individual theBest = new Individual();
			for(Entry<Individual, Double> anIndividual : map.entrySet()) {
				if(anIndividual.getValue()>score) {
					score=anIndividual.getValue();
					theBest=anIndividual.getKey();
				}
			}
			map.remove(theBest);
			bestHalfOfTheGeneration.add(theBest);
		}
		population = bestHalfOfTheGeneration;
	}
	
	private Individual[] oneCoupleReproduction(Individual father, Individual mother) {
		Individual[] children = new Individual[2];
		//For now the sizes of all the population member will be taken as the same
		int genesSize = father.getGenes().size();
		int separator;
		if(halfSeparation) separator = genesSize/2;
		else separator = (int) (Math.random()*genesSize);
		
		ArrayList<Integer> firstPartGenesFather = father.getFirstPartgenes(separator);
		ArrayList<Integer> lastPartGenesFather = father.getLastPartgenes(separator);
		ArrayList<Integer> firstPartGenesMother = mother.getFirstPartgenes(separator);
		ArrayList<Integer> lastPartGenesMother = mother.getLastPartgenes(separator);
		
		ArrayList<Integer> sonGene = firstPartGenesFather, daughterGene = firstPartGenesMother;
		sonGene.addAll(lastPartGenesMother);
		daughterGene.addAll(lastPartGenesFather);
		
		
		Individual son = new Individual(sonGene), daughter = new Individual(daughterGene);
		children[0] = son;
		children[1] = daughter;
		return children;
	}
	
}
