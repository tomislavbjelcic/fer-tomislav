package srs.lab2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import srs.lab2.command.UserAddCommand;
import srs.lab2.command.VaultCommand;
import srs.lab2.vault.Vault;

import static srs.lab2.Common.*;

public class UsermgmtMain {
	
	private static final Map<String, VaultCommand> CMDS = cmds();
	private static Map<String, VaultCommand> cmds() {
		Map<String, VaultCommand> cmds = new HashMap<>();
		cmds.put("add", new UserAddCommand());
		return cmds;
	}
	
	private static void printSupportedCommands() {
		CMDS.keySet().forEach(System.out::println);
	}
	
	public static void main(String[] args) {
		
		int len = args.length;
		if (len < 1) {
			System.out.println("Missing command. Supported: ");
			printSupportedCommands();
			return;
		}
		
		String command = args[0];
		VaultCommand cmd = CMDS.get(command);
		if (cmd == null) {
			System.out.println("Unsupported command \"" + command + "\". Supported: ");
			printSupportedCommands();
			return;
		}
		
		String[] cmdArgs = Arrays.copyOfRange(args, 1, len);
		Vault vault = LOADER.load(VAULT_PATH, HASHER);
		
		var result = cmd.execute(vault, cmdArgs);
		
		if (!result.success || result.vaultChanged) {
			LOADER.save(VAULT_PATH, vault);
		}
		
		System.out.println(result);
		
	}
	
}
