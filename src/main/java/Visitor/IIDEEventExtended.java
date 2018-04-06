package Visitor;
import cc.kave.commons.model.events.IIDEEvent;

public interface IIDEEventExtended extends IIDEEvent{

	void accept(EventVisitor eventVisitor);
	
}
