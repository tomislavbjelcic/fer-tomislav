package srs.lab2.command;

import static srs.lab2.command.VaultCommandResult.fail;
import static srs.lab2.command.VaultCommandResult.success;

import java.util.Arrays;

import srs.lab2.pw.PasswordUtils;
import srs.lab2.vault.UserInfo;
import srs.lab2.vault.Vault;

public class LoginCommand extends AbstractVaultCommand {

	@Override
	public VaultCommandResult execute(Vault vault, String username) {
		
		char[] pwEnter = PasswordUtils.getPasswordFromConsole("Password: ");
		boolean auth = vault.auth(username, pwEnter);
		
		if (!auth)
			return fail("Username or password incorrect.");
		
		UserInfo ui = vault.getUserInfo(username);
		boolean vaultChanged = false;
		if (ui.changePass) {
			char[] newPw = PasswordUtils.getPasswordFromConsole("New password: ");
			char[] repeat = PasswordUtils.getPasswordFromConsole("Repeat new password: ");
			boolean eq = Arrays.equals(newPw, repeat);
			if (!eq)
				return fail("Login failed. Password mismatch.");
			
			boolean samePw = vault.auth(username, newPw);
			if (samePw)
				return fail("Cannot have the same password from before!");
			
			String err = PasswordUtils.checkPassword(newPw);
			if (err != null)
				return fail(err);
			
			vault.putUser(username, newPw);
			vaultChanged = true;
		}
		
		return success("Login successful.", vaultChanged);
	}

}
