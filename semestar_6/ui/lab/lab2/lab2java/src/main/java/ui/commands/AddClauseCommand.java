package ui.commands;

import ui.Clause;
import ui.ClauseSet;

public class AddClauseCommand extends AbstractUserCommand {

	public AddClauseCommand(char symbol) {
		super(symbol);
	}

	@Override
	public void execute(ClauseSet knowledge, Clause clause) {
		
		knowledge.addClause(clause);
		System.out.println("Added " + clause.toString());
		
	}

}
