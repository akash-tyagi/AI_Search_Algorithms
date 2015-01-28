package assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node {
	public int vertex_id;
	public int depth;
	public Node parent;
	public float heur;
	public Graph graph;

	public static Map<Integer, Boolean> visited;

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

	public static void traceback(Node node) {
		if (node == null)
			return;
		
		System.out.println("Solution path:");
		System.out.println("Path Length:" + node.depth);
		while (node != null) {
			Vertex v = node.graph.map_id_vertex.get(node.vertex_id);
			System.out.println("vertex " + node.vertex_id + " (" + v.x + ","
					+ v.y + ")");
			node = node.parent;
		}
	}

	public static Node Search(Node initial_state, Vertex goal_vertex,
			int frontier_value) {
		visited = new HashMap<Integer, Boolean>();

		Frontier frontier = get_frontier(frontier_value, goal_vertex);
		frontier.push(initial_state);
		visited.put(initial_state.vertex_id, true);

		while (frontier.is_empty() != true) {
			Node front_node = frontier.pop();

			if (front_node.vertex_id == goal_vertex.id) {
				return front_node;
			}

			List<Node> successors = front_node.successors();
			for (Node successor : successors) {
				if (!visited.containsKey(successor.vertex_id)) {
					frontier.push(successor);
					visited.put(successor.vertex_id, true);
				}
			}
		}
		return null;
	}

	public static Frontier get_frontier(int value, Vertex goal_vertex) {
		Frontier frontier = null;
		if (value == FrontierType.QUEUE.get_value()) {
			frontier = new MyQueue();
		} else if (value == FrontierType.STACK.get_value()) {
			frontier = new MyStack();
		} else {
			frontier = new MyPriorityQueue(goal_vertex);
		}
		return frontier;
	}
}
