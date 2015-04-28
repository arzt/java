package de.berlin.arzt.datastruct.trie;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TrieElement {
	public Map<Character, TrieElement> next = new HashMap<Character, TrieElement>();
	public char[] values;
	
	public TrieElement(char[] values, int offset, int length) {
		this.values = new char[length];
		System.arraycopy(values, offset, this.values, 0, length);
	}
	
	public void insert(char[] seq, int i) {
		if (i + 1 < seq.length) {
			TrieElement trieElement = next.get(seq[i + 1]);
			if (trieElement == null) {
				trieElement = new TrieElement(seq, i + 1, 1);
				int j = i + 1;
				char key = seq[j];
				next.put(key, trieElement);
			}
			trieElement.insert(seq, i + 1);
		}		
	}

	public void print() {
		print(System.out, 0);
	}

	private void print(PrintStream out, int offset) {
		out.print(Arrays.toString(values));
		out.print('\t');
		Iterator<TrieElement> iterator = next.values().iterator();
		if (iterator.hasNext()) {
			iterator.next().print(out, offset + 1);
			while (iterator.hasNext()) {
				for (int i = 0; i <= offset; i++) {
					out.print('\t');
				}
				iterator.next().print(out, offset + 1);
			}
		} else {
			out.println();
		}
	}
}
