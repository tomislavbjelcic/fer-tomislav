package ui;

import java.util.Objects;

public class Literal {
	
	public static final char NEG_SYM = '~';
	
	private static final String REGEX = "" + NEG_SYM + "*\\w+";
	
	private String atom;
	private boolean negated;
	
	private Literal(String atom, boolean negated) {
		this.atom = atom;
		this.negated = negated;
	}
	
	public static Literal fromString(String literalstr) {
		Objects.requireNonNull(literalstr);
		String lowercase = literalstr.toLowerCase().strip();
		if (!lowercase.matches(REGEX))
			throw new IllegalArgumentException("Invalid literal String: " + literalstr);
		
		boolean negated = false;
		int i = 0;
		int len = lowercase.length();
		for (; i<len; i++) {
			char ch = lowercase.charAt(i);
			if (ch != NEG_SYM)
				break;
			negated = !negated;
		}
		
		String atom = lowercase.substring(i);
		return new Literal(atom, negated);
	}
	
	public String getAtom() {
		return atom;
	}
	
	public boolean isNegated() {
		return negated;
	}
	
	public Literal noNegated() {
		return new Literal(atom, false);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(atom, negated);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!(obj instanceof Literal))
			return false;
		Literal other = (Literal) obj;
		boolean eq = this.negated == other.negated && this.atom.equals(other.atom);
		return eq;
	}
	
	@Override
	public String toString() {
		return negated ? NEG_SYM+atom : atom;
	}
	
	
}
