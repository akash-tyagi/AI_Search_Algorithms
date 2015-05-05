package goal_regression;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MyPriorityQueue {

	PriorityQueue<QueueNode> priorityQueue;

	public MyPriorityQueue() {
		Comparator<QueueNode> comparator = new HeuristicComparator();
		this.priorityQueue = new PriorityQueue<QueueNode>(1, comparator);
	}

	public QueueNode pop() {
		return priorityQueue.poll();
	}

	public boolean isEmpty() {
		return priorityQueue.isEmpty();
	}

	public boolean push(QueueNode node) {
		return priorityQueue.add(node);
	}

	public int size() {
		return priorityQueue.size();
	}

	public class HeuristicComparator implements Comparator<QueueNode> {

		@Override
		public int compare(QueueNode p1, QueueNode p2) {
			return Integer.compare(p1.plan.size(), p2.plan.size());
		}

	}
}
