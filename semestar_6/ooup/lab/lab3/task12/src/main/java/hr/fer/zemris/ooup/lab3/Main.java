package hr.fer.zemris.ooup.lab3;

import hr.fer.zemris.ooup.lab3.model.Animal;

public class Main {
	
	public static void main(String[] args) {
		
		String[] kinds = new String[] {"Dog", "Parrot", "Cat"};
		String name = "Mirko";
		
		for (String kind : kinds) {
			Animal animal = AnimalFactory.newInstance(kind, name);
			if (animal == null) {
				System.out.println("Unable to load animal kind " + kind);
				continue;
			}
			
			animal.animalPrintGreeting();
			animal.animalPrintMenu();
		}
		
	}
	
}
