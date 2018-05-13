package services;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import com.google.common.collect.Lists;
import Model.Recommendation;
import Service.IRecommender;
import Service.Impl.ReadingArchiveEvents;
import Service.Impl.Recommender;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.model.naming.impl.v0.types.TypeName;
public class RecommenderTest {
	
	private IRecommender recommender;
	
	@Before
	public void setup() throws UnsupportedEncodingException, FileNotFoundException, IOException {
		recommender = new Recommender("src"+File.separator+"test"+File.separator+"java"+File.separator+"Recommender"+File.separator+"TestCollections");
	}
	
	@Test
	public void testGetRecommendationSingle() throws FileNotFoundException {
		
		for (String user : findAllUsers("src"+File.separator+"test"+File.separator+"java"+File.separator+"Recommender"+File.separator+"SingleEvent")) {
			ReadingArchiveEvents ra = new ReadingArchiveEvents(new File(user));
			while (ra.hasNext()) {
				IIDEEvent event = ra.getNext(IIDEEvent.class);
				
				if (event instanceof CompletionEvent) {
					ICompletionEvent ce = (CompletionEvent) event;
					List<Recommendation> resultList = recommender.getRecommendations(ce.getContext().getSST().getEnclosingType());
					assertTrue(resultList.size()==11);
				}
			}
			ra.close();
		}
	}
	
	@Test
	public void testGetRecommendationMultiple() throws FileNotFoundException {
		
		for (String user : findAllUsers("src"+File.separator+"test"+File.separator+"java"+File.separator+"Recommender"+File.separator+"MultipleEvents")) {
			ReadingArchiveEvents ra = new ReadingArchiveEvents(new File(user));
			while (ra.hasNext()) {
				IIDEEvent event = ra.getNext(IIDEEvent.class);
				
				if (event instanceof CompletionEvent) {
					ICompletionEvent ce = (CompletionEvent) event;
					List<Recommendation> resultList = recommender.getRecommendations(ce.getContext().getSST().getEnclosingType());
					assertTrue(resultList.size()>0);
				}
			}
			ra.close();
		}
	}
	
	@Test
	public void testGetRecommendationForSpecificType() throws FileNotFoundException {
		
		
		TypeName typeName1 = new TypeName("KaVE.RS.SolutionAnalysis.Tests.SortByUser.SortByUserIoTest, KaVE.RS.SolutionAnalysis.Tests");
		TypeName typeName2 = new TypeName("KaVE.RS.Commons.Tests_Integration.BaseCodeCompletionTest, KaVE.RS.Commons.Tests_Integration");
		
		List<Recommendation> resultList1 = recommender.getRecommendations(typeName1);
		assertTrue(resultList1.size()>0);
		
		List<Recommendation> resultList2 = recommender.getRecommendations(typeName2);
		assertTrue(resultList2.size()>0);
		
	}

	public static List<String> findAllUsers(String path) {
		List<String> zips = Lists.newLinkedList();
		for (File f : FileUtils.listFiles(new File(path), new String[] { "zip" }, true)) {
			zips.add(f.getAbsolutePath());
		}
		return zips;
	}
	



}
