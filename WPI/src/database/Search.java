package database;
import flight.SeatsCollect;
import flight.Seats;
import airport.Airport;
import airport.Airports;
import timeWindow.TimeWindow;
import java.lang.UnsupportedOperationException;
import java.time.Duration;
import java.time.LocalDateTime;

import flight.Flight;
import flight.Flights;
import flight.Seat;
import flight.SeatType;
import conf.Saps;

public class Search {
	private static final UnsupportedOperationException e = null;
	private DAI dao;
	private TimeConversionAPI tca;
	private Airports airports;
	public Search(DAI dao, TimeConversionAPI tca){
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
	
	public Airport getAirport(String code){
		if(airports == null){
			String xml = dao.getAirports(Saps.ticketAgency);
			airports = XMLParser.parseAirports(xml);
		}
		for(Airport a : airports){
			if(a.Code.equals(code)){
				return a;
			}
		}
		return null;
	}
	
	private long getTimeZoneOffset(Airport s){
		if(s.timeZoneOffset == null){
			long offset = (long)XMLParser.parseRawOffset(tca.getTimeOffset(Double.toString(s.Latitude),
					Double.toString(s.Longitude)));
			s.timeZoneOffset = offset;
		}
		return s.timeZoneOffset;
	}
	
	public SeatsCollect searchDepartLocal(Airport s, TimeWindow st, SeatType seatType){
		long offset = getTimeZoneOffset(s);
		TimeWindow newst = new TimeWindow();
		newst.start = st.start.minusSeconds(offset);
		newst.end   = st.end.minusSeconds(offset);
		SeatsCollect ssc = searchDepart(s, newst, seatType);
		for(Seats ss: ssc){
			for(Seat seat: ss){
				offset = getTimeZoneOffset(getAirport(seat.flight.CodeDepart));
				seat.flight.TimeDepart.plusSeconds(offset);
				offset = getTimeZoneOffset(getAirport(seat.flight.CodeArrival));
				seat.flight.TimeArrival.plusSeconds(offset);
				
			}
		}
		return ssc;
	}
	
	public SeatsCollect searchDepart(Airport s, TimeWindow st, SeatType seatType){
		SeatsCollect ans = new SeatsCollect();
		LocalDateTime start = st.start;
		LocalDateTime end   = st.end;
		LocalDateTime time = start;
		do{
			SeatsCollect ssc = searchOnDay(s, TimeWindow.getDate(time), seatType);
			for(Seats ss: ssc ){
				if(ss.size() == 0){
					throw new RuntimeException("Unexpected empty seats");
				}
				LocalDateTime depart = ss.get(0).flight.TimeDepart;
				if(depart.isBefore(end) &&
						depart.isAfter(start)){
					ans.add(ss);
				}
			}
			time = time.plusDays(1);
		}while(time.getYear() <= st.end.getYear() 
			&& time.getDayOfYear() <= st.end.getDayOfYear());;
		return ans;
	}
	
	public SeatsCollect searchOnDay(Airport s, String day, SeatType seatType){
		SeatsCollect ans = new SeatsCollect();
		if(seatType == null){
			ans.addAll(searchOnDay(s, day, SeatType.Coach));
			ans.addAll(searchOnDay(s, day, SeatType.FirstClass));
			return ans;
		}
		
		String fs = dao.getFlightsDeparting(Saps.ticketAgency, s.Code, day);
		Flights flights =XMLParser.parseFlights(fs);
		SeatsCollect sc = new SeatsCollect();
		Seat seat;
		Seats seats;
		for(Flight f : flights){
			if(seatType == SeatType.Coach && f.SeatsCoach > 0
				|| seatType == SeatType.FirstClass && f.SeatsFirstclass > 0){
				seat = new Seat();
				seats = new Seats();
				seat.flight = f;
				seat.seatType = seatType;
				seats.add(seat);
				ans.add(seats);
			}
		}
		return ans;
	}
	
	
	public Airports getAirports(){
		if(airports == null){
			String xml = dao.getAirports(Saps.ticketAgency);
			airports = XMLParser.parseAirports(xml);
		}
		return airports;
	}
	
	void reserve(Seats seats) throws Exception{
		throw e;
	}
	
	
}
