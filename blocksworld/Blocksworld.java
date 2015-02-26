package blocksworld;

import blocksworld.Node.Solution;

public class Blocksworld {

	public static void main(String args[]) {
		int totalBlocks = Integer.parseInt(args[0]);
		int totalStacks = Integer.parseInt(args[1]);

		Solution solution = new Solution();
		Node target = solution.targetGenerator(totalStacks, totalBlocks);

		/* Two initial state generators */
		// Node start = solution.problemGenerator(totalStacks, totalBlocks, 20);
		Node start = solution.randomGenerator(totalStacks, totalBlocks);

		Node result = solution.Search(start, target);
		solution.traceback(result);
	}
}
