package task5.listeners;

import java.util.Collection;
import java.util.Objects;

import task5.CollectionListener;

public abstract class ReducePrintListener<R> implements CollectionListener {
	
	protected String reductionName;
	
	public ReducePrintListener(String reductionName) {
		this.reductionName = Objects.requireNonNull(reductionName);
	}
	
	public abstract R reduce(Collection<Integer> col);
	
	@Override
	public void collectionChanged(Collection<Integer> col) {
		R reducted = reduce(Objects.requireNonNull(col));
		String msg = reducted == null ? (reductionName + " undefined because collection is empty.")
						: (reductionName + ": " + reducted);
		System.out.println(msg);
	}

}
