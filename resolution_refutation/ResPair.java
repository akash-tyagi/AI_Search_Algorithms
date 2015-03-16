package resolution_refutation;

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
		System.out.println("Proporsition:" + proposition);
	}

	public Clause resolve() {
		Clause resolvedClause = new Clause();
		for (String literal : clause1.literals) {
			if (literal.equals(proposition)
					|| literal.equals("-" + proposition))
				continue;

			resolvedClause.addLiteral(literal);
		}
		for (String literal : clause2.literals) {
			if (literal.equals(proposition)
					|| literal.equals("-" + proposition))
				continue;

			resolvedClause.addLiteral(literal);
		}

		System.out.println("resolving " + clause1.id + " and " + clause2.id
				+ " on " + proposition + ": (" + clause1.toString() + ") and ("
				+ clause2.toString() + ") -> (" + resolvedClause.toString()
				+ ")");
		return resolvedClause;
	}
}
