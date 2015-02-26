package simulated_annealing;

public class City {
	public String name;
	public double lati, longi;

	public City(String name, double lati, double longi) {
		this.name = name;
		this.lati = lati;
		this.longi = longi;
	}

	public static double getDistance(City city1, City city2) {
		double lat1, lat2, lon1, lon2;
		lat1 = getRadian(city1.lati);
		lat2 = getRadian(city2.lati);
		lon1 = getRadian(city1.longi);
		lon2 = getRadian(city2.longi);

		double dlon = lon2 - lon1;
		double dlat = lat2 - lat1;
		double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1)
				* Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double d = 3961 * c;
		return d;
	}

	public static double getRadian(double degree) {
		return degree * 3.14159 / 180.0;
	}

}
