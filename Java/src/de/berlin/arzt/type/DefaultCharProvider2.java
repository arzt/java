package de.berlin.arzt.type;

import de.berlin.arzt.type.interfaces.CharProvider;

public class DefaultCharProvider2 implements CharProvider {
	DiscreteDistribution<Integer> charSource;
	int lastChar; 
	
	public DefaultCharProvider2(String chars) {
		setChars(chars);
	}
	
	@Override
	public char getNextChar() {
		int newChar = charSource.getSample();
		while (newChar == lastChar) {
			newChar = charSource.getSample();
		}
		lastChar = newChar;
		return (char) newChar;
	}
	
	public void setChars(String chars) {
		charSource = new DiscreteDistribution<Integer>();
		for (int i = 0; i < chars.length(); i++) {
			charSource.put((int) chars.charAt(i), 1d);
		}
		charSource.normalize();
	}

	@Override
	public void penalize(char c) {
		charSource.muliply((int) c, 1.5);
		System.out.println("Penalized: " + c);
		System.out.println(charSource);
	}
	
	@Override
	public void reward(char c) {
		charSource.uniformize(0.1);
		System.out.println("Rewarded: " + c);
		System.out.println(charSource);
	}

}
