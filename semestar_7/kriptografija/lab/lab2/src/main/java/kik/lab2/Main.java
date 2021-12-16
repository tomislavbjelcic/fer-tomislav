package kik.lab2;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class Main {
	
	public static void main(String[] args) throws Exception {
		AddBcProv.add();
		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] pt = "TomekBijeli".getBytes(StandardCharsets.US_ASCII);
		byte[] digest = md.digest(pt);
		System.out.println(Util.byteToHex(digest));
		
	}
	
}
