package flight;

public class Seat {
	public SeatType seatType;
	public Flight flight;
	public String toString(){
		return String.format("%s%-15s%-10s", flight, seatType, 
				seatType == SeatType.FirstClass? flight.PriceFirstclass: flight.PriceCoach);
	}
	
	public static String Header(){
		return String.format("%s%-15s%-10s", Flight.Header(), "Seat Type", "Price");
	}
}
