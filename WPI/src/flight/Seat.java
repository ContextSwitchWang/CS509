package flight;
import util.Util;

public class Seat {
	public SeatType seatType;
	public Flight flight;
	public String toString(){
		return String.format("%s%-15s%-10s", flight, seatType,
				Util.printUSDollar(seatType == SeatType.FirstClass? flight.PriceFirstclass: flight.PriceCoach));
	}
	
	public static String Header(){
		return String.format("%s%-15s%-10s", Flight.Header(), "Seat Type", "Price");
	}
	
	public Double getPrice(){
		if(seatType == SeatType.Coach){
			return flight.PriceCoach.doubleValue();
		}
		else{
			return flight.PriceFirstclass.doubleValue();
		}
	}
	public Seat copy(){
		Seat seat = new Seat();
		seat.seatType = this.seatType;
		seat.flight = this.flight.copy();
		return seat;
	}
}
