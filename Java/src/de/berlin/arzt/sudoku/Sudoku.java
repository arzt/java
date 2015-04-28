package de.berlin.arzt.sudoku;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 *
 * Beschreibung.
 *
 * @version 1.0 vom 21.08.2005
 * @author
 */

public class Sudoku implements Cloneable, State {
	public static int breite = 3 , hoehe = 3;
	private int tiefe;
	private byte[][] sudoku;
	private int score = 0;
	ObjectRecycler<State> recylcer = new ObjectRecycler<State>(1000);
	private boolean[] test;

	Sudoku(int breite, int hoehe) {
		Sudoku.breite = breite;
		Sudoku.hoehe = hoehe;
		this.tiefe = breite * hoehe;
		this.sudoku = new byte[tiefe][tiefe];
		initTest(tiefe);
	}
	
	public void initTest(int tiefe) {
		test = new boolean[tiefe];
	}
	
	public void resetTest() {
		for (int i = 0; i < test.length; i++) {
			test[i] = false;
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public void computeScore() {
		score = 0;
		for (int i = 0; i < getTiefe(); i++) {
			checkLineConstraint(i, true);
			checkLineConstraint(i, false);
		}
		for (int i = 0; i < getHoehe(); i++) {
			for (int j = 0; j < getBreite(); j++) {
				checkSquareConstraint(i, j);
			}
		}
	}
	
	private void checkLineConstraint(int j, boolean mode) {
		resetTest();
		for (int i = 0; i < getTiefe(); i++) {
			int value = mode ? get(i, j) - 1 : get(j, i) - 1;
			if (test[value]) {
				score++;
			}
			else {
				test[value] = true;
			}
		}
	}
	
	public void checkSquareConstraint(int x, int y) {
		int offsetX = getBreite()*x;
		int offsetY = getHoehe()*y;
		resetTest();
		for (int i = 0; i < getHoehe(); i++) {
			for (int j = 0; j < getBreite(); j++) {
				int val = get(offsetX + j, offsetY + i) -1;
				if (test[val]) {
					score++;
				}
				else {
					test[val] = true;
				}
			}
		}
	}
	
	public void fillRandom() {
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				sudoku[i][j] = (byte) (1 + (byte) (Math.random() * getTiefe()));
			}
		}
	}
	
	public void fillRandomHorizontal() {
		for (int i = 0; i < sudoku.length; i++) {
			List<Byte> values = getNumbers(getTiefe(), true); 
			for (int j = 0; j < sudoku[i].length; j++) {
				sudoku[i][j] = values.get(j);
			}
		}
	}
	
	public static List<Byte> getNumbers(int tiefe, boolean randomize) {
		List<Byte>  output = new ArrayList<Byte>(tiefe);
		for (byte i = 1; i <= tiefe; i++) {
			output.add(i);
		}
		if (randomize) {
			Collections.shuffle(output);
		}
		return output;
	}
	
	public void clear() {
		for (int x=0;x<tiefe;x++) {
			for (int y=0;y<tiefe;y++) {
				sudoku[x][y] = 0;
			}
		}
	}
	public int getTiefe() {
		return tiefe;
	}

	public int getHoehe() {
		return hoehe;
	}

	public int getBreite() {
		return breite;
	}

	public int[][] toIntArray() {
		int[][] out = new int[tiefe][tiefe];
		synchronized (sudoku) {
			System.arraycopy(sudoku,0,out,0,sudoku.length);
		}
		return out;
	}

	public byte get(int x, int y) {
		return this.sudoku[x][y];
	}
	
	public void set(int breite, int hoehe, String values) {
		int index = 0;
		char c;
		for (int i = 0; i < hoehe*breite; i++) {
			for (int j = 0; j < breite*hoehe; j++) {
				do {
					c = values.charAt(index);
					index++;
				}
				while (!Character.isDigit(c) 
						&& index < values.length());
				set(i, j, (byte) Character.getNumericValue(c));
			}
		}
	}
	
