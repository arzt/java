package de.berlin.arzt.datastruct.trie;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;



public class Trie {

	/**
	 * @param args
	 */
	/**
	 * @param args
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	public static void main(String[] args) throws MalformedURLException, IOException {
		InputStream stream = new URL("http://zope.informatik.hu-berlin.de/forschung/gebiete/wbi/teaching/archive/ws1011/pr_text/english_stop_words.txt").openStream();
		Scanner scanner = new Scanner(stream);
		int offset = 0;
		int length = 1;
		TrieElement trie = new TrieElement(new char[] {'$'}, offset, length);
		while (scanner.hasNext()) {
			String wort = "$" + scanner.next();
			System.out.println(wort);
			trie.insert(wort.toCharArray(), offset);
		}
		trie.print();
	}

}
