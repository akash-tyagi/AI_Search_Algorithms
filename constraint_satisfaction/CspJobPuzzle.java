package constraint_satisfaction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CspJobPuzzle extends CSP {

	static class Worker {
		public void setValues(String name, boolean male, boolean isEducated,
				int totalJobsAssigned) {
			this.name = name;
			this.male = male;
			this.isEducated = isEducated;
			this.totalJobsAssigned = totalJobsAssigned;
		}

		public void incrementJobsAssigned() {
			totalJobsAssigned++;
			if (totalJobsAssigned > 2) {
				System.out.println("################Total Jobs Assigned More");
			}
		}

		public void decrementJobsAssigned() {
			totalJobsAssigned--;
			if (totalJobsAssigned < 0) {
				System.out.println("################Total Jobs less than 0");
			}
		}

		public String name;
		public boolean male;
		public boolean isEducated;
		public int totalJobsAssigned;

	}

	List<Job> unassigned_jobs;
	List<Job> assigned_jobs;

	List<Worker> workers_notAvailable;
	List<Worker> workers_available;

	public static final int TOTALJOBS = 8;
	public static final int TOTALWORKER = 4;

	public CspJobPuzzle() {
		unassigned_jobs = new ArrayList<Job>();
		assigned_jobs = new ArrayList<Job>();
		workers_available = new ArrayList<Worker>();
		workers_notAvailable = new ArrayList<Worker>();
	}

	@Override
	public void setupProblem() {

		for (int i = 0; i < TOTALWORKER; i++) {
			Worker singlePerson = new Worker();
			workers_available.add(singlePerson);
		}
		workers_available.get(0).setValues("Roberta", false, true, 0);
		workers_available.get(1).setValues("Thelma", false, true, 0);
		workers_available.get(2).setValues("Steve", true, true, 0);
		workers_available.get(3).setValues("Pete", true, false, 0);

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
	public Job getUnassignedJob() {
		if (unassigned_jobs.size() == 0)
			return null;
		return unassigned_jobs.get(0);
	}

	@Override
	public List<Worker> getAvailableWorkers() {
		return workers_available;
	}

	@Override
	public boolean isConsistent() {
		for (Job job1 : assigned_jobs) {

			if (job1.assignedWorker.totalJobsAssigned > 2
					|| job1.assignedWorker.totalJobsAssigned <= 0) {
				System.out.println("Jobs assigned count inconsistent");
				return false;
			}

			if (job1.name.equals("Nurse") && job1.assignedWorker.male == false) {
				System.out.println("Nurse can not be Female");
				return false;
			}
			if (job1.name.equals("Actor") && job1.assignedWorker.male == false) {
				System.out.println("Actor can not be Female");
				return false;
			}

			if (job1.name.equals("Boxer")
					&& job1.assignedWorker.name.equals("Roberta")) {
				System.out.println("Roberta can not be boxer");
				return false;
			}
			if ((job1.name.equals("Nurse") || job1.name.equals("Teacher") || job1.name
					.equals("Police"))
					&& job1.assignedWorker.isEducated == false) {
				System.out
						.println("Nurse Teacher and Police can not be uneducated");
				return false;
			}

			if (job1.name.equals("Chef")
					&& !job1.assignedWorker.name.equals("Thelma")) {
				return false;
			}

			if (job1.name.equals("Clerk")
					&& job1.assignedWorker.name.equals("Thelma")) {
				return false;
			}

			if (job1.name.equals("Police")
					&& (job1.assignedWorker.name.equals("Roberta") || job1.assignedWorker.name
							.equals("Thelma"))) {
				return false;
			}

			// for (Job job2 : assigned_jobs) {
			// if (job1 == job2)
			// continue;
			//
			// // job should not be assigned to two people
			// if (job1.assignedWorker == job2.assignedWorker)
			// return false;
			//
			// }
		}

		return true;
	}

	@Override
	public void printSolution() {
		System.out.println("Expected Solution:$$$$$$$$$$$$$$$$$$$");
		for (Job job : assigned_jobs) {
			System.out.println("Job:" + job.name + "  Worker:"
					+ job.assignedWorker.name);
		}
	}

	@Override
	public void assignJobToWorker(Job job, Worker worker) {
		job.assignWorker(worker);
		unassigned_jobs.remove(job);
		assigned_jobs.add(job);

		worker.incrementJobsAssigned();
		if (worker.totalJobsAssigned == 2) {
			workers_available.remove(worker);
			workers_notAvailable.add(worker);
		}
		areDuplicates();
		System.out.println("Assigned Job:" + job.name + " Worker:"
				+ worker.name);
	}

	@Override
	public void unassignJobForWorker(Job job) {
		Worker worker = job.unAssignWorker();
		unassigned_jobs.add(job);
		assigned_jobs.remove(job);

		if (worker.totalJobsAssigned == 2) {
			workers_notAvailable.remove(worker);
			workers_available.add(worker);
		}
		worker.decrementJobsAssigned();

		areDuplicates();
		System.out.println("Unassigning Job:" + job.name + " worker:"
				+ worker.name);
	}

	public void areDuplicates() {
		Set<Job> setUnassignedJobs = new HashSet<Job>(unassigned_jobs);
		if (setUnassignedJobs.size() < unassigned_jobs.size()) {
			System.out.println("#########Duplicates Unassigned job");
		}

		Set<Job> setassignedJobs = new HashSet<Job>(assigned_jobs);
		if (setassignedJobs.size() < assigned_jobs.size()) {
			System.out.println("#########Duplicates Assigned Job");
		}

		Set<Worker> setAvailableWorkers = new HashSet<Worker>(workers_available);
		if (setAvailableWorkers.size() < workers_available.size()) {
			System.out.println("#########Duplicates Workers available");
		}

		Set<Worker> setUnavailableWorkers = new HashSet<Worker>(
				workers_notAvailable);
		if (setUnavailableWorkers.size() < workers_notAvailable.size()) {
			System.out.println("#########Duplicates workers not available");
		}

	}
}
