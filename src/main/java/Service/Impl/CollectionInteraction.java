package Service.Impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import Model.ClassCollection;
import Service.ICollectionInteraction;

public class CollectionInteraction implements ICollectionInteraction{

	
	public ClassCollection parseClassfile(File file) throws FileNotFoundException {
		
		Gson gson = new GsonBuilder().create();
		
		JsonReader reader = new JsonReader(new FileReader(file));
		ClassCollection classCollection = gson.fromJson(reader,ClassCollection.class);

		return classCollection;	
	}
}
