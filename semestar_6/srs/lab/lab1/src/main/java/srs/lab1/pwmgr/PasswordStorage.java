package srs.lab1.pwmgr;

public interface PasswordStorage {
	
	String get(String site);
	String put(String site, String password);
	byte[] convertToBytes();
	
	
}
