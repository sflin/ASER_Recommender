package Visitor;

import java.util.ArrayList;
import java.util.List;

import cc.kave.commons.model.events.IIDEEvent;
import cc.kave.commons.model.events.completionevents.Context;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;

public class CompletionVisitorEvent {
	
	List<String> methods = new ArrayList<String>();
	
	public CompletionVisitorEvent(IIDEEvent e) {
		
	}
	
	public void addMethod(String method) {
		if(!methods.contains(method)) {
			methods.add(method);
		}
	}
	
	public List<String> getMethods(){
		return methods;
	}

}
