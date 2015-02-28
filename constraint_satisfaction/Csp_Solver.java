package constraint_satisfaction;

import java.util.ArrayList;
import java.util.List;

public class Csp_Solver {

	static int iter = 1;

	public boolean backTrack(CSP problem) {
		System.out.println("\n" + iter++);

		Variable var = problem.getUnassignedVariable();
		if (var == null) {
			return true;
		}
		System.out.println("Considering Var:" + problem.getVariableName(var)
				+ "\n");

		List<Integer> availableValues = new ArrayList<Integer>(
				problem.getAvailableValues(var));

		if (availableValues.size() <= 0)
			System.out.println("No one available");
		for (Integer worker : availableValues) {
			System.out.println("Available:" + worker);
		}

		for (int value : availableValues) {
			System.out.println("Considering Value:"
					+ problem.getValueName(value));

			problem.assignValueToVariable(var, value);
			if (problem.isConsistent() && backTrack(problem)) {
				return true;
			}
			System.out.println("Not Consistent\n");
			problem.unassignValueToVariable(var);
		}
		return false;
	}

	public static void main(String args[]) {
		Csp_Solver solver = new Csp_Solver();

		CspJobPuzzle problem = new CspJobPuzzle();
		problem.setupProblem();

		// CspZebraPuzzle problem = new CspZebraPuzzle();
		// problem.setupProblem();

		if (solver.backTrack(problem)) {
			problem.printSolution();
		} else {
			System.out.println("Done");
			problem.printSolution();
		}

	}
}
