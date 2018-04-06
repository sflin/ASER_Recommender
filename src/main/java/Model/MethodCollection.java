package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class MethodCollection {
	
	private String fullname;
	
	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	
	private ArrayList<HashMap<String,Object>> methods;

	public ArrayList<HashMap<String, Object>> getMethods() {
		return methods;
	}

	public void setMethods(ArrayList<HashMap<String, Object>> methods) {
		this.methods = methods;
	}
}
