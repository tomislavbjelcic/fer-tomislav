package ui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

public class ClauseSet implements Iterable<Clause> {
	
	
	private Set<Clause> clauses;
	
	public ClauseSet() {
		this(null);
	}
	
	private ClauseSet(Set<Clause> cls) {
		clauses = cls == null ? new HashSet<>() : new HashSet<>(cls);
	}
	
	public boolean removeClause(Clause c) {
		Objects.requireNonNull(c);
		return clauses.remove(c);
	}
	
	private Clause findRedundant(Clause c) {
		for (var current : this) {
			Clause keep = Clause.redundancyCheck(current, c);
			if (keep == null)
				continue;
			if (keep == current)
				break;
			
			return current;
		}
		return null;
	}
	
	public boolean addClause(Clause c) {
		Objects.requireNonNull(c);
		
		if (c.isTautology() || c.isNIL())
			return false;
		
		/*
		var it = clauses.iterator();
		while (it.hasNext()) {
			Clause current = it.next();
			Clause keep = Clause.redundancyCheck(current, c);
			if (keep == null)
				continue;
			if (keep == current)
				return false;
			
			it.remove();
			break;
		}*/
		
		Clause redundant = findRedundant(c);
		if (redundant != null) {
			if (redundant.equals(c))
				return false;
			clauses.remove(redundant);
		}
			
		
		clauses.add(c);
		
		return true;
		
	}
	
	public ClauseSet copy() {
		return new ClauseSet(clauses);
	}
	
	@Override
	public String toString() {
		return clauses.toString();
	}

	@Override
	public Iterator<Clause> iterator() {
		return clauses.iterator();
	}
	
}
