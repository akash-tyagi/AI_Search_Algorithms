package assignment;

import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import assignment.Node.Searching;

public class Graph {
	public int vertexCount;
	public int edgeCount;
	public Map<Integer, Vertex> map_id_vertex;
	public Map<String, Integer> map_cordi_id;

	public Graph() {
		vertexCount = edgeCount = 0;
		map_id_vertex = new HashMap<Integer, Vertex>();
		map_cordi_id = new HashMap<String, Integer>();
	}

	public void add_vertex_to_maps(int id, Vertex vertex) {
		map_id_vertex.put(id, vertex);
		map_cordi_id.put(vertex.x + ":" + vertex.y, id);
	}

	public Vertex get_vertex_from_cordi(int x, int y) {
		return map_id_vertex.get(map_cordi_id.get(x + ":" + y));
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
				map_id_vertex.get(vertex1_id).add_edge(edge);
				map_id_vertex.get(vertex2_id).add_edge(edge);
			} else {
				// Read vertices from file
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
				add_vertex_to_maps(id, vertex);
			}
		}
	}

	public static void main(String[] args) throws IOException {

		int frontier_value = Integer.parseInt(args[1]);
		int x0 = Integer.parseInt(args[2]);
		int y0 = Integer.parseInt(args[3]);
		int x1 = Integer.parseInt(args[4]);
		int y1 = Integer.parseInt(args[5]);

		Graph graph = new Graph();
		graph.generate_graph_from_file(args[0]);

		int start_vertex_id = graph.map_cordi_id.get(x0 + ":" + y0);
		Node initial_state = new Node(start_vertex_id, graph);
		Vertex goal_vertex = graph.get_vertex_from_cordi(x1, y1);

		Searching searching = new Searching();
		Node result_state = searching.Search(initial_state, goal_vertex,
				frontier_value);
		searching.traceback(result_state);
	}
}
