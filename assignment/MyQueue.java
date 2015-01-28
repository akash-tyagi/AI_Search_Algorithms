package assignment;

import java.util.LinkedList;
import java.util.Queue;

public class MyQueue extends Frontier {

	Queue<Node> queue;

	public MyQueue() {
		queue = new LinkedList<Node>();
	}

	@Override
	public Node pop() {
		return queue.remove();
	}

	@Override
	public boolean is_empty() {
		return queue.isEmpty();
	}

	@Override
	public boolean push(Node node) {
		return queue.add(node);
	}

	@Override
	public int get_size() {
		return queue.size();
	}
}
