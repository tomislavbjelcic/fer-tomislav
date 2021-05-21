package ui;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public interface Features {
	
	
	void addFeature(String feature);
	
	default String getFeature(int index) {
		List<String> featList = getFeatureList();
		Objects.checkIndex(index, featList.size());
		String feat = featList.get(index);
		return feat;
	};
	
	void addValue(String feature, String value);
	String getOutputType();
	void setOutputType(String out);
	void addOutputValue(String outVal);
	Set<String> getOutputValues();
	Set<String> getValues(String feature);
	List<String> getFeatureList();
	
}
