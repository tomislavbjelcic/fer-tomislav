package ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Clause {
	
	public static final char OR_SYM = 'v';
	
	private static final String REG = "" + ' ' + OR_SYM + ' ';
	
	private static final Clause TAUT = taut();
	private static Clause taut() {
		Clause c = new Clause();
		c.isTautology = true;
		return c;
	}
	
	private Map<Literal, Literal> literals = new HashMap<>();
	private boolean isTautology = false;
	
	public static Clause fromLiterals(Literal[] literals) {
		Objects.requireNonNull(literals);
		
		Clause clause = new Clause();
		
		for (Literal l : literals) {
			if (clause.isTautology)
				break;
			clause.addLiteral(l);
		}
		return clause;
	}
	
	public static Clause fromString(String str) {
		Objects.requireNonNull(str);
		String lwrcs = str.strip().toLowerCase();
		if (str.isEmpty())
			return new Clause();
		
		String[] splitted = lwrcs.split(REG);
		int len = splitted.length;
		Literal[] lits = new Literal[len];
		for (int i=0; i<len; i++)
			lits[i] = Literal.fromString(splitted[i]);
		return fromLiterals(lits);
	}
	
	public boolean isTautology() {
		return isTautology;
	}
	
	public int literalCount() {
		return isTautology ? -1 : literals.size();
	}
	
	public boolean isNIL() {
		return literalCount() == 0;
	}
	
	public void addLiteral(Literal l) {
		Objects.requireNonNull(l);
		if (isTautology)
			return;
		
		Literal noneg = l.noNegated();
		Literal existing = literals.get(noneg);
		if (existing == null) {
			literals.put(noneg, l);
			return;
		}
		
		boolean negEx = existing.isNegated();
		if (negEx != l.isNegated())
			isTautology = true;
		
	}
	
	@Override
	public String toString() {
		if (isTautology)
			return "TAUTOLOGY";
		if (isNIL())
			return "NIL";
		
		Collection<Literal> vals = literals.values();
		String str = vals.stream().map(Literal::toString).collect(Collectors.joining(REG));
		return str;
	}
	
	
}
