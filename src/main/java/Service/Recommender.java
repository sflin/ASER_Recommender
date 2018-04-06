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

public class Recommender {
	
	private String contextCollectionPath;
	private String dataType=".json";
	
	public Recommender(String contextCollectionPath) {
		if(contextCollectionPath.endsWith("\\")) {
			this.contextCollectionPath = contextCollectionPath;
		}else {
			this.contextCollectionPath = contextCollectionPath+"\\";
		}
		
	}
	
	public List<String> getRecommendations(String type) throws FileNotFoundException{
		
		List<String> recommendations = new ArrayList<String>();
		
		FileInteraction fileInteraction = new FileInteraction();
		
		File file = new File(contextCollectionPath+type+dataType);
		
		MethodCollection methodCollection = null;
		
		if(file.exists()){
			methodCollection = fileInteraction.parseFile(file);
		}else {
			return recommendations;
		}
		
		System.out.println(type);
		System.out.println("==================================================");
		
		int totalAmountOfOccurences = 0;
		HashMap<Object,Float> methodsWithPercentage = new HashMap<Object,Float>();

		for(HashMap<String,Object> map : methodCollection.getMethods()) {
			totalAmountOfOccurences+=Integer.parseInt((String)map.get("count"));
		}
		for(HashMap<String,Object> map : methodCollection.getMethods()) {
			methodsWithPercentage.put(map.get("name"), calculatePercentage(Integer.parseInt((String)map.get("count")),totalAmountOfOccurences));
		}

		printMap(sortByValue(methodsWithPercentage));
		System.out.println("==================================================");
		
		return recommendations;
	}
	
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Entry.comparingByValue());

        Map<K, V> result = new LinkedHashMap<>();
        for (Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }

    private <K, V> void printMap(Map<K, V> map) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            System.out.println("Key : " + entry.getKey() 
				+ " Value : " + entry.getValue()+"%");
        }
    }
    
    private float round(float value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
    }
	
	private float calculatePercentage(int amountOfOccurences, int totalAmount) {
		
		return round(((float)amountOfOccurences/(float)totalAmount)*100,2);
	}
	

}
