package Service;

import java.io.FileNotFoundException;
import java.util.List;

import Model.Recommendation;
import cc.kave.commons.model.naming.types.ITypeName;

public interface IRecommender {
	
	public List<Recommendation> getRecommendations(ITypeName type) throws FileNotFoundException;
	
}
