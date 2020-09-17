package beans;

import java.util.Comparator;

public class SortByPriceDescending implements Comparator<Apartment> {

	@Override
	public int compare(Apartment o1, Apartment o2) {
		if(o1.getPrice() > o1.getPrice()) {
			return -1;
		}
		else if(o1.getPrice() < o1.getPrice()) {
			return 1;
		}
		else {
			return 0;
		}
	}

}
