package srs.lab2.vault;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import srs.lab2.pw.PasswordHasher;
import srs.lab2.pw.PasswordUtils;

/**
 * Predstavlja spremnik podataka korisnika.
 * 
 * @author tomislav
 *
 */
public class Vault {
	
	/**
	 * Mapiranje username -> podaci o korisniku
	 */
	private Map<String, UserInfo> vaultMap;
	/**
	 * Funkcija sažetka koju koristi ovaj spremnik.
	 */
	private PasswordHasher hasher;
	
	public Vault(PasswordHasher hasher) {
		this(hasher, null);
	}
	
	Vault(PasswordHasher hasher, Map<String, UserInfo> vaultMap) {
		this.vaultMap = vaultMap == null ? new HashMap<>() : vaultMap;
		this.hasher = Objects.requireNonNull(hasher);
	}
	
	Map<String, UserInfo> getVaultMap() {
		return vaultMap;
	}
	
	public UserInfo getUserInfo(String userName) {
		return vaultMap.get(userName);
	}
	
	public void forcePassword(String userName) {
		UserInfo uinfo = getUserInfo(userName);
		if (uinfo == null)
			return;
		uinfo.changePass = true;
	}
	
	public void putUser(String userName, char[] password) {
		
		UserInfo uinfo = getUserInfo(userName);
		boolean exists = uinfo != null;
		UserInfo ui = exists ? uinfo : new UserInfo();
		
		byte[] salt = PasswordUtils.generateSalt();
		byte[] pwHash = PasswordUtils.generatePasswordHash(password, hasher, salt);
		
		ui.changePass = false;
		ui.salt = salt;
		ui.pwHash = pwHash;
		
		if (!exists)
			vaultMap.put(userName, ui);
	}
	
	public void removeUser(String userName) {
		vaultMap.remove(userName);
	}
	
	public boolean auth(String userName, char[] enteredPassword) {
		UserInfo uinfo = getUserInfo(userName);
		if (uinfo == null)
			return false;
		
		boolean match = PasswordUtils.checkPasswordMatch(enteredPassword, uinfo.pwHash, hasher, uinfo.salt);
		return match;
	}
	
}
