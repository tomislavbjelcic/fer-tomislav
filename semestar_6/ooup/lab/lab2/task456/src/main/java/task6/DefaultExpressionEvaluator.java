package task6;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import task6.operator.OperatorPlus;

public class DefaultExpressionEvaluator implements ExpressionEvaluator {
	
	private static final Set<BinaryOperator> SUPPORTED = Set.of(new OperatorPlus());
	
	@Override
	public double evaluate(String expr) {
		Map<String, BinaryOperator> symbols = new HashMap<>();
		for (var op : SUPPORTED) {
			symbols.put(op.symbol(), op);
		}
		
		
		return 0;
	}

	@Override
	public Set<BinaryOperator> supportedOperators() {
		return SUPPORTED;
	}
	
	
	
}
