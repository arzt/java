package de.berlin.arzt;

public class Achim {
	
	public static void find() {
		for (int a = 1; a < 7; a++) {
			for (int b = a+1; b < 8; b++) {
				for (int d = b+1; d < 9; d++) {
					for (int e = d+1; e < 10; e++) {
						if (test(a,b,d,e)) {
							int c = 15 - a - b;
							int f = 15 - d - e;
							int g = 15 - a - d;
							int h = 15 - b - e;
							int i = 15 - c - f;
							String output = String.format("%d %d %d\n%d %d %d\n%d %d %d\n", a, b, c, d, e, f, g, h, i);
							System.out.println(output);
						}
					}
				}
			}
		}
	}
	
	public static boolean test(int a, int b, int d, int e) {
		int c = 15 - a - b;
		int f = 15 - d - e;
		int g = 15 - a - d;
		int h = 15 - b - e;
		int i = 15 - c - f;
		return 
		isCorrect(a, b, c) && 
		isCorrect(d, e, f) && 
		isCorrect(g, h, i) && 
		isCorrect(a, d, g) && 
		isCorrect(b, e, h) && 
		isCorrect(c, f, i) &&
		areDifferent(a, b, c, d, e, f, g, h, i);
	}
	
	public static boolean isCorrect(int a, int b, int c) {
		return (a+b+c) == 15 && isInRange(a) && isInRange(b) && isInRange(c);
	}
	
	public static boolean isInRange(int a) {
		return a > 0 && a < 10; 
	}
	
	public static boolean areDifferent(int a, int b, int c, int d, int e, int f, int g, int h, int i) {
		return a != b && a!= c && a != d && a != e && a != f && a!= g && a!= h && a != i &&
		b!= c && b != d && b != e && b != f && b!= g && b!= h && b != i &&
		c != d && c != e && c != f && c!= g && c!= h && c != i &&
		d != e && d != f && d!= g && d!= h && d != i &&
		e != f && e!= g && e!= h && e != i &&
		f!= g && f!= h && f != i &&
		g!= h && g != i &&
		h != i;
	}
	
	public static void main(String[] args) {
		find();
	}

}
