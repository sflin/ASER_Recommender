package Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import Model.MethodCollection;
import Model.Method;
import Model.Recommendation;

public class Recommender {
	
	private String contextCollectionPath;
	private String dataType=".json";
	private FileInteraction fileInteraction;
	
	public Recommender(String contextCollectionPath) {
		if(contextCollectionPath.endsWith("\\")) {
			this.contextCollectionPath = contextCollectionPath;
		}else {
			this.contextCollectionPath = contextCollectionPath+"\\";
		}
		fileInteraction = new FileInteraction();
	}
	
	public List<Recommendation> getRecommendations(String type) throws FileNotFoundException{
		
		List<Recommendation> recommendations = new ArrayList<Recommendation>();
		type = type.replace('>', '-').replace(':',';');
		//System.out.println("Search for: "+contextCollectionPath+type+dataType);
		
		MethodCollection methodCollection = null;
		File file = new File(contextCollectionPath+type+dataType);
		if(file.exists()){
			System.out.println("Found file for: "+contextCollectionPath+type+dataType);
			methodCollection = fileInteraction.parseFile(file);
		}else {
			//System.out.println("Did not find file: "+contextCollectionPath+type+dataType);
			return recommendations;
		}
		
		System.out.println(type);
		System.out.println("==================================================");
		
		int totalAmountOfOccurences = 0;

		/*Calculate the total amount of all method occurences in MethodCollection*/
		for(Method method : methodCollection.getMethods()) {
			totalAmountOfOccurences+=method.getCount();
		}
		
		for(Method method : methodCollection.getMethods()) {
			recommendations.add(new Recommendation(method.getName(),calculatePercentage(method.getCount(),totalAmountOfOccurences)));
		}

		recommendations.sort(new RecommendationComparator());
		
		for(Recommendation reco : recommendations) {
			System.out.println(reco.toString());
		}
		
		System.out.println("==================================================");
		
		return recommendations;
	}
	
	private double calculatePercentage(int count, int totalAmount) {
		
		return ((double)count/(double)totalAmount)*100;
	}
	

}
