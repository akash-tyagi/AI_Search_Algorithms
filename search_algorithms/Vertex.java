package search_algorithms;

import java.util.ArrayList;
import java.util.List;

public class Vertex {
	public int id;
	public int x;
	public int y;
	public List<Edge> edges;

	public Vertex(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		edges = new ArrayList<Edge>();
	}

	public void add_edge(Edge edge) {
		edges.add(edge);
	}
}
