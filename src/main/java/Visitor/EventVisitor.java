package Visitor;
import cc.kave.commons.model.events.completionevents.ICompletionEvent;

public class EventVisitor {
	
	public EventVisitor() {
		
	}
	
	public void visit(ICompletionEvent event) {

		event.getContext().getSST().accept(new CompletionVisitor(), null);
		
		System.out.println("EventVisitor visited");
	}

}
