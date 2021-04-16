package climatechange;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

// To read and write to file
public class WeatherIO implements IWeatherIO {

	
	// read data from file
	@Override
	public ArrayList<ITemperature> readDataFromFile(String fileName) {
		ArrayList<ITemperature> arrayList = new ArrayList<>();

		try {
			Scanner scanner = new Scanner(new File(fileName));
			scanner.nextLine();
			while (scanner.hasNextLine()) {
				// read line
				Scanner lineSc = new Scanner(scanner.nextLine());
				lineSc.useDelimiter(", ");
				
				// read data
				double temp = lineSc.nextDouble();
				int year = lineSc.nextInt();
				String month = lineSc.next().trim();
				String country = lineSc.next().trim();
				String code = lineSc.next().trim();
				arrayList.add(new Temperature(country, code, month, year, temp));
				lineSc.close();
			}
			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return arrayList;
	}

	
	// write subject header to file
	@Override
	public void writeSubjectHeaderInFile(String filename, String subject) {
		try {
			PrintWriter pw = new PrintWriter(new File(filename));
			pw.println(subject);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	
	// write dtata to file
	@Override
	public void writeDataToFile(String filename, String topic, ArrayList<ITemperature> theWeatherList) {
		try {
			// write subject header
			writeSubjectHeaderInFile(filename, topic);
			
			// append the remaining data
			PrintWriter pw = new PrintWriter(new FileWriter(filename, true));
			pw.println("Temperature, Year, Month_Avg, Country, Country_Code");
			
			for (ITemperature temp : theWeatherList) {
				if(temp != null) // if not null, print
					pw.printf("%.2f(C) %.2f(F), %d, %s, %s, %s%n", temp.getTemperature(false), temp.getTemperature(true),
						temp.getYear(), temp.getMonth(), temp.getCountry(), temp.getCountry3LetterCode());
			}

			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
