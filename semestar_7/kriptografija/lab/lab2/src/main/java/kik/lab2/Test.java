package kik.lab2;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.MGF1ParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;

public class Test {
	

	public static void main(String[] args) {
		AddBcProv.add();
		
		/*
		for (Provider provider: Security.getProviders()) {
			System.out.println(provider.getName());
			for (String key: provider.stringPropertyNames())
				System.out.println("\t" + key + "\t\t\t" + provider.getProperty(key));
		}*/
		
		/*
		String str = "Base64 kodiranje koristi se da se";
		byte[] strbytes = str.getBytes(StandardCharsets.US_ASCII);
		String encstr = Base64.getEncoder().encodeToString(strbytes);
		System.out.println(encstr);*/
		
		//String h = Hex.toHexString(null);
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("RSA/NONE/OAEPPadding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		KeyPairGenerator keygen = null;
		try {
			keygen = KeyPairGenerator.getInstance("RSA");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		keygen.initialize(1024);
		var pair = keygen.generateKeyPair();
		var pk = pair.getPublic();
		var sk = pair.getPrivate();
		
		SecureRandom rng = new SecureRandom();
		//int blocksize = cipher.getBlockSize();
		byte[] ivb = new byte[16];
		rng.nextBytes(ivb);
		AlgorithmParameterSpec spec = new OAEPParameterSpec("SHA256", 
				 "MGF1", MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT);
		try {
			cipher.init(Cipher.ENCRYPT_MODE, pk, spec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] randomdata = new byte[] {0};
		//rng.nextBytes(randomdata);
		
		System.out.println(cipher.getParameters());
		
		try {
			byte[] ciphertext = cipher.doFinal(randomdata);
			System.out.println(ciphertext.length);
			System.out.println(Util.byteToHex(ciphertext));
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
