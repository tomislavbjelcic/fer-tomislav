package hr.fer.zemris.ooup.lab3;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.ooup.lab3.model.Animal;

public class AnimalFactory {
	
	private static final String PLUGINS_PACKAGE_STRING = "hr.fer.zemris.ooup.lab3.model.plugins";
	private static final Map<String, ClassLoader> CLASS_LOADERS = new HashMap<>();
	private static final URLClassLoader EXTERNAL_CLASS_LOADER = extCL("plugins/", "plugins/zivotinje.jar");
	
	private static URLClassLoader extCL(String... paths) {
		int len = paths.length;
		URL[] urls = new URL[len];
		for (int i=0; i<len; i++) {
			Path p = Path.of(paths[i]);
			URL url = null;
			try {
				url = p.toUri().toURL();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			urls[i] = url;
		}
		
		URLClassLoader cl = new URLClassLoader(urls, AnimalFactory.class.getClassLoader());
		return cl;
	}
	
	public static Animal newInstance(String animalKind, String name) {
		String fqn = PLUGINS_PACKAGE_STRING + "." + animalKind;
		
		Class<?> clazz = null;
		ClassLoader loader = CLASS_LOADERS.get(animalKind);
		if (loader != null) {
			try {
				clazz = loader.loadClass(fqn);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			try {
				clazz = Class.forName(fqn);
			} catch (ClassNotFoundException e) {
				// ignore
			}
			
			if (clazz == null) {
				// pokusaj vanjski class loader
				try {
					clazz = EXTERNAL_CLASS_LOADER.loadClass(fqn);
				} catch (ClassNotFoundException e) {
					return null;
				}
			}
			
			ClassLoader ldr = clazz.getClassLoader();
			CLASS_LOADERS.put(animalKind, ldr);
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