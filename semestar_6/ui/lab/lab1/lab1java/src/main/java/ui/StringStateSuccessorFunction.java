package ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ui.collections.MySet;

public class StringStateSuccessorFunction implements SuccessorFunction<StringState> {
	
	
	private Map<StringState, MySet<StateCostPair<StringState>>> succ;
	
	public StringStateSuccessorFunction() {
		succ = new HashMap<>();
	}
	
	
	
	public void defineSuccessors(StringState s, MySet<StateCostPair<StringState>> stateCostPairs) {
		succ.put(s, stateCostPairs);
		
	}
	
	@Override
	public MySet<StateCostPair<StringState>> apply(StringState t) {
		return succ.get(t);
	}



	@Override
	public Set<StringState> getStateSet() {
		return succ.keySet();
	}



	@Override
	public Double getCost(StringState from, StringState to) {
		var ms = apply(from);
		if (ms==null)
			return null;
		StringState dummy = new StringState(to.toString());
		StateCostPair<StringState> dummypair = new StateCostPair<StringState>(dummy);
		StateCostPair<StringState> realPair = ms.getByKey(dummypair);
		return realPair == null ? null : realPair.getCost();
	}


}
