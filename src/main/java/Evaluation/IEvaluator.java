package Evaluation;

import java.util.List;

import Model.Recommendation;
import cc.kave.commons.model.events.completionevents.IProposal;

public interface IEvaluator {
	
	void evaluate(List<Recommendation> resultList, IProposal actualSelection);

	void summarizeResults();
}
