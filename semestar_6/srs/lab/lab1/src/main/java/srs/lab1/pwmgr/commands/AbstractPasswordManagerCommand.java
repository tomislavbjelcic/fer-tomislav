package srs.lab1.pwmgr.commands;

import srs.lab1.pwmgr.PasswordManagerCommand;

public abstract class AbstractPasswordManagerCommand implements PasswordManagerCommand {
	
	protected String cmdName;
	
	
	@Override
	public String getCommandName() {
		return cmdName;
	}
	
}
