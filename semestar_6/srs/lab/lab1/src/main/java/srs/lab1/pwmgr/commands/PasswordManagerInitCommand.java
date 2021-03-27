package srs.lab1.pwmgr.commands;

import srs.lab1.pwmgr.PasswordManagerCommand;

public class PasswordManagerInitCommand extends AbstractPasswordManagerCommand {
	
	{
		cmdName = "init";
	}
	
	
	
	@Override
	public String execute(String[] args) {
		if (args.length != 1)
			return "there has to be exactly 1 argument: master password.";
		
		String masterPassword = args[0];
		char[] chars = masterPassword.toCharArray();
		
		return null;
	}
	
	
	
}
