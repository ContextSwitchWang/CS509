package flight;

import java.time.LocalDateTime;
import java.lang.Number;

import airport.Airport;

public class Flight {
	public String Airplane;
	public String FlightTime;
	public String Number;
	public String CodeDepart;
	public LocalDateTime TimeDepart;
	public String CodeArrival;
	public LocalDateTime TimeArrival;
	public Number PriceFirstclass;
	public int SeatsFirstclass;
	public Number PriceCoach;
	public int SeatsCoach;
	public boolean isValid() {
		//TODO
		//check TimeArrival after TimeDepart
		return true;
	}
	public String toString(){
		return String.format("%-10s%-10s%-21s%-8s%-15s%-23s%-10s", Airplane, Number, TimeDepart, CodeDepart, FlightTime.toString() +  " min", TimeArrival, CodeArrival);
	}
	
	public static String Header(){
		return String.format("%-10s%-10s%-21s%-8s%-15s%-23s%-10s", "Airplane", "Number", "TimeDepart", "Depart", "FlightTime", "TimeArrival", "Arrivial");
	}
	
	public Flight copy(){
		Flight flight = new Flight();
		flight.Airplane = this.Airplane;
		flight.FlightTime = this.FlightTime;
		flight.Number = this.Number;
		flight.CodeDepart = this.CodeDepart;
		flight.TimeDepart = this.TimeDepart;
		flight.CodeArrival = this.CodeArrival;
		flight.TimeArrival = this.TimeArrival;
		flight.PriceFirstclass = this.PriceFirstclass;
		flight.SeatsFirstclass = this.SeatsFirstclass;
		flight.PriceCoach = this.PriceCoach;
		flight.SeatsCoach = this.SeatsCoach;
		return flight;
	}
}
