package propositional_satisfiability_solver;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PropSatSolver {
	List<String> symbols = new ArrayList<String>();
	List<Clause> clauses = new ArrayList<Clause>();

	public static void main(String args[]) throws IOException {
		String file_name = args[0];
		file_name = "src/propositional_satisfiability_solver/" + file_name;

		PropSatSolver prover = new PropSatSolver();
		prover.readClausesFromFile(file_name);

		System.out.println("Props:");
		for (String symbol : prover.symbols) {
			System.out.print(symbol + " ");
		}

		System.out.println("\n\nInitial Clauses:");
		for (Clause clause : prover.clauses) {
			clause.print();
		}

		DPLL dpll = new DPLL(prover.symbols, prover.clauses);
		if (dpll.dpllSatisfiable() == false) {
			System.out.println("\n\nFailed");
		}
	}

	private List<Clause> readClausesFromFile(String file_name)
			throws IOException {
		Charset charset = Charset.forName("US-ASCII");

		for (String line : Files.readAllLines(Paths.get(file_name), charset)) {
			if (line.contains("#") || line.isEmpty())
				continue;

			String words[] = line.split(" ");
			Clause clause = new Clause();

			for (String literal : words) {
				clause.addLiteral(literal);

				literal = Clause.getPositiveLiteral(literal);
				if (symbols.contains(literal) == false)
					symbols.add(literal);

			}
			clauses.add(clause);
		}
		return clauses;
	}
}
