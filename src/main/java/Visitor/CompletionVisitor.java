package Visitor;
import java.util.ArrayList;
import java.util.List;

import cc.kave.commons.model.ssts.expressions.assignable.ICompletionExpression;
import cc.kave.commons.model.ssts.expressions.assignable.IInvocationExpression;
import cc.kave.commons.model.ssts.impl.visitor.AbstractTraversingNodeVisitor;
import cc.kave.commons.model.ssts.references.IVariableReference;


public class CompletionVisitor extends AbstractTraversingNodeVisitor<CompletionVisitorEvent, List<String>> {

	List<String> names;
	
	public CompletionVisitor() {
		names = new ArrayList<String>();
	}
	
	@Override
	public List<String> visit(ICompletionExpression e, CompletionVisitorEvent completionEvent) {

		completionEvent.addMethod("visit called");

		return names;
	}
	
}