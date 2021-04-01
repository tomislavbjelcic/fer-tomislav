package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class StringStateSuccessorFunction implements SuccessorFunction<StringState> {
	
	
	private Map<StringState, Set<StateCostPair<StringState>>> succ;
	
	public StringStateSuccessorFunction() {
		succ = new HashMap<>();
	}
	
	
	
	public void defineSuccessors(StringState s, Set<StateCostPair<StringState>> stateCostPairs) {
		succ.put(s, stateCostPairs);
		
	}
	
	@Override
	public Set<StateCostPair<StringState>> apply(StringState t) {
		return succ.get(t);
	}

}
