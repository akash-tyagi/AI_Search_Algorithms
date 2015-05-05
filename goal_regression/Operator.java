package goal_regression;

import java.util.ArrayList;
import java.util.List;

public class Operator {
	public String name;
	public List<String> precondList, addList, conflictList, delList;

	boolean isOperatorRelevant(String goal) {
		if (addList.contains(goal))
			return true;
		return false;
	}

	boolean isOperatorConsistent(List<String> goals) {
		// List a,b; union is caculating as a+(b-a);
		List<String> uniqueConflictList = new ArrayList<String>(conflictList);
		uniqueConflictList.removeAll(delList);

		List<String> unionList = new ArrayList<String>(delList);
		unionList.addAll(uniqueConflictList);

		List<String> conflictingGoals = new ArrayList<String>(goals);
		conflictingGoals.removeAll(unionList);
		if (goals.size() == conflictingGoals.size())
			return true;
		return false;
	}

	public List<String> regress(List<String> goals) {
		List<String> newGoals = new ArrayList<String>(goals);
		newGoals.removeAll(addList);
		newGoals.addAll(precondList);
		return newGoals;
	}

	public void addPrecondList(String cond) {
		precondList.add(cond);
	}

	public void addAddlist(String cond) {
		addList.add(cond);
	}

	public void addConflictList(String cond) {
		conflictList.add(cond);
	}

	public void addDelList(String cond) {
		delList.add(cond);
	}

	public void printList(List<String> list) {
		for (String string : list) {
			System.out.print(string + " ");
		}
		System.out.println("");
	}

	public void printOperator() {
		System.out.println("OPER: " + name);
		System.out.print("precond: ");
		printList(precondList);
		System.out.print("addlist: ");
		printList(addList);
		System.out.print("dellist: ");
		printList(delList);
		System.out.print("conflict: ");
		printList(conflictList);
		System.out.println("");
	}

	public Operator() {
		precondList = new ArrayList<String>();
		addList = new ArrayList<String>();
		conflictList = new ArrayList<String>();
		delList = new ArrayList<String>();
	}

}
