package ui.commands;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import ui.Clause;
import ui.UserCommand;
import ui.UserCommandInfo;

public class UserCommands {
	
	private static final Map<Character, UserCommand> CMD_MAP = cmdMap();
	private static final String REG = ".* .";
	
	private static Map<Character, UserCommand> cmdMap() {
		Map<Character, UserCommand> m = new HashMap<>();
		m.put('?', new QueryCommand('?'));
		m.put('+', new AddClauseCommand('+'));
		m.put('-', new RemoveClauseCommand('-'));
		return m;
	}
	
	private UserCommands() {}
	
	public static UserCommandInfo getFromString(String str) {
		Objects.requireNonNull(str);
		
		boolean match = str.matches(REG);
		if (!match)
			throw new IllegalArgumentException("Invalid String: " + str);
		
		int len = str.length();
		char last = str.charAt(len - 1);
		
		String substr = str.substring(0, len-2);
		Clause clause = Clause.fromString(substr);
		
		UserCommand cmd = CMD_MAP.get(last);
		if (cmd == null)
			throw new RuntimeException("Unknown command type: \"" + last + "\"");
		
		UserCommandInfo info = new UserCommandInfo();
		info.cmd = cmd;
		info.clause = clause;
		
		return info;
	}
	
}
