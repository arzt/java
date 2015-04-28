package de.berlin.arzt.wordSearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipInputStream;


public class WordSearch {
	List<ScoredWord> bestWords = new ArrayList<ScoredWord>(100);
	Map<Integer, ArrayList<String>> lengthGroup = new HashMap<Integer, ArrayList<String>>();
	List<String> result = new ArrayList<String>(1000);
	double t = 1;
	
	public void processGermanWordList() {
		File listFolder = new File("/home/realdocx/Desktop/german/");
		for (File file : listFolder.listFiles()) {
//			System.out.println("Next file: " + file.getName());
			try {
				BufferedReader reader = new BufferedReader(
					new FileReader(file)
				);
				String word;
				while ((word = reader.readLine()) != null) {
//					System.out.println(word);
					double score = score(word);
					if (score >= t) {
						ScoredWord entry = new ScoredWord();
						entry.word = word;
						entry.score = score;
						bestWords.add(entry);
						if (lengthGroup.get(word.length()) == null) {
							lengthGroup.put(word.length(), new ArrayList<String>(100));
						}
						lengthGroup.get(word.length()).add(word);
//						System.out.println("Found: " + bestWords.size());
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(bestWords);
		for (ScoredWord word : bestWords) {
//			System.out.println(word.word + "\t" + word.score);
		}
//		System.out.println(bestWords.size());
		int wordLength = 9;
		for (ScoredWord scoredWord : bestWords) {
			String prefix = scoredWord.word;
			int lengthRest = wordLength - prefix.length();
			if (lengthRest >= 0) {
				List<String> group = lengthGroup.get(lengthRest);
				if (group != null) {
					for (String suffix : group) {
						String composedWord = prefix + suffix;
						if (score(composedWord) >= 0.75) {
							result.add(composedWord + "\t" + prefix + "|" + suffix);
						}
					}
				}
			}
		}
		Collections.sort(result);
		for (String word: result) {
			System.out.println(word);
		}
	}
	
	public void processEnglishWordlist() {
		File listFolder = new File("/home/realdocx/Desktop/scowl-6/final");
		for (File file : listFolder.listFiles()) {
//			System.out.println("Next file: " + file.getName());
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String word;
				while ((word = reader.readLine()) != null) {
					double score = score(word);
					if (score >= t) {
						ScoredWord entry = new ScoredWord();
						entry.word = word;
						entry.score = score;
						bestWords.add(entry);
						if (lengthGroup.get(word.length()) == null) {
							lengthGroup.put(word.length(), new ArrayList<String>(100));
						}
						lengthGroup.get(word.length()).add(word);
//						System.out.println("Found: " + bestWords.size());
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Collections.sort(bestWords);
		for (ScoredWord word : bestWords) {
//			System.out.println(word.word + "\t" + word.score);
		}
//		System.out.println(bestWords.size());
		int wordLength = 9;
		for (ScoredWord scoredWord : bestWords) {
			String prefix = scoredWord.word;
			int lengthRest = wordLength - prefix.length();
			if (lengthRest >= 0) {
				for (String suffix : lengthGroup.get(lengthRest)) {
					String composedWord = prefix + suffix;
					if (score(composedWord) >= 0.9) {
						result.add(composedWord + "\t" + prefix + "|" + suffix);
					}
				}
			}
		}
		Collections.sort(result);
		for (String word: result) {
			System.out.println(word);
		}
	}
	
	public double score(String word) {
		int containmentsCount = 0;
		String check = "aelpnrcot";
		for (int i = 0; i < check.length(); i++) {
			if (word.indexOf(check.charAt(i)) != -1) {
				containmentsCount++;
			}
		}
		return containmentsCount/(double) word.length();
	}
	
	public static void main(String[] args) {
		WordSearch rakes = new WordSearch();
		rakes.processEnglishWordlist();
	}
}
