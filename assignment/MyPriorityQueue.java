package assignment;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MyPriorityQueue extends Frontier {

	PriorityQueue<Node> priority_queue;

	public MyPriorityQueue(Vertex goal_vertex) {
		Comparator<Node> comparator = new EuclideanComparator();
		this.priority_queue = new PriorityQueue<Node>(1, comparator);
	}

	@Override
	public Node pop() {
		return priority_queue.poll();
	}

	@Override
	public boolean is_empty() {
		return priority_queue.isEmpty();
	}

	@Override
	public boolean push(Node node) {
		return priority_queue.add(node);
	}

	public class EuclideanComparator implements Comparator<Node> {

		@Override
		public int compare(Node n1, Node n2) {
			return Double.compare(n1.heur, n2.heur);
		}
	}

	@Override
	public int get_size() {
		// TODO Auto-generated method stub
		return priority_queue.size();
	}
}
