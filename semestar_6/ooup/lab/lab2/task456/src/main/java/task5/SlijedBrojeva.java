package task5;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class SlijedBrojeva {
	
	private static final long TIME_MS = 1000;
	
	private Collection<Integer> col = new ArrayList<>();
	private IzvorBrojeva source;
	private List<CollectionListener> listeners = new LinkedList<>();
	
	public SlijedBrojeva(IzvorBrojeva source) {
		this.source = Objects.requireNonNull(source);
	}
	
	public void kreni() {
		while(true) {
			int nextInt = -1;
			
			try {
				nextInt = source.getNext();
			} catch (IOException e) {}
			
			if (nextInt == -1)
				break;
			
			addToCollection(nextInt);
			try {
				Thread.sleep(TIME_MS);
			} catch (InterruptedException e) {}
		}
	}
	
	public void addCollectionListener(CollectionListener cl) {
		listeners.add(Objects.requireNonNull(cl));
	}
	
	private void addToCollection(int i) {
		boolean added = col.add(i);
		if (added)
			notifyListeners();
	}
	
	private void notifyListeners() {
		for (var li : listeners)
			li.collectionChanged(col);
	}

}
