package ui.commands;

import ui.Clause;
import ui.ClauseSet;

public class RemoveClauseCommand extends AbstractUserCommand {

	public RemoveClauseCommand(char symbol) {
		super(symbol);
	}

	@Override
	public void execute(ClauseSet knowledge, Clause clause) {
		
		knowledge.removeClause(clause);
		System.out.println("removed " + clause.toString());
		
	}

}
