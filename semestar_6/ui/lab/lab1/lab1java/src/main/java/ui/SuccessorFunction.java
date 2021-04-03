package ui;

import java.util.Set;
import java.util.function.Function;

import ui.collections.MySet;

public interface SuccessorFunction<S extends State> extends Function<S, MySet<StateCostPair<S>>> {
	
	Set<S> getStateSet();
	Double getCost(S from, S to);
	
}
