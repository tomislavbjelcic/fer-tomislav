package kik.lab2;

import java.math.BigInteger;
import java.nio.file.Path;
import java.util.Arrays;

import kik.lab2.impl.AES;

public class Main {
	
	public static void main(String[] args) {
		
		String str = "who/ah";
		String regex = "/";
		String[] splitted = str.split(regex);
		System.out.println(Arrays.toString(splitted));
		
		BigInteger bi = new BigInteger("128");
		String yy = bi.toString(16);
		System.out.println(yy);
		System.out.println(Util.byteToHex(bi.toByteArray()));
		
		System.out.println("olalahfwhifa".matches("ol.*"));
		
		var data = CryptoData.fromFile(Path.of("in.txt"));
		System.out.println(data);
		
		EncryptionAlgorithm ea = new AES();
		ea.extractData(data);
		
		System.out.println(ea.getAlgorithmName());
		System.out.println(ea.getTransformation());
		//var spec = ea.getAlgorithmParameterSpec();
		System.out.println();
		
	}
	
}
