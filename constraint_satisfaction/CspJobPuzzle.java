package constraint_satisfaction;

import java.util.ArrayList;
import java.util.List;

public class CspJobPuzzle extends CSP {

	public static final int CHEF = 0;
	public static final int NURSE = 1;
	public static final int GUARD = 2;
	public static final int CLERK = 3;
	public static final int POLICE_OFFICER = 4;
	public static final int TEACHER = 5;
	public static final int ACTOR = 6;
	public static final int BOXER = 7;

	public static final int ROBERTA = 0;
	public static final int THELMA = 1;
	public static final int STEVE = 2;
	public static final int PETE = 3;

	public CspJobPuzzle() {
		totalValues = 4;
		totalVariables = 8;
	}

	public void setupProblem() {

		for (int i = 0; i < totalVariables; i++) {
			Variable variable = new Variable();
			unassigned_variables.add(variable);
			variables.add(variable);
		}
		int i = 0;
		unassigned_variables.get(i++).setValues(VariableType.JOBS, TEACHER, -1);
		unassigned_variables.get(i++).setValues(VariableType.JOBS, GUARD, -1);
		unassigned_variables.get(i++).setValues(VariableType.JOBS,
				POLICE_OFFICER, -1);
		unassigned_variables.get(i++).setValues(VariableType.JOBS, BOXER, -1);
		unassigned_variables.get(i++).setValues(VariableType.JOBS, CLERK, -1);
		unassigned_variables.get(i++).setValues(VariableType.JOBS, ACTOR, -1);
		unassigned_variables.get(i++).setValues(VariableType.JOBS, NURSE, -1);
		unassigned_variables.get(i++).setValues(VariableType.JOBS, CHEF, -1);
	}

	@Override
	public List<Integer> getAvailableValues(Variable var) {
		List<Integer> available_values = new ArrayList<Integer>();
		for (int i = 0; i < totalValues; i++) {
			available_values.add(i);
		}
		return available_values;
	}

	public Variable getUnassignedVariable() {
		if (unassigned_variables.size() == 0)
			return null;

		return unassigned_variables
				.get((int) (Math.random() * unassigned_variables.size()));
	}

	@Override
	public boolean isConsistent() {
		int[] job_per_person = new int[4];
		for (Variable var1 : assingned_variables) {
			job_per_person[var1.assignedValue] = 1;
			boolean isMale = (var1.assignedValue == STEVE || var1.assignedValue == PETE);
			boolean isEducated = (var1.assignedValue != PETE);

			for (Variable var2 : assingned_variables) {
				if (var1 == var2)
					continue;

				if (var1.assignedValue == var2.assignedValue) {
					job_per_person[var1.assignedValue]++;
					if (job_per_person[var1.assignedValue] > 2) {
						System.out
								.println("Failed: More than 2 person have job");
						return false;
					}
				}
			}

			if (var1.name == NURSE && isMale == false) {
				System.out.println("Failed: Nurse can not be Female");
				return false;
			}

			if (var1.name == ACTOR && isMale == false) {
				System.out.println("Failed: Actor can not be Female");
				return false;
			}

			if (var1.name == BOXER && var1.assignedValue == ROBERTA) {
				System.out.println("Failed: Roberta can not be boxer");
				return false;
			}

			if ((var1.name == NURSE || var1.name == TEACHER || var1.name == POLICE_OFFICER)
					&& isEducated == false) {
				System.out
						.println("Failed: Nurse Teacher and Police can not be uneducated");
				return false;
			}

			if (var1.name == CHEF && var1.assignedValue != THELMA) {
				System.out.println("Failed: THELMA has to be chef");
				return false;
			}

			if (var1.name == CLERK
					&& (var1.assignedValue == THELMA || var1.assignedValue == ROBERTA)) {
				System.out.println("Failed: THELMA/ROBERTA can not be clerk");
				return false;
			}
			if (var1.name == POLICE_OFFICER && isMale == false) {
				System.out.println("Failed: THELMA can not be clerk");
				return false;
			}
		}
		return true;
	}

	public String getVariableName(Variable var) {
		if (var.type == VariableType.JOBS) {
			switch (var.name) {
			case ACTOR:
				return "ACTOR";
			case CHEF:
				return "CHEF";
			case GUARD:
				return "GUARD";
			case POLICE_OFFICER:
				return "POLICE_OFFICER";
			case NURSE:
				return "NURSE";
			case TEACHER:
				return "TEACHER";
			case BOXER:
				return "BOXER";
			case CLERK:
				return "CLERK";
			}
		}
		System.out.println("No Name Found:" + var.name);
		return "";
	}

	public String getValueName(int val) {
		switch (val) {
		case ROBERTA:
			return "ROBERTA";
		case THELMA:
			return "THELMA";
		case STEVE:
			return "STEVE";
		case PETE:
			return "PETE";
		}
		return "";
	}
}
