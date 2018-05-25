package recommender;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import recommender.Client;

public class ClientTest {

	private String output;
	private File recommenderDir;
	private File recoOutput;
	
	
	@Before
	public void setup() {
		
	}
	
	@Test
	public void testRun() throws FileNotFoundException {
		
		recommenderDir = new File(System.getProperty("user.home")+File.separator+"Recommender");
		output = System.getProperty("user.home")+File.separator+"Recommender"+File.separator+"OutputTest";
		recoOutput = new File(output);
			
		if(!recommenderDir.exists()) {
			recommenderDir.mkdir();
		}
		
		if(!recoOutput.exists()) {
			recoOutput.mkdir();
		}

		String[] args = {"src/test/java/Recommender","src/test/java/recommender/collections",output,"-e"};
		Client.main(args);

		assertTrue(recoOutput.exists());
		assertTrue(recoOutput.isDirectory());
		File[] files = recoOutput.listFiles();
	}
	
	@After
	public void tearDown() {

		for(File dir : recoOutput.listFiles()) {
			dir.delete();
		}
		recoOutput.delete();
	}
	
}
