package flight;

import java.time.LocalDateTime;

import airport.Airport;

public class Flight {
	public String Airplane;
	public String FlightTime;
	public String Number;
	public String CodeDepart;
	//TODO: in string format, create field in time format
	public LocalDateTime TimeDepart;
	public String CodeArrival;
	//TODO: in string format, create field in time format
	public LocalDateTime TimeArrival;
	public String PriceFirstclass;
	public int SeatsFirstclass;
	public String PriceCoach;
	public int SeatsCoach;
	public boolean isValid() {
		//TODO
		//check TimeArrival after TimeDepart
		return true;
	}
	public String toString(){
		//TODO print all fields nicely
		return String.format("%-10s%-10s%-21s%-8s%-15s%-23s%-10s", Airplane, Number, TimeDepart, CodeDepart, FlightTime.toString() +  " min", TimeArrival, CodeArrival);
	}
	
	public static String Header(){
		return String.format("%-10s%-10s%-21s%-8s%-15s%-23s%-10s", "Airplane", "Number", "TimeDepart", "Depart", "FlightTime", "TimeArrival", "Arrivial");
	}
}
