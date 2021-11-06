package kik.lab1.aes;

import java.util.List;

public class AESKeySchedule {
	
	private static final List<Integer> PERMITTED_KEY_SIZES_BITS = List.of(128, 192, 256);
	private static final int[] RCON = new int[] {
		0x01_00_00_00,
		0x02_00_00_00,
		0x04_00_00_00,
		0x08_00_00_00,
		0x10_00_00_00,
		0x20_00_00_00,
		0x40_00_00_00,
		0x80_00_00_00,
		0x1b_00_00_00,
		0x36_00_00_00
	};
	
	private int R;
	private int keySizeBits;
	private int[] words;
	
	public AESKeySchedule(int keySizeBits) {
		
		if (!PERMITTED_KEY_SIZES_BITS.contains(keySizeBits)) {
			throw new IllegalArgumentException("Invalid key size! Permitted: " + PERMITTED_KEY_SIZES_BITS);
		}
		this.keySizeBits = keySizeBits;
		this.R = roundCount(keySizeBits);
		this.words = new int[4*R-1];
		
	}
	
	private static int roundCount(int keySizeBits) {
		return 11 + ((keySizeBits - 128) >>> 5);
	}
	
	public void generate(byte[] keyBytes) {
		if (keyBytes.length != keySizeBits>>>8)
			throw new IllegalArgumentException("This AES Key Schedule accepts only " + keySizeBits + " bit keys!");
		
		int k = words[0] + RCON[0];
		System.out.println(k);
	}
	
	public static void main(String[] args) {
		int r = roundCount(128);
		System.out.println(r);
	}
	
}
