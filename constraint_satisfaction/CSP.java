package constraint_satisfaction;

import java.util.List;

public abstract class CSP {

	public abstract void setupProblem();

	public abstract Variable getUnassignedVariable();

	public abstract List<Integer> getAvailableValues(Variable var);

	public abstract boolean isConsistent();

	public abstract void printSolution();

	public abstract void assignValueToVariable(Variable variable, int value);

	public abstract void unassignValueToVariable(Variable variable);

	public abstract String getVariableName(Variable var);

	public abstract String getValueName(int val);
};
