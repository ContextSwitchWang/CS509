package Flights;

import static org.junit.Assert.*;

import java.time.LocalDateTime;

import org.junit.Before;
import org.junit.Test;

import flight.Flight;
import flight.Flights;

public class FlightsTest {


	@Test
	public void test() {
		Flights flights = new Flights();
		assertTrue(flights.isValid());
		flights.add(new Flight());
		flights.add(new Flight());
//		flights.add(new Flight());
		flights.get(0).TimeDepart= LocalDateTime.of(2016,12,31,16,22);
		flights.get(0).TimeArrival=LocalDateTime.of(2016,12,31,23,6);
		flights.get(1).TimeDepart= LocalDateTime.of(2017,1,1,1,30);
		flights.get(1).TimeArrival=LocalDateTime.of(2017,1,1,19,6);
//		flights.get(2).TimeDepart= LocalDateTime.of(2017,5,2,23,30);
//		flights.get(2).TimeArrival=LocalDateTime.of(2017,5,3,11,6);
		Boolean result = flights.isValid();
		System.out.println(result);
	}

}
