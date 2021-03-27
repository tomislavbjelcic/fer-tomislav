package srs.lab1.pwmgr;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StringStorage implements PasswordStorage {
	
	private static final char SEP = ',';
	private static final char ESCAPE = '\\';
	private static final String ESCAPE_REGEX = "\\" + ESCAPE + ".";
	private static final int PREFIX_BYTE_SIZE = 16;
	
	private StringBuilder storage;
	private Map<String, String> pwMap = new HashMap<>();
	
	
	public StringStorage(String data) {
		storage = new StringBuilder(data);
		data.lines().map(StringStorage::splitUserPasswordPair)
					.forEach(a -> pwMap.put(a[0], a[1]));
		
	}
	
	public static StringStorage fromBytes(byte[] bytes) {
		int off = PREFIX_BYTE_SIZE;
		int len = bytes.length - PREFIX_BYTE_SIZE;
		String data = new String(bytes, off, len, StandardCharsets.UTF_8);
		return new StringStorage(data);
	}
	
	@Override
	public String get(String site) {
		return pwMap.get(site);
	}

	@Override
	public String put(String site, String password) {
		String retval = pwMap.put(site, password);
		String pairStr = insertEscapes(site) + SEP + insertEscapes(password) + '\n';
		storage.append(pairStr);
		return retval;
	}

	@Override
	public byte[] convertToBytes() {
		byte[] prefix = Util.getRandomBytes(PREFIX_BYTE_SIZE);
		byte[] data = storage.toString().getBytes(StandardCharsets.UTF_8);
		return Util.concatByteArrays(prefix, data);
	}
	
	private static String removeEscapes(String str) {
		StringBuilder sb = new StringBuilder(str);
		for (int i=0; i<sb.length(); i++) {
			char c = sb.charAt(i);
			if (c == ESCAPE) {
				int nextIdx = i+1;
				if (nextIdx >= sb.length())
					throw new IllegalArgumentException("Invalid escape sequence: " + str);
				char nextc = sb.charAt(nextIdx);
				if (nextc != ESCAPE && nextc != SEP)
					throw new IllegalArgumentException("Invalid escape sequence: " + str);
				sb.deleteCharAt(i);
				continue;
			}
		}
		return sb.toString();
	}
	
	private static String insertEscapes(String str) {
		StringBuilder output = new StringBuilder();
		for (int i=0, len=str.length(); i<len; i++) {
			char c = str.charAt(i);
			if (c == ESCAPE || c == SEP)
				output.append(ESCAPE);
			output.append(c);
		}
		return output.toString();
	}
	
	private static String[] splitUserPasswordPair(String line) {
		boolean escapeActive = false;
		int sepIdx = -1;
		for (int i=0, len=line.length(); i<len; i++) {
			char c = line.charAt(i);
			if (c == ESCAPE) {
				escapeActive = !escapeActive;
				continue;
			}
			if (c == SEP && !escapeActive) {
				sepIdx = i;
				break;
			}
			escapeActive = false;
		}
		
		String[] splitted = new String[] {
				line.substring(0, sepIdx),
				line.substring(sepIdx+1)
		};
		return splitted;
	}
	
}
