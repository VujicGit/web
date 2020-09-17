package beans;

import java.util.Comparator;

public class SortByGuestsAscending implements Comparator<Apartment> {
	
	@Override
	public int compare(Apartment o1, Apartment o2) {
		if(o1.getNumberOfGuests() > o2.getNumberOfGuests()) {
			return 1;
		}
		else if(o1.getNumberOfGuests() < o2.getNumberOfGuests()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
