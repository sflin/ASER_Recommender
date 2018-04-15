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

	
	private ArrayList<Method> methods;

	public ArrayList<Method> getMethods() {
		return methods;
	}

	public void setMethods(ArrayList<Method> methods) {
		this.methods = methods;
	}

}
