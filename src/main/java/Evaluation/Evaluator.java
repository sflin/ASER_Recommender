package Evaluation;

import java.util.ArrayList;
import java.util.List;

import Model.Recommendation;
import cc.kave.commons.model.events.completionevents.IProposal;

public class Evaluator implements IEvaluator {

	private static List<Double> apList;
	private static int truePositive;
	private static int falsePositive;
	private static int falseNegative;

	public Evaluator() {
		apList = new ArrayList<>();
		truePositive = 0;
		falsePositive = 0;
		falseNegative = 0;
	}

	public void evaluate(List<Recommendation> resultList, IProposal actualSelection) {
		String name = NameUtility.getName(actualSelection);
		int rank = getRank(name, resultList);
		apList.add(getAveragePrecision(rank));
	}

	public void summarizeResults() {
		System.out.println("Number of processed queries in total: " + apList.size());
		System.out.println("Number of successful recommendations: " + getSuccessfulRecommendations());
		System.out.println("Precision of recommender: " + getPrecision());
		System.out.println("Recall of recommender: " + getRecall());
		System.out.println("On average you have to look at " + getMeanAveragePrecision()
				+ " entries to get a match (mean average precision)");
	}

	private int getRank(String name, List<Recommendation> resultList) {
		// String type = actualSelection.isConstructor() ? "CTOR" : "SIMPLE";
		if (resultList.isEmpty()) {
			// FN: we do not have a hit list for this event
			falseNegative += 1;
			return -1;
		}
		int rank = 0;
		for (Recommendation r : resultList) {
			if (r.getName().equals(name)) {
				rank = resultList.indexOf(r);
				rank += 1;
			}
		}
		if (rank > 0) {
			// TP: rank > 0, hit-list existing
			truePositive += 1;
		} else {
			// FP: rank = 0, hit-list existing, but no entry found
			falsePositive += 1;
		}
		// Expression-type: [cast, simple, ctor, prop]
		return rank;
	}

	/**
	 * Calculates precision with TruePositive / (TruePositive + FalsePositive) where
	 * TruePositive denotes the amout of queries found in the hit-list and
	 * FalsePositive the amount of missed hits.
	 * 
	 * @return double precision
	 */
	private double getPrecision() {
		if ((truePositive + falsePositive) != 0) {
			return truePositive / (truePositive + falsePositive);
		}
		return 0.0;
	}

	/**
	 * Calculates recall with TruePositive / (FalseNegative + TruePositive) where
	 * TruePositive denotes the amount of queries found in the hit-list and
	 * FalseNegative the ones where no hit-list exists.
	 * 
	 * @return double recall
	 */
	private double getRecall() {
		if ((falseNegative + truePositive) != 0) {
			return truePositive / (falseNegative + truePositive);
		}
		return 0.0;
	}

	/**
	 * Calculates the averagePrecision of an entry at rank k. It corresponds to the
	 * average distance from the top-result to the actual rank; the bigger AP is, the
	 * better.
	 * 
	 * @param rankOfK
	 * @return double averagePrecision
	 */
	private double getAveragePrecision(int rankOfK) {
		return rankOfK > 0 ? (1 / rankOfK) : 0.0;
	}

	/**
	 * Calculates the meanAveragePrecision of a list of averagePrecisions. It
	 * corresponds to the average of the averagePrecisions, i.e. the average
	 * distance of all ranks to the top-result.
	 * 
	 * @param rankOfK
	 * @return double meanAveragePrecision
	 */
	private double getMeanAveragePrecision() {
		double apSum = 0.0;
		for (double ap : apList) {
			apSum += ap;
		}
		return apList.size() != 0 ? (apSum / apList.size()) : -1.0;
	}

	private int getSuccessfulRecommendations() {
		int successfulHits = 0;
		for (double ap : apList) {
			if (ap > 0.0) {
				successfulHits += 1;
			}
		}
		return successfulHits;
	}
}
