package constraint_satisfaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CspJobPuzzle extends CSP {

	static class Value {
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

	List<Variable> unassigned_jobs;
	List<Variable> assigned_jobs;

	List<Value> workers_notAvailable;
	List<Value> workers_available;

	public static final int TOTALJOBS = 8;
	public static final int TOTALWORKER = 4;

	public CspJobPuzzle() {
		unassigned_jobs = new ArrayList<Variable>();
		assigned_jobs = new ArrayList<Variable>();
		workers_available = new ArrayList<Value>();
		workers_notAvailable = new ArrayList<Value>();
	}

	@Override
	public void setupProblem() {

		for (int i = 0; i < TOTALWORKER; i++) {
			Value singlePerson = new Value();
			workers_available.add(singlePerson);
		}
		workers_available.get(0).setValues("Roberta", false, true, 0);
		workers_available.get(1).setValues("Thelma", false, true, 0);
		workers_available.get(2).setValues("Steve", true, true, 0);
		workers_available.get(3).setValues("Pete", true, false, 0);

		for (int i = 0; i < TOTALJOBS; i++) {
			Variable job = new Variable();
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
	public Variable getUnassignedVariable() {
		if (unassigned_jobs.size() == 0)
			return null;
		return unassigned_jobs.get(0);
	}

	@Override
	public List<Value> getAvailableValues() {
		return workers_available;
	}

	@Override
	public boolean isConsistent() {
		for (Variable job1 : assigned_jobs) {

			if (job1.assignedValue.totalJobsAssigned > 2
					|| job1.assignedValue.totalJobsAssigned <= 0) {
				System.out.println("Jobs assigned count inconsistent");
				return false;
			}

			if (job1.name.equals("Nurse") && job1.assignedValue.male == false) {
				System.out.println("Nurse can not be Female");
				return false;
			}
			if (job1.name.equals("Actor") && job1.assignedValue.male == false) {
				System.out.println("Actor can not be Female");
				return false;
			}

			if (job1.name.equals("Boxer")
					&& job1.assignedValue.name.equals("Roberta")) {
				System.out.println("Roberta can not be boxer");
				return false;
			}
			if ((job1.name.equals("Nurse") || job1.name.equals("Teacher") || job1.name
					.equals("Police"))
					&& job1.assignedValue.isEducated == false) {
				System.out
						.println("Nurse Teacher and Police can not be uneducated");
				return false;
			}

			if (job1.name.equals("Chef")
					&& !job1.assignedValue.name.equals("Thelma")) {
				return false;
			}

			if (job1.name.equals("Clerk")
					&& job1.assignedValue.name.equals("Thelma")) {
				return false;
			}

			if (job1.name.equals("Police")
					&& (job1.assignedValue.name.equals("Roberta") || job1.assignedValue.name
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
		for (Variable job : assigned_jobs) {
			System.out.println("Job:" + job.name + "  Worker:"
					+ job.assignedValue.name);
		}
	}

	@Override
	public void assignValueToVariable(Variable job, Value worker) {
		job.assignValue(worker);
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
	public void unassignValueToVariable(Variable job) {
		Value worker = job.unAssignValue();
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
		Set<Variable> setUnassignedJobs = new HashSet<Variable>(unassigned_jobs);
		if (setUnassignedJobs.size() < unassigned_jobs.size()) {
			System.out.println("#########Duplicates Unassigned job");
		}

		Set<Variable> setassignedJobs = new HashSet<Variable>(assigned_jobs);
		if (setassignedJobs.size() < assigned_jobs.size()) {
			System.out.println("#########Duplicates Assigned Job");
		}

		Set<Value> setAvailableWorkers = new HashSet<Value>(workers_available);
		if (setAvailableWorkers.size() < workers_available.size()) {
			System.out.println("#########Duplicates Workers available");
		}

		Set<Value> setUnavailableWorkers = new HashSet<Value>(
				workers_notAvailable);
		if (setUnavailableWorkers.size() < workers_notAvailable.size()) {
			System.out.println("#########Duplicates workers not available");
		}

	}
}
