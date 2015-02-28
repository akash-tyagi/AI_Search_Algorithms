package constraint_satisfaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CspZebraPuzzle extends CSP {

	Map<Integer, Integer> mapVariableToValue;
	List<Variable> variables;
	List<Variable> unassigned_variables;
	List<Variable> assingned_variables;

	public static final int ENGLISH = 0;
	public static final int SPANISH = 1;
	public static final int NORWEIGH = 2;
	public static final int JAPANESE = 3;
	public static final int UKRAIN = 4;

	public static final int SNICKERS = 0;
	public static final int SMARTIES = 1;
	public static final int MILKYWAY = 2;
	public static final int KITKAT = 3;
	public static final int HERSHEY = 4;

	public static final int JUICE = 0;
	public static final int MILK = 1;
	public static final int COFFEE = 2;
	public static final int TEA = 3;
	public static final int WATER = 4;

	public static final int DOG = 0;
	public static final int ZEBRA = 1;
	public static final int HORSE = 2;
	public static final int SNAIL = 3;
	public static final int FOX = 4;

	public static final int GREEN = 0;
	public static final int BLUE = 1;
	public static final int YELLOW = 2;
	public static final int IVORY = 3;
	public static final int RED = 4;

	public CspZebraPuzzle() {
		mapVariableToValue = new HashMap<Integer, Integer>();
		unassigned_variables = new ArrayList<Variable>();
		assingned_variables = new ArrayList<Variable>();
		variables = new ArrayList<Variable>();
	}

	@Override
	public void setupProblem() {
		for (int i = 0; i < 25; i++) {
			Variable variable = new Variable();
			unassigned_variables.add(variable);
			variables.add(variable);
		}
		unassigned_variables.get(0).setValues(VariableType.NATIONALITY,
				ENGLISH, -1);
		unassigned_variables.get(1).setValues(VariableType.NATIONALITY,
				SPANISH, -1);
		unassigned_variables.get(2).setValues(VariableType.NATIONALITY,
				NORWEIGH, -1);
		unassigned_variables.get(3).setValues(VariableType.NATIONALITY, UKRAIN,
				-1);
		unassigned_variables.get(4).setValues(VariableType.NATIONALITY,
				JAPANESE, -1);
		unassigned_variables.get(5).setValues(VariableType.COLOR, GREEN, -1);
		unassigned_variables.get(6).setValues(VariableType.COLOR, BLUE, -1);
		unassigned_variables.get(7).setValues(VariableType.COLOR, YELLOW, -1);
		unassigned_variables.get(8).setValues(VariableType.COLOR, IVORY, -1);
		unassigned_variables.get(9).setValues(VariableType.COLOR, RED, -1);
		unassigned_variables.get(10).setValues(VariableType.DRINK, JUICE, -1);
		unassigned_variables.get(11).setValues(VariableType.DRINK, COFFEE, -1);
		unassigned_variables.get(12).setValues(VariableType.DRINK, MILK, -1);
		unassigned_variables.get(13).setValues(VariableType.DRINK, TEA, -1);
		unassigned_variables.get(14).setValues(VariableType.DRINK, WATER, -1);
		unassigned_variables.get(15).setValues(VariableType.FOOD, SNICKERS, -1);
		unassigned_variables.get(16).setValues(VariableType.FOOD, SMARTIES, -1);
		unassigned_variables.get(17).setValues(VariableType.FOOD, MILKYWAY, -1);
		unassigned_variables.get(18).setValues(VariableType.FOOD, KITKAT, -1);
		unassigned_variables.get(19).setValues(VariableType.FOOD, HERSHEY, -1);
		unassigned_variables.get(20).setValues(VariableType.PET, DOG, -1);
		unassigned_variables.get(21).setValues(VariableType.PET, HORSE, -1);
		unassigned_variables.get(22).setValues(VariableType.PET, ZEBRA, -1);
		unassigned_variables.get(23).setValues(VariableType.PET, SNAIL, -1);
		unassigned_variables.get(24).setValues(VariableType.PET, FOX, -1);
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
				add(4);
			}
		};

		for (Variable variable : assingned_variables) {
			if (variable.type == var.type) {
				// System.out.println(getVariableName(variable) + ":"
				// + variable.assignedValue);
				int index = available_values.indexOf(variable.assignedValue);
				available_values.remove(index);
			}
		}
		return available_values;
	}

	@Override
	public boolean isConsistent() {
		for (Variable var1 : assingned_variables) {
			for (Variable var2 : assingned_variables) {
				if (var1 != var2 && var1.type == var2.type
						&& var1.assignedValue == var2.assignedValue) {
					System.out.println("Failed 0");
					return false;
				}
			}

			if (var1.type == VariableType.NATIONALITY
					&& var1.name == ENGLISH
					&& getVariableValue(VariableType.COLOR, RED) != -1
					&& var1.assignedValue != getVariableValue(
							VariableType.COLOR, RED)) {
				System.out.println("Failed 1");
				return false;
			}

			if (var1.type == VariableType.NATIONALITY
					&& var1.name == SPANISH
					&& getVariableValue(VariableType.PET, DOG) != -1
					&& var1.assignedValue != getVariableValue(VariableType.PET,
							DOG)) {
				System.out.println("Failed 2");
				return false;
			}

			if (var1.type == VariableType.NATIONALITY && var1.name == NORWEIGH
					&& var1.assignedValue != 0) {
				System.out.println("Failed 3");
				return false;
			}

			if (var1.type == VariableType.COLOR
					&& var1.name == GREEN
					&& getVariableValue(VariableType.COLOR, IVORY) != -1
					&& var1.assignedValue != (getVariableValue(
							VariableType.COLOR, IVORY) + 1)) {
				System.out.println("Failed 3");
				return false;
			}

		}
		return true;
	}

	@Override
	public void printSolution() {
		for (Variable variable : assingned_variables) {
			System.out.println("Name:" + getVariableName(variable) + " House:"
					+ variable.assignedValue);
		}
	}

	@Override
	public void assignValueToVariable(Variable var, int value) {
		var.assignedValue = value;
		unassigned_variables.remove(var);
		assingned_variables.add(var);
		System.out.println("Assigned " + getVariableName(var) + " House:"
				+ value);
		areDuplicates();
	}

	@Override
	public void unassignValueToVariable(Variable var) {
		System.out.println("Unassigning " + getVariableName(var) + " House:"
				+ var.assignedValue);
		var.assignedValue = -1;
		unassigned_variables.add(var);
		assingned_variables.remove(var);
		areDuplicates();
	}

	public int getVariableValue(VariableType type, int name) {
		for (Variable variable : variables) {
			if (variable.type == type && variable.name == name)
				return variable.assignedValue;
		}
		return -1;
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
		if (var.type == VariableType.COLOR) {
			switch (var.name) {
			case RED:
				return "RED";
			case GREEN:
				return "GREEN";
			case BLUE:
				return "BLUE";
			case YELLOW:
				return "YELLOW";
			case IVORY:
				return "IVORY";
			}
		} else if (var.type == VariableType.NATIONALITY) {
			switch (var.name) {
			case ENGLISH:
				return "ENGLISH";
			case SPANISH:
				return "SPANISH";
			case NORWEIGH:
				return "NORWEIGH";
			case JAPANESE:
				return "JAPANESE";
			case UKRAIN:
				return "UKRAIN";
			}
		} else if (var.type == VariableType.DRINK) {
			switch (var.name) {
			case JUICE:
				return "JUICE";
			case MILK:
				return "MILK";
			case COFFEE:
				return "COFFEE";
			case TEA:
				return "TEA";
			case WATER:
				return "WATER";
			}
		} else if (var.type == VariableType.FOOD) {
			switch (var.name) {
			case SNICKERS:
				return "SNICKERS";
			case SMARTIES:
				return "SMARTIES";
			case MILKYWAY:
				return "MILKYWAY";
			case KITKAT:
				return "KITKAT";
			case HERSHEY:
				return "HERSHEY";
			}
		} else if (var.type == VariableType.PET) {
			switch (var.name) {
			case DOG:
				return "DOG";
			case ZEBRA:
				return "ZEBRA";
			case HORSE:
				return "HORSE";
			case SNAIL:
				return "SNAIL";
			case FOX:
				return "FOX";
			}
		}
		return "";
	}

	@Override
	public String getValueName(int val) {
		return String.valueOf(val);
	}
}
