package constraint_satisfaction;

public class Variable {

	public VariableType type;
	public int name;
	public int assignedValue;

	public Variable() {
		type = null;
		name = -1;
		assignedValue = -1;
	}

	public void setValues(VariableType type, int name, int assignedValue) {
		this.type = type;
		this.name = name;
		this.assignedValue = assignedValue;
	}
}
