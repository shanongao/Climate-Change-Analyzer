package climatechange;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class ClimateAnalyzer implements IClimateAnalyzer {
	// Month names
	private static final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
			"Nov", "Dec" };

	// input file
	private static final String INPUT_FILE = "world_temp_2000-2016.csv";

	private ArrayList<ITemperature> temperatures;
	private Comparator<ITemperature> comparator; // compare two objects
	private IWeatherIO weatherIO;

	// constructor reads the file
	public ClimateAnalyzer() {
		weatherIO = new WeatherIO();
		temperatures = weatherIO.readDataFromFile(INPUT_FILE);
		comparator = new ClimateComparator();
	}

	// lowest temperature for given country and month
	@Override
	public ITemperature getLowestTempByMonth(String country, int month) {
		ITemperature result = null;

		for (ITemperature temp : temperatures) {
			// if country and month matches
			if (temp.getCountry().equalsIgnoreCase(country) && temp.getMonth().equalsIgnoreCase(MONTHS[month - 1])) {
				if (result == null || result.getTemperature(false) > temp.getTemperature(false))
					result = temp;
			}
		}

		return result;
	}

	// highest temperature for given country and month
	@Override
	public ITemperature getHighestTempByMonth(String country, int month) {
		ITemperature result = null;

		for (ITemperature temp : temperatures) {
			if (temp.getCountry().equalsIgnoreCase(country) && temp.getMonth().equalsIgnoreCase(MONTHS[month - 1])) {
				if (result == null || result.getTemperature(false) < temp.getTemperature(false))
					result = temp;
			}
		}

		return result;
	}

	// lowest temperature for given country and year
	@Override
	public ITemperature getLowestTempByYear(String country, int year) {
		ITemperature result = null;
		for (ITemperature temp : temperatures) {
			if (temp.getCountry().equalsIgnoreCase(country) && temp.getYear() == year) {
				if (result == null || result.getTemperature(false) > temp.getTemperature(false))
					result = temp;
			}
		}

		return result;
	}

	// highest temperature for given country and year
	@Override
	public ITemperature getHighestTempByYear(String country, int year) {
		ITemperature result = null;

		for (ITemperature temp : temperatures) {
			if (temp.getCountry().equalsIgnoreCase(country) && temp.getYear() == year) {
				if (result == null || result.getTemperature(false) < temp.getTemperature(false))
					result = temp;
			}
		}

		return result;
	}

	// get data of country with temperature in given range
	@Override
	public TreeSet<ITemperature> getTempWithinRange(String country, double rangeLowTemp, double rangeHighTemp) {
		TreeSet<ITemperature> treeset = new TreeSet<>(comparator);
		for (ITemperature temp : temperatures) {
			// check same country and within range
			if (temp.getCountry().equalsIgnoreCase(country) && temp.getTemperature(false) >= rangeLowTemp
					&& temp.getTemperature(false) <= rangeHighTemp) {
				treeset.add(temp);
			}
		}

		return treeset;
	}

	// get lowest temperature of a country
	@Override
	public ITemperature getLowestTempYearByCountry(String country) {
		ITemperature result = null;

		for (ITemperature temp : temperatures) {
			// check same country
			if (temp.getCountry().equalsIgnoreCase(country)) {
				if (result == null || result.getTemperature(false) > temp.getTemperature(false))
					result = temp;
			}
		}

		return result;
	}

	// get highest temperature of a country
	@Override
	public ITemperature getHighestTempYearByCountry(String country) {
		ITemperature result = null;

		for (ITemperature temp : temperatures) {
			if (temp.getCountry().equalsIgnoreCase(country)) {
				if (result == null || result.getTemperature(false) < temp.getTemperature(false))
					result = temp;
			}
		}

		return result;
	}

	// get top 10 lowest temperature countries for a given month
	@Override
	public ArrayList<ITemperature> allCountriesGetTop10LowestTemp(int month) {
		// sort temperatures
		Collections.sort(temperatures, comparator);
		ArrayList<ITemperature> result = new ArrayList<>();

		for (ITemperature temp : temperatures) {
			// if same month
			if (temp.getMonth().equalsIgnoreCase(MONTHS[month - 1]))
				result.add(temp);

			if (result.size() == 10) // 10 size reached
				break;
		}

		return result;

	}

	// get top 10 highest temperature countries for a given month
	@Override
	public ArrayList<ITemperature> allCountriesGetTop10HighestTemp(int month) {
		// sort temperatures
		Collections.sort(temperatures, comparator);
		ArrayList<ITemperature> result = new ArrayList<>();

		for (ITemperature temp : temperatures) {
			if (temp.getMonth().equalsIgnoreCase(MONTHS[month - 1])) {
				if (result.size() == 10) // if full, remove first
					result.remove(0);
				result.add(temp);
			}
		}

		return result;
	}

	// get top 10 lowest temperature countries
	@Override
	public ArrayList<ITemperature> allCountriesGetTop10LowestTemp() {
		// sort temperatures
		Collections.sort(temperatures, comparator);
		ArrayList<ITemperature> result = new ArrayList<>();
		for (ITemperature temp : temperatures) {
			result.add(temp);

			if (result.size() == 10) // size reached
				break;
		}

		return result;
	}

	// get top 10 highest temperature countries
	@Override
	public ArrayList<ITemperature> allCountriesGetTop10HighestTemp() {
		// sort temperatures
		Collections.sort(temperatures, comparator);
		ArrayList<ITemperature> result = new ArrayList<>();
		for (ITemperature temp : temperatures) {
			if (result.size() == 10) { // if full, remove 0
				result.remove(0);
			}

			result.add(temp);
		}

		return result;
	}

	// get all data with temperature within given range
	@Override
	public ArrayList<ITemperature> allCountriesGetAllDataWithinTempRange(double lowRangeTemp, double highRangeTemp) {
		// sort temperatures
		Collections.sort(temperatures, comparator);
		ArrayList<ITemperature> result = new ArrayList<>();
		for (ITemperature temp : temperatures) {
			if (temp.getTemperature(false) >= lowRangeTemp && temp.getTemperature(false) <= highRangeTemp)
				result.add(temp);
		}

		return result;

	}

	// top 10 countries with max temperature change for a given month andf two years
	@Override
	public ArrayList<ITemperature> allCountriesTop10TempDelta(int month, int year1, int year2) {
		ArrayList<ITemperature> result = new ArrayList<>();

		// to store data of all countries and the temperatures for two years
		HashMap<String, ITemperature> data1 = new HashMap<>();
		HashMap<String, ITemperature> data2 = new HashMap<>();

		// read data
		for (ITemperature t : temperatures) {
			if (t.getMonth().equalsIgnoreCase(MONTHS[month - 1]) && t.getYear() == year1) {
				data1.put(t.getCountry3LetterCode(), t);
			} else if (t.getMonth().equalsIgnoreCase(MONTHS[month - 1]) && t.getYear() == year2) {
				data2.put(t.getCountry3LetterCode(), t);
			}
		}

		// convert hashmaps values to parallel array lists
		ArrayList<ITemperature> tempResult1 = new ArrayList<>();
		ArrayList<ITemperature> tempResult2 = new ArrayList<>();
		for (String countryCode : data1.keySet()) {
			if (data2.containsKey(countryCode)) {
				tempResult1.add(data1.get(countryCode));
				tempResult2.add(data2.get(countryCode));
			}
		}

		
		// sort by delta
		for (int i = 1; i < tempResult1.size(); i++) {
			int j = i;

			while (j > 0 && 
					Math.abs(tempResult1.get(j - 1).getTemperature(false) - tempResult2.get(j - 1).getTemperature(false))
						> Math.abs(tempResult1.get(j).getTemperature(false) - tempResult2.get(j).getTemperature(false))

			) {
				Collections.swap(tempResult1, j - 1, j);
				Collections.swap(tempResult2, j - 1, j);
				j--;
			}
		}

		
		// get last 10 records
		int start = 0;
		if (tempResult1.size() > 10)
			start = tempResult1.size() - 10;
		for (int i = start; i < tempResult1.size(); i++) {
			result.add(tempResult1.get(i));
			result.add(tempResult2.get(i));
		}

		return result;

	}

	@Override
	public void runClimateAnalyzer() {
		Scanner scanner = new Scanner(System.in);

		// Task A-1
		System.out.println("Task A1: Get data which has the lowest and highest temperature reading for a given month");
		System.out.print("Enter country name: ");
		String country = scanner.nextLine();
		System.out.print("Enter month number: ");
		int month = scanner.nextInt();
		scanner.nextLine();
		
		ArrayList<ITemperature> taskA1 = new ArrayList<>();
		taskA1.add(this.getLowestTempByMonth(country, month));
		taskA1.add(this.getHighestTempByMonth(country, month));
		weatherIO.writeDataToFile("taskA1_climate_info.csv",
				"Highest and Lowest temperatures of " + country + " for " + MONTHS[month - 1], taskA1);

		// Task A2
		System.out.println("\nTask A2: Get data for the month which has the lowest and highest temperature in a given year.");
		
		System.out.print("Enter country name: ");
		country = scanner.nextLine();
		System.out.print("Enter year(2001-2016): ");
		int year = scanner.nextInt();
		scanner.nextLine();
		
		
		ArrayList<ITemperature> taskA2 = new ArrayList<>();
		taskA2.add(this.getLowestTempByYear(country, year));
		taskA2.add(this.getHighestTempByYear(country, year));
		weatherIO.writeDataToFile("taskA2_climate_info.csv",
				"Highest and Lowest temperatures of " + country + " for Year " + year, taskA2);

		// Task A3
		System.out.println("\nTask A3: Get all data that falls within a specific temperature range");
		
		System.out.print("Enter country name: ");
		country = scanner.nextLine();
		System.out.print("Enter range start value: ");
		double start = scanner.nextDouble();
		System.out.print("Enter range end value: ");
		double end = scanner.nextDouble();
		scanner.nextLine();
		
		ArrayList<ITemperature> taskA3 = new ArrayList<>();
		taskA3.addAll(getTempWithinRange(country, start, end));
		weatherIO.writeDataToFile("taskA3_climate_info.csv", "Temperatures for " + country + " in range " + start + " and " + end,
				taskA3);

		// Task A4
		System.out.println("\nTask A4: Get data for the year with the lowest temperature and for the year with the highest\n" + 
				"temperature reading ");
		System.out.print("Enter country name: ");
		country = scanner.nextLine();
		ArrayList<ITemperature> taskA4 = new ArrayList<>();
		taskA4.add(this.getLowestTempYearByCountry(country));
		taskA4.add(this.getHighestTempYearByCountry(country));
		weatherIO.writeDataToFile("taskA4_climate_info.csv", "Highest and Lowest temperatures of " + country, taskA4);

		// Task B1
		System.out.println("\nTask B1: The top 10 countries with the lowest temperature reading and the top 10 countries with\n" + 
				"the highest temperature reading for a given month between 2000 and 2016.");
		System.out.print("Enter month number: ");
		month = scanner.nextInt();
		scanner.nextLine();
		ArrayList<ITemperature> taskB1 = new ArrayList<>();
		taskB1.addAll(this.allCountriesGetTop10LowestTemp(month));
		taskB1.addAll(this.allCountriesGetTop10HighestTemp(month));
		weatherIO.writeDataToFile("taskB1_climate_info.csv", "Top 10 Highest and Lowest temperatures for " + MONTHS[month - 1], taskB1);

		// Task B2
		System.out.println("\nTask B2: The top 10 countries with the lowest temperature reading and the top 10 countries with\n" + 
				"the highest temperature readings between 2000 and 2016");
		ArrayList<ITemperature> taskB2 = new ArrayList<>();
		taskB2.addAll(this.allCountriesGetTop10LowestTemp());
		taskB2.addAll(this.allCountriesGetTop10HighestTemp());
		weatherIO.writeDataToFile("taskB2_climate_info.csv", "Top 10 Highest and Lowest temperatures", taskB2);

		// Task B3
		System.out.println("\nTask B3: List all of the countries that fall within a specific temperature range");
		System.out.print("Enter range start value: ");
		start = scanner.nextDouble();
		System.out.print("Enter range end value: ");
		end = scanner.nextDouble();
		scanner.nextLine();
		ArrayList<ITemperature> taskB3 = allCountriesGetAllDataWithinTempRange(start, end);
		weatherIO.writeDataToFile("taskB3_climate_info.csv", "Temperatures in range "+start+" and "+end, taskB3);

		// Task C
		System.out.println("\nTask C: The top 10 countries with the largest change in temperature in the same month between\n" + 
				"two different years");
		System.out.print("Enter month number: ");
		month = scanner.nextInt();
		System.out.print("Enter start year: ");
		int startYear = scanner.nextInt();
		System.out.print("Enter end year: ");
		int endYear = scanner.nextInt();
		scanner.nextLine();
		ArrayList<ITemperature> taskC = allCountriesTop10TempDelta(1, startYear, endYear);
		weatherIO.writeDataToFile("taskC_climate_info.csv", "Top climate change in "+MONTHS[month-1]+" month for year "+startYear+" and " + endYear,
				taskC);
	}

}
