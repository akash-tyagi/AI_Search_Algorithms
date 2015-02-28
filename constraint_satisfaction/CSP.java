package constraint_satisfaction;

import java.util.List;

import constraint_satisfaction.CspJobPuzzle.Value;

public abstract class CSP {

	public abstract void setupProblem();

	public abstract Variable getUnassignedVariable();

	public abstract List<Value> getAvailableValues();

	public abstract boolean isConsistent();

	public abstract void printSolution();

	public abstract void assignValueToVariable(Variable variable, Value value);

	public abstract void unassignValueToVariable(Variable variable);
}
