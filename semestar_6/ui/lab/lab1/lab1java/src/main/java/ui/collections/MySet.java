package ui.collections;

import java.util.Set;

public interface MySet<E> extends Set<E> {

	E getByKey(E e);
	
}
