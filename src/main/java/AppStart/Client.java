package AppStart;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;

import Evaluation.Evaluator;
import Model.Recommendation;
import Service.IExport;
import Service.Impl.Export;
import Service.Impl.ReadingArchiveEvents;
import Service.Impl.Recommender;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;

public class Client {

	public static void main(String[] args) throws FileNotFoundException {
		run(args[0],args[1],args[2],args[3]);
	}

	private static String DIR_USERDATA;
	private static String DIR_METHODCOLLECTIONS;
	
	private static boolean doEvaluation = false;
	private static Evaluator evaluator;
	private static IExport export;
	private static boolean debugging = false;
	
	public static void run(String userdata, String methodCollections,String outputDirectory,String flag) throws FileNotFoundException {
		
		if(userdata.isEmpty()) {
			DIR_USERDATA = System.getProperty("user.home") + File.separator +"Recommender"+ File.separator +"Events";
		}else {
			DIR_USERDATA=userdata;
		}
		if(methodCollections.isEmpty()) {
			DIR_METHODCOLLECTIONS =System.getProperty("user.home") + File.separator + "Recommender" +File.separator + "MethodCollections";
		}else {
			DIR_METHODCOLLECTIONS=methodCollections;
		}
			
		checkForFolders(DIR_USERDATA);
		checkForFolders(DIR_METHODCOLLECTIONS);
		
		if(flag != null && flag.equals("-e")) {
			doEvaluation = true;
			evaluator = new Evaluator();
		}
		export = new Export(outputDirectory);
		
		for (String user : findAllUsers()) {
			ReadingArchiveEvents ra = new ReadingArchiveEvents(new File(user));
			while (ra.hasNext()) {
				IIDEEvent event = ra.getNext(IIDEEvent.class);
				List<Recommendation> recommendations = process(event);
				
				if(debugging) {
					for(Recommendation reco: recommendations) {
						System.out.println(reco.toString());
					}
				}
				
				if(!recommendations.isEmpty()) {
					export.printRecommendationsToCsv(recommendations);
				}
			}
			ra.close();
		}
		if(doEvaluation) {
			evaluator.summarizeResults();
		}

	}
	
	private static void checkForFolders(String foldername) {
		File f = new File(foldername);
		if(!f.exists()) {
			f.mkdir();
		}
	}
	
	public static List<String> findAllUsers() {
		List<String> zips = Lists.newLinkedList();
		for (File f : FileUtils.listFiles(new File(DIR_USERDATA), new String[] { "zip" }, true)) {
			zips.add(f.getAbsolutePath());
		}
		return zips;
	}
	
	private static List<Recommendation> process(IIDEEvent event) throws FileNotFoundException {

		if (event instanceof CompletionEvent) {
			ICompletionEvent ce = (CompletionEvent) event;
			Recommender recommender = new Recommender(DIR_METHODCOLLECTIONS);
			List<Recommendation> resultList = recommender.getRecommendations(ce.getContext().getSST().getEnclosingType());
			
			//Activate for Event debugging
			//export.printEvent(ce);
			
			if(doEvaluation) {
				evaluator.evaluate(resultList, ce.getLastSelectedProposal());
			}
			return resultList;
		}
		return new ArrayList<Recommendation>();
	}

	

}
