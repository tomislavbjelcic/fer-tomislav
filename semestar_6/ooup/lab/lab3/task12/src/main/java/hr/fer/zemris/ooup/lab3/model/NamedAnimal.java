package hr.fer.zemris.ooup.lab3.model;

public abstract class NamedAnimal extends Animal {
	
	protected String name;
	
	public NamedAnimal(String name) {
		this.name = name;
	}
	
	@Override
	public String name() {
		return name;
	}
	
}
