package graphResolution;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Population p = new Population(false);
		p.start(Constants.NUMBEROFGENERATIONS);
		Individual i = p.getTheBest();
		System.out.println(i);
		System.out.println("Son score : " + i.getScore());
		String[] namesOrder = new String[i.getGenes().size()];
		for(int j=0; j<i.getGenes().size(); j++) {
			namesOrder[j] = i.getGenes().get(j).toString();
		}
		System.out.println("Sa distance totale : "+Constants.THEGRAPHTEST.calcTotalValue(namesOrder));
		System.out.println("La moyenne : "+Constants.THEGRAPHTEST.getMeanValue());
	}

}
