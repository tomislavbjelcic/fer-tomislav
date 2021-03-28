package srs.lab1.crypto;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.AEADBadTagException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;

import srs.lab1.pwmgr.InvalidByteArrayException;
import srs.lab1.pwmgr.Util;

public class FileCrypto {
	
	public static final String ENCRYPTION_ALGORITHM = "AES/GCM/NoPadding";
	public static final int IV_BYTE_LENGTH = 16;
	public static final int SALT_BYTE_LENGTH = 16;
	public static final int TAG_BIT_LENGTH = 128;
	
	private static final SecureRandom SECURE_RNG = new SecureRandom();
	
	private static Cipher getAESGCMCipherObject(int mode, SecretKey key, byte[] iv) {
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(ENCRYPTION_ALGORITHM);
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		AlgorithmParameterSpec gcmSpec = new GCMParameterSpec(TAG_BIT_LENGTH, iv);
		try {
			cipher.init(mode, key, gcmSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return cipher;
	}
	
	public static void encryptToFile(byte[] plain, String masterPassword, Path file) {
		
		byte[] iv = Util.getRandomBytes(IV_BYTE_LENGTH, SECURE_RNG);
		byte[] salt = Util.getRandomBytes(SALT_BYTE_LENGTH, SECURE_RNG);
		char[] passwordArr = masterPassword.toCharArray();
		
		SecretKey aesKey = KeyUtils.generateAESKeyFromPassword(passwordArr, salt);
		
		Cipher cipher = getAESGCMCipherObject(Cipher.ENCRYPT_MODE, aesKey, iv);
		
		byte[] cipherBytes = null;
		try {
			cipherBytes = cipher.doFinal(plain);
		} catch (IllegalBlockSizeException | BadPaddingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try (OutputStream os = Files.newOutputStream(file)) {
			//iv+salt+ciphertext
			os.write(iv);
			os.write(salt);
			os.write(cipherBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static byte[] decryptFromFile(String masterPassword, Path file) {
		
		byte[] iv = null;
		byte[] salt = null;
		byte[] cipherBytes = null;
		
		try (InputStream is = Files.newInputStream(file)) {
			iv = is.readNBytes(IV_BYTE_LENGTH);
			if (iv.length < IV_BYTE_LENGTH)
				throw new InvalidByteArrayException("File size too short!");
			salt = is.readNBytes(SALT_BYTE_LENGTH);
			if (salt.length < SALT_BYTE_LENGTH)
				throw new InvalidByteArrayException("File size too short!");
			
			
			cipherBytes = is.readAllBytes();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		char[] passwordArr = masterPassword.toCharArray();
		
		SecretKey aesKey = KeyUtils.generateAESKeyFromPassword(passwordArr, salt);
		
		Cipher cipher = getAESGCMCipherObject(Cipher.DECRYPT_MODE, aesKey, iv);
		
		byte[] plain = null;
		
		try {
			plain = cipher.doFinal(cipherBytes);
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			if (!(e instanceof AEADBadTagException))
				e.printStackTrace();
			else
				throw new InvalidByteArrayException("AEADBadTagException.");
		}
		
		return plain;
	}
	
}
