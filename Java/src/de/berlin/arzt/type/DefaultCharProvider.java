package de.berlin.arzt.type;

import de.berlin.arzt.type.interfaces.CharProvider;

public class DefaultCharProvider implements CharProvider {
	ObjectEmitter<Character> charSource;
	char lastChar; 
	
	public DefaultCharProvider(String chars) {
		charSource = new ObjectEmitter<Character>();
		setChars(chars);
		charSource.setNoRepeatLength(1);
		charSource.setVariable(true);
	}
	
	@Override
	public char getNextChar() {
		Character c = charSource.getRandomNoRepeat();
		assert(c != null);
		lastChar = c;
		return lastChar;
	}
	
	public void setChars(String chars) {
		charSource = new ObjectEmitter<Character>();
		for (int i = 0; i < chars.length(); i++) {
			charSource.add(chars.charAt(i), 1);
		}
	}

	@Override
	public void penalize(char c) {
		charSource.increment(c, 1);
	}
	
	@Override
	public void reward(char c) {
		charSource.increment(c, -1);
	}

}
