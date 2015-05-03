package goal_regression;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import propositional_satisfiability_solver.Clause;

public class GoalRegression {
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
