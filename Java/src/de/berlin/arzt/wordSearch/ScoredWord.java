package de.berlin.arzt.wordSearch;

public class ScoredWord implements Comparable<ScoredWord> {
	public String word;
	public double score;
	
	@Override
	public int compareTo(ScoredWord o) {
		return score > o.score ? 1 : (score == o.score ? 0 : -1);
	}
}
