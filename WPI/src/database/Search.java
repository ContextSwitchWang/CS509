package database;
import flight.SeatsCollect;
import flight.Seats;
import airport.Airport;
import timeWindow.TimeWindow;
import java.lang.UnsupportedOperationException;
import java.time.Duration;
import flight.SeatType;

public class Search {
	private static final UnsupportedOperationException e = null;
	private DAI dao;
	private TimeConversionAPI tca;
	Search(DAI dao, TimeConversionAPI tca){
		this.dao = dao;
		this.tca = tca;
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
	SeatsCollect search(Airport s, Airport d, TimeWindow st, TimeWindow dt, int legs, Duration overlay, SeatType seatType) throws Exception{
		throw e;
	}
	
	void reserve(Seats seats) throws Exception{
		throw e;
	}
}