	public boolean set(int x, int y, int wert) {
		if (x>getTiefe() || y>getTiefe()|| wert>getTiefe() || wert<0) {
			return false;
		}
		else {
			this.sudoku[x][y] = (byte) wert;
			return true;
		}
	}
	
	public boolean set(Sudoku source) {
		if (source.getBreite() <= getBreite() && source.getTiefe() <= getTiefe()) {
			for (int i = 0; i < sudoku.length; i++) {
				for (int j = 0; j < sudoku[i].length; j++) {
					sudoku[i][j] = source.get(i, j);
				}
			}
			return true;
		}
		else {
			return false;
		}
	}

	public void toByteArray(byte[] puffer) {
		int index = 0;
		for (int y=0;y<tiefe;y++) {
			for (int x=0;x<tiefe;x++) {
				puffer[index++] = (byte) get(x,y);
			}
		}
	}

	public Sudoku copy() {
		Sudoku out = new Sudoku(getBreite(),getHoehe());
		for (int ix = 0; ix < getTiefe(); ix++) {
			for (int iy = 0; iy < getTiefe(); iy++) {
				out.set(ix, iy, get(ix, iy));
			}
		}
		out.computeScore();
		return out;
	}

	public boolean equals(Object o) {
		if (o==this) {
			return true;
		}
		else if (o.getClass().equals(getClass())) {
			Sudoku in = (Sudoku) o;
			if (in.getBreite()==getBreite() && in.getHoehe()==getHoehe()) {
				for (int ix=0;ix<getTiefe();ix++) {
					for (int iy=0;iy<getTiefe();iy++) {
						if (in.get(ix,iy)!=get(ix,iy)) return false;
					}
				}
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}

	}

	public String toNumberString() {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < getTiefe(); i++) {
			for (int j = 0; j < getTiefe(); j++) {
				output.append(get(j, i));
			}
		}
		return output.toString();
	}
	
	public String toString() {
		StringBuilder output = new StringBuilder(1200);
		for (int y = 0; y < getTiefe(); y++) {
			if (y % getHoehe() == 0 && y != getTiefe()) {
				output.append('\n');
			}
			for (int x = 0; x < getTiefe(); x++) {
				if (x % getBreite() == 0 && x != getTiefe()) {
					output.append(' ');
					output.append('|');
				}
				output.append("  ");
				if (getTiefe() > 9 || true) {
					int value = get(y,x);
					if (value > 9) {
						output.append(value);
					}
					else {
						output.append(' ');
						output.append(value);
					}
				}
				else {
					output.append(get(y,x));
				}
			}
			output.append("|\n");
		}
		return output.toString();
	}
	
	public int hashCode() {
		int hash = 0;
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				hash = hash << 3 + get(i, j);
			}
		}
		return toNumberString().hashCode();
	}

	public static void printSudoku(Sudoku s) {
		System.out.println(s);
	}

	public static void main(final String[] args) throws Exception  {
		SudokuGenerator s = new SudokuGenerator();
		Sudoku sudoku = s.generateSudoku(3,3);
		System.out.println(sudoku);
//		RaetselGenerator gen = new RaetselGenerator();
//		Sudoku hui = gen.erstelleRaetsel(sudoku);
//		Sudoku.printSudoku(gen.erstelleRaetsel(hui));
	}

	@Override
	public int compareTo(State o) {
		return getScore() - o.getScore();
	}

	@Override
	public void getSuccessors(List<State> output) {
		output.clear();
		for (int i = 0; i < sudoku.length; i++) {
			for (int j = 0; j < sudoku[i].length; j++) {
				for (byte val = 1; val <= getTiefe(); val++) {
					if (get(i, j) != val 
//							&& Math.random() < 0.2
							) {
						Sudoku newSudoku = new Sudoku(getBreite(), getHoehe());
						newSudoku.set(this);
						newSudoku.set(i, j, val);
						newSudoku.computeScore();
						output.add(newSudoku);
					}
				}
			}
		}
	}

}


