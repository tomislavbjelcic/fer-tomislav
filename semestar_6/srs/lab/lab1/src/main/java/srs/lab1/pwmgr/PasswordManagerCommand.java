package srs.lab1.pwmgr;

public interface PasswordManagerCommand {
	
	String execute(String[] args);
	String getCommandName();
	
}
