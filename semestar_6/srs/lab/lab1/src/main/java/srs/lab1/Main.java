package srs.lab1;

import java.util.Arrays;
import java.util.Map;

import srs.lab1.pwmgr.PasswordManagerCommand;
import srs.lab1.pwmgr.commands.PasswordManagerGetCommand;
import srs.lab1.pwmgr.commands.PasswordManagerInitCommand;
import srs.lab1.pwmgr.commands.PasswordManagerPutCommand;

public class Main {
	
	private static final Map<String, PasswordManagerCommand> SUPPORTED_COMMANDS = Map.of(
			"init", new PasswordManagerInitCommand(),
			"put", new PasswordManagerPutCommand(),
			"get", new PasswordManagerGetCommand()
	);
	
	private static void printSupportedCommands() {
		SUPPORTED_COMMANDS.keySet().forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		
		if (args.length == 0) {
			System.out.println("Missing command. Supported: ");
			printSupportedCommands();
			return;
		}
		
		String command = args[0];
		PasswordManagerCommand cmd = SUPPORTED_COMMANDS.get(command);
		if (cmd == null) {
			System.out.println("Unsupported command \"" + command + "\". Supported: ");
			printSupportedCommands();
			return;
		}
		
		String[] argsNoCmdName = Arrays.copyOfRange(args, 1, args.length);
		
		String resultMsg = cmd.execute(argsNoCmdName);
		System.out.println("Command " + cmd.getCommandName() + ": " + resultMsg);
	}
	
}
