package simulated_annealing;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class TSP {
	public Map<Integer, City> idToCity;
	int totalCities;

	public TSP() {
		idToCity = new HashMap<Integer, City>();
	}

	public void readCities(String file_name) throws IOException {
		Charset charset = Charset.forName("US-ASCII");
		int id = 0;
		for (String line : Files.readAllLines(Paths.get(file_name), charset)) {
			String words[] = line.split("\\s+");
			City city = new City(words[0], Double.parseDouble(words[1]),
					Double.parseDouble(words[2]));
			idToCity.put(id++, city);
		}
		totalCities = id;
	}

	public static void main(String args[]) throws IOException {
		TSP tsp = new TSP();
		tsp.readCities("texas_cities_with_coordinates.txt");
		Annealing annealing = new Annealing(tsp.totalCities, tsp.idToCity);
		int[] sol = annealing.annealing(10000);
		annealing.printCities(sol);
		System.out
				.println("Final Tour Length: " + annealing.getTourLength(sol));
	}
}
