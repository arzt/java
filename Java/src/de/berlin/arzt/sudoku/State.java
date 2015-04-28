package de.berlin.arzt.sudoku;
import java.util.List;


public interface State extends Comparable<State> {
	public int getScore();
	public void getSuccessors(List<State> list);
}
