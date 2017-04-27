package database;
import flight.SeatsCollect;
import flight.Seats;
import airport.Airport;
import airport.Airports;
import timeWindow.TimeWindow;
import java.lang.UnsupportedOperationException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedHashMap;

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
	private LinkedHashMap<String, SeatsCollect> cache;
	public Search(DAI dao, TimeConversionAPI tca){
		this.dao = dao;
		this.tca = tca;
		this.cache = new LinkedHashMap<String, SeatsCollect>(Saps.cacheSize);
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
	public SeatsCollect searchLocal(Airport s, Airport d, TimeWindow st, TimeWindow dt, Integer legs, SeatType seatType){
		if(Saps.clearCacheEachSearch){
			this.cache.clear();
		}
		long offset = getTimeZoneOffset(s);
		TimeWindow newst = new TimeWindow();
		newst.start = st.start.minusSeconds(offset);
		newst.end   = st.end.minusSeconds(offset);
		TimeWindow newdt = new TimeWindow();
		
		offset = getTimeZoneOffset(d);
		newdt.start = dt.start.minusSeconds(offset);
		newdt.end   = dt.end.minusSeconds(offset);
		
		SeatsCollect ssc = search(s, d, newst, newdt, legs, seatType);
		ssc.sortOnPrice(true);
		SeatsCollect newssc = translateToLocalTime(Saps.numberSearchResult > ssc.size()? ssc: ssc.subList(0, Saps.numberSearchResult));
		return newssc;
	}
	public SeatsCollect translateToLocalTime(Collection<? extends Seats> ssc){
		SeatsCollect newssc = new SeatsCollect();
		long offset;
		for(Seats ss: ssc){
			Seats newss = new Seats();
			for(Seat seat: ss){
				Seat newSeat = seat.copy();
				offset = getTimeZoneOffset(getAirport(seat.flight.CodeDepart));
				newSeat.flight.TimeDepart = seat.flight.TimeDepart.plusSeconds(offset);
				offset = getTimeZoneOffset(getAirport(seat.flight.CodeArrival));
				newSeat.flight.TimeArrival = seat.flight.TimeArrival.plusSeconds(offset);
				newss.add(newSeat);
			}
			newssc.add(newss);
		}
		return newssc;
	}
	/**/
	
	
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
	public SeatsCollect search(Airport s, Airport d, TimeWindow st, TimeWindow dt, int legs, SeatType seatType){
		SeatsCollect ans = new SeatsCollect();
		SeatsCollect depart;
		SeatsCollect arriv;
		arriv = searchDepartOrArriv(d, dt, seatType, false);
		for(Seats ss: arriv){
			if(ss.get(0).flight.CodeDepart.equals(s.Code) && st.inBetween(ss.get(0).flight.TimeDepart)){
				ans.add(ss);
			}
		}
		
		depart = searchDepartOrArriv(s, st, seatType, true);
		for(int i = 1; i < legs; i++){
			for(Seats ss: arriv){
				for(Seats ssd: depart){
					String code2 = ss.get(0).flight.CodeDepart;
					LocalDateTime time2 = ss.get(0).flight.TimeDepart;
					String code1 = ssd.get(ssd.size()-1).flight.CodeArrival;
					LocalDateTime time1 = ssd.get(ssd.size()-1).flight.TimeArrival;
					
					if(code1.equals(code2) 
							&& time2.isBefore(time1.plusMinutes(Saps.maxLayover))
							&& time2.isAfter(time1.plusMinutes(Saps.minLayover))){
						Seats seats = new Seats();
						seats.addAll(ssd);
						seats.addAll(ss);
						ans.add(seats);
					}
				}	
			}
			SeatsCollect t = new SeatsCollect();
			for(Seats ssd: depart){
				String code1 = ssd.get(ssd.size()-1).flight.CodeArrival;
				LocalDateTime time1 = ssd.get(ssd.size()-1).flight.TimeArrival;
				TimeWindow tw = new TimeWindow();
				tw.start = time1.plusMinutes(Saps.minLayover);
				tw.end = time1.plusMinutes(Saps.maxLayover);
				for(Seats ss: searchDepartOrArriv(getAirport(code1), tw, seatType, true)){
					boolean repeated = false;
					for(Seat seat: ssd){
						if(seat.flight.CodeDepart.equals(ss.get(0).flight.CodeArrival)){
							repeated = true;
							break;
						}
					}
					if(repeated){
						continue;
					}
					Seats seats = new Seats();
					seats.addAll(ssd);
					seats.add(ss.get(0));
					t.add(seats);
				}
			}
			depart = t;
		}
		return ans;
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
	
	public long getTimeZoneOffset(Airport s){
		if(s.timeZoneOffset < 0){
			long offset = (long)XMLParser.parseRawOffset(tca.getTimeOffset(Double.toString(s.Latitude),
					Double.toString(s.Longitude)));
			s.timeZoneOffset = offset;
		}
		return s.timeZoneOffset;
	}
	/*
	public SeatsCollect searchDepartLocal(Airport s, TimeWindow st, SeatType seatType){
		long offset = getTimeZoneOffset(s);
		TimeWindow newst = new TimeWindow();
		newst.start = st.start.minusSeconds(offset);
		newst.end   = st.end.minusSeconds(offset);
		SeatsCollect ssc = searchDepartOrArriv(s, newst, seatType, true);
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
	*/
	/*public SeatsCollect searchDepart(Airport s, TimeWindow st, SeatType seatType){
		SeatsCollect ans = new SeatsCollect();
		LocalDateTime start = st.start;
		LocalDateTime end   = st.end;
		LocalDateTime time = start;
		do{
			SeatsCollect ssc = searchOnDay(s, TimeWindow.getDate(time), seatType, true);
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
	}*/
	
	public SeatsCollect searchDepartOrArriv(Airport s, TimeWindow st, SeatType seatType,  boolean departing){
		SeatsCollect ans = new SeatsCollect();
		LocalDateTime start = st.start;
		LocalDateTime end   = st.end;
		LocalDateTime time = start;
		do{
			SeatsCollect ssc = searchOnDay(s.Code, TimeWindow.getDate(time), seatType, departing);
			for(Seats ss: ssc ){
				if(ss.size() == 0){
					throw new RuntimeException("Unexpected empty seats");
				}
				LocalDateTime t;
				if(departing){
					t = ss.get(0).flight.TimeDepart;
				}
				else{
					t = ss.get(0).flight.TimeArrival;
				}
				if(t.isBefore(end) &&
						t.isAfter(start)){
					ans.add(ss);
				}
			}
			time = time.plusDays(1);
		}while(time.getYear() <= st.end.getYear() 
			&& time.getDayOfYear() <= st.end.getDayOfYear());;
		return ans;
	}
	
	/*public SeatsCollect searchOnDay(String s, String day, SeatType seatType, boolean departing){
		SeatsCollect ans = new SeatsCollect();
		if(seatType == null){
			ans.addAll(searchOnDay(s, day, SeatType.Coach, departing));
			ans.addAll(searchOnDay(s, day, SeatType.FirstClass, departing));
			return ans;
		}
		
		String fs;
		if(departing){
			fs = dao.getFlightsDeparting(Saps.ticketAgency, s, day);
		}
		else{
			fs = dao.getFlightsArriving(Saps.ticketAgency, s, day);
		}
		
		Flights flights =XMLParser.parseFlights(fs);
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
	}*/
	
	public String getKey(String s, String day, boolean departing){
		return s+day+ (departing? "1":"0");
	}
	
	public SeatsCollect searchOnDay(String s, String day, SeatType seatType, boolean departing){
		SeatsCollect ans = new SeatsCollect();
		if(seatType != null){
			for(Seats seats : searchOnDay(s, day, null, departing)){
				if(seatType == seats.get(0).seatType){
					ans.add(seats);
				}
			}
			return ans;
		}
		String key = getKey(s, day, departing);
		if(this.cache != null && this.cache.containsKey(key)){
			if(Saps.searchReturnCopy){
				return this.cache.get(key).copy();
			}
			else{
				return this.cache.get(key);
			}
		}
		String fs;
		if(departing){
			fs = dao.getFlightsDeparting(Saps.ticketAgency, s, day);
		}
		else{
			fs = dao.getFlightsArriving(Saps.ticketAgency, s, day);
		}
		
		Flights flights = XMLParser.parseFlights(fs);
		Seat seat;
		Seats seats;
		for(Flight f : flights){
			if(f.SeatsCoach > 0){
				seat = new Seat();
				seats = new Seats();
				seat.flight = f;
				seat.seatType = SeatType.Coach;
				seats.add(seat);
				ans.add(seats);
			}
			if(f.SeatsFirstclass > 0){
				seat = new Seat();
				seats = new Seats();
				seat.flight = f;
				seat.seatType = SeatType.FirstClass;
				seats.add(seat);
				ans.add(seats);
			}
		}
		
		this.cache.put(key, ans);
		if(Saps.searchReturnCopy){
			return ans.copy();
		}
		else{
			return ans;
		}
	}
	
	public Airports getAirports(){
		if(airports == null){
			String xml = dao.getAirports(Saps.ticketAgency);
			airports = XMLParser.parseAirports(xml);
		}
		return airports;
	}
	
	public String reserveURL(Seats seats){
		StringBuilder sb = new StringBuilder();
		sb.append("<Flights>");
		for(Seat seat: seats){
			sb.append(String.format("<Flight number=\"%s\" seating=\"%s\"/>", seat.flight.Number, seat.seatType));	
		}
		sb.append("</Flights>");
		return sb.toString();
	}
	public void reserve(Seats seats){
		dao.reserve(Saps.ticketAgency, reserveURL(seats));
	}
	
	
}
