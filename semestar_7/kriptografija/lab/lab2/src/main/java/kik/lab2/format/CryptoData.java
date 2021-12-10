package kik.lab2.format;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class CryptoData {
	
	public static final String BEGIN = "---BEGIN CRYPTO DATA---";
	public static final String END = "---END CRYPTO DATA---";
	private static final Charset CS = StandardCharsets.US_ASCII;
	
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
	
	private Map<String, String> data = new LinkedHashMap<>();
	
	
	public static CryptoData fromFile(Path file) {
		
		return null;
	}
	
}
