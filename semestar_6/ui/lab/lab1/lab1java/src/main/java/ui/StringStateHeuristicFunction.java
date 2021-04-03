package ui;

import java.util.HashMap;
import java.util.Map;

public class StringStateHeuristicFunction implements HeuristicFunction<StringState> {
	
	Map<StringState, Double> hmap;
	
	public StringStateHeuristicFunction() {
		hmap = new HashMap<>();
	}
	
	public void defineHeuristic(StringState state, double value) {
		hmap.put(state, value);
	}
	
	@Override
	public double applyAsDouble(StringState value) {
		Double val = hmap.get(value);
		double h = val==null ? 0.0 : val.doubleValue();
		return h;
	}

}
