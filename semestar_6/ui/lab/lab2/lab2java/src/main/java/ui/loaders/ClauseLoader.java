package ui.loaders;

import ui.Clause;

public class ClauseLoader extends AbstractLoader<Clause> {

	@Override
	protected Clause load(String str) {
		return Clause.fromString(str);
	}
	
	
	
}
