package srs.lab2.pw;

public interface PasswordHasher {
	
	byte[] hashPassword(char[] password, byte[] salt, int iterations);
	
}
