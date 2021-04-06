package ui;

import java.util.function.ToDoubleFunction;

public interface HeuristicFunction<S extends State> extends ToDoubleFunction<S>{
	
	static <T extends State> HeuristicFunction<T> getDefault() {
		return s -> 0.0;
	}
	
}
