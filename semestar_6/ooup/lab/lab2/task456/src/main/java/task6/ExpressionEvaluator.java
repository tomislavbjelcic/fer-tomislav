package task6;

import java.util.Set;

public interface ExpressionEvaluator {
	
	double evaluate(String expr);
	Set<BinaryOperator> supportedOperators();
	
}
