package kik.lab1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

import kik.lab1.aes.AESECB;
import kik.lab1.util.ByteUtils;

public class ECBMain {
	
	public static void main(String[] args) {
		
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Demonstracijski program algoritma AES u ECB načinu rada.\n");
		System.out.print("Čitati iz datoteke? (y/n)");
		String in = sc.nextLine().strip().toLowerCase();
		byte[] plaintext = null;
		if (in.equals("y")) {
			System.out.print("Unesite putanju do datoteke >");
			String pathstr = sc.nextLine();
			Path path = Path.of(pathstr);
			try {
				plaintext = Files.readAllBytes(path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.print("Hex unos? (y/n)");
			String inh = sc.nextLine().strip().toLowerCase();
			if (inh.equals("y")) {
				System.out.println("Hex jasni/kriptirani tekst >");
				String hexplain = sc.nextLine();
				plaintext = ByteUtils.hexToByte(removeWhitespaces(hexplain));
			} else {
				System.out.println("Jasni/kriptirani tekst >");
				String hexplain = sc.nextLine();
				plaintext = hexplain.getBytes(StandardCharsets.UTF_8);
			}
		}
		
		byte[] keyBytes = null;
		System.out.print("Hex tajni ključ >");
		String hkey = sc.nextLine();
		keyBytes = ByteUtils.hexToByte(removeWhitespaces(hkey));
		
		try {
			AESECB cipher = new AESECB(keyBytes);
			byte[] ciphertext = null;
		} catch (RuntimeException ex) {
			
		}
		
		System.out.println("\n");
		System.out.println("Jasni tekst >" + ByteUtils.byteToHex(plaintext));
		System.out.println("Ključ >" + ByteUtils.byteToHex(keyBytes));
		
		
		
		
	}
	
	
	public static String removeWhitespaces(String str) {
		char[] arr = str.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : arr) {
			if (!Character.isWhitespace(c))
				sb.append(c);
		}
		return sb.toString();
	}
}
