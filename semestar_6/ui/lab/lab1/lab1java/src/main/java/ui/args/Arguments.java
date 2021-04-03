package ui.args;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arguments {
	
	public static final String ARG_ALG = "--alg";
	public static final String ARG_SS = "--ss";
	public static final String ARG_H = "--h";
	public static final String FLAG_CHECK_OPTIMISTIC = "--check-optimistic";
	public static final String FLAG_CHECK_CONSISTENT = "--check-consistent";
	
	private static final List<String> FLAGS = List.of(
			FLAG_CHECK_OPTIMISTIC,
			FLAG_CHECK_CONSISTENT
	);
	private static final List<String> ARGS = List.of(
			ARG_ALG, ARG_SS, ARG_H
	);
	private static final String DUMMY = "";
	
	private Map<String, String> argsMap = new HashMap<>();
	
	public static Arguments parse(String[] args) {
		Arguments arguments = new Arguments();
		for (int i=0, len=args.length; i<len; i++) {
			String arg = args[i];
			int idx = ARGS.indexOf(arg);
			if (idx == -1) {
				idx = FLAGS.indexOf(arg);
				if (idx == -1)
					throw new IllegalArgumentException("Unknown argument " + arg);
				
				arguments.argsMap.put(arg, DUMMY);
				continue;
			}
			
			if (i==len-1)
				throw new IllegalArgumentException("Unspecified argument " + arg);
			
			i++;
			String nextArg = args[i];
			boolean isArgItself = (ARGS.indexOf(nextArg)!=-1) || (FLAGS.indexOf(nextArg)!=-1);
			if (isArgItself)
				throw new IllegalArgumentException(
				String.format("After argument \"%s\" follows \"%s\" which is illegal!", arg, nextArg)
				);
			
			arguments.argsMap.put(arg, nextArg);
		}
		return arguments;
	}
	
	private String getArg(String arg) {
		return argsMap.get(arg);
	}
	
	public String getArgAlg() {
		return getArg(ARG_ALG);
	}
	
	public String getArgSS() {
		return getArg(ARG_SS);
	}
	
	public String getArgH() {
		return getArg(ARG_H);
	}
	
	public boolean checkOptimisticFlag() {
		return getArg(FLAG_CHECK_OPTIMISTIC) != null;
	}
	
	public boolean checkConsistentFlag() {
		return getArg(FLAG_CHECK_CONSISTENT) != null;
	}
	
	
}
