package climatechange;


// Stores a temperature object details
public class Temperature implements ITemperature {

	private String country;
	private String countryCode;
	private String month;
	private int year;
	private double tempCelsius;

	public Temperature(String country, String countryCode, String month, int year, double tempCelsius) {
		this.country = country;
		this.countryCode = countryCode;
		this.month = month;
		this.year = year;
		this.tempCelsius = tempCelsius;
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public String getCountry3LetterCode() {
		return countryCode;
	}

	@Override
	public String getMonth() {
		return month;
	}

	@Override
	public int getYear() {
		return year;
	}

	@Override
	public double getTemperature(boolean getFahrenheit) {
		return getFahrenheit ? (tempCelsius * 9 / 5) + 32 : tempCelsius;
	}

}
