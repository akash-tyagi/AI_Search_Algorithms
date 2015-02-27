package constraint_satisfaction;

import constraint_satisfaction.CspJobPuzzle.Worker;

public class Csp_Solver {

	static int iter = 0;

	public boolean backTrack(CSP problem) {
		System.out.println(iter++);

		Job job = problem.getUnassignedVariable();
		for (Worker worker : problem.getDomainValues()) {
			problem.assignJobToWorker(job, worker);
			if (problem.isConsistent() && backTrack(problem)) {
				return true;
			}
			problem.unassignJobForWorker(job);
		}
		return false;
	}

	public static void main(String args[]) {
		Csp_Solver solver = new Csp_Solver();

		CspJobPuzzle problem = new CspJobPuzzle();
		problem.setupProblem();

		if (solver.backTrack(problem)) {
			problem.printSolution();
		}

	}
}
