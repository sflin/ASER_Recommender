package Service;

import java.io.FileNotFoundException;
import java.util.List;

import Model.Recommendation;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;

public interface IExport {

	public void printRecommendationsToCsv(List<Recommendation> recommendations) throws FileNotFoundException;
	
	public void printEvent(ICompletionEvent completionEvent) throws FileNotFoundException;
	
}
