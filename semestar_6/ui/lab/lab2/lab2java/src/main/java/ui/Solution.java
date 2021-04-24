package ui;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import ui.loaders.AbstractLoader;
import ui.loaders.ClauseLoader;
import ui.loaders.UserCommandLoader;
import ui.resolution.ProofResult;
import ui.resolution.Reasoning;

public class Solution {
	
	private static final String RESO = "resolution";
	private static final String COOK = "cooking";
	private static final AbstractLoader<Clause> KNOWLEDGE_LOADER = new ClauseLoader();
	private static final AbstractLoader<UserCommandInfo> CMD_LOADER = new UserCommandLoader();

	public static void main(String ... args) {
		/*
		AbstractLoader<Clause> loader = new ClauseLoader();
		
		String constant = "lab2_files/resolution_examples/";
		String pathstr = constant + args[0];
		Path p = Paths.get(pathstr);
		List<Clause> clauses = loader.loadFromFile(p);
		
		ClauseSet cs = new ClauseSet();
		Clause last = null;
		int size = clauses.size();
		
		var lit = clauses.listIterator();
		while (lit.hasNext()) {
			int idx = lit.nextIndex();
			Clause c = lit.next();
			if (idx == size - 1)
				last = c;
			else
				cs.addClause(c);
		}
		
		ProofResult res = Reasoning.prove(cs, last);
		System.out.println(res);
		*/
		
		String flagStr = args[0];
		String knowledgePathStr = args[1];
		Path knowledgePath = Paths.get(knowledgePathStr);
		
		List<Clause> clauses = KNOWLEDGE_LOADER.loadFromFile(knowledgePath);
		ClauseSet knowledge = new ClauseSet();
		Clause last = null;
		int size = clauses.size();
		
		var lit = clauses.listIterator();
		while (lit.hasNext()) {
			int idx = lit.nextIndex();
			Clause c = lit.next();
			if (idx == size - 1)
				last = c;
			else
				knowledge.addClause(c);
		}
		
		boolean resoFlag = RESO.equals(flagStr);
		boolean cookFlag = COOK.equals(flagStr);
		
		if (resoFlag) {
			ProofResult res = Reasoning.prove(knowledge, last);
			System.out.println(res);
			return;
		}
		
		if (cookFlag) {
			
			System.out.println("Constructed with knowledge:");
			clauses.forEach(System.out::println);
			System.out.println();
			
			knowledge.addClause(last);
			String commandsPathStr = args[2];
			Path commandsPath = Paths.get(commandsPathStr);
			
			List<UserCommandInfo> commands = CMD_LOADER.loadFromFile(commandsPath);
			for (var ucmd : commands) {
				System.out.println(ucmd);
				UserCommand cmd = ucmd.cmd;
				Clause cls = ucmd.clause;
				cmd.execute(knowledge, cls);
				System.out.println();
			}
		}
		
	}

}
