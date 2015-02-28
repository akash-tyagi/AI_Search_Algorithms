package constraint_satisfaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

	Map<Integer, Integer> mapVariableToValue;
	List<Variable> variables;
	List<Variable> unassigned_variables;
	List<Variable> assingned_variables;

	public static final int TOTALJOBS = 8;
	public static final int TOTALWORKER = 4;

	public CspJobPuzzle() {
		mapVariableToValue = new HashMap<Integer, Integer>();
		unassigned_variables = new ArrayList<Variable>();
		assingned_variables = new ArrayList<Variable>();
		variables = new ArrayList<Variable>();
	}

	@Override
	public void setupProblem() {

		for (int i = 0; i < TOTALJOBS; i++) {
			Variable variable = new Variable();
			unassigned_variables.add(variable);
			variables.add(variable);
		}

		unassigned_variables.get(0).setValues(VariableType.JOBS, BOXER, -1);
		unassigned_variables.get(1).setValues(VariableType.JOBS, CHEF, -1);
		unassigned_variables.get(2).setValues(VariableType.JOBS, CLERK, -1);
		unassigned_variables.get(3).setValues(VariableType.JOBS, ACTOR, -1);
		unassigned_variables.get(4).setValues(VariableType.JOBS,
				POLICE_OFFICER, -1);
		unassigned_variables.get(5).setValues(VariableType.JOBS, NURSE, -1);
		unassigned_variables.get(6).setValues(VariableType.JOBS, GUARD, -1);
		unassigned_variables.get(7).setValues(VariableType.JOBS, TEACHER, -1);
	}

	@Override
	public Variable getUnassignedVariable() {
		if (unassigned_variables.size() == 0)
			return null;
		return unassigned_variables.get(0);
	}

	@Override
	public List<Integer> getAvailableValues(Variable var) {
		List<Integer> available_values = new ArrayList<Integer>() {
			{
				add(0);
				add(1);
				add(2);
				add(3);
			}
		};
		// for (Variable variable : assingned_variables) {
		// if (variable.type == var.type) {
		// // System.out.println(getVariableName(variable) + ":"
		// // + variable.assignedValue);
		// int index = available_values.indexOf(variable.assignedValue);
		// available_values.remove(index);
		// }
		// }
		return available_values;
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
					// System.out.println("Same value for "
					// + getVariableName(var1) + ":"
					// + getVariableName(var2));
					job_per_person[var1.assignedValue]++;
					if (job_per_person[var1.assignedValue] > 2) {
						System.out.println("More than 2 person have job");
						return false;
					}
				}
			}

			if (var1.name == NURSE && isMale == false) {
				System.out.println("Nurse can not be Female");
				return false;
			}

			if (var1.name == ACTOR && isMale == false) {
				System.out.println("Actor can not be Female");
				return false;
			}

			if (var1.name == BOXER && var1.assignedValue == ROBERTA) {
				System.out.println("Roberta can not be boxer");
				return false;
			}

			if ((var1.name == NURSE || var1.name == TEACHER || var1.name == POLICE_OFFICER)
					&& isEducated == false) {
				System.out
						.println("Nurse Teacher and Police can not be uneducated");
				return false;
			}

			if (var1.name == CHEF && var1.assignedValue != THELMA) {
				System.out.println("THELMA has to be chef");
				return false;
			}

			if (var1.name == CLERK
					&& (var1.assignedValue == THELMA || var1.assignedValue == ROBERTA)) {
				System.out.println("THELMA/ROBERTA can not be clerk");
				return false;
			}
			if (var1.name == POLICE_OFFICER && isMale == false) {
				System.out.println("THELMA can not be clerk");
				return false;
			}
		}
		return true;
	}

	@Override
	public void printSolution() {
		System.out.println("Expected Solution:$$$$$$$$$$$$$$$$$$$");
		for (Variable variable : assingned_variables) {
			System.out.println("JOB:" + getVariableName(variable) + " PERSON:"
					+ getValueName(variable.assignedValue));
		}
	}

	@Override
	public void assignValueToVariable(Variable var, int value) {
		var.assignedValue = value;
		unassigned_variables.remove(var);
		assingned_variables.add(var);
		System.out.println("Assigned " + getVariableName(var) + " PERSON:"
				+ getValueName(value));
		areDuplicates();
	}

	@Override
	public void unassignValueToVariable(Variable var) {
		System.out.println("Unassigning " + getVariableName(var) + " PERSON:"
				+ getValueName(var.assignedValue));
		var.assignedValue = -1;
		unassigned_variables.add(var);
		assingned_variables.remove(var);
		areDuplicates();
	}

	public void areDuplicates() {
		Set<Variable> setUnassignedVariables = new HashSet<Variable>(
				unassigned_variables);
		if (setUnassignedVariables.size() < unassigned_variables.size()) {
			System.out.println("#########Duplicates Unassigned variables");
		}

		Set<Variable> setAssignedVariables = new HashSet<Variable>(
				assingned_variables);
		if (setAssignedVariables.size() < assingned_variables.size()) {
			System.out.println("#########Duplicates Assigned variables");
		}

	}

	@Override
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

	@Override
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
