package assignment;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Graph {
	public int vertexCount;
	public int edgeCount;
	public Map<Integer, Vertex> vertices;

	public Graph() {
		vertexCount = edgeCount = 0;
		vertices = new HashMap<Integer, Vertex>();
	}

	public void add_vertex(int id, Vertex vertex) {
		vertices.put(id, vertex);
	}

	public void generate_graph_from_file(String file_name) throws IOException {
		Charset charset = Charset.forName("US-ASCII");
		boolean is_edge = false;
		for (String line : Files.readAllLines(Paths.get(file_name), charset)) {
			String words[] = line.split(" ");
			if (is_edge) {
				// Read edges from file
				int id = Integer.valueOf(words[0]);
				int vertex1_id = Integer.valueOf(words[1]);
				int vertex2_id = Integer.valueOf(words[2]);
				Edge edge = new Edge(id, vertex1_id, vertex2_id);
				vertices.get(vertex1_id).add_edge(edge);
				vertices.get(vertex2_id).add_edge(edge);
			} else {
				if (words[0].equals("vertices:")) {
					vertexCount = Integer.valueOf(words[1]);
					continue;
				} else if (words[0].equals("edges:")) {
					edgeCount = Integer.valueOf(words[1]);
					is_edge = true;
					continue;
				}
				// Read vertices from file
				int id = Integer.valueOf(words[0]);
				int x = Integer.valueOf(words[1]);
				int y = Integer.valueOf(words[2]);
				Vertex vertex = new Vertex(id, x, y);
				add_vertex(id, vertex);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		Graph graph = new Graph();
		graph.generate_graph_from_file("src/assignment/ATM.graph.txt");
	}

}
