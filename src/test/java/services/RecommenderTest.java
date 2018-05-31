package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import com.google.common.collect.Lists;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.model.events.completionevents.IProposal;
import recommender.evaluation.Evaluator;
import recommender.model.Recommendation;
import recommender.service.impl.ReadingArchiveEvents;
import recommender.visitor.CompletionExpressionVisitor;

public class RecommenderTest {
	
	@Test
	public void testGetRecommendation() throws FileNotFoundException {

		Evaluator evaluator = new Evaluator();
		
		for (String user : findAllUsers("src/test/java/recommender")) {
			ReadingArchiveEvents ra = new ReadingArchiveEvents(new File(user));
			while (ra.hasNext()) {
				IIDEEvent event = ra.getNext(IIDEEvent.class);
				if (event instanceof CompletionEvent) {
					ICompletionEvent ce = (CompletionEvent) event; 

					HashMap<List<Recommendation>, IProposal> evaluationSet = new HashMap<List<Recommendation>, IProposal>();

					ce.getContext().getSST().accept(new CompletionExpressionVisitor("src/test/java/recommender/collections", evaluationSet, ce),null);

					Iterator<Entry<List<Recommendation>, IProposal>> it = evaluationSet.entrySet().iterator();
					while (it.hasNext()) {
						
						Map.Entry<List<Recommendation>, IProposal> pair = (Map.Entry<List<Recommendation>, IProposal>) it.next();
						
						assertTrue(round(pair.getKey().get(0).getPercentage(),2)==Float.parseFloat("13.66"));
						assertTrue(pair.getKey().get(0).getName().equals("System.String.IsNullOrEmpty(System.String value)"));
						assertTrue(pair.getKey().size()==131);
						
						evaluator.evaluate(pair.getKey(), pair.getValue());
					}
				}
			}
			ra.close();
		}		
	}
	
	private float round(double d, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(d);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.floatValue();
 }

	public static List<String> findAllUsers(String path) {
		List<String> zips = Lists.newLinkedList();
		for (File f : FileUtils.listFiles(new File(path), new String[] { "zip" }, true)) {
			zips.add(f.getAbsolutePath());
		}
		return zips;
	}
	



}
