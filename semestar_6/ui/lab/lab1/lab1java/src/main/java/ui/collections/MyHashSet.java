package ui.collections;

import java.util.AbstractSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Spliterator;

public class MyHashSet<E>
extends AbstractSet<E>
implements MySet<E> {

	private HashMap<E,E> map;

	public MyHashSet() {
		map = new HashMap<>();
	}

	public Iterator<E> iterator() {
		return map.keySet().iterator();
	}


	public int size() {
		return map.size();
	}


	public boolean isEmpty() {
		return map.isEmpty();
	}

	public boolean contains(Object o) {
		return map.containsKey(o);
	}

	public boolean add(E e) {
		return map.put(e, e)==null;
	}

	public boolean remove(Object o) {
		E rmvl = map.get(o);
		return map.remove(o)==rmvl;
	}

	public void clear() {
		map.clear();
	}

	public Spliterator<E> spliterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object[] toArray() {
		return new Object[0];
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}

	@Override
	public E getByKey(E e) {
		return map.get(e);
	}
}

