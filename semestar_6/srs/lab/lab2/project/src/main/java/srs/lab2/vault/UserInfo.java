package srs.lab2.vault;

/**
 * Razred predstavlja zapis koji sprema potrebne podatke nekog korisnika (osim username-a).
 * 
 * @author tomislav
 *
 */
public class UserInfo {
	
	/**
	 * Separator koji se koristi pri zapisivanju članskih varijabli u String.
	 */
	private static final String SEP = ",";
	
	
	/**
	 * Zastavica koja označava da korisnik mora promijeniti lozinku nakon sljedeće prijave.
	 */
	public boolean changePass;
	/**
	 * Slučajan niz bajtova, tzv. salt, koji se koristio za generiranje sažetka lozinke.
	 */
	public byte[] salt;
	/**
	 * Sažetak (hash) lozinke.
	 */
	public byte[] pwHash;
	
	public UserInfo() {}
	
	public UserInfo(boolean changePass, byte[] salt, byte[] pwHash) {
		this.changePass = changePass;
		this.salt = salt;
		this.pwHash = pwHash;
	}
	
	/**
	 * Iz String zapisa podataka {@code str} stvara primjerak ovog razreda.
	 * 
	 * @param str
	 * @return
	 */
	public static UserInfo parse(String str) {
		String[] splitted = str.split(SEP);
		
		boolean changePass = Boolean.parseBoolean(splitted[0]);
		byte[] salt = HexUtils.hexToByte(splitted[1]);
		byte[] pwHash = HexUtils.hexToByte(splitted[2]);
		
		UserInfo userInfo = new UserInfo(changePass, salt, pwHash);
		return userInfo;
	}
	
	/**
	 * Pretvara zapis u String na način da se nizovi bajtova prvo pretvore u hex zapis, pa se 
	 * onda sve članske varijable konkateniraju koristeći separator {@link UserInfo#SEP}.
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		String saltHex = HexUtils.byteToHex(salt);
		String hashHex = HexUtils.byteToHex(pwHash);
		
		sb.append(changePass).append(SEP)
			.append(saltHex).append(SEP)
			.append(hashHex);
		
		return sb.toString();
	}
	
}
