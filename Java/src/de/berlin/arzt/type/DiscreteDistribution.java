package de.berlin.arzt.type;

import java.util.HashMap;
import java.util.Iterator;
import de.berlin.arzt.Math2;

public class DiscreteDistribution<E> extends HashMap<E, Double> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 862667662940363927L;
	
	public DiscreteDistribution() {
		super();
	}
	
	public void uniformize(double w) {
		double pUni = 1d/keySet().size();
		for (E key: keySet()) {
			double pOrg = get(key);
			double pWeighted = w*pUni + (1-w)*pOrg;
			put(key, pWeighted);
		}
	}
	
	public void normalize() {
		double sum = Math2.sum(values());
		for (E key: keySet()) {
			double value = get(key)/sum;
			put(key, value);
		}
	}
	
	public void muliply(E key, double factor) {
		put(key, get(key)*factor);
		normalize();
	}
		
	public E getSample(double p) {
		assert(p >= 0);
		assert(p < 1);
		E retval = null;
		Iterator<E> keys = keySet().iterator();
		double sum = 0;
		do {
			retval = keys.next();
			double value = get(retval);
			sum += value;
		} while (p > sum && keys.hasNext());
		assert(retval != null);
		return retval;
	}
	
	public E getSample() {
		return getSample(Math.random());
	}

	public void toUniformDistribution() {
		double p = 1d/keySet().size();
		for (E key: keySet()) {
			put(key, p);
		}
	}

}