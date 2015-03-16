package resolution_refutation;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MyPriorityQueue {

	PriorityQueue<ResPair> priorityQueue;

	public MyPriorityQueue() {
		Comparator<ResPair> comparator = new HeuristicComparator();
		this.priorityQueue = new PriorityQueue<ResPair>(1, comparator);
	}

	public ResPair pop() {
		return priorityQueue.poll();
	}

	public boolean isEmpty() {
		return priorityQueue.isEmpty();
	}

	public boolean push(ResPair resPair) {
		return priorityQueue.add(resPair);
	}

	public int size() {
		return priorityQueue.size();
	}

	public boolean push(Clause clause1, Clause clause2, String proposition) {
		ResPair resPair = new ResPair(clause1, clause2, proposition);
		return push(resPair);
	}

	public class HeuristicComparator implements Comparator<ResPair> {

		@Override
		public int compare(ResPair p1, ResPair p2) {
			return -1 * Integer.compare(p2.heur, p1.heur);
		}

	}
}
