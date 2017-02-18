package database;
import flight.FlightsCollect;
import flight.Flight;
import airport.Airport;
import timeWindow.TimeWindow;
import java.lang.UnsupportedOperationException;
import java.time.Duration;

public class Search {
	private static final UnsupportedOperationException e = null;
	private DAI dao;
	Search(DAI dao){
		this.dao = dao;
	}
	/**
	 * *
	 * @param s depart airport
	 * @param d arrival airport
	 * @param st depart time window
	 * @param dt arrival time window
	 * @param legs number of legs allowed
	 * @param overlay max time of overlay allowed
	 * @return a collection of valid flights
	 * @throws Exception
	 */
	FlightsCollect search(Airport s, Airport d, TimeWindow st, TimeWindow dt, int legs, Duration overlay) throws Exception{
		throw e;
	}
	
	void reserve(Flight flight) throws Exception{
		throw e;
	}
}
