package resolution_refutation;

import java.util.ArrayList;
import java.util.List;

public class ResPair {
	Clause clause1;
	Clause clause2;
	String proposition;
	public int heur;

	public ResPair(Clause clause1, Clause clause2, String proposition) {
		this.clause1 = clause1;
		this.clause2 = clause2;
		this.proposition = proposition;
		heur = clause1.getSize() + clause2.getSize();
	}

	public void print() {
		System.out.println("------------------------------------");
		System.out.print("Clause1:");
		clause1.print();
		System.out.print("Clause2:");
		clause2.print();
		System.out.println("Proposition:" + proposition);
	}

	public Clause resolve() {
		Clause resolvedClause = new Clause();
		List<String> actual = resolvedClause.literals;

		List<String> literals1 = new ArrayList<String>(clause1.literals);
		List<String> literals2 = new ArrayList<String>(clause2.literals);

		String negProposition = (proposition.contains("-")) ? proposition
				.substring(1) : "-" + proposition;

		if (literals1.contains(proposition)
				&& literals2.contains(negProposition)) {
			literals1.remove(literals1.lastIndexOf(proposition));
			literals2.remove(literals2.lastIndexOf(negProposition));
		} else {
			literals1.remove(literals1.lastIndexOf(negProposition));
			literals2.remove(literals2.lastIndexOf(proposition));
		}

		// Removing Duplicate literals
		for (String string : literals1) {
			if (actual.contains(string))
				continue;
			actual.add(string);
		}
		for (String string : literals2) {
			if (actual.contains(string))
				continue;
			actual.add(string);
		}

		resolvedClause.resClause1 = clause1;
		resolvedClause.resClause2 = clause2;

		System.out.println("resolving " + clause1.id + " and " + clause2.id
				+ " on " + proposition + ": (" + clause1.toString() + ") and ("
				+ clause2.toString() + ") -> (" + resolvedClause.toString()
				+ ")");
		return resolvedClause;
	}
}
