package Recommender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import AppStart.Client;

public class ClientTest {

	private String output;
	
	@Before
	public void setup() {
		
		output = System.getProperty("user.home") + "/RecommenderOutput";
	}
	
	@Test
	public void testRun() throws FileNotFoundException {
		String[] args = {};
		Client.main(args);
		File recoOutput = new File(output);
		assertTrue(recoOutput.exists());
		assertTrue(recoOutput.isDirectory());
		File[] files = recoOutput.listFiles();
		assertEquals(files.length, 1);
	}
	
	@After
	public void tearDown() {
		File archive = new File(output);
		for(File dir : archive.listFiles()) {
			for(File file : dir.listFiles()) {
				file.delete();
			}
			dir.delete();
		}
		archive.delete();
	}

}
