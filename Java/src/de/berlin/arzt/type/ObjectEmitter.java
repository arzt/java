package de.berlin.arzt.type;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ObjectEmitter<E> {
	int staticSum;
	int variableSum;
	int noRepeatLength;
	Map<E, Integer> staticMap;
	Map<E, Integer> variableMap;
	List<E> repeats;
	boolean isVariable;
	
	public ObjectEmitter() {
		super();
		staticMap = new HashMap<E, Integer>();
		variableMap = new HashMap<E, Integer>();
		repeats = new ArrayList<E>(100);
	}
	
	public void setVariable(boolean flag) {
		isVariable = flag;
	}
	
	public void setNoRepeatLength(int length) {
		noRepeatLength = length;
//		System.out.println("Length :" + noRepeatLength);
	}
	
	public void add(E key, int value) {
		value = Math.max(value, 0);
		staticMap.put(key, value);
		staticSum += value;
	}
	
	public void add(E key) {
		add(key, 0);
	}

	public E get(int i) {
		E retval = null;
		Set<E> keys = staticMap.keySet();
		for (E key : keys) {
			retval = key;
			int value = staticMap.get(key);
			if (isVariable && variableMap.containsKey(key)) {
				value+= variableMap.get(key);
			}
			if (i > value) {
				i -= value;
			} else {
				return key;
			}
		}
		return retval;
	}
	
	public E getRandom() {
		int sum = isVariable ? staticSum + variableSum : staticSum;
		int i = (int) (Math.random() * sum);
		return get(i);
	}

	public E getRandomNoRepeat() {
		E newObject = getRandom();
		if (noRepeatLength > 0) {
//			System.out.println(System.currentTimeMillis());
			while (repeats.contains(newObject)) {
				newObject = getRandom();
			}
			repeats.add(newObject);
			while (repeats.size() > noRepeatLength) {
				repeats.remove(0);
			}
		}
		return newObject;
	}

	public void increment(E key, int incValue) {
		int oldValue = 0;
		if (variableMap.containsKey(key)) {
			oldValue = variableMap.get(key);
		}
		int newValue = Math.max(oldValue + incValue, 0);
		variableMap.put(key, newValue);
		variableSum += newValue - oldValue;
	}
	
	public void increment(E key) {
		increment(key, 1);
	}
	
	public void decrement(E key) {
		increment(key, -1);
	}
}