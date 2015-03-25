package resolution_refutation;

import java.util.ArrayList;
import java.util.List;

public class Clause {
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
	private String getNegativeLiteral(String literal) {
		return (literal.contains("-")) ? literal.substring(1) : "-" + literal;
	}

	public List<String> getOppositeLiterals(Clause clause) {
		List<String> propositions = new ArrayList<String>();
		for (String literal : literals) {
			String negLiteral = getNegativeLiteral(literal);
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
}
