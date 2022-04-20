package graphResolution;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		Population p = new Population(false);
//		p.start(Constants.NUMBEROFGENERATIONS);
//		Individual i = p.getTheBest();
//		System.out.println(i);
//		System.out.println("Son score : " + i.getScore());
//		String[] namesOrder = new String[i.getGenes().size()];
//		for(int j=0; j<i.getGenes().size(); j++) {
//			namesOrder[j] = i.getGenes().get(j).toString();
//		}
//		System.out.println("Sa distance totale : "+Constants.THEGRAPHTEST.calcTotalValue(namesOrder));
//		System.out.println("La moyenne : "+Constants.THEGRAPHTEST.getMeanValue());
//		System.out.println();
////
//		PopulationWithTwoGenes p2 = new PopulationWithTwoGenes(false);
//		p2.start(Constants.NUMBEROFGENERATIONS);
//		IndividualWithTwoGenes i2 = p2.getTheBest();
//		System.out.println(i2);
//		System.out.println("Son score : " + i2.getScoreTotal());
////		
//////		IndividualWithTwoGenes i3 = new IndividualWithTwoGenes();
//////		System.out.println(i3);
//////		System.out.println("Son score : " + i3.getScoreTotal());
//		
//		int n = 5;
//		PopulationWithNGenes p3 = new PopulationWithNGenes(n);
//		p3.start(Constants.NUMBEROFGENERATIONS);
//		IndividualWithNGenes i4 = p3.getTheBest();
//		System.out.println("La moyenne : "+Constants.THEGRAPHTEST.getMeanValue());
//		System.out.println("La moyenne pour "+n+" groupes : "+Constants.THEGRAPHTEST.getMeanValue()/n);
//		System.out.println("Population with N genes : ");
//		System.out.println(i4);
//		System.out.println("Son score : " + i4.getScoreTotal());
//		System.out.println();
		
		IndividualWithNGenes i5 = new IndividualWithNGenes(4);
		System.out.println(i5);
		System.out.println("Son score : " + i5.getScoreTotal());
		System.out.println(i5.getAllGenes());
		System.out.println(TemporaryStaticTransformation.a2DTabOfIntToString(TemporaryStaticTransformation.listOfGenesTo2DTabOfInt(i5.getAllGenes())));
		
//		IndividualWithGenetInception i = new IndividualWithGenetInception(4);
//		System.out.println(i);
//		System.out.println("Son score : "+i.getScoreTotal());
		
//		PopulationWithGenetInception p4 = new PopulationWithGenetInception(4);
//		IndividualWithGenetInception i6 = p4.getBest();
//		System.out.println("Population with genetique Inception : ");
//		System.out.println(i6);
//		System.out.println("Son score total : "+i6.getScoreTotal());
		
		
	}

}
