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
		List<Edge> edges = graph.vertices.get(vertex_id).edges;
		for (Edge edge : edges) {
			int child_vertex_id = edge.vertex1_id != vertex_id ? edge.vertex1_id
					: edge.vertex2_id;

			if (parent != null && child_vertex_id == parent.vertex_id)
				continue;

			Node childNode = new Node(child_vertex_id, graph, this);
			successors.add(childNode);
		}
		return successors;
	}

	public static void traceback(Node node) {
		while (node != null) {
			System.out.println(node.vertex_id);
			node = node.parent;
		}
	}

	public static Node Search(Node initial_state, Vertex goal_vertex,
			Frontier frontier) {
		visited = new HashMap<Integer, Boolean>();

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
}
