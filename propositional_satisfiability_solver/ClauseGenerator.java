package propositional_satisfiability_solver;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class ClauseGenerator {
	public static String[] person = { "Fa", "Fx", "Ch", "Gr" };
	public static String[] direction = { "L", "R" };

	public static void main(String args[]) throws FileNotFoundException,
			UnsupportedEncodingException {

		// Script to get the locations at particular time
		// Ti_person[i]L/R
		// for (int i = 0; i < 4; i++) {
		// for (int j = 0; j < 3; j++) {
		// System.out.println(getLocation(i, j)[0] + " "
		// + getLocation(i, j)[1]);
		// System.out.println("-" + getLocation(i, j)[0] + " -"
		// + getLocation(i, j)[1]);
		// }
		// }

		// Script to capture actions
		// for (int i = 0; i < 3; i++) {
		// for (int j = 0; j < 3; j++) {
		// for (int k = 0; k < 2; k++) {
		// // farmer movement only
		// if (j == 0) {
		// String action = "-" + getFarmerActions(i)[k];
		// System.out.println(action + " " + getLocation(i, j)[k]);
		// System.out.println(action + " "
		// + getLocation(i + 1, j)[(k + 1) % 2]);
		// continue;
		// }
		// // movement along with other persons
		// String action = "-" + getActions(i, j)[k];
		// System.out.println(action + " " + getLocation(i, j)[k]);
		// System.out.println(action + " " + getLocation(i, 0)[k]);
		// System.out.println(action + " "
		// + getLocation(i + 1, j)[(k + 1) % 2]);
		// System.out.println(action + " "
		// + getLocation(i + 1, 0)[(k + 1) % 2]);
		//
		// }
		// }
		// }

		// Indirect Clause Generator
		// for (int i = 0; i < 3; i++) {
		// for (int j = 0; j < 3; j++) {
		// for (int k = 0; k < 2; k++) {
		// for (int l = 0; l < 3; l++) {
		// if (l == 0)
		// continue;
		// else if (j == 0) {
		// System.out.println("-" + getFarmerActions(i)[k]
		// + " " + "-" + getLocation(i, l)[k] + " "
		// + getLocation(i + 1, l)[k]);
		// System.out.println("-" + getFarmerActions(i)[k]
		// + " " + getLocation(i, l)[k] + " " + "-"
		// + getLocation(i + 1, l)[k]);
		// } else if (l != j) {
		// System.out.println("-" + getActions(i, j)[k] + " "
		// + "-" + getLocation(i, l)[k] + " "
		// + getLocation(i + 1, l)[k]);
		// System.out.println("-" + getActions(i, j)[k] + " "
		// + getLocation(i, l)[k] + " " + "-"
		// + getLocation(i + 1, l)[k]);
		// }
		// }
		// }
		// }
		// }

		// actions for single action to be true
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int l = j + 1; l < 3; l++) {
					for (int k = 0; k < 2; k++) {
						if (j == 0) {
							System.out.println("-" + getFarmerActions(i)[k]
									+ " -" + getActions(i, l)[k]);
						} else {
							System.out.println("-" + getActions(i, j)[k] + " -"
									+ getActions(i, l)[k]);
						}

					}
				}
			}

		}
	}

	// arguments time and person --> T-_person[0-3]L/R
	private static String[] getLocation(int i, int j) {
		String dirL = "T" + i + "_" + person[j] + "L";
		String dirR = "T" + i + "_" + person[j] + "R";
		String[] locations = { dirL, dirR };
		return locations;
	}

	// Actions -> Ti_mv_person[1-3]_LR/RL
	private static String[] getActions(int i, int j) {
		String actL = "T" + i + "_mv_" + person[j] + "_LR";
		String actR = "T" + i + "_mv_" + person[j] + "_RL";
		String[] actions = { actL, actR };
		return actions;
	}

	// Farmer Actions only: Ti_mv_no_LR/RL
	private static String[] getFarmerActions(int i) {
		String actL = "T" + i + "_mv_no_LR";
		String actR = "T" + i + "_mv_no_RL";
		String[] actions = { actL, actR };
		return actions;
	}

}
