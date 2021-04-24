package ui;

public class UserCommandInfo {
	
	public UserCommand cmd;
	public Clause clause;
	
	@Override
	public String toString() {
		return "User's command: " + clause.toString() + ' ' + cmd.getSymbol();
	}
	
}
