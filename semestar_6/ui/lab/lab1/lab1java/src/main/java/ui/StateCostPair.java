package ui;

import java.util.Objects;

public class StateCostPair<S extends State> {
	
	private static final double DEFAULT_COST = 1.0;
	
	private S state;
	private double cost;
	
	public StateCostPair(S state) {
		this(state, DEFAULT_COST);
	}
	
	public StateCostPair(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}
	
	public S getState() {
		return state;
	}
	
	public double getCost() {
		return cost;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(state);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		
		StateCostPair<?> other = (StateCostPair<?>) obj;
		return this.state.equals(other.state);
	}
	
	@Override
	public String toString() {
		return "(" + state.toString() + ", " + cost + ")";
	}
	
}
