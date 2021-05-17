package hr.fer.zemris.ooup.lab3.texteditor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@FunctionalInterface
interface I {
	void method();
}

class O {
	void something() {
		System.out.println("Something");
	}
}

public class Main {
	
	public static void main(String[] args) {
		
		String str = "wort\nez\n";
		
		List<String> list = str.lines().collect(Collectors.toCollection(ArrayList::new));
		System.out.println("Count: " + list.size());
		list.forEach(System.out::println);
		
	}
	
}