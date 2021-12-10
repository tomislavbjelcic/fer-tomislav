package kik.lab2.format;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CryptoData {
	
	public static final String BEGIN = "---BEGIN CRYPTO DATA---";
	public static final String END = "---END CRYPTO DATA---";
	private static final Charset CS = StandardCharsets.US_ASCII;
	private static final String SEP = ":";
	private static final String PREF = "    ";
	
	public static final String DESCRIPTION = "description";
	public static final String FILE_NAME = "file name";
	public static final String METHOD = "method";
	public static final String KEY_LENGTH = "key length";
	public static final String SECRET_KEY = "secret key";
	public static final String IV = "initialization vector";
	public static final String MODULUS = "modulus";
	public static final String PUBLIC_EXPONENT = "public exponent";
	public static final String PRIVATE_EXPONENT = "private exponent";
	public static final String SIG = "signature";
	public static final String ENCRYPTED_DATA = "data";
	public static final String ENVELOPE_DATA = "envelope data";
	public static final String ENVELOPE_CRYPT_KEY = "envelope crypt key";
	
	private static final Set<String> FIELDS = new HashSet<>();
	
	static {
		registerFields(DESCRIPTION, FILE_NAME, METHOD, KEY_LENGTH, SECRET_KEY, 
				IV, MODULUS, PUBLIC_EXPONENT, PRIVATE_EXPONENT, SIG,
				ENCRYPTED_DATA, ENVELOPE_DATA, ENVELOPE_CRYPT_KEY);
	}
	
	public static void registerFields(String... fields) {
		for (String f : fields) {
			FIELDS.add(f.toLowerCase());
		}
	}
	
	private Map<String, List<String>> data = new LinkedHashMap<>();
	
	public Map<String, List<String>> getData() {
		return data;
	}
	
	public static CryptoData fromFile(Path file) {
		CryptoData cd = new CryptoData();
		try (BufferedReader br = Files.newBufferedReader(file, CS)) {
			int state = 0;	// 0=nije doslo do pocetka, 1=citaju se kriptografski podaci, 2=doslo je do kraja
			boolean readingField = false;
			String field = null;
			List<String> values = null;
			while (true) {
				String lineRead = br.readLine();
				if (lineRead == null)
					break;
				
				String lineStripped = lineRead.strip();
				if (state == 0) {
					if (lineStripped.equals(BEGIN)) {
						state = 1;
					}
					continue;
				}
				
				if (state == 2)
					continue;
				
				if (state == 1) {
					if (!readingField) {
						if (lineStripped.isEmpty())
							continue;
						
						if (lineRead.equals(END)) {
							state = 2;
							continue;
						}
						
						if (!lineStripped.endsWith(SEP))
							throw new RuntimeException(lineStripped + " ne zavrsava sa " + SEP);
						
						field = lineRead.substring(0, lineRead.length()-SEP.length()).toLowerCase();
						
						if (!FIELDS.contains(field))
							throw new RuntimeException("Nepoznato polje: " + field);
						readingField = true;
						values = new ArrayList<>();
						cd.data.put(field, values);
						continue;
					}
					
					if (lineStripped.isEmpty()) {
						field = null;
						readingField = false;
						continue;
					}
					
					if (!lineRead.startsWith(PREF))
						throw new RuntimeException("Invalid line: " + lineRead);
					
					
					values.add(lineStripped);
					continue;
				}
				
				
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cd;
	}
	
	public static void main(String[] args) {
		Path file = Path.of("in.txt");
		CryptoData cryptoData = fromFile(file);
		var dataMap = cryptoData.getData();
		for (var e : dataMap.entrySet()) {
			System.out.println(e);
		}
	}
	
}
