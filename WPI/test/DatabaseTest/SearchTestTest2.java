package DatabaseTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import airport.Airport;
import database.DAC;
import database.Search;
import database.TimeConversion;
import flight.SeatType;
import flight.SeatsCollect;
import timeWindow.TimeWindow;

public class SearchTestTest2 {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void SearchTest() throws Exception {
		Search s = new Search(new DAC(), new TimeConversion());
		Airport a = s.getAirports().get(0);
		Airport b = s.getAirports().get(4);
		System.out.println(a);
		System.out.println(b);
		TimeWindow st = new TimeWindow();
		st.start = TimeWindow.parseDateUI("2017-05-15-00:00");
		st.end = TimeWindow.parseDateUI("2017-05-16-00:00");
		
		TimeWindow dt = new TimeWindow();
		dt.start = TimeWindow.parseDateUI("2017-05-16-00:00");
		dt.end = TimeWindow.parseDateUI("2017-05-17-00:00");
		SeatType seatType = SeatType.FirstClass;
		SeatsCollect ssc = s.searchLocal(a, b, st, 3, seatType);
		ssc.sortOnPrice(true);
		System.out.println(ssc);
	}
	

	
	@Test
	public void getAirportsTest() {
		Search s = new Search(new DAC(), new TimeConversion());
		System.out.println(s.getAirports());
	}
}
