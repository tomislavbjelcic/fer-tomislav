package srs.lab1.pwmgr;

import java.util.Random;

public class Util {
	
	private static final Random RNG = new Random();
	
	private Util() {}
	
	public static byte[] concatByteArrays(byte[]... arrays) {
		int len = 0;
		for (byte[] a : arrays)
			len += a.length;
		
		byte[] result = new byte[len];
		int off = 0;
		for (byte[] a : arrays) {
			System.arraycopy(a, 0, result, off, a.length);
			off += a.length;
		}
		return result;
	}
	
	public static byte[] getRandomBytes(int size) {
		byte[] out = new byte[size];
		RNG.nextBytes(out);
		return out;
	}
}
