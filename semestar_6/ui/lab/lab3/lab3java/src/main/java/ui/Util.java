package ui;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Util {
	
	private static final double LOG_2 = Math.log(2.0);
	
	private Util() {}
	
	private static class EqualsPredicate<T> implements Predicate<T> {
		
		T obj;
		
		@Override
		public boolean test(T t) {
			return obj.equals(t);
		}
		
	}
	
	private static class MapValueExtractor<K, V> implements Function<Map<K, V>, V> {
		
		K key;
		
		@Override
		public V apply(Map<K, V> t) {
			return t.get(key);
		}
		
	}
	
	private static class MutableInteger implements Comparable<MutableInteger> {
		int val;
		
		MutableInteger(int val) { this.val = val; }
		int getVal() { return val; }
		void setVal(int val) { this.val = val; }
		void increment() { setVal(val+1); }
		@Override
		public int hashCode() { return Objects.hash(val); }
		@Override
		public boolean equals(Object obj) {
			if (obj == null)
				return false;
			if (this == obj)
				return true;
			if (this.getClass() != obj.getClass())
				return false;
			MutableInteger other = (MutableInteger) obj;
			return this.val==other.val;
		}
		@Override
		public int compareTo(MutableInteger o) {
			return Integer.compare(val, o.val);
		}
	}
	
	public static <T> Set<T> subset(Set<T> original, Supplier<Set<T>> setCreator, Predicate<? super T> pred) {
		Set<T> created = setCreator.get();
		for (T elem : original) {
			if (pred.test(elem))
				created.add(elem);
		}
		return created;
	}
	
	public static <A, R> ArgMaxResult<A, R> argMax(Iterable<A> it, Function<A, R> function, Comparator<? super R> comp, 
			Supplier<? extends List<A>> argCollectionCreator) {
		boolean firstPassed = false;
		R max = null;
		List<A> col = argCollectionCreator.get();
		for (A arg : it) {
			R argRes = function.apply(arg);
			
			if (!firstPassed || comp.compare(argRes, max) > 0) {
				firstPassed = true;
				max = argRes;
			}
			
		}
		
		for (A arg : it) {
			R argRes = function.apply(arg);
			if (comp.compare(argRes, max) == 0)
				col.add(arg);
		}
		
		ArgMaxResult<A, R> res = new ArgMaxResult<>(max, col);
		return res;
	}
	
	public static <T, U> Map<U, Integer> count(Iterable<T> it, Function<T, U> extractor, Supplier<Map<U, Integer>> mapCreator) {
		return count(it, extractor, o->true, mapCreator);
	}
	
	public static <T, U> Map<U, Integer> count(Iterable<T> it, Function<T, U> extractor, Predicate<? super U> pred, Supplier<Map<U, Integer>> mapCreator) {
		Map<U, Integer> countMap = mapCreator.get();
		count(it, extractor, pred, mapCreator.get());
		return countMap;
	}
	
	static <T, U> void count(Iterable<T> it, Function<T, U> extractor, Predicate<? super U> pred, Map<U, Integer> m) {
		Map<U, MutableInteger> countMap = new HashMap<>();
		for (T elem : it) {
			U extracted = extractor.apply(elem);
			if (!pred.test(extracted))
				continue;
			MutableInteger mi = countMap.get(extracted);
			if (mi == null) {
				countMap.put(extracted, new MutableInteger(0));
				mi = countMap.get(extracted);
			}
			
			mi.increment();
		}
		
		for (var e : countMap.entrySet()) {
			m.put(e.getKey(), e.getValue().getVal());
		}
		countMap.clear();
	}
	
	public static <T> Map<T, Integer> count(Iterable<T> it, Supplier<Map<T, Integer>> mapCreator) {
		return count(it, Function.identity(), mapCreator);
	}
	
	public static <K, V> double informationGain(Set<Map<K, V>> data, K key, K out, Set<? extends V> values) {
		MapValueExtractor<K, V> mve = new MapValueExtractor<>();
		mve.key = key;
		//TODO
		Map<V, Integer> countMap = count(data, mve, HashMap::new);
		double startingEntropy = entropy(countMap);
		return 0.0;
	}
	
	public static double entropyCol(Collection<?> list) {
		int total = list.size();
		Map<?, Integer> countMap = count(list, HashMap::new);
		return entropy(countMap.values(), total, countMap.size());
	}
	
	public static double entropy(Collection<Integer> frequencies) {
		int total = 0;
		for (Integer count : frequencies) {
			if (count == null)
				continue;
			int v = count.intValue();
			total += v;
		}
		return entropy(frequencies, total, frequencies.size());
	}
	
	private static double entropy(Collection<Integer> frequencies, int total, int diff) {
		if (total <= 1 || diff <= 1)
			return 0.0;
		double sum = 0.0;
		for (var fobj : frequencies) {
			if (fobj==null)
				continue;
			int f = fobj.intValue();
			if (f < 0)
				throw new IllegalArgumentException("Invalid frequency: " + f);
			double prob = (1.0 * f) / total;
			double log = Math.log(prob) / LOG_2;
			sum += (prob*log);
		}
		return -sum;
	}
	
	public static double entropy(Map<?, Integer> map) {
		return entropy(map.values());
	}
	
}
