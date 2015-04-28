package de.berlin.arzt;

import de.berlin.arzt.type.DiscreteDistribution;
import de.berlin.arzt.util.StableSet;

public class Prototype {

	public static void main(String[] args) {
		discreteDist();
	}
	
	public static void stableSet() {
		StableSet<Object> set = new StableSet<Object>();
		set.add("Hello");
		set.add(12);
		set.add(null);
		set.add('r');
		set.add(58d);
		set.add(8f);
		System.out.println(set);
		set.remove(null);
		System.out.println(set);
		set.add(null);
		System.out.println(set);
	}
	
	public static void discreteDist() {
		DiscreteDistribution<Character> dist = new DiscreteDistribution<>();
		dist.put('a', 1d/9);
		dist.put('b', 1d/2);
		dist.put('c', 1d);
		dist.normalize();
		dist.toUniformDistribution();
		dist.muliply('a', 2);
		System.out.println(dist);
		int numSamples = 100;
		for (int i = 0; i < numSamples; i++) {
			System.out.print(dist.getSample());
		}


	}

}
