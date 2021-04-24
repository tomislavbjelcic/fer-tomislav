package ui.commands;

import ui.Clause;
import ui.ClauseSet;
import ui.resolution.ProofResult;
import ui.resolution.Reasoning;

public class QueryCommand extends AbstractUserCommand {
	
	public QueryCommand(char symbol) {
		super(symbol);
	}

	@Override
	public void execute(ClauseSet knowledge, Clause clause) {
		
		ProofResult res = Reasoning.prove(knowledge, clause);
		System.out.println(res);
		
	}

}
