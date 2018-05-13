package Service.Impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import Model.MethodCollection;
import Model.ClassCollection;
import Model.Method;
import Model.Recommendation;
import Service.IRecommender;
import cc.kave.commons.model.naming.types.ITypeName;

public class Recommender implements IRecommender{
	
	private String contextCollectionPath;
	private String dataType=".json";
	private CollectionInteraction fileInteraction;
	
	public Recommender(String contextCollectionPath) {
		if(contextCollectionPath.endsWith("\\")) {
			this.contextCollectionPath = contextCollectionPath;
		}else {
			this.contextCollectionPath = contextCollectionPath+"\\";
		}
		fileInteraction = new CollectionInteraction();
	}
	
	public List<Recommendation> getRecommendations(ITypeName type) throws FileNotFoundException{
		
		List<Recommendation> recommendations = new ArrayList<Recommendation>();
		
		ClassCollection classCollection = null;
		String parentDir = type.getAssembly().getName().toString();
		String filename = type.getName().toString();
		
		String filepath = parentDir +"\\"+filename;
		
		File file = new File(contextCollectionPath+filepath+dataType);
		if(file.exists()){
			classCollection = fileInteraction.parseClassfile(file);
		}else {
			return recommendations; //did not find any files
		}
		
		int totalAmountOfOccurences = 0;

		/*Calculate the total amount of all method occurences in MethodCollection*/
		for(MethodCollection methodCollection : classCollection.getCollections()) {
			
			if(methodCollection.getFullName().equals(type.getFullName())) {
				for(Method method : methodCollection.getMethods()) {
					totalAmountOfOccurences+=method.getCount();
				}
				
				for(Method method : methodCollection.getMethods()) {
					recommendations.add(new Recommendation(method.getName(),method.getType(),calculatePercentage(method.getCount(),totalAmountOfOccurences)));
				}
		
				recommendations.sort(new RecommendationComparator());
			}
		}
		
		return recommendations;
	}
	
	private double calculatePercentage(int count, int totalAmount) {
		
		return ((double)count/(double)totalAmount)*100;
	}
	

}
