package task5.listeners;

import java.util.Collection;
import java.util.OptionalDouble;

public class AveragePrintListener extends ReducePrintListener<Double> {

	public AveragePrintListener() {
		super("Average");
	}

	@Override
	public Double reduce(Collection<Integer> col) {
		OptionalDouble odavg = col.stream().mapToInt(Integer::intValue).average();
		return odavg.isPresent() ? odavg.getAsDouble() : null;
	}

}
