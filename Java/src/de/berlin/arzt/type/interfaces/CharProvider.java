package de.berlin.arzt.type.interfaces;

public interface CharProvider {
	public char getNextChar();
	public void reward(char c);
	public void penalize(char c);
}
