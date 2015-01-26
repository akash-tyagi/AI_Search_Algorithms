package assignment;

import java.util.List;

public class Node {
	public int vertex_id;
	public int depth;
	public Node parent;
	public float heur;

	public Node(int vertex_id) {
		this.vertex_id = vertex_id;
		depth = 0;
		parent = null;
		heur = 0;
	}

	public List<Node> successors() {
		return null;
	}
}
