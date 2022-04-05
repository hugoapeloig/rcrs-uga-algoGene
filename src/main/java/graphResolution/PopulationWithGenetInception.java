package graphResolution;

import java.util.ArrayList;

public class PopulationWithGenetInception {
	//We have a list of people, for each of them we solve the best trail for each of them genes
	//Then we keep the best one between all of them
	ArrayList<IndividualWithGenetInception> population = new ArrayList<IndividualWithGenetInception>();
	
	public PopulationWithGenetInception() {
		this(1);
	}
	
	public PopulationWithGenetInception(int n) {
		for(int i=0; i<Constants.POPULATIONSIZE; i++) population.add(new IndividualWithGenetInception(n));
	}
	
	
}
