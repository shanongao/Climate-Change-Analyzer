package climatechange;

import java.util.Comparator;
// class to compare two temperature objects
public class ClimateComparator implements Comparator<ITemperature> {
	
	private static final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct",
			"Nov", "Dec" };


	@Override
	public int compare(ITemperature o1, ITemperature o2) {
		// compare temp
		int diff = Double.compare(o1.getTemperature(false), o2.getTemperature(false));
		if(diff == 0) // same, compare countries
			diff = o1.getCountry().compareToIgnoreCase(o2.getCountry());
		if(diff == 0) // same, compare years
			diff = o1.getYear() - o2.getYear();
		if(diff == 0) // same, compare months
			diff = getMonthNum(o1) - getMonthNum(o2);
		
		return diff;
	}

	
	// to get month num for given temperature
	private int getMonthNum(ITemperature o1) {
		for (int i = 0; i < MONTHS.length; i++) {
			if(MONTHS[i].equalsIgnoreCase(o1.getMonth()))
				return i + 1;
		}
		
		return -1;
	}
}
