package de.berlin.arzt;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class Ackermann {
	static int depth = 0;
	
	static Map<BigInteger, Map<BigInteger, BigInteger>> cache = new HashMap<BigInteger, Map<BigInteger,BigInteger>>();
	
	public static BigInteger ackermann(BigInteger x, BigInteger y) {
		if (cache.get(x) != null && cache.get(x).get(y) != null) {
//			System.out.println("Found result in cache!");
			return cache.get(x).get(y);
		}
		depth++;
		if (x.equals(BigInteger.ZERO)) {
			depth -= 2;
			if (cache.get(x) == null) {
				cache.put(x, new HashMap<BigInteger, BigInteger>());
			}
			BigInteger result = y.add(BigInteger.ONE);
			cache.get(x).put(y, result);
			return result;
		} else if (y.equals(BigInteger.ZERO)) {
			return ackermann(x.subtract(BigInteger.ONE), BigInteger.ONE);
		} else {
			return ackermann(x.subtract(BigInteger.ONE), ackermann(x, y.subtract(BigInteger.ONE)));
		}
	}
	
	
	public static byte[] toByteArray(int x) {
		byte[] output = new byte[4];
		output[3] = (byte) x;
		output[2] = (byte) (x >> 8);
		output[1] = (byte) (x >> 16);
		output[0] = (byte) (x >> 24);
		return output;
	}
	
	public static int toInteger(byte[] x) {
		int a = 0x000000FF & x[0];
		int b = 0x000000FF & x[1];
		int c = 0x000000FF & x[2];
		int d = 0x000000FF & x[3];
		return (((((d << 8) + c) << 8) + b) << 8) + a;
	}
	
	public static BigInteger ackermann(int x, int y) {
		return ackermann(new BigInteger(toByteArray(x)), new BigInteger(toByteArray(y)));
	}
	
	public static void main(String... args) throws FileNotFoundException {
		System.out.println(ackermann(4, 1));
	}
}
