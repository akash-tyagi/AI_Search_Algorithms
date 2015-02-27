package constraint_satisfaction;

import constraint_satisfaction.CspJobPuzzle.Worker;

public class Job {
	public String name;
	public Worker assignedWorker;

	public Job() {
		this.name = null;
		assignedWorker = null;
	}

	public void assignWorker(Worker worker) {
		assignedWorker = worker;

	}

	public Worker unAssignWorker() {
		Worker prevWorker = assignedWorker;
		assignedWorker = null;
		return prevWorker;
	}

}
