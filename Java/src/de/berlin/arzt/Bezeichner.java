package de.berlin.arzt;

public class Bezeichner {
	
	public static boolean isValidIdentifier(String s) {
		char firstChar = s.charAt(0);
		if (Character.isLowerCase(firstChar) | Character.isUpperCase(firstChar) | firstChar == '_') {
			for (int i = 0; i < s.length(); i++) {
				char currentChar = s.charAt(i);
				if (Character.isJavaIdentifierPart(currentChar)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(isValidIdentifier(""));
	}
}
