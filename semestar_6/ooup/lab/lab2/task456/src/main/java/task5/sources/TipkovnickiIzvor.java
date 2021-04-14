package task5.sources;

import java.io.IOException;
import java.util.Scanner;

import task5.IzvorBrojeva;

public class TipkovnickiIzvor implements IzvorBrojeva {
	
	private Scanner sc = new Scanner(System.in);

	@Override
	public void close() throws IOException {
		// nemoj zatvoriti scanner jer System.in ne smije biti zatvoren
	}

	@Override
	public int getNext() {
		boolean hasNext = sc.hasNextInt();
		int i = hasNext ? sc.nextInt() : -1;
		return i;
	}

}
