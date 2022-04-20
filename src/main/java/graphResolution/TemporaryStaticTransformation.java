package graphResolution;

import java.util.ArrayList;

public class TemporaryStaticTransformation {
	//It won't be necessary if we only work with list but if not ;)
	
	public static int[][] listOfGenesTo2DTabOfInt(ArrayList<ArrayList<Integer>> list) {
		int[][] finalTab = new int[list.size()][];
		for(int i=0; i<list.size(); i++) {
			ArrayList<Integer> subList = list.get(i);
			finalTab[i] = new int[subList.size()];
			for(int j=0; j<subList.size(); j++) finalTab[i][j] = subList.get(j);
		}
		return finalTab;
	}
	
	public static String a2DTabOfIntToString(int[][] tab) {
		String s = "[";
		int cpt=0;
		for(int[] subTab : tab) {
			s += "[";
			for(int i=0; i<subTab.length-1;i++) s += subTab[i]+", ";
			s += subTab[subTab.length-1]+"]";
			if(cpt!=tab.length-1) s+=", ";
			cpt++;
		}
		s +="]";
		return s;
	}
}
