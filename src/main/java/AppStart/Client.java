package AppStart;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.google.common.collect.Lists;

import Evaluation.Evaluator;
import Model.Recommendation;
import Service.Recommender;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.utils.io.ReadingArchive;

public class Client {

	public static void main(String[] args) throws FileNotFoundException {
		run(args);
	}

	private static String DIR_USERDATA = "C:\\temp\\Events\\";
	private static String DIR_METHODCOLLECTIONS ="C:\\temp\\MethodCollections";
	private static boolean doEvaluation = false;
	private static Evaluator evaluator;
	
	public static void run(String[] args) throws FileNotFoundException {
		
		if(args[0] != null && args[0].equals("-e")) {
			doEvaluation = true;
			evaluator = new Evaluator();
		}
		
		for (String user : findAllUsers()) {
			ReadingArchive ra = new ReadingArchive(new File(user));
			while (ra.hasNext()) {
	
				IIDEEvent event = ra.getNext(IIDEEvent.class);
				process(event);
				
			}
			ra.close();
		}
		if(doEvaluation) {
			evaluator.summarizeResults();
		}

	}
	
	public static List<String> findAllUsers() {
		List<String> zips = Lists.newLinkedList();
		for (File f : FileUtils.listFiles(new File(DIR_USERDATA), new String[] { "zip" }, true)) {
			zips.add(f.getAbsolutePath());
		}
		return zips;
	}
	
	private static void process(IIDEEvent event) throws FileNotFoundException {

		if (event instanceof CompletionEvent) {
			ICompletionEvent ce = (CompletionEvent) event;
			Recommender recommender = new Recommender(DIR_METHODCOLLECTIONS);
			List<Recommendation> resultList = recommender.getRecommendations(ce.getContext().getSST().getEnclosingType());
			if(doEvaluation) {
				evaluator.evaluate(resultList, ce.getLastSelectedProposal());
			}
			
		}
	}

	

}
