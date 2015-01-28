package assignment;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MyPriorityQueue extends Frontier {

	PriorityQueue<Node> priority_queue;

	public MyPriorityQueue(Vertex goal_vertex) {
		Comparator<Node> comparator = new EuclideanComparator(goal_vertex);
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
		Vertex goal_vertex;

		public EuclideanComparator(Vertex goal_vertex) {
			this.goal_vertex = goal_vertex;
		}

		@Override
		public int compare(Node n1, Node n2) {
			Node goal_node = new Node(goal_vertex.id, n1.graph);
			double d1 = eucliden_distance(n1, goal_node);
			double d2 = eucliden_distance(n2, goal_node);
			return Double.compare(d1, d2);
		}

		private double eucliden_distance(Node n1, Node n2) {
			int v1_x = n1.graph.map_id_vertex.get(n1.vertex_id).x;
			int v1_y = n1.graph.map_id_vertex.get(n1.vertex_id).y;
			int v2_x = n2.graph.map_id_vertex.get(n2.vertex_id).x;
			int v2_y = n2.graph.map_id_vertex.get(n2.vertex_id).y;
			return Math.sqrt(Math.pow((v1_x - v2_x), 2)
					+ Math.pow((v1_y - v2_y), 2));
		}
	}
}
