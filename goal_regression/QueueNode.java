package goal_regression;

import java.util.List;

public class QueueNode {
	public Goal goal;
	public List<String> plan;

	public QueueNode(Goal goals, List<String> plan) {
		this.plan = plan;
		this.goal = goals;
	}

}
