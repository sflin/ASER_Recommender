package Service;

import java.io.File;
import java.io.FileNotFoundException;

import Model.ClassCollection;

public interface ICollectionInteraction {

	public ClassCollection parseClassfile(File file) throws FileNotFoundException;
}
