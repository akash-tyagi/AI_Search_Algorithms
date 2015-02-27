package constraint_satisfaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CspJobPuzzle extends CSP {

	static class Worker {
		public void setValues(String name, boolean male, boolean isEducated,
				int totalJobsAssigned) {
			this.name = name;
			this.male = male;
			this.isEducated = isEducated;
			this.totalJobsAssigned = totalJobsAssigned;
		}

		public String name;
		public boolean male;
		public boolean isEducated;
		public int totalJobsAssigned;

	}

	List<Job> unassigned_jobs;
	List<Job> assigned_jobs;

	List<Worker> workers_with_job;
	List<Worker> workers_without_job;

	public static final int TOTALJOBS = 8;
	public static final int TOTALWORKER = 4;

	public CspJobPuzzle() {
		unassigned_jobs = new ArrayList<Job>();
		assigned_jobs = new ArrayList<Job>();
		workers_without_job = new ArrayList<Worker>();
		workers_with_job = new ArrayList<Worker>();
	}

	@Override
	public void setupProblem() {

		for (int i = 0; i < TOTALWORKER; i++) {
			Worker singlePerson = new Worker();
			workers_without_job.add(singlePerson);
		}
		workers_without_job.get(0).setValues("Roberto", true, true, 0);
		workers_without_job.get(1).setValues("Thelma", false, true, 0);
		workers_without_job.get(2).setValues("Steve", true, true, 0);
		workers_without_job.get(3).setValues("Pete", true, false, 0);

		for (int i = 0; i < TOTALJOBS; i++) {
			Job job = new Job();
			unassigned_jobs.add(job);
		}

		unassigned_jobs.get(0).name = "Chef";
		unassigned_jobs.get(1).name = "Guard";
		unassigned_jobs.get(2).name = "Nurse";
		unassigned_jobs.get(3).name = "Clerk";
		unassigned_jobs.get(4).name = "Police";
		unassigned_jobs.get(5).name = "Teacher";
		unassigned_jobs.get(6).name = "Actor";
		unassigned_jobs.get(7).name = "Boxer";

	}

	@Override
	public Job getUnassignedVariable() {
		return unassigned_jobs.get(0);
	}

	@Override
	public List<Worker> getDomainValues() {
		return workers_without_job;
	}

	@Override
	public boolean isConsistent() {
		for (Job job1 : assigned_jobs) {

			if (job1.assignedWorker.totalJobsAssigned > 2
					|| job1.assignedWorker.totalJobsAssigned <= 0
					|| (job1.name.equals("Nurse") && job1.assignedWorker.male == false)
					|| (job1.name.equals("Actor") && job1.assignedWorker.male == false)
					|| (job1.name.equals("Boxer") && job1.assignedWorker.name
							.equals("Roberta"))
					|| ((job1.name.equals("Nurse")
							|| job1.name.equals("Teacher") || job1.name
								.equals("Police")) && job1.assignedWorker.isEducated == false)) {
				return false;
			}

			for (Job job2 : assigned_jobs) {
				if (job1 == job2)
					continue;

				// job should not be assigned to two people
				if (job1.assignedWorker == job2.assignedWorker)
					return false;

			}
		}

		return true;
	}

	@Override
	public void printSolution() {
		System.out.println("Expected Solution:");
		for (Job job : assigned_jobs) {
			System.out.println("Job:" + job.name + "  Worker:"
					+ job.assignedWorker.name);
		}
	}

	@Override
	public void assignJobToWorker(Job job, Worker worker) {
		job.assignWorker(worker);
		worker.totalJobsAssigned++;
		workers_without_job.remove(worker);
		workers_with_job.add(worker);
	}

	@Override
	public void unassignJobForWorker(Job job) {
		Worker worker = job.unAssignWorker();
		worker.totalJobsAssigned--;

		if (worker.totalJobsAssigned <= 0) {
			workers_with_job.remove(worker);
			workers_without_job.add(worker);
		}

	}
}
