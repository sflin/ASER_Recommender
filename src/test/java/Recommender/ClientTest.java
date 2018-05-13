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
	private File recoOutput;
	
	
	@Before
	public void setup() {
		output = System.getProperty("user.home")+"/Recommender"+"/OutputTest";
		recoOutput = new File(output);
		if(!recoOutput.exists()) {
			recoOutput.mkdir();
		}
	}
	
	@Test
	public void testRun() throws FileNotFoundException {
		
		String[] args = {"src//test//java/Recommender//SingleEvent//","src//test//java//Recommender//TestCollections//",output,"-e"};
		Client.main(args);
		assertTrue(recoOutput.exists());
		assertTrue(recoOutput.isDirectory());
		File[] files = recoOutput.listFiles();
		assertEquals(files.length,1);
	}
	
	@After
	public void tearDown() {

		for(File dir : recoOutput.listFiles()) {
			dir.delete();
		}
		recoOutput.delete();
	}

}
