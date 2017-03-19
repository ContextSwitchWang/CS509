package database;
import flight.SeatsCollect;
import flight.Seats;
import airport.Airport;
import airport.Airports;
import timeWindow.TimeWindow;
import java.lang.UnsupportedOperationException;
import java.time.Duration;

import flight.Flight;
import flight.Flights;
import flight.Seat;
import flight.SeatType;
import conf.Saps;

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
	
	SeatsCollect search(Airport s, TimeWindow st, SeatType seatType){
		SeatsCollect ans = new SeatsCollect();
		String fs = dao.getFlightsDeparting(Saps.ticketAgency, s.Code, st.getStartDate());
		Flights flights =XMLParser.parseFlights(fs);
		SeatsCollect sc = new SeatsCollect();
		Seat seat;
		Seats seats;
		for(Flight f : flights){
			if(seatType == SeatType.Coach && f.SeatsCoach > 0
				|| seatType == SeatType.FirstClass && f.SeatsFirstclass > 0){
				seat = new Seat();
				seats = new Seats();
				seat.fight = f;
				seat.seatType = seatType;
				seats.add(seat);
				ans.add(seats);
			}
		}
		return null;
	}
	
	Airports getAirports(){
		String xml = dao.getAirports(Saps.ticketAgency);
		return XMLParser.parseAirports(xml);
	}
	
	void reserve(Seats seats) throws Exception{
		throw e;
	}
	
	
}
