package search_algorithms;

import java.util.Stack;

public class MyStack extends Frontier {

	Stack<Node> stack;

	public MyStack() {
		this.stack = new Stack<Node>();
	}

	@Override
	public Node pop() {
		return stack.pop();
	}

	@Override
	public boolean is_empty() {
		return stack.isEmpty();
	}

	@Override
	public boolean push(Node node) {
		return stack.push(node) != null;
	}

	@Override
	public int get_size() {
		// TODO Auto-generated method stub
		return stack.size();
	}

}
