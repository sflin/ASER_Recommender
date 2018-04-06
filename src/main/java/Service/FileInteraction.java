package Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import Model.MethodCollection;

public class FileInteraction {

	
	public MethodCollection parseFile(File file) throws FileNotFoundException {
		
		Gson gson = new GsonBuilder().create();
		
		JsonReader reader = new JsonReader(new FileReader(file));
		MethodCollection methodCollection = gson.fromJson(reader,MethodCollection.class);

		return methodCollection;	
	}
}
