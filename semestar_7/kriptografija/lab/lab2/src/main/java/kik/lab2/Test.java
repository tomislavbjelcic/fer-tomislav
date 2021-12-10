package kik.lab2;

import java.security.Provider;
import java.security.Provider.Service;
import java.security.Security;
import java.util.Set;
import java.util.TreeSet;

public class Test {
	

	public static void main(String[] args) {
		AddBcProv.add();
		
		/*
		for (Provider provider: Security.getProviders()) {
			System.out.println(provider.getName());
			for (String key: provider.stringPropertyNames())
				System.out.println("\t" + key + "\t\t\t" + provider.getProperty(key));
		}*/
		
		Set<String> algs = new TreeSet<>();
		for (Provider provider : Security.getProviders()) {
		    provider.getServices().stream()
		                          .filter(s -> "Cipher".equals(s.getType()))
		                          .map(Service::getAlgorithm)
		                          .forEach(algs::add);
		}
		algs.forEach(System.out::println);

	}

}
