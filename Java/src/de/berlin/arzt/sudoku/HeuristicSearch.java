package de.berlin.arzt.sudoku;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class HeuristicSearch {
	private List<State> open;
	private Set<State> closed;
	
	public HeuristicSearch(State start) {
		open = new ArrayList<State>(1000);
		closed = new HashSet<State>(5000);
		open.add(start);
	}
	
	public void startSearch() {
		int minScore;
		int currentScore;
		List<State> succ = new ArrayList<State>(1000);
		List<State> newSucc = new ArrayList<State>(1000);
		while ((currentScore = open.get(0).getScore()) > 0) {
//			Collections.sort(open);
			State currentState = open.remove(0);
			closed.add(currentState);
			
			long time = System.currentTimeMillis();
			currentState.getSuccessors(succ);
//			System.out.println("Time " + (System.currentTimeMillis()-time));
			Collections.sort(succ);

			newSucc.clear();
			for (int i = 0; i < succ.size(); i++) {
				State s = succ.get(i);
				if (!closed.contains(s) && currentState.getScore() +3 >= s.getScore() && Math.random() < 0.1) {
					newSucc.add(s);
				}
			}
//			System.out.println("NewSucc: " + newSucc.size());
			open.addAll(0, newSucc);
			while (open.size() > 10000) {
				open.remove(open.size() - 1);
			}
//			System.out.println(currentState);
			System.out.println("Closed: " + closed.size() + " Open: " + open.size());
			System.out.println("First: " + currentState.getScore() + " Last: " + open.get(open.size()-1).getScore());
		}
		System.out.println("Found: ");
		System.out.println(open.get(0));
		System.out.println("Score: " + open.get(0).getScore() + " Hash: " + open.get(0).hashCode());
	}
	
	public static void main(String[] args) {
		Sudoku test = new Sudoku(4,4);
//		test.set(3, 3, "132467589 854219376 697385124 561732498 948651237 273894615 486923751 319576842 725148963");
		test.fillRandom();
		System.out.println(test);
		test.computeScore();
		System.out.println("Score: " + test.getScore());
		HeuristicSearch search = new HeuristicSearch(test);
		search.startSearch();
	}
}
