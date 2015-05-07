package goal_regression;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoalRegression {

	Map<String, Boolean> hashTable = new HashMap<String, Boolean>();

	public static void main(String args[]) throws IOException {
		GoalRegression goalRegression = new GoalRegression();
		Goal goals = goalRegression.getGoals();
		System.out.println("Initializing Goal Regression...\n");
		List<Operator> operators = goalRegression
				.readOpersFromFile("src/goal_regression/blocksworld.opers");
		List<String> kb = goalRegression.readKB("src/goal_regression/init.kb");

		List<String> success = goalRegression.goalRegression(goals, operators,
				kb);

		if (success.size() != 0) {
			System.out.println("Solution Found");
			System.out.println("plan:");
			for (String string : success) {
				System.out.println(string);
			}
		} else {
			System.out.println("Solution Not Found !! ");
		}
	}

	public Goal getGoals() throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter Goals :");
		String[] g = br.readLine().split(" ");

		List<String> gList = new ArrayList<String>();
		for (int i = 0; i < g.length; i++) {
			gList.add(g[i]);
		}

		Goal goal = new Goal(gList);
		return goal;
	}

	public List<String> goalRegression(Goal goals, List<Operator> operators,
			List<String> kb) {
		MyPriorityQueue queue = new MyPriorityQueue();
		QueueNode queueNode = new QueueNode(goals, new ArrayList<String>(),
				null);
		queue.push(queueNode);
		int iter = 1;

		while (queue.isEmpty() == false) {
			System.out.println("\niter=" + iter++ + ", queue=" + queue.size());

			queueNode = queue.pop();
			if (queueNode.goal.isGoalSatisfied(kb) == true) {
				return queueNode.plan;
			}
			List<String> goalsList = queueNode.goal.goalList;
			if (queueNode.oper != null)
				System.out.println("context: " + queueNode.oper.name);
			System.out.println("goal stack:" + goalsList.toString());

			for (String goal : goalsList) {
				for (Operator oper : operators) {

					if (oper.isOperatorRelevant(goalsList)
							&& oper.isOperatorConsistent(goalsList)) {

						List<String> newGoals = oper.regress(goalsList);
						List<String> newPlan = new ArrayList<String>(
								queueNode.plan);
						newPlan.add(oper.name);

						if (hashTable.containsKey(newPlan.toString()) == false) {
							System.out.println("Considering using " + oper.name
									+ " to achieve " + goal);
							hashTable.put(newPlan.toString(), true);
							queue.push(new QueueNode(new Goal(newGoals),
									newPlan, oper));

						}
					}
				}
			}
		}
		return null;
	}

	public List<String> readKB(String file_name) throws IOException {
		Charset charset = Charset.forName("US-ASCII");
		List<String> kb = new ArrayList<String>();

		for (String line : Files.readAllLines(Paths.get(file_name), charset)) {
			if (line.contains("#") || line.isEmpty())
				continue;

			String words[] = line.split(" ");
			for (String literal : words) {
				kb.add(literal);
			}
		}
		System.out.println("Init KB:" + kb.toString() + "\n");
		return kb;
	}

	public List<Operator> readOpersFromFile(String file_name)
			throws IOException {
		Charset charset = Charset.forName("US-ASCII");
		List<Operator> operators = new ArrayList<Operator>();

		Operator oper = null;
		for (String line : Files.readAllLines(Paths.get(file_name), charset)) {
			if (line.contains("#") || line.isEmpty())
				continue;

			String words[] = line.split(" ");
			int listType = -1, prevListType = -1;
			for (String literal : words) {
				// System.out.println(literal);

				if (literal.equals("OPER")) {
					oper = new Operator();
					listType = 0;
				} else if (literal.contains("precond")) {
					listType = 1;
				} else if (literal.contains("addlist")) {
					listType = 2;
				} else if (literal.contains("dellist")) {
					listType = 3;
				} else if (literal.contains("conflict")) {
					listType = 4;
				} else if (literal.contains("END")) {
					operators.add(oper);
					// oper.printOperator();
					oper = null;
					listType = 5;
				}
				if (listType != prevListType) {
					prevListType = listType;
					continue;
				}

				switch (listType) {
				case 0:
					oper.name = literal;
					break;
				case 1:
					oper.addPrecondList(literal);
					break;
				case 2:
					oper.addAddlist(literal);
					break;
				case 3:
					oper.addDelList(literal);
					break;
				case 4:
					oper.addConflictList(literal);
					break;
				default:
					System.out.println("ERROR!!!!!!" + literal);
				}

			}
		}
		return operators;
	}
}