package kik.lab2;

import java.util.Map;
import java.util.function.Supplier;

import kik.lab2.impl.RSAKeyExtractor;

public class Common {
	
	public static final Map<String, Supplier<KeyExtractor>> KEY_EXTRACTORS 
		= Map.of("RSA", RSAKeyExtractor::new);
	
}
