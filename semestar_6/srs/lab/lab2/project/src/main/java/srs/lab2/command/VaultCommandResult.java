package srs.lab2.command;

public class VaultCommandResult {
	
	public boolean success;
	public boolean vaultChanged;
	public String msg;
	
	public VaultCommandResult() {}
	
	private VaultCommandResult(boolean success, boolean vaultChanged, String msg) {
		this.success = success;
		this.vaultChanged = vaultChanged;
		this.msg = msg;
	}
	
	public static VaultCommandResult fail(String msg, boolean vaultChanged) {
		return new VaultCommandResult(false, vaultChanged, msg);
	}
	
	public static VaultCommandResult fail(String msg) {
		return fail(msg, false);
	}
	
	public static VaultCommandResult success(String msg, boolean vaultChanged) {
		return new VaultCommandResult(true, vaultChanged, msg);
	}
	
	@Override
	public String toString() {
		return msg;
	}
	
}
