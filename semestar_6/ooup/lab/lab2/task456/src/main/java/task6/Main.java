package task6;

public class Main {
	
	public static void main(String[] args) {
		
		Sheet sh = new Sheet(5,5);
		
		sh.set("A1", "2");
		sh.set("A2", "5");
		sh.set("A3", "A1+A2");
		System.out.println(sh);
		
		sh.set("A1", "4");
		sh.set("A4", "A1+A3");
		
		sh.set("B1", "A4+1+7");
		System.out.println(sh);
		
		try {
			sh.set("A1", "1+B1"); // A1 -> B1 -> A4 -> A1 ...
			System.out.println(sh);
		} catch (RuntimeException ex) {
			System.out.println("Caught exception: " + ex);
		}
		
	}
	
}
