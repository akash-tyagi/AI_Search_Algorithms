package assignment;

import java.util.Comparator;

public class MyPriorityQueue extends Frontier {

	@Override
	public Node pop() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean is_empty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean push(Node node) {
		// TODO Auto-generated method stub
		return false;
	}

	public class EuclideanComparator implements Comparator<Vertex> {
		Vertex goal_vertex;

		public EuclideanComparator(Vertex goal_vertex) {
			this.goal_vertex = goal_vertex;
		}

		@Override
		public int compare(Vertex v1, Vertex v2) {
			double d1 = eucliden_distance(v1, goal_vertex);
			double d2 = eucliden_distance(v2, goal_vertex);
			return Double.compare(d1, d2);
		}

		private double eucliden_distance(Vertex v1, Vertex v2) {
			return Math.sqrt(Math.pow((v1.x - v2.x), 2)
					+ Math.pow((v1.y - v2.y), 2));
		}
	}
}
