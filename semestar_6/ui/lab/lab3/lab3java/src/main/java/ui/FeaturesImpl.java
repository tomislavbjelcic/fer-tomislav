package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class FeaturesImpl implements Features {
	
	
	private Map<String, Set<String>> featMap = new HashMap<>();
	private List<String> feats = new ArrayList<>();
	private String out;
	private Set<String> outTypes = new HashSet<>();
	
	@Override
	public void addFeature(String feature) {
		Objects.requireNonNull(feature);
		if (featMap.containsKey(feature))
			return;
		
		Set<String> featureSet = new HashSet<>();
		featMap.put(feature, featureSet);
		feats.add(feature);
	}
	

	@Override
	public void addValue(String feature, String value) {
		Objects.requireNonNull(value);
		Set<String> vals = getValues(feature);
		if (vals == null)
			return;
		
		vals.add(value);
	}

	@Override
	public Set<String> getValues(String feature) {
		return featMap.get(Objects.requireNonNull(feature));
	}

	@Override
	public List<String> getFeatureList() {
		return feats;
	}

	@Override
	public String getOutputType() {
		return out;
	}

	@Override
	public void setOutputType(String out) {
		this.out = Objects.requireNonNull(out);
		outTypes.clear();
	}

	@Override
	public Set<String> getOutputValues() {
		return outTypes;
	}


	@Override
	public void addOutputValue(String outVal) {
		outTypes.add(Objects.requireNonNull(outVal));
	}

}
