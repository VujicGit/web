package beans;

import java.util.Comparator;

public class SortByRoomsAscending implements Comparator<Apartment> {

	@Override
	public int compare(Apartment o1, Apartment o2) {
		if(o1.getNumberOfRooms() > o2.getNumberOfRooms()) {
			return 1;
		}
		else if(o1.getNumberOfRooms() < o2.getNumberOfRooms()) {
			return -1;
		}
		else {
			return 0;
		}
	}

}
