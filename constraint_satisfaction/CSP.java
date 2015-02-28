package constraint_satisfaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CSP {

	Map<Integer, Integer> mapVariableToValue;
	List<Variable> variables;
	List<Variable> unassigned_variables;
	List<Variable> assingned_variables;

	public CSP() {
		mapVariableToValue = new HashMap<Integer, Integer>();
		unassigned_variables = new ArrayList<Variable>();
		assingned_variables = new ArrayList<Variable>();
		variables = new ArrayList<Variable>();
	}

	public void setupProblem() {
	}

	public Variable getUnassignedVariable() {
		if (unassigned_variables.size() == 0)
			return null;
		return unassigned_variables.get(0);
	}

	public Variable getMRVBasedVariable() {
		return getUnassignedVariable();
	}

	public List<Integer> getAvailableValues(Variable var) {
		return null;
	}

	public boolean isConsistent() {
		return false;
	}

	public void printSolution() {
		System.out.println("Expected Solution:$$$$$$$$$$$$$$$$$$$");
		for (Variable variable : assingned_variables) {
			System.out.println(getVariableName(variable) + " ----> "
					+ getValueName(variable.assignedValue));
		}
	}

	public void assignValueToVariable(Variable var, int value) {
		var.assignedValue = value;
		unassigned_variables.remove(var);
		assingned_variables.add(var);
		System.out.println("Assigned " + getVariableName(var) + " PERSON:"
				+ getValueName(value));
		areDuplicates();
	}

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

	public String getVariableName(Variable var) {
		return "";
	}

	public String getValueName(int val) {
		return "";
	}
};
