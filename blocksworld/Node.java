package blocksworld;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Stack;

public class Node {
	public int totalStacks;
	public int totalBlocks;
	public List<Stack<Character>> stacks;
	public int depth;
	public Node parent;
	public double heur;

	public Node(int totalStacks, int totalBlocks) {
		this.totalStacks = totalStacks;
		this.totalBlocks = totalBlocks;
		depth = 0;
		stacks = new ArrayList<Stack<Character>>();
		for (int i = 0; i < totalStacks; i++) {
			stacks.add(new Stack<Character>());
		}
		parent = null;
	}

	public Node(Node node) {
		this.totalStacks = node.totalStacks;
		this.totalBlocks = node.totalBlocks;
		depth = 0;
		stacks = new ArrayList<Stack<Character>>();
		for (int i = 0; i < totalStacks; i++) {
			stacks.add((Stack<Character>) node.stacks.get(i).clone());
		}
		parent = null;
	}

	public List<Node> getSuccessors() {
		List<Node> successors = new ArrayList<Node>();
		for (int i = 0; i < totalStacks; i++) {
			if (stacks.get(i).isEmpty())
				continue;
			for (int k = 0; k < totalStacks; k++) {
				if (i == k)
					continue;
				Node succ = new Node(this);
				succ.stacks.get(k).add(succ.stacks.get(i).pop());
				succ.depth = this.depth + 1;
				succ.parent = this;
				successors.add(succ);
			}
		}
		return successors;
	}

	public void calculate_heur(Node target) {
		double h = 0;
		h = method3();
		heur = depth + h;
	}

	public double method3() {
		// Store the stack number and position number
		int[][] data = new int[totalBlocks][2];
		for (int i = 0; i < totalStacks; i++) {
			Stack<Character> stack = stacks.get(i);
			int j = 0;
			while (j < stack.size()) {
				char ch = stack.elementAt(j);
				data[ch - 'A'][0] = i;
				data[ch - 'A'][1] = j++;
			}
		}

		// Calculating the total number of blocks need to move to reach the
		// target state
		double total_estimated_moves = 0;
		for (int i = 0; i < totalBlocks; i++) {
			if (data[i][0] == 0)
				continue;
			total_estimated_moves += Math.abs(data[i][1]
					- stacks.get(data[i][0]).size());
		}

		// Calculating the blocks needed to move the next in order to come to
		// target stack (stack number 0)
		double current_state_move = 0;
		int i = 0;
		while (i < stacks.get(0).size()
				&& stacks.get(0).elementAt(i) == ('A' + i)) {
			i++;
		}

		if (i < stacks.get(0).size()) {
			current_state_move = (stacks.get(0).size() - i)
					+ (stacks.get(data[i][0]).size() - data[i][1]);
		}

		int future_moves = 0;
		while (i < totalBlocks) {
			future_moves += (stacks.get(data[i][0]).size() - data[i][1]);
			i++;
		}

		return (3.5 * current_state_move + 0.1 * total_estimated_moves + future_moves);

	}

	public String getId() {
		String id = "";
		for (int i = 0; i < totalStacks; i++) {
			id += i;
			Stack<Character> stack = stacks.get(i);
			for (int j = 0; j < stack.size(); j++) {
				id += stack.elementAt(j);
			}
		}
		return id;
	}

	public void print() {
		for (int i = 0; i < totalStacks; i++) {
			System.out.print(i + " | ");
			Stack<Character> stack = stacks.get(i);
			for (int j = 0; j < stack.size(); j++) {
				System.out.print(stack.elementAt(j));
			}
			System.out.println();
		}
		System.out.println("\n");
	}

	public static class Solution {
		private Map<String, Boolean> visited;
		private int max_depth;
		private int total_iter;
		private int max_frontier_size;
		private boolean debug = true;

		private void initialize() {
			visited = new HashMap<String, Boolean>();
			total_iter = 0;
			max_depth = 0;
			max_frontier_size = 1;
		}

		public Node targetGenerator(int totalStacks, int totalBlocks) {
			Node target = new Node(totalStacks, totalBlocks);
			for (int i = 0; i < totalBlocks; i++) {
				target.stacks.get(0).add((char) ('A' + i));
			}
			return target;
		}

