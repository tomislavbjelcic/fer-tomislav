package task5.listeners;

import java.util.Collection;

public class SumPrintListener extends ReducePrintListener<Integer> {

	public SumPrintListener() {
		super("Sum");
	}

	@Override
	public Integer reduce(Collection<Integer> col) {
		int sum = col.stream().mapToInt(Integer::intValue).sum();
		return sum;
	}

}
