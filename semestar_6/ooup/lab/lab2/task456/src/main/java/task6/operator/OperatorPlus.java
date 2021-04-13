package task6.operator;

import task6.AbstractBinaryOperator;

public class OperatorPlus extends AbstractBinaryOperator {

	

	public OperatorPlus() {
		super("+");
	}

	@Override
	public double operate(double left, double right) {
		return Double.sum(left, right);
	}

}
