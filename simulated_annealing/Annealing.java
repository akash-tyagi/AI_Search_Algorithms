package simulated_annealing;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

public class Annealing {
	public int totalCities;
	public Map<Integer, City> idToCity;
	public static double INITIAL_TEMP = 50;

	public Annealing(int totalCities, Map<Integer, City> idToCity) {
		this.totalCities = totalCities;
		this.idToCity = idToCity;
	}

	public int[] getInitialArrangement() {
		int[] curr = new int[totalCities];
		curr = new int[totalCities];
		for (int i = 0; i < totalCities; i++) {
			curr[i] = i;
		}
		System.out.println(getTourLength(curr));
		return curr;
	}

	public double schedule(int iter) {
		return INITIAL_TEMP - 0.001 * iter;
	}

	public void printCities(int[] curr) {
		System.out.print("Final tour: ");
		for (int i = 0; i < totalCities; i++) {
			System.out.print(idToCity.get(curr[i]).name + " ");
		}
		System.out.print("\n");
	}

	public int[] successor(int curr[]) {
		int[] next = curr.clone();
		int a, b;
		Random rand = new Random();
		do {
			a = rand.nextInt(totalCities);
			b = rand.nextInt(totalCities);
		} while (a >= b);
		for (int i = a; i <= (a + b) / 2; i++) {
			swap(next, i, a + b - i);
		}

		return next;
	}

	public int[] annealing(int total_iter) throws IOException {

		double temp = INITIAL_TEMP;
		int[] curr = getInitialArrangement();
		System.out.println("Initial tour length:" + getTourLength(curr));
		int iter = 0;
		while (true) {

			temp = schedule(iter);
			if (temp <= 0) {
				System.out.println("\n\nTotal Iterations: " + iter);
				break;
			}

			int[] next = successor(curr);
			double diff = getTourLength(curr) - getTourLength(next);
			double prob1 = getProbability(diff, temp);
			System.out.println("iter=" + iter + " len="
					+ (int) getTourLength(curr) + " newLen="
					+ (int) getTourLength(next) + " delta=" + diff + " temp="
					+ temp + " p<" + prob1);

			if (diff > 0)
				curr = next;
			else {
				double prob2 = Math.random();
				if (prob1 > prob2)
					curr = next;
			}
			iter++;
		}
		return curr;
	}

	private double getProbability(double diff, double temp) {
		return Math.exp(diff / temp);
	}

	public double getTourLength(int[] curr) {
		double tourLength = 0;
		City city1, city2;
		for (int i = 0; i < totalCities; i++) {
			city1 = idToCity.get(curr[i]);
			city2 = idToCity.get(curr[(i + 1) % totalCities]);
			tourLength += City.getDistance(city1, city2);
		}
		return tourLength;
	}

	public void swap(int[] arr, int a, int b) {
		int temp = arr[a];
		arr[a] = arr[b];
		arr[b] = temp;

	}

}
