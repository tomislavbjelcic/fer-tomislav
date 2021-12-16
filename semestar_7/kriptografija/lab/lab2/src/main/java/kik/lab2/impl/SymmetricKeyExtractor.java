package kik.lab2.impl;

import java.security.Key;
import java.util.List;
import java.util.Map;

import kik.lab2.KeyExtractor;

public abstract class SymmetricKeyExtractor implements KeyExtractor {
	
	@Override
	public Key extract(Map<String, List<String>> data) {
		
		return null;
	}
	
}
