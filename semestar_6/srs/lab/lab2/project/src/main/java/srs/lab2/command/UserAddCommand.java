package srs.lab2.command;

import java.util.Arrays;

import srs.lab2.pw.PasswordUtils;
import srs.lab2.vault.Vault;

import static srs.lab2.command.VaultCommandResult.*;

public class UserAddCommand extends AbstractVaultCommand {

	@Override
	protected VaultCommandResult execute(Vault vault, String username) {
		
		char[] pw = PasswordUtils.getPasswordFromConsole("Password: ");
		char[] repeat = PasswordUtils.getPasswordFromConsole("Repeat Password: ");
		boolean eq = Arrays.equals(pw, repeat);
		if (!eq)
			return fail("User add failed. Password mismatch.");
		
		try {
			vault.putUser(username, pw);
		} catch (IllegalArgumentException ex) {
			return fail("User add failed. " + ex.getMessage());
		}
		
		return success("User add successfully added.", true);
	}

	

}
