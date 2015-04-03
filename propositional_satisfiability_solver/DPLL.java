package propositional_satisfiability_solver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import propositional_satisfiability_solver.Clause.Values;

public class DPLL {
	List<String> literals;
	List<Clause> clauses;
	int iter;

	public DPLL(List<String> literals, List<Clause> clauses) {
		this.literals = literals;
		this.clauses = clauses;
	}

	private boolean isEveryClauseTrue(Map<String, Boolean> model) {
		for (Clause clause : clauses) {
			if (clause.getValueIfAllLiteralDefined(model) != Values.TRUE) {
				return false;
			}
		}
		return true;
	}

	private boolean isSomeClausefalse(Map<String, Boolean> model) {

		for (Clause clause : clauses) {
			if (clause.getValueIfAllLiteralDefined(model) == Values.FALSE) {
				clause.print();
				return true;
			}
		}
		return false;
	}

	public void printModel(Map<String, Boolean> model) {
		System.out.print("model={");
		for (String key : model.keySet()) {
			System.out.print("'" + key + "': " + model.get(key).toString()
					+ " ,");
		}
		System.out.println("}");
	}

	private void printSuccessModel(Map<String, Boolean> model, int iter) {
		System.out.println("--------------------------------------");
		System.out.println("nodes searched:" + iter);
		System.out.println("solution:");
		Map<String, Boolean> map = new TreeMap<String, Boolean>(model);
		for (String key : map.keySet()) {
			System.out.println(key + "=" + map.get(key).toString());
		}
		System.out.println("----------------------");
		System.out.println("true props:");
		for (String key : map.keySet()) {
			if (map.get(key).toString().equals("true"))
				System.out.println(key);
		}
	}

	private String findPureSymbol(List<String> symbols,
			Map<String, Boolean> model) {

		Set<String> pureSymbols = new LinkedHashSet<String>();
		Set<String> literalsSet = new LinkedHashSet<String>();

		for (Clause clause : clauses) {
			for (String literal : clause.literals) {
				if (symbols.contains(Clause.getPositiveLiteral(literal)) == false
						|| clause.getValueEvenIfAllLiteralUndefined(model) == Values.TRUE)
					continue;

				literalsSet.add(literal);
				String inverseLiteral = Clause.getInverseOfLiteral(literal);

				if (literalsSet.contains(inverseLiteral)) {
					pureSymbols.removeAll(Collections.singleton(literal));
					pureSymbols
							.removeAll(Collections.singleton(inverseLiteral));

					continue;
				}

				pureSymbols.add(literal);
			}
		}

		String res = null;
		if (pureSymbols.size() > 0) {
			for (String literal : pureSymbols) {
				res = literal;
			}
		}
		return res;
	}

	private String findUnitClause(List<String> symbols,
			Map<String, Boolean> model) {
		for (Clause clause : clauses) {
			String uniLiteral = clause.getUnitLiteral(model);
			if (clause.getValueEvenIfAllLiteralUndefined(model) != Values.TRUE
					&& uniLiteral != null) {
				System.out.print("unit clause on:");
				clause.print();
				return uniLiteral;
			}
		}
		return null;
	}

	public boolean dpllSatisfiable() {
		Map<String, Boolean> model = new HashMap<String, Boolean>();
		iter = 0;

		System.out.println("initial clauses:");
		for (Clause clause : clauses) {
			clause.toString();
		}
		System.out.println("---------------------------");

		return dpll(literals, model);
	}

	private boolean dpll(List<String> symbols, Map<String, Boolean> model) {
		printModel(model);

		if (isEveryClauseTrue(model) == true) {
			printSuccessModel(model, iter);
			return true;
		}
		if (isSomeClausefalse(model) == true)
			return false;

		Map<String, Boolean> newModel = new HashMap<String, Boolean>(model);
		List<String> rest = new ArrayList<String>(symbols);

		// Trying the pure symbol
		String unitSymbol = findPureSymbol(symbols, model);
		if (unitSymbol != null) {
			boolean value = (unitSymbol.contains("-") ? false : true);
			newModel.put(Clause.getPositiveLiteral(unitSymbol), value);
			rest.remove(unitSymbol);
			System.out.println("pure_symbol: "
					+ Clause.getPositiveLiteral(unitSymbol) + "=" + value
					+ "\n");
			iter++;
			return dpll(symbols, newModel);
		}

		// Trying unit clauses
		String uniLiteral = findUnitClause(symbols, newModel);
		if (uniLiteral != null) {
			boolean value = (uniLiteral.contains("-") ? false : true);
			newModel.put(Clause.getPositiveLiteral(uniLiteral), value);
			rest.remove(Clause.getPositiveLiteral(uniLiteral));
			System.out.println("implies "
					+ Clause.getPositiveLiteral(uniLiteral) + "=" + value
					+ "\n");
			iter++;
			return dpll(rest, newModel);
		}

		// Trying some random value for random symbol
		String firstSymbol = symbols.get(0);
		rest.remove(firstSymbol);

		newModel.put(firstSymbol, true);
		System.out.println("trying " + firstSymbol + "= T\n");
		iter++;
		boolean res1 = dpll(rest, newModel);
		if (res1)
			return true;

		newModel.put(firstSymbol, false);
		System.out.println("trying " + firstSymbol + "= F\n");
		iter++;
		return dpll(rest, newModel);

	}
}
