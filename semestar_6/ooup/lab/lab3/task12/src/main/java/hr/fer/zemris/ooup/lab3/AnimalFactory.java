package hr.fer.zemris.ooup.lab3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import hr.fer.zemris.ooup.lab3.model.Animal;

public class AnimalFactory {
	
	private static final String PLUGINS_PACKAGE_STRING = "hr.fer.zemris.ooup.lab3.model.plugins";
	
	public static Animal newInstance(String animalKind, String name) {
		
		Class<?> clazz = null;
		try {
			clazz = Class.forName(PLUGINS_PACKAGE_STRING + "." + animalKind);
		} catch (ClassNotFoundException e) {
			return null;
		}
		
		Constructor<?> ctr = null;
		try {
			ctr = clazz.getConstructor(String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}
		
		Animal animal = null;
		try {
			animal = (Animal) ctr.newInstance(name);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			return null;
		}
		
		return animal;
	}

}