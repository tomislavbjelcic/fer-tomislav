package hr.fer.zemris.ooup.lab3.model.plugins;

import hr.fer.zemris.ooup.lab3.model.NamedAnimal;

public class Parrot extends NamedAnimal {

	public Parrot(String name) {
		super(name);
	}

	@Override
	public String greet() {
		return "papiga!";
	}

	@Override
	public String menu() {
		return "bilo što";
	}

	

}
