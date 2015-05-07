package goal_regression;

import java.util.List;

public class QueueNode {
	public Goal goal;
	public List<String> plan;
	public Operator oper;

	public QueueNode(Goal goals, List<String> plan, Operator oper) {
		this.plan = plan;
		this.goal = goals;
		this.oper = oper;
	}

}
