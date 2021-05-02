package srs.lab2.vault;

public class UserInfo {
	
	private static final String SEP = ",";
	
	
	public boolean changePass;
	public byte[] salt;
	public byte[] pwHash;
	
	public UserInfo() {}
	
	public UserInfo(boolean changePass, byte[] salt, byte[] pwHash) {
		this.changePass = changePass;
		this.salt = salt;
		this.pwHash = pwHash;
	}
	
	public static UserInfo parse(String str) {
		String[] splitted = str.split(SEP);
		
		boolean changePass = Boolean.parseBoolean(splitted[0]);
		byte[] salt = HexUtils.hexToByte(splitted[1]);
		byte[] pwHash = HexUtils.hexToByte(splitted[2]);
		
		UserInfo userInfo = new UserInfo(changePass, salt, pwHash);
		return userInfo;
	}
	
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
