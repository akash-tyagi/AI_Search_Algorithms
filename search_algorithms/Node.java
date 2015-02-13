package search_algorithms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class Node {
	public int vertex_id;
	public int depth;
	public Node parent;
	public double heur;
	public Graph graph;

	public Node(int vertex_id, Graph graph) {
		this.vertex_id = vertex_id;
		depth = 0;
		parent = null;
		heur = 0;
		this.graph = graph;
	}

	public Node(int vertex_id, Graph graph, Node parent) {
		this.vertex_id = vertex_id;
		depth = 0;
		this.parent = parent;
		heur = 0;
		this.graph = graph;
	}

	public Vertex get_vertex() {
		return graph.map_id_vertex.get(vertex_id);
	}

	public List<Node> successors() {
		List<Node> successors = new ArrayList<Node>();
		List<Edge> edges = graph.map_id_vertex.get(vertex_id).edges;
		for (Edge edge : edges) {
			int child_vertex_id = edge.vertex1_id != vertex_id ? edge.vertex1_id
					: edge.vertex2_id;

			if (parent != null && child_vertex_id == parent.vertex_id)
				continue;

			Node childNode = new Node(child_vertex_id, graph, this);
			childNode.depth = this.depth + 1;
			successors.add(childNode);
		}
		return successors;
	}

	public void calculate_heur(Vertex goal_vertex) {
		Vertex v = graph.map_id_vertex.get(vertex_id);
		heur = Math.sqrt(Math.pow((v.x - goal_vertex.x), 2)
				+ Math.pow((v.y - goal_vertex.y), 2));
		heur = (double) Math.round(heur * 10) / 10;
	}

	public static class Searching {
		private Map<Integer, Boolean> visited;
		private int total_iter;
		private int max_frontier_size;
		private String search_type;
		private int max_depth;

		private void initialize() {
			visited = new HashMap<Integer, Boolean>();
			total_iter = 0;
			max_frontier_size = 1;
			search_type = null;
			max_depth = 0;
		}

		public void traceback(Node node) {
			Stack<Node> stack = new Stack<Node>();
			if (node == null)
				return;
			Node target = node;
			while (node != null) {
				stack.add(node);
				node = node.parent;
			}

			System.out.println("\n\nSolution path:");
			while (stack.isEmpty() == false) {
				node = stack.pop();
				Vertex v = node.graph.map_id_vertex.get(node.vertex_id);
				System.out.println("vertex " + node.vertex_id + " (" + v.x
						+ "," + v.y + ")");
			}

			System.out.println("\nSearch algorithm:" + search_type);
			System.out.println("Total Iterations:" + total_iter);
			System.out.println("Maximum Frontier Size:" + max_frontier_size);
			System.out.println("Vertices Visited:" + visited.size() + "/"
					+ target.graph.vertexCount);
			System.out.println("Path Length:" + target.depth);
			System.out.println("Max Depth: " + max_depth);
		}

		public Node Search(Node initial_state, Vertex goal_vertex,
				int frontier_value) {
			if (Graph.debug_flag) {
				System.out.println("Vertices="
						+ initial_state.graph.vertexCount + ", Edges="
						+ initial_state.graph.edgeCount);
				Vertex initial = initial_state.get_vertex();
				System.out.println("start= (" + initial.x + "," + initial.y
						+ "), goal= (" + goal_vertex.x + "," + goal_vertex.y
						+ "), vertices: " + initial_state.vertex_id + " and "
						+ goal_vertex.id);
			}

			initialize();
			initial_state.calculate_heur(goal_vertex);
			Frontier frontier = get_frontier(frontier_value, goal_vertex);
			frontier.push(initial_state);
			visited.put(initial_state.vertex_id, true);

			while (frontier.is_empty() != true) {
				Node front_node = frontier.pop();
				total_iter++;

				if (Graph.debug_flag) {
					Vertex front_vertex = front_node.get_vertex();
					System.out.println("iter=" + total_iter + ", frontier="
							+ frontier.get_size() + ", popped="
							+ front_node.vertex_id + " (" + front_vertex.x
							+ "," + front_vertex.y + "), depth="
							+ front_node.depth + ", dist2goal="
							+ front_node.heur);
				}

				if (max_depth < front_node.depth) {
					max_depth = front_node.depth;
				}

				if (front_node.vertex_id == goal_vertex.id) {
					return front_node;
				}

				List<Node> successors = front_node.successors();
				for (Node successor : successors) {
					if (!visited.containsKey(successor.vertex_id)) {
						successor.calculate_heur(goal_vertex);
						frontier.push(successor);
						visited.put(successor.vertex_id, true);

						if (frontier.get_size() > max_frontier_size)
							max_frontier_size = frontier.get_size();

						if (Graph.debug_flag) {
							Vertex succ_vertex = successor.get_vertex();
							System.out.println("Pushed " + successor.vertex_id
									+ " (" + succ_vertex.x + ","
									+ succ_vertex.y + ")");
						}
					}
				}
			}
			return null;
		}

		public Frontier get_frontier(int value, Vertex goal_vertex) {
			Frontier frontier = null;
			if (value == FrontierType.QUEUE.get_value()) {
				frontier = new MyQueue();
				search_type = FrontierType.QUEUE.to_string();
			} else if (value == FrontierType.STACK.get_value()) {
				frontier = new MyStack();
				search_type = FrontierType.STACK.to_string();
			} else {
				frontier = new MyPriorityQueue(goal_vertex);
				search_type = FrontierType.PRIORITY_QUEUE.to_string();
			}
			return frontier;
		}
	}
}
