package constraint_satisfaction;

import constraint_satisfaction.CspJobPuzzle.Value;

public class Variable {
	public String name;
	public Value assignedValue;

	public Variable() {
		this.name = null;
		assignedValue = null;
	}

	public void assignValue(Value value) {
		assignedValue = value;

	}

	public Value unAssignValue() {
		Value prevValue = assignedValue;
		assignedValue = null;
		return prevValue;
	}

}
