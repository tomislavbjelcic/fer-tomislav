package ui.commands;

import ui.UserCommand;

public abstract class AbstractUserCommand implements UserCommand {
	
	private char symbol;
	
	public AbstractUserCommand(char symbol) {
		this.symbol = symbol;
	}
	
	public char getSymbol() {
		return symbol;
	}
	
}
