package de.berlin.arzt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



public class RandomDictat {
	public static final String LEFT_CHARS = "qwertasdfgyxcvb";
	public static final String RIGHT_CHARS = "zuiopühjklöänmß";
	public String leftChars = "qwertzasdfgyxcvb";
	public String rightChars = "zuiopühjklöänmß";
	public String possibleChars = "abcdefghijklmnopqrstuvwxyzäöüß";
	public String neededChars = "";
	
	int wordCount = 564;
	int minWordLength = 3;
	int maxWordLength = 20;
//	File wordList = new File("/home/realdocx/Desktop/german/words.german");
	File wordList = new File("/usr/share/myspell/dicts/de_DE.dic");
	
	public List<String> extractMatchingWords(List<String> target) {
		if (target == null) {
			target = new ArrayList<String>();
		} 
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(wordList), "ISO-8859-1"));
			String line;
			while ((line = reader.readLine()) != null) {
				line = line.split("[/]")[0];
				if (isValid(line)) {
					target.add(line);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return target;
	}
	
	public String getMatchingDictat() {
		List<String> target = extractMatchingWords(new ArrayList<String>());
		System.out.println("Size " + target.size());
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < wordCount && i < target.size(); i++) {
			int index = (int) (Math.random() * target.size());
			output.append(target.remove(index));
			output.append('\n');
		}
		return output.toString();
	}

	
	public boolean isValid(String word) {
		for (int j = 0; j < word.length(); j++) {
			if (possibleChars.indexOf(word.charAt(j)) == -1) {
				return false;
			}
		}
		for (int i = 0; i < neededChars.length(); i++) {
			if (word.indexOf(neededChars.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}
	
	public String getDictat() {
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < wordCount; i++) {
			int currentLegth = (int) (minWordLength + Math.random() * (maxWordLength - minWordLength + 1));
			for (int j = 0; j < currentLegth; j++) {
				output.append(i % 2 == 0 ? 
						j % 2 == 0 ? getRandomChar(leftChars) : getRandomChar(rightChars) : 
							j % 2 == 0 ? getRandomChar(rightChars) : getRandomChar(leftChars));
			}
			output.append('\n');
		}
		return output.toString();
	}
	
	public String getAlternatingDictat() {
		StringBuffer output = new StringBuffer();
		for (int i = 0; i < wordCount; i++) {
			int currentLegth = (int) (minWordLength + Math.random() * (maxWordLength - minWordLength + 1));
			for (int j = 0; j < currentLegth; j++) {
				output.append(getRandomChar(0.5));
			}
			output.append('\n');
		}
		return output.toString();
	}
	
	public static char getRandomChar(String chars) {
		return chars.charAt((int) (Math.random()*chars.length()));
	}
	
	public static char getRandomChar() {
		return (char) ('a' + Math.random()*('z' - 'a' + 1));
	}
	
	public static char getRandomChar(double p) {
		return Math.random() > p ? getRandomChar() : Character.toUpperCase(getRandomChar());
	}
	
	public static void main(String[] args) {
		RandomDictat dic = new RandomDictat();
		dic.leftChars = "uiaeo";
		dic.rightChars = "nrtd";
		dic.possibleChars = RIGHT_CHARS;
		dic.neededChars = "";
		System.out.println(dic.getMatchingDictat());
	}
}
