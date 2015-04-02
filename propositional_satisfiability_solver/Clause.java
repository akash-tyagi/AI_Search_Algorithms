package propositional_satisfiability_solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Clause {
	public static enum Values {
		FALSE, TRUE, CANT_TELL
	}

	public List<String> literals;
	public int id;
	public Clause resClause1, resClause2;

	public Clause() {
		literals = new ArrayList<String>();
		resClause1 = resClause2 = null;
	}

	public void addLiteral(String literal) {
		literals.add(literal);
	}

	public void print() {
		for (String literal : literals) {
			System.out.print(literal + " ");
		}
		System.out.println("");
	}

	public int getSize() {
		return literals.size();
	}

	public boolean isEmpty() {
		return literals.size() == 0 ? true : false;
	}

	// Return inverse of any literal
	public static String getInverseOfLiteral(String literal) {
		return (literal.contains("-")) ? literal.substring(1) : "-" + literal;
	}

	public static String getPositiveLiteral(String literal) {
		return (literal.contains("-")) ? literal.substring(1) : literal;
	}

	public List<String> getOppositeLiterals(Clause clause) {
		List<String> propositions = new ArrayList<String>();
		for (String literal : literals) {
			String negLiteral = getInverseOfLiteral(literal);
			if (clause.literals.contains(negLiteral)
					&& propositions.contains(literal) == false) {
				propositions.add(literal);
			}
		}
		return propositions;
	}

	public String toString() {
		String result = " ";
		for (String literal : literals) {
			result += literal + " ";
		}
		return result;
	}

	private Values getLiteralValue(String literal, Map<String, Boolean> model) {
		Values value = Values.CANT_TELL;

		if (literal.charAt(0) == '-') {
			literal = getPositiveLiteral(literal);
			if (model.containsKey(literal))
				value = model.get(literal) ? Values.FALSE : Values.TRUE;

		} else {
			if (model.containsKey(literal)) {
				value = model.get(literal) ? Values.TRUE : Values.FALSE;

			}
		}

		return value;
	}

	public Values getValueIfAllLiteralDefined(Map<String, Boolean> model) {
		Values value = Values.FALSE;

		for (String literal : literals) {
			switch (getLiteralValue(literal, model)) {
			case FALSE:
				break;

			case TRUE:
				value = Values.TRUE;
				break;

			case CANT_TELL:
				return Values.CANT_TELL;
			}
		}
		return value;

	}

	public Values getValueEvenIfAllLiteralUndefined(Map<String, Boolean> model) {
		Values value = Values.FALSE;

		for (String literal : literals) {
			switch (getLiteralValue(literal, model)) {
			case FALSE:
				break;

			case TRUE:
				return Values.TRUE;

			case CANT_TELL:
				value = Values.CANT_TELL;
			}
		}
		return value;

	}

	public String getUnitLiteral(Map<String, Boolean> model) {
		String uniLiteral = null;
		for (String literal : literals) {
			switch (getLiteralValue(literal, model)) {
			case FALSE:
				break;

			case TRUE:
				return null;

			case CANT_TELL:
				if (uniLiteral == null)
					uniLiteral = literal;
				else
					return null;
				break;
			}
		}
		return uniLiteral;
	}

}
