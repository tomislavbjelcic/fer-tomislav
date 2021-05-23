package ui;

import java.util.List;

public class ArgMaxResult<A, R> {
	
	public R max;
	public List<A> args;
	
	public ArgMaxResult() {}
	
	public ArgMaxResult(R max, List<A> args) {
		this.max = max;
		this.args = args;
	}
	
	
}
