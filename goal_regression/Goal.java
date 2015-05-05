package goal_regression;

import java.util.ArrayList;
import java.util.List;

public class Goal {
	public List<String> goalList;

	public Goal() {
		goalList = new ArrayList<String>();
	}

	public Goal(List<String> goalList) {
		this.goalList = goalList;
	}

	public Goal(String goal) {
		goalList = new ArrayList<String>();
		goalList.add(goal);
	}

	public boolean isGoalSatisfied(List<String> kb) {
		List<String> unSatisfiedGoals = new ArrayList<String>(goalList);
		unSatisfiedGoals.removeAll(kb);
		if (unSatisfiedGoals.size() == 0)
			return true;
		return false;
	}

	public void printGoalList() {
		for (String goal : goalList) {
			System.out.print(goal + " ");
		}
	}
}
