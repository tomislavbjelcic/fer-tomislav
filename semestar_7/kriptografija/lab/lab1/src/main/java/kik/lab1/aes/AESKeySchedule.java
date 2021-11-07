package kik.lab1.aes;

import java.util.List;

import kik.lab1.util.ByteUtils;

public class AESKeySchedule {
	
	public static final List<Integer> PERMITTED_KEY_SIZES_BITS = List.of(128, 192, 256);
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
		this.words = new int[4*R];
		
	}
	
	private static int roundCount(int keySizeBits) {
		return 11 + ((keySizeBits - 128) >>> 5);
	}
	
	public void generate(byte[] keyBytes) {
		if (keyBytes.length != keySizeBits/8)
			throw new IllegalArgumentException("This AES Key Schedule accepts only " + keySizeBits + " bit keys!");
		
		int N = keySizeBits/32;
		for (int i=0; i<words.length; i++) {
			if (i<N) {
				words[i] = ByteUtils.fromBytes(keyBytes[i*4], keyBytes[i*4+1], keyBytes[i*4+2], keyBytes[i*4+3]);
			} else if (i>=N && i%N==0) {
				words[i] = words[i-N] ^ SBox.subWord(Integer.rotateLeft(words[i-1], 8)) ^ RCON[i/N - 1];
			} else if (i>=N && N>6 && i%N==4) {
				words[i] = words[i-N] ^ SBox.subWord(words[i-1]);
			} else {
				words[i] = words[i-N] ^ words[i-1];
			}
		}
	}
	
	public int[] getWords() {
		return words;
	}
	
	public static void main(String[] args) {
		String kh = "603deb1015ca71be2b73aef0857d77811f352c073b6108d72d9810a30914dff4";
		byte[] keyBytes = ByteUtils.hexToByte(kh);
		AESKeySchedule schedule = new AESKeySchedule(256);
		schedule.generate(keyBytes);
		int[] words = schedule.getWords();
		for (int i=0; i<words.length; i++)
			System.out.println("" + i + ". " + ByteUtils.intToHex(words[i]));
		
	}
	
}
