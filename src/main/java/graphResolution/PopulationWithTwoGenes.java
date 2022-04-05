package graphResolution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class PopulationWithTwoGenes {
	private ArrayList<IndividualWithTwoGenes> populationWithTwoGenes = new ArrayList<IndividualWithTwoGenes>();
	private boolean halfSeparation;
	
	public PopulationWithTwoGenes() {
		for(int i=0; i<Constants.POPULATIONSIZE; i++) populationWithTwoGenes.add(new IndividualWithTwoGenes());
		halfSeparation = true;
	}
	
	public PopulationWithTwoGenes(boolean halfSeparation) {
		this();
		this.halfSeparation=halfSeparation;
	}
	
	public PopulationWithTwoGenes(PopulationWithTwoGenes previousPopulationWithTwoGenes) {
		this.populationWithTwoGenes = previousPopulationWithTwoGenes.populationWithTwoGenes;
		this.halfSeparation=previousPopulationWithTwoGenes.halfSeparation;
	}
	
	public ArrayList<IndividualWithTwoGenes> getPopulationWithTwoGenes(){
		return populationWithTwoGenes;
	}
	
	public IndividualWithTwoGenes getTheBest() {
		IndividualWithTwoGenes best = populationWithTwoGenes.get(0);
		for(IndividualWithTwoGenes i : populationWithTwoGenes) if(i.getScoreTotal()>best.getScoreTotal()) best = i;
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
		ArrayList<IndividualWithTwoGenes> newGeneration = new ArrayList<IndividualWithTwoGenes>();
		
		for(int i=0; i<populationWithTwoGenes.size()-1; i++) {
			//All the couples are taking unless the couple : last and first IndividualWithTwoGenes
			IndividualWithTwoGenes father = populationWithTwoGenes.get(i);
			IndividualWithTwoGenes mother = populationWithTwoGenes.get(i+1);
			IndividualWithTwoGenes[] sonAndDaughter = oneCoupleReproduction(father, mother);
			
			//A mutation append if we are in the probability space
			double m = Math.random();
			if(m<Constants.MUTATIONPROBABILITY) sonAndDaughter[0].mutate();

			newGeneration.add(sonAndDaughter[0]);
			newGeneration.add(sonAndDaughter[1]);
		}
		//We make the reproduction of the first and last one
		IndividualWithTwoGenes father = populationWithTwoGenes.get(populationWithTwoGenes.size()-1);
		IndividualWithTwoGenes mother = populationWithTwoGenes.get(0);
		IndividualWithTwoGenes[] sonAndDaughter = oneCoupleReproduction(father, mother);
		
		//A mutation append if we are in the probability space
		double m = Math.random();
		if(m<Constants.MUTATIONPROBABILITY) sonAndDaughter[0].mutate();

		newGeneration.add(sonAndDaughter[0]);
		newGeneration.add(sonAndDaughter[1]);
		
		populationWithTwoGenes = newGeneration;
	}
	
	private void naturalSelection() {
		ArrayList<IndividualWithTwoGenes> bestHalfOfTheGeneration = new ArrayList<IndividualWithTwoGenes>();
		HashMap<IndividualWithTwoGenes, Double> map = new HashMap<IndividualWithTwoGenes, Double>();
		for(int i=0; i<populationWithTwoGenes.size(); i++) map.put(populationWithTwoGenes.get(i), (Double)populationWithTwoGenes.get(i).getScoreTotal());
		while(bestHalfOfTheGeneration.size()<populationWithTwoGenes.size()/2) {
			double score = -1;
			IndividualWithTwoGenes theBest = new IndividualWithTwoGenes();
			for(Entry<IndividualWithTwoGenes, Double> anIndividualWithTwoGenes : map.entrySet()) {
				if(anIndividualWithTwoGenes.getValue()>score) {
					score=anIndividualWithTwoGenes.getValue();
					theBest=anIndividualWithTwoGenes.getKey();
				}
			}
			map.remove(theBest);
			bestHalfOfTheGeneration.add(theBest);
		}
		populationWithTwoGenes = bestHalfOfTheGeneration;
	}
	
	private IndividualWithTwoGenes[] oneCoupleReproduction(IndividualWithTwoGenes father, IndividualWithTwoGenes mother) {
		IndividualWithTwoGenes[] children = new IndividualWithTwoGenes[2];
		//For now the sizes of all the PopulationWithTwoGenes member will be taken as the same
		int genesSize = father.getGenesSecond().size();
		int separator1, separator2;
		if(halfSeparation) {
			separator1 = genesSize/2;
			separator2 = genesSize/2;
		}
		else {
			separator1 = (int) (Math.random()*genesSize);
			separator2 = (int) (Math.random()*genesSize);
		}
		
		ArrayList<Integer> firstPartGenesOneFather = father.getFirstPartgenes(1,separator1);
		ArrayList<Integer> firstPartGenesTwoFather = father.getFirstPartgenes(2,separator2);
		ArrayList<Integer> lastPartGenesOneFather = father.getLastPartgenes(1,separator1);
		ArrayList<Integer> lastPartGenesTwoFather = father.getLastPartgenes(2,separator2);
		
		ArrayList<Integer> firstPartGenesOneMother = mother.getFirstPartgenes(1,separator1);
		ArrayList<Integer> firstPartGenesTwoMother = mother.getFirstPartgenes(2,separator2);
		ArrayList<Integer> lastPartGenesOneMother = mother.getLastPartgenes(1,separator1);
		ArrayList<Integer> lastPartGenesTwoMother = mother.getLastPartgenes(2,separator2);
		
		ArrayList<Integer> sonGeneOne = firstPartGenesOneFather,
				sonGeneTwo = firstPartGenesTwoFather,
				daughterGeneOne = firstPartGenesOneMother,
				daughterGeneTwo = firstPartGenesTwoMother;
		sonGeneOne.addAll(lastPartGenesOneMother);
		sonGeneTwo.addAll(lastPartGenesTwoMother);
		daughterGeneOne.addAll(lastPartGenesOneFather);
		daughterGeneTwo.addAll(lastPartGenesTwoFather);
		
		
		IndividualWithTwoGenes son = new IndividualWithTwoGenes(sonGeneOne,sonGeneTwo), daughter = new IndividualWithTwoGenes(daughterGeneOne,daughterGeneTwo);
		children[0] = son;
		children[1] = daughter;
		return children;
	}
}
