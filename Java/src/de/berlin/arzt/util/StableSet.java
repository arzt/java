package de.berlin.arzt.util;

import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class StableSet<E> extends AbstractSet<E>  {
	
	List<E> linearSet = new ArrayList<E>();
	Set<E> hashSet = new HashSet<E>();
	
	@Override
	public boolean add(E e) {
		boolean retVal = hashSet.add(e);
		if (retVal) {
			linearSet.add(e);
		}
		return retVal;
	}
	
	@Override
	public Iterator<E> iterator() { 
		return new Iterator<E>() {

			Iterator<E> i1 = linearSet.iterator();
			E last = null;
			
			@Override
			public boolean hasNext() {
				return i1.hasNext();
			}

			@Override
			public E next() {
				last =  i1.next();
				return last;
			}

			@Override
			public void remove() {
				i1.remove();
				hashSet.remove(last);
			}
		};
	}

	@Override
	public int size() {
		return linearSet.size();
	}


}
