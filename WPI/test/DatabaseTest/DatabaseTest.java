package DatabaseTest;

import static org.junit.Assert.*;
import database.DAC;
import database.Search;
import database.TimeConversion;
import flight.Seat;
import flight.SeatType;
import flight.SeatsCollect;
import timeWindow.TimeWindow;

import org.junit.Before;
import org.junit.Test;

import airport.Airport;

public class DatabaseTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() {
		DAC dac = new DAC();
		//dac.getAirports("aaa");
		//dac.getFlightsDeparting("BOS", "2017_05_10");
		//dac.getAirplanes("aaa");
		//dac.getTimeOffset("39.6034810", "-119.6822510");
	}
	
	@Test
	public void timeConvertionTest(){
		TimeConversion tca = new TimeConversion();
		System.out.println(tca.getTimeOffset("42", "-71"));
	}

	@Test
	public void ReserveTest(){
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
		SeatsCollect ssc = s.searchLocal(a, b, st, dt, 3, seatType);
		ssc.sortOnPrice(true);
		StringBuilder sb = new StringBuilder();
		sb.append("<Flights>");
		for(Seat seat: ssc.get(0)){
			sb.append(String.format("<Flight number=%s seating=%s/>", seat.flight.Number, seat.seatType));	
		}
		sb.append("</Flights>");
		System.out.println(sb.toString());
		s.reserve(ssc.get(0));
	}
}
