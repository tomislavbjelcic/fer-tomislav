package kik.lab2;

import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;

public enum EncryptionMode {
	
	ECB(true, false),
	NONE(true, false),
	CBC(true, true),
	OFB(false, true),
	CFB(false, true),
	CTR(false, true),
	GCM(false, true);
	
	private final boolean requiresPadding;
	private final boolean requiresIv;
	
	private EncryptionMode(boolean requiresPadding, boolean requiresIv) {
		this.requiresPadding = requiresPadding;
		this.requiresIv = requiresIv;
	}
	
	public String getDefaultPadding() {
		return requiresPadding ? "PKCS5Padding" : "NoPadding";
	}
	
	public AlgorithmParameterSpec getAlgorithmParameterSpec(byte[] iv) {
		if (!requiresIv)
			return null;
		
		return this==GCM ? new GCMParameterSpec(128, iv) : new IvParameterSpec(iv);
	}
	
	public static void main(String[] args) {
		System.out.println(GCM.toString());
	}
	
}
