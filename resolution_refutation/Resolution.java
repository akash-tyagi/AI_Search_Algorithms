package resolution_refutation;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Resolution {
	MyPriorityQueue candidate_queue;
	Map<String, Clause> mapClause;

	public void init() {
		candidate_queue = new MyPriorityQueue();
		mapClause = new Hashtable<String, Clause>();
	}

	private String getKey(Clause clause) {
		Set<String> treeSet = new TreeSet<String>();

		for (String literal : clause.literals) {
			treeSet.add(literal);
		}
		return treeSet.toString();
	}

	private boolean isOldClause(Clause clause) {
		String key = getKey(clause);
		return (mapClause.containsKey(key)) ? true : false;
	}

	private void addResolvableResPairs(Clause clause1, Clause clause2) {
		// propositions on which clauses can be resolved
		ResPair resPair = null;
		List<String> propositions = clause1.getOppositeLiterals(clause2);
		if (propositions.size() == 0)
			return;

		for (String proposition : propositions) {
			resPair = new ResPair(clause1, clause2, proposition);
			candidate_queue.push(resPair);
		}

	}

	public Clause resolution(List<Clause> clauses) {
		ResPair resPair = null;
		init();
		int id = 0;

		System.out.println("initial clauses:");
		for (Clause clause1 : clauses) {
			if (isOldClause(clause1))
				continue;
			clause1.id = id++;

			mapClause.put(getKey(clause1), clause1);
			System.out.println(clause1.id + ": (" + clause1.toString() + ")");

			for (Clause clause2 : clauses) {
				if (isOldClause(clause2))
					continue;
				addResolvableResPairs(clause1, clause2);
			}
		}

		System.out.println("------------------------------");

		while (candidate_queue.isEmpty() != true) {
			System.out.print("[Qsize=" + candidate_queue.size() + "]");
			resPair = candidate_queue.pop();
			Clause resolvedClause = resPair.resolve();
			resolvedClause.id = id++;

			System.out.println(resolvedClause.id + ": ("
					+ resolvedClause.toString() + ")");

			if (resolvedClause.isEmpty()) {
				System.out.println("Success - empty clause!");
				return resolvedClause;
			}

			if (isOldClause(resolvedClause))
				continue;

			mapClause.put(getKey(resolvedClause), resolvedClause);
			for (String key : mapClause.keySet()) {
				addResolvableResPairs(resolvedClause, mapClause.get(key));
			}

			if (candidate_queue.size() > 5000000)
				break;

		}
		System.out.println("Failure");
		return null;
	}

	void printProofTrace(Clause clause) {
		if (clause == null)
			return;
		System.out.println("--------------------");
		System.out.println("proof trace:");
		proofTrace(clause, 1);
	}

	private void proofTrace(Clause clause, int depth) {
		if (clause == null)
			return;

		for (int i = 0; i < depth; i++) {
			System.out.print("  ");
		}
		System.out.print(clause.id + ": (" + clause.toString() + ") ");
		if (clause.resClause1 == null)
			System.out.println("input");
		else
			System.out.println("[" + clause.resClause1.id + ","
					+ clause.resClause2.id + "]");
		proofTrace(clause.resClause1, depth + 1);
		proofTrace(clause.resClause2, depth + 1);
	}
}
