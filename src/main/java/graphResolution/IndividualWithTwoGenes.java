package graphResolution;

import java.util.ArrayList;

public class IndividualWithTwoGenes {

	private ArrayList<Integer> genesFirst = new ArrayList<Integer>();
	private ArrayList<Integer> genesSecond = new ArrayList<Integer>();
	private int score1, score2; 
	private double scoreTotal;
	
	//It's for now just a copy of Individual it need to be update with the idea of the two part genes
	
	public IndividualWithTwoGenes() {
		genesFirst.add(0); //The start is always 0 
		genesSecond.add(0);
		ArrayList<Integer> total = new ArrayList<Integer>();
		for(int i=1; i<Constants.NUMBEROFBUILDINGS; i++) total.add(Constants.GETANONALREADYCHOOSENNUMBER(total));
		int imp=0;
		if(total.size()%2 !=0) imp=1;
		for(int i=0; i<(total.size()/2)+imp; i++) genesFirst.add(total.get(i)); //On déplace la première moitié dans First
		for(int i=(total.size()/2)+imp; i<total.size(); i++) genesSecond.add(total.get(i)); //On déplace ce qui reste
		calcScores();
	}
	
	public IndividualWithTwoGenes(IndividualWithTwoGenes clone) {
		this.genesFirst = clone.genesFirst;
		this.genesSecond = clone.genesSecond;
		this.score1 = clone.score1;
		this.score2 = clone.score2;
		this.scoreTotal = clone.scoreTotal;
	}
	
	public IndividualWithTwoGenes(ArrayList<Integer> genesFirst, ArrayList<Integer> genesSecond) {
		this.genesFirst = genesFirst;
		this.genesSecond = genesSecond;
		calcScores();
	}
	
	public ArrayList<Integer> getGenesFirst(){
		return genesFirst;
	}
	
	public ArrayList<Integer> getGenesSecond(){
		return genesSecond;
	}
	
	public int getScore1() {
		return score1;
	}
	
	public int getScore2() {
		return score2;
	}
	
	public double getScoreTotal() {
		return scoreTotal;
	}
	
	public ArrayList<Integer> getFirstPartgenes(int geneNumber, int separator){
		ArrayList<Integer> genes;
		if(geneNumber==1) genes=genesFirst;
		else genes=genesSecond;
		if(separator>genes.size()) throw new RuntimeException("The separator calcul is wrong !");
		ArrayList<Integer> firstPartGenes = new ArrayList<Integer>();
		for(int i=0; i<separator; i++) firstPartGenes.add(genes.get(i));
		return firstPartGenes;
	}
	
	public ArrayList<Integer> getLastPartgenes(int geneNumber, int separator){
		ArrayList<Integer> genes;
		if(geneNumber==1) genes=genesFirst;
		else genes=genesSecond;
		if(separator>genes.size()) throw new RuntimeException("The separator calcul is wrong !");
		ArrayList<Integer> lastPartGenes = new ArrayList<Integer>();
		for(int i=separator; i<genes.size(); i++) lastPartGenes.add(genes.get(i));
		return lastPartGenes;
	}
	
	public void mutate() {
		//In this case, a mutation is the switching of two nodes between the two genes (at the same position)
		//Not the first place wich is zero for the two
		int mutationPlace = (int) (Math.random()*(genesSecond.size()-1))+1; //On the second because he is less long or equals to the first
		int mut1 = genesFirst.remove(mutationPlace);
		int mut2 = genesSecond.remove(mutationPlace);
		if(mutationPlace >= genesFirst.size()) genesFirst.add(mut2);
		else genesFirst.add(mutationPlace, mut2);
		if(mutationPlace >= genesSecond.size()) genesSecond.add(mut1);
		else genesSecond.add(mutationPlace, mut1);
		
	}
	
	public String toString(){
		String s = "Le parcours 1: ";
		for(int i : genesFirst) s+=""+i+";";
		s += " et son score est : "+score1;
		s += " Le parcours 2: ";
		for(int i : genesSecond) s+=""+i+";";
		s += " et son score est : "+score2;
		return s;
	}
	
	private void calcScores() {
		String[] namesOrder1 = new String[genesFirst.size()];
		String[] namesOrder2 = new String[genesSecond.size()];
		for(int i=0; i<genesFirst.size(); i++) {
			namesOrder1[i] = genesFirst.get(i).toString();
		}
		for(int i=0; i<genesSecond.size(); i++) {
			namesOrder2[i] = genesSecond.get(i).toString();
		}
//		for(int i=0; i<namesOrder.length; i++) System.out.print(namesOrder[i]);
//		System.out.println();
		try{
			int s1 = Constants.THEGRAPHTEST.calcTotalValue(namesOrder1);
			score1 = s1;
			int s2 = Constants.THEGRAPHTEST.calcTotalValue(namesOrder2);
			score2 = s2;//Higher the value of the way is, the lower his score became
			if(score1>score2) scoreTotal = score1; //The score is defined by the highest one
			else scoreTotal = score2;
			scoreTotal = (double) 1/scoreTotal;
		}catch(RuntimeException r) {
//			System.out.println(r.getMessage());
			scoreTotal = 0;
		}
		
		//We check if there is double inside the teams trails
		boolean inDouble = false; 
		for(int i=0; i<genesFirst.size(); i++) for(int j=0; j<genesFirst.size(); j++) if(i!=j && genesFirst.get(i)==genesFirst.get(j)) inDouble = true;
		for(int i=0; i<genesSecond.size(); i++) for(int j=0; j<genesSecond.size(); j++) if(i!=j && genesSecond.get(i)==genesSecond.get(j)) inDouble = true;
		
		//Now we check if every nodes are past 
		ArrayList<Integer> allNodes = new ArrayList<Integer>();
		for(int i=0; i<Constants.NUMBEROFBUILDINGS; i++) allNodes.add(i);
		boolean notAllIn = false;
		for(int i : allNodes) {
			boolean in = false;
			for(int j : genesFirst) if(i==j) in=true;
			for(int j : genesSecond) if(i==j) in=true;
			if(!in) {
				notAllIn = true;
				break;
			}
		}
		
		if(inDouble || notAllIn) scoreTotal=0;
		
	}
}