		/*
		 * Generates a random initial state starting from target state and
		 * taking num steps backward
		 */
		public Node problemGenerator(int totalStacks, int totalBlocks, int num) {

			/*
			 * Creates a random state based on the totalStack and totalBlocks
			 * parameters, if need to provide specific state like provided in
			 * assignment please uncomment the states in the code below
			 */
			Node succ = targetGenerator(totalStacks, totalBlocks);
			Random rand = new Random(Calendar.getInstance().getTimeInMillis());
			int randStack1, randStack2;
			while (num > 0) {
				num--;
				// succ.print();
				do {
					randStack1 = rand.nextInt(totalStacks);
					randStack2 = rand.nextInt(totalStacks);
				} while (succ.stacks.get(randStack1).isEmpty()
						|| randStack1 == randStack2);

				// System.out.println(randStack1 + ":" + randStack2);
				succ.stacks.get(randStack2).add(
						succ.stacks.get(randStack1).pop());
			}

			/*
			 * Creating a particular state for testing purpose, specially one's
			 * given in the assignment, comment the above code first
			 */

			// STATE 1: PLEASE PROVIDE TOTALSTACKS AS 5 AND TOTALBLOCKS AS 10
			// Node succ = new Node(totalStacks, totalBlocks);
			// succ.stacks.get(0).add('D');
			// succ.stacks.get(1).add('E');
			// succ.stacks.get(1).add('F');
			// succ.stacks.get(1).add('I');
			// succ.stacks.get(1).add('J');
			// succ.stacks.get(2).add('B');
			// succ.stacks.get(2).add('G');
			// succ.stacks.get(3).add('C');
			// succ.stacks.get(3).add('H');
			// succ.stacks.get(4).add('A');

			// STATE 2: PLEASE PROVIDE TOTALSTACKS AS 3 AND TOTALBLOCKS AS 5 FOR
			// REST OF THE STATES
			// Node succ = new Node(totalStacks, totalBlocks);
			// succ.stacks.get(0).add('B');
			// succ.stacks.get(1).add('C');
			// succ.stacks.get(1).add('E');
			// succ.stacks.get(2).add('A');
			// succ.stacks.get(2).add('D');

			// STATE 3
			// Node succ = new Node(totalStacks, totalBlocks);
			// succ.stacks.get(0).add('D');
			// succ.stacks.get(1).add('C');
			// succ.stacks.get(2).add('E');
			// succ.stacks.get(2).add('B');
			// succ.stacks.get(2).add('A');

			// STATE 4
			// Node succ = new Node(totalStacks, totalBlocks);
			// succ.stacks.get(0).add('A');
			// succ.stacks.get(0).add('D');
			// succ.stacks.get(1).add('B');
			// succ.stacks.get(2).add('E');
			// succ.stacks.get(2).add('C');

			return succ;
		}

		/* Generates a random initial state with no relation to target state */
		public Node randomGenerator(int totalStacks, int totalBlocks) {
			Node succ = new Node(totalStacks, totalBlocks);
			Random rand = new Random(Calendar.getInstance().getTimeInMillis());
			for (int i = 0; i < totalBlocks; i++) {
				succ.stacks.get(rand.nextInt(totalStacks))
						.add((char) ('A' + i));
			}
			return succ;
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

			System.out.println("\n\nsolution path:");
			while (stack.isEmpty() == false) {
				node = stack.pop();
				node.print();
			}
			System.out.println("Success!!");
			System.out.println("Total goal tests:" + total_iter);
			System.out.println("Maximum queue size:" + max_frontier_size);
			System.out.println("depth:" + target.depth);
		}

		public Node Search(Node initial_state, Node goal_state) {
			initialize();
			Frontier frontier = new MyPriorityQueue();
			initial_state.calculate_heur(goal_state);
			frontier.push(initial_state);
			visited.put(initial_state.getId(), true);

			if (debug) {
				System.out.println("Initial State:");
				initial_state.print();
			}

			while (!frontier.is_empty()) {
				Node front_node = frontier.pop();
				total_iter++;

				if (debug) {
					double h = (double) Math.round(front_node.heur * 10) / 10;
					System.out.println("iter:" + total_iter + ", queue="
							+ frontier.get_size() + ", f=g+h=" + h + ", depth="
							+ front_node.depth);

				}

				if (max_depth < front_node.depth) {
					max_depth = front_node.depth;
				}

				if (front_node.getId().equals(goal_state.getId())) {
					return front_node;
				}

				List<Node> successors = front_node.getSuccessors();
				for (Node successor : successors) {
					if (!visited.containsKey(successor.getId())) {
						successor.calculate_heur(goal_state);
						frontier.push(successor);
						visited.put(successor.getId(), true);

						if (frontier.get_size() > max_frontier_size)
							max_frontier_size = frontier.get_size();
					}
				}
			}
			System.out.println("ERROR: Not able to complete the task");
			return null;
		}
	}
}
