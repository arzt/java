package de.berlin.arzt.seam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class SeamCarver {
	
	private BufferedImage image;
	private int[][] m;

	public SeamCarver(BufferedImage image) {
		this.image = image;
		System.out.println(image.getWidth() + ":" + image.getHeight());
		m = new int[image.getHeight()][image.getWidth()];
	}
	
	public static int getSummedDifference(int p1, int p2) {
		int a1 = p1 & 0xFF;
		int b1 = p1 >> 8 & 0xFF;
		int c1 = p1 >> 16 & 0xFF;
		int d1 = p1 >> 24 & 0xFF;
		
		int a2 = p2 & 0xFF;
		int b2 = p2 >> 8 & 0xFF;
		int c2 = p2 >> 16 & 0xFF;
		int d2 = p2 >> 24 & 0xFF;
		
		return Math.abs(a1 - a2) + Math.abs(b1 - b2) + Math.abs(c1 - c2) + Math.abs(d1 - d2);
	}
	
	public int getPixelDifference(int x1, int y1, int x2, int y2) {
		int a = 0;
		int b = 0;
		try {
			a = image.getRGB(x1, y1);
		} catch (Exception e) {
			System.err.println(x1 + ":" + y1);
		}
		try {
			b = image.getRGB(x2, y2);
		} catch (Exception e) {
			System.err.println(x1 + ":" + y1);
		}
		return getSummedDifference(a, b);
	}
	
	public int getCostsLeftDown(int x, int y) {
		return getPixelDifference(x, y-1, x-1, y) +  getCostsDown(x, y);
	}
	
	public int getCostsDown(int x, int y) {
		if (x == 0 || x == image.getWidth() - 1) {
			return 0;
		} else {
			return getPixelDifference(x - 1, y, x + 1, y);
		}
	}
	
	public int getCostsRightDown(int x, int y) {
		if (x == image.getWidth() - 1) {
			return getCostsDown(x, y);
		} else {
			return getPixelDifference(x, y - 1, x + 1, y) +  getCostsDown(x, y);
		}
	}
	
	public void fillM() {
		for (int y = 1; y < image.getWidth(); y++) {
			for (int x = 0; x < image.getWidth(); x++) {
//				System.out.println(String.format("%2d:%2d", x, y));
				if (x == 0) {
					int a = m[y-1][x] + getCostsDown(x, y);
					int b = m[y-1][x + 1] + getCostsRightDown(x, y);
					m[y][x] = Math.min(a, b);
				} else if (x == image.getWidth() - 1) {
					int a = m[y-1][x] + getCostsDown(x, y);
					int b = m[y-1][x-1] + getCostsLeftDown(x, y);
					m[y][x] = Math.min(a, b);
				} else {
					int a = m[y-1][x - 1] + getCostsLeftDown(x, y);
					int b = m[y-1][x] + getCostsDown(x, y);
					int c = m[y-1][x + 1] + getCostsRightDown(x, y); 
					m[y][x] = Math.min(a, Math.min(b, c));
				}
			}
		}
	}
	
	public void printM() {
		for (int y = 0; y < image.getHeight(); y++) {
			System.out.println(Arrays.toString(m[y]));			
		}
	}
	
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		BufferedImage test = ImageIO.read(new File("/home/realdocx/Desktop/021_small.png"));
		int[] array = new int[test.getHeight()*test.getWidth()];
		test.getRGB(0, 0, test.getWidth(), test.getHeight(), array, 0, test.getWidth());
		System.out.println(Arrays.toString(array));
		SeamCarver carver = new SeamCarver(test);
		carver.fillM();
		carver.printM();
	}

}
