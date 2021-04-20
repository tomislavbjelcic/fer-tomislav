package ui;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
	
	public Set<Literal> negate() {
		Set<Literal> negs = new HashSet<>();
		if (isTautology || isNIL())
			return negs;
		
		for (var entry : literals.entrySet()) {
			Literal l = entry.getValue();
			Literal lneg = l.negate();
			negs.add(lneg);
		}
		return negs;
	}
	
	public static Clause redundancyCheck(Clause c1, Clause c2) {
		Objects.requireNonNull(c1);
		Objects.requireNonNull(c2);
		
		if (c1.isTautology() || c2.isTautology())
			return null;
		if (c1.equals(c2))
			return c1;
		
		int lc1 = c1.literalCount();
		int lc2 = c2.literalCount();
		Clause smaller = lc1<lc2 ? c1 : c2;
		Clause other = smaller==c1 ? c2 : c1;
		for (var entry : smaller.literals.entrySet()) {
			Literal spl = entry.getKey();
			Literal ol = other.literals.get(spl);
			if (ol == null)
				return null;
			
			Literal l = entry.getValue();
			if (!ol.equals(l))
				return null;
		}
		return smaller;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (!(obj instanceof Clause))
			return false;
		Clause other = (Clause) obj;
		boolean eqistaut = this.isTautology == other.isTautology;
		boolean bothtaut = this.isTautology && other.isTautology;
		if (bothtaut)
			return true;
		boolean eq = eqistaut && this.literals.equals(other.literals);
		return eq;
	}
	
	@Override
	public int hashCode() {
		if (isTautology)
			return Objects.hash(true);
		return Objects.hash(isTautology, literals);
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
