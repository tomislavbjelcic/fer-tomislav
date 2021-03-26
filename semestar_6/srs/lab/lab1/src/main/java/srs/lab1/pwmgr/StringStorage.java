package srs.lab1.pwmgr;

import java.util.Map;
import java.util.stream.Stream;

public class StringStorage implements PasswordStorage {
	
	private static final char SEP = ',';
	private static final char ESCAPE = '\\';
	private static final String ESCAPE_REGEX = "\\" + ESCAPE + ".";
	
	private StringBuilder storage;
	private Map<String, String> pwMap;
	
	
	public StringStorage(String data) {
		Stream<String> linesStream = data.lines();
		linesStream.map(null);
		
	}
	
	@Override
	public String get(String site) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String put(String site, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] convertToBytes() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static String removeEscapes(String str) {
		String removed = str.replaceAll(ESCAPE_REGEX, str);
		return removed;
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
	
	public static void main(String[] args) {
		String str = "www.fer.hr,m";
		String e = insertEscapes(str);
		System.out.println(e);
	}
}
