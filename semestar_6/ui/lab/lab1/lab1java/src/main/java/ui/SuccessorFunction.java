package ui;

import java.util.Set;
import java.util.function.Function;

public interface SuccessorFunction<S extends State> extends Function<S, Set<StateCostPair<S>>> {
	
	Set<S> getStateSet();
	
}
