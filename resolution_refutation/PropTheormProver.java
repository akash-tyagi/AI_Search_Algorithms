package resolution_refutation;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class PropTheormProver {

	public static void main(String args[]) throws IOException {
		String file_name = args[0];
		file_name = "src/resolution_refutation/" + file_name;

		PropTheormProver prover = new PropTheormProver();
		List<Clause> clauses = prover.readClausesFromFile(file_name);

		// for (Clause clause : clauses) {
		// clause.print();
		// System.out.println("");
		// }

		Resolution resolution = new Resolution();
		resolution.resolution(clauses);
	}

	private List<Clause> readClausesFromFile(String file_name)
			throws IOException {
		Charset charset = Charset.forName("US-ASCII");
		List<Clause> clauses = new ArrayList<Clause>();

		for (String line : Files.readAllLines(Paths.get(file_name), charset)) {
			if (line.contains("#") || line.isEmpty())
				continue;

			String words[] = line.split(" ");
			Clause clause = new Clause();

			for (String literal : words) {
				clause.addLiteral(literal);
			}
			clauses.add(clause);
		}
		return clauses;
	}
}
