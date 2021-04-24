package ui;

public interface UserCommand {
	
	void execute(ClauseSet knowledge, Clause clause);
	char getSymbol();

}
