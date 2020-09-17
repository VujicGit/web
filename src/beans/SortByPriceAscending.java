package beans;

import java.util.Comparator;

public class SortByPriceAscending implements Comparator<Apartment> {

	@Override
	public int compare(Apartment arg0, Apartment arg1) {
		if(arg0.getPrice() > arg1.getPrice()) {
			return 1;
		}
		else if(arg0.getPrice() < arg1.getPrice()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
