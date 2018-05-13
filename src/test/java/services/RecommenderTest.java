package services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.gson.Gson;

import Model.ClassCollection;
import Model.Recommendation;
import Service.IRecommender;
import Service.Impl.ReadingArchiveEvents;
import Service.Impl.Recommender;
import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.CompletionEvent;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;
import cc.kave.commons.model.naming.impl.v0.codeelements.MethodName;
import cc.kave.commons.model.naming.impl.v0.codeelements.PropertyName;
import cc.kave.commons.model.naming.impl.v0.types.TypeName;

public class RecommenderTest {
	
	private ClassCollection classCollection;
	
	@Before
	public void setup() throws UnsupportedEncodingException, FileNotFoundException, IOException {

	}
	
	@Test
	public void testGetRecommendation() throws FileNotFoundException {
		IRecommender recommender = new Recommender("src//test//java//Recommender//TestCollections//");
		
		for (String user : findAllUsers("src//test//java/Recommender//")) {
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

	public static List<String> findAllUsers(String path) {
		List<String> zips = Lists.newLinkedList();
		for (File f : FileUtils.listFiles(new File(path), new String[] { "zip" }, true)) {
			zips.add(f.getAbsolutePath());
		}
		return zips;
	}
	



}
