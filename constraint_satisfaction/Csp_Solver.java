package constraint_satisfaction;

import java.util.ArrayList;
import java.util.List;

import constraint_satisfaction.CspJobPuzzle.Worker;

public class Csp_Solver {

	static int iter = 1;

	public boolean backTrack(CspJobPuzzle problem) {
		System.out.println("\n" + iter++);

		Job job = problem.getUnassignedJob();
		if (job == null) {
			return true;
		}
		System.out.println("Considering Job:" + job.name + "\n");

		List<Worker> availableWorkers = new ArrayList<Worker>(
				problem.getAvailableWorkers());
		if (availableWorkers.size() <= 0)
			System.out.println("No one available");
		for (Worker worker : availableWorkers) {
			System.out.println("Available:" + worker.name);
		}

		for (Worker worker : availableWorkers) {
			System.out.println("Considering Worker:" + worker.name);
			problem.assignJobToWorker(job, worker);
			if (problem.isConsistent() && backTrack(problem)) {
				return true;
			}
			System.out.println("Not Consistent\n");
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
		} else {
			System.out.println("Done");
			problem.printSolution();
		}

	}
}
