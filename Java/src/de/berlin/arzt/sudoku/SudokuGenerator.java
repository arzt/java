package de.berlin.arzt.sudoku;
/**
 *
 * Beschreibung.
 *
 * @version 1.0 vom 02.11.2005
 * @author
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class SudokuGenerator {
	Sudoku s = null;
	protected short[][] versuche;
	protected boolean[][][] zahlen;
	protected Random zufall = new Random();

	public Sudoku generateSudoku(int breite, int hoehe) {
		s = new Sudoku(breite, hoehe);
		int tiefe = s.getTiefe();
		versuche = new short[tiefe][tiefe];
		zahlen = new boolean[tiefe][tiefe][tiefe];
		generate2();
		return s;
	}

	public Sudoku generateSudoku(int breite, int hoehe, long id) {
		zufall = new Random(id);
		return generateSudoku(breite, hoehe);
	}

	
	public void generate2() {
		int[][][] score = new int[s.getTiefe()][s.getTiefe()][s.getTiefe()];
		int minScore = 1000;
		int swapRow = 0;
		int swapX1 = 0;
		int swapX2 = 0;
		List<Integer> ints = new ArrayList<Integer>(s.getTiefe());
		for (int i = 0; i < s.getTiefe(); i++) {
			ints.add(i);
		}
		for (int i = 0; i < s.getTiefe(); i++) {
			Collections.shuffle(ints);
			for (int j = 0; j < s.getTiefe(); j++) {
				s.set(j, i, ints.get(j)+1);
			}
		}
		while (minScore != 0) {
			for (int row = 0; row < s.getTiefe(); row++) {
				for (int x1 = 0; x1 < s.getTiefe()-1; x1++) {
					for (int x2 = x1+1; x2 < s.getTiefe(); x2++) {
						int lokalScore = calculateScore(row, x1, row, x2);
						if (lokalScore < minScore) {
							minScore = lokalScore;
							swapRow = row;
							swapX1 = x1;
							swapX2 = x2;
						}
					}
				}
			}
			System.out.println("Swaprow " + swapRow + ": " + swapX1 + " : " + swapX2);
			swap( swapX1, swapRow, swapX2, swapRow);
			System.out.println(s);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private int calculateScore(int x1, int y1, int x2, int y2) {
		swap(x1, y1, x2, y2);
		s.computeScore();
		int score = s.getScore();
		swap(x2, y2, x1, y1);
		return score;
	}
	
	public void swap(int x1, int y1, int x2, int y2) {
		int tmpVal = s.get(x1, y1);
		s.set(x1, y1, s.get(x2, y2));
		s.set(x2, y2, tmpVal);
	}
	
	private void generate() {
		int x=0;
		int y=0;
		int wert;
		int tiefe = s.getTiefe();
		while (s.get(tiefe-1,tiefe-1) == 0) {
			if (versuche[x][y] != 9) {
				wert = zufall.nextInt(s.getTiefe());
				versuche[x][y]++;
				if (!zahlen[x][y][wert]) {
					zahlen[x][y][wert] = true;
					if (teste(x,y,++wert)) {
						s.set(x ,y ,wert);
//						System.out.println(s.toNumberString());
//						System.out.println(s);
						try {
							Thread.sleep(1);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						if (++x == s.getTiefe()) {
							x=0;
							++y;
						}
					}
				}
			}
			else {
				resetFlags(x,y);
				s.set(x, y, 0);
				if (x==0) {
					x=tiefe-1;
					--y;
				}
				else  {
					--x;
				}
			}
		}
	}

	public void resetFlags(int x, int y) {
		for (int i=0; i<s.getTiefe(); i++) {
			zahlen[x][y][i] = false;
		}
		versuche[x][y] = 0;
	}

	public boolean teste(int x, int y, int wert) {
		return testeKasten(x,y,wert) && testeZeile(x,y,wert) && testeSpalte(x,y,wert);
	}

	public boolean testeZeile(int x, int y, int wert) {
		for (int i = 0; i < x; i++) {
			if (s.get(i, y) == wert) {
				return false;
			}
		}
		return true;
	}

	public boolean testeSpalte(int x, int y, int wert) {
		for (int i=0; i<y;i++) {
			if (s.get(x, i) == wert) {
				return false;
			}
		}
		return true;
	}

	public boolean testeKasten(int x, int y, int wert) {
		int offsetX = (x / s.getBreite()) * s.getBreite();
		int offsetY = (y / s.getHoehe()) * s.getHoehe();
		for (int iy = 0; iy < s.getHoehe(); iy++) {
			for (int ix = 0; ix < s.getBreite(); ix++) {
				if (s.get(offsetX + ix, offsetY + iy) == wert) {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		SudokuGenerator test = new SudokuGenerator();
		System.out.println("hallo:");
		System.out.println(test.generateSudoku(3, 3));
	}
}

