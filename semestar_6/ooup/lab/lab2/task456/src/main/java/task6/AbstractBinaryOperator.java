package task6;

import java.util.Objects;

public abstract class AbstractBinaryOperator implements BinaryOperator {
	
	protected String symbol;
	
	public AbstractBinaryOperator(String symbol) {
		this.symbol = Objects.requireNonNull(symbol);
	}
	
	@Override
	public String symbol() {
		return symbol;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return false;
		if (!(obj instanceof BinaryOperator))
			return false;
		BinaryOperator other = (BinaryOperator) obj;
		return symbol.equals(other.symbol());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(symbol);
	}
	
	@Override
	public String toString() {
		return symbol;
	}
	
}
