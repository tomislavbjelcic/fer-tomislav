package ui;

import java.util.function.ToDoubleFunction;

public interface HeuristicFunction<S extends State> extends ToDoubleFunction<S>{
	
	HeuristicFunction<? extends State> DEFAULT = s -> 0.0;
	
}
