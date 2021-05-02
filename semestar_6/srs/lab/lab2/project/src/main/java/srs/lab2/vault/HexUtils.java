package srs.lab2.vault;

import java.util.Objects;

public class HexUtils {
	
	private static final String HEX_REGEX = "(\\p{XDigit})*";
	private static final int INT_MASK = 0x00_00_00_ff;
	private static final int LEFT_MASK = 0x00_00_00_f0;
	private static final int RIGHT_MASK = 0x00_00_00_0f;
	private static final char[] DIGITS_CHARS = new char[] {
			'0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f'
	};
	
	private HexUtils() {}
	
	public static String byteToHex(byte[] bytes) {
		Objects.requireNonNull(bytes);
		
		int len = bytes.length;
		StringBuilder sb = new StringBuilder(len * 2);
		
		for (byte b : bytes) {
			int i = ((int) b) & INT_MASK;
			int leftDigit = (i & LEFT_MASK) >>> 4;
			int rightDigit = (i & RIGHT_MASK);
			sb.append(DIGITS_CHARS[leftDigit]).append(DIGITS_CHARS[rightDigit]);
		}
		
		return sb.toString();
	}
	
	public static byte[] hexToByte(String hex) {
		Objects.requireNonNull(hex);
		checkHexString(hex);
		
		int count = hex.length() / 2;
		byte[] output = new byte[count];
		for (int i=0; i<count; i++) {
			int off = i*2;
			char firstDigitChar = hex.charAt(off);
			char secondDigitChar = hex.charAt(off+1);
			
			int firstDigit = hexDigitValue(firstDigitChar);
			int secondDigit = hexDigitValue(secondDigitChar);
			
			int val = firstDigit * 16 + secondDigit;
			output[i] = (byte) val;
		}
		return output;
	}
	
	private static void checkHexString(String hex) {
		int len = hex.length();
		if (len%2 != 0)
			throw new IllegalArgumentException("Hex " + hex + " has odd number of hex digits.");
		
		boolean match = hex.matches(HEX_REGEX);
		if (!match)
			throw new IllegalArgumentException("Invalid hex: " + hex);
	}
	
	private static int hexDigitValue(char digit) {
		boolean isDigit = Character.isDigit(digit);
		if (isDigit)
			return (int) (digit - '0');
		char lowercase = Character.toLowerCase(digit);
		return (int) (lowercase - 'a' + 10);
	}
	
}
