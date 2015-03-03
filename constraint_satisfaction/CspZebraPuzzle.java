package constraint_satisfaction;

import java.util.ArrayList;
import java.util.List;

public class CspZebraPuzzle extends CSP {

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
		super();
		totalVariables = 25;
		totalValues = 5;
	}

	@Override
	public void setupProblem() {
		for (int i = 0; i < totalVariables; i++) {
			Variable variable = new Variable();
			unassigned_variables.add(variable);
			variables.add(variable);
		}
		int i = 0;
		unassigned_variables.get(i++).setValues(VariableType.NATIONALITY,
				ENGLISH, -1);
		unassigned_variables.get(i++).setValues(VariableType.NATIONALITY,
				SPANISH, -1);
		unassigned_variables.get(i++).setValues(VariableType.NATIONALITY,
				NORWEIGH, -1);
		unassigned_variables.get(i++).setValues(VariableType.NATIONALITY,
				UKRAIN, -1);
		unassigned_variables.get(i++).setValues(VariableType.NATIONALITY,
				JAPANESE, -1);
		unassigned_variables.get(i++).setValues(VariableType.COLOR, GREEN, -1);
		unassigned_variables.get(i++).setValues(VariableType.COLOR, BLUE, -1);
		unassigned_variables.get(i++).setValues(VariableType.COLOR, YELLOW, -1);
		unassigned_variables.get(i++).setValues(VariableType.COLOR, IVORY, -1);
		unassigned_variables.get(i++).setValues(VariableType.COLOR, RED, -1);
		unassigned_variables.get(i++).setValues(VariableType.DRINK, JUICE, -1);
		unassigned_variables.get(i++).setValues(VariableType.DRINK, COFFEE, -1);
		unassigned_variables.get(i++).setValues(VariableType.DRINK, MILK, -1);
		unassigned_variables.get(i++).setValues(VariableType.DRINK, TEA, -1);
		unassigned_variables.get(i++).setValues(VariableType.DRINK, WATER, -1);
		unassigned_variables.get(i++)
				.setValues(VariableType.FOOD, SNICKERS, -1);
		unassigned_variables.get(i++)
				.setValues(VariableType.FOOD, SMARTIES, -1);
		unassigned_variables.get(i++)
				.setValues(VariableType.FOOD, MILKYWAY, -1);
		unassigned_variables.get(i++).setValues(VariableType.FOOD, KITKAT, -1);
		unassigned_variables.get(i++).setValues(VariableType.FOOD, HERSHEY, -1);
		unassigned_variables.get(i++).setValues(VariableType.PET, DOG, -1);
		unassigned_variables.get(i++).setValues(VariableType.PET, HORSE, -1);
		unassigned_variables.get(i++).setValues(VariableType.PET, ZEBRA, -1);
		unassigned_variables.get(i++).setValues(VariableType.PET, SNAIL, -1);
		unassigned_variables.get(i++).setValues(VariableType.PET, FOX, -1);
	}

	@Override
	public List<Integer> getAvailableValues(Variable var) {
		List<Integer> available_values = new ArrayList<Integer>();
		for (int i = 0; i < totalValues; i++) {
			available_values.add(i);
		}

		for (Variable variable : assingned_variables) {
			if (variable.type == var.type) {
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
				System.out.println("Failed 4");
				return false;
			}

			if (var1.type == VariableType.FOOD
					&& var1.name == HERSHEY
					&& getVariableValue(VariableType.PET, FOX) != -1
					&& (getVariableValue(VariableType.PET, FOX) + 1) != var1.assignedValue) {
				System.out.println("Failed 5");
				return false;
			}

			if (var1.type == VariableType.COLOR
					&& var1.name == YELLOW
					&& getVariableValue(VariableType.FOOD, KITKAT) != -1
					&& getVariableValue(VariableType.FOOD, KITKAT) != var1.assignedValue) {
				System.out.println("Failed 6");
				return false;
			}

			if (var1.type == VariableType.COLOR && var1.name == BLUE
					&& var1.assignedValue != 1) {
				System.out.println("Failed 7");
				return false;
			}

			if (var1.type == VariableType.FOOD
					&& var1.name == SMARTIES
					&& getVariableValue(VariableType.PET, SNAIL) != -1
					&& getVariableValue(VariableType.PET, SNAIL) != var1.assignedValue) {
				System.out.println("Failed 8");
				return false;
			}

			if (var1.type == VariableType.FOOD
					&& var1.name == SNICKERS
					&& getVariableValue(VariableType.DRINK, JUICE) != -1
					&& getVariableValue(VariableType.DRINK, JUICE) != var1.assignedValue) {
				System.out.println("Failed 9");
				return false;
			}

			if (var1.type == VariableType.NATIONALITY
					&& var1.name == UKRAIN
					&& getVariableValue(VariableType.DRINK, TEA) != -1
					&& getVariableValue(VariableType.DRINK, TEA) != var1.assignedValue) {
				System.out.println("Failed 10");
				return false;
			}

			if (var1.type == VariableType.NATIONALITY
					&& var1.name == JAPANESE
					&& getVariableValue(VariableType.FOOD, MILKYWAY) != -1
					&& getVariableValue(VariableType.FOOD, MILKYWAY) != var1.assignedValue) {
				System.out.println("Failed 11");
				return false;
			}

			if (var1.type == VariableType.FOOD
					&& var1.name == KITKAT
					&& getVariableValue(VariableType.PET, HORSE) != -1
					&& (getVariableValue(VariableType.PET, HORSE) - 1) != var1.assignedValue) {
				System.out.println("Failed 12");
				return false;
			}

			if (var1.type == VariableType.DRINK
					&& var1.name == COFFEE
					&& getVariableValue(VariableType.COLOR, GREEN) != -1
					&& getVariableValue(VariableType.COLOR, GREEN) != var1.assignedValue) {
				System.out.println("Failed 13");
				return false;
			}

			if (var1.type == VariableType.DRINK && var1.name == MILK
					&& var1.assignedValue != 2) {
				System.out.println("Failed 14");
				return false;
			}

		}
		return true;
	}

	public int getVariableValue(VariableType type, int name) {
		for (Variable variable : variables) {
			if (variable.type == type && variable.name == name)
				return variable.assignedValue;
		}
		return -1;
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
