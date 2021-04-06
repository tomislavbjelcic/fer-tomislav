package ui;

import java.util.Objects;

public class StringState implements State {
	
	private String str;
	
	public StringState(String str) {
		this.str = str;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		
		StringState other = (StringState) obj;
		return str.equals(other.str);
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(str);
	}
	
	@Override
	public String toString() {
		return str;
	}
	
}
