package constraint_satisfaction;

import java.util.ArrayList;
import java.util.List;

public class Csp_Solver {

	static int iter = 1;

	public boolean backTrack(CSP problem, int useMRV) {
		System.out.println("\n" + iter++);

		Variable var = null;
		if (useMRV == 0) {
			var = problem.getUnassignedVariable();
		} else {
			var = problem.getMRVBasedVariable();
		}

		if (var == null) {
			return true;
		}

		System.out.println("Considering Var:" + problem.getVariableName(var)
				+ "\n");

		List<Integer> availableValues = new ArrayList<Integer>(
				problem.getAvailableValues(var));

		for (int value : availableValues) {
			System.out.println("Considering Value:"
					+ problem.getValueName(value));

			problem.assignValueToVariable(var, value);
			if (problem.isConsistent() && backTrack(problem, useMRV)) {
				return true;
			}
			System.out.println("Not Consistent\n");
			problem.unassignValueToVariable(var);
		}
		return false;
	}

	public static void main(String args[]) {
		int probNo = Integer.parseInt(args[0]);
		int useMRV = Integer.parseInt(args[1]);

		Csp_Solver solver = new Csp_Solver();
		CSP problem = null;

		if (probNo == 1) {
			problem = new CspJobPuzzle();
			problem.setupProblem();
		} else {
			problem = new CspZebraPuzzle();
			problem.setupProblem();
		}

		if (solver.backTrack(problem, useMRV)) {
			System.out.println("Done: Solution Found");
			problem.printSolution();
		} else {
			System.out.println("Done: No Solution Found");
			problem.printSolution();
		}

	}
}
