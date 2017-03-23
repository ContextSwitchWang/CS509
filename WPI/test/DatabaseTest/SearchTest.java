package DatabaseTest;

import static org.junit.Assert.*;
import database.Search;
import database.DAC;
import database.TimeConversion;
import flight.SeatType;
import timeWindow.TimeWindow;

import org.junit.Before;
import org.junit.Test;

import airport.Airport;

public class SearchTest {

	@Before
	public void setUp() throws Exception {
	}

	
	
	@Test
	public void SearchTest() {
		Search s = new Search(new DAC(), new TimeConversion());
		Airport a = s.getAirports().get(0);
		System.out.println(a);
		TimeWindow st = new TimeWindow();
		st.start = TimeWindow.parseDateUI("2017-05-15-00:00");
		st.end = TimeWindow.parseDateUI("2017-05-16-00:00");
		SeatType seatType = SeatType.FirstClass;
		System.out.println(s.searchDepartLocal(a, st, seatType));
	}
	

	
	@Test
	public void getAirportsTest() {
		Search s = new Search(new DAC(), new TimeConversion());
		System.out.println(s.getAirports());
	}

}
