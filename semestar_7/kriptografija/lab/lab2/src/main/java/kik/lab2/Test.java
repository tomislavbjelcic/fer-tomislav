package kik.lab2;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

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
			cipher = Cipher.getInstance("DESede/CTR/NoPadding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		KeyGenerator keygen = null;
		try {
			keygen = KeyGenerator.getInstance("DESede");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		SecretKey symkey = keygen.generateKey();
		
		SecureRandom rng = new SecureRandom();
		byte[] ivb = new byte[8];
		rng.nextBytes(ivb);
		AlgorithmParameterSpec ivspec = new IvParameterSpec(ivb);
		try {
			cipher.init(Cipher.ENCRYPT_MODE, symkey, ivspec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		byte[] randomdata = new byte[50];
		rng.nextBytes(randomdata);
		
		System.out.println(cipher.getParameters());
		
		try {
			byte[] ciphertext = cipher.doFinal(randomdata);
			System.out.println(ciphertext.length);
		} catch (IllegalBlockSizeException | BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
