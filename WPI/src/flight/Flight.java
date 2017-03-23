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
		return String.format("%-10s%-10s%-25s%-15s%-25s", Airplane, Number, TimeDepart, FlightTime.toString() +  " min", TimeArrival);
	}
	
	public static String Header(){
		return String.format("%-10s%-10s%-25s%-15s%-25s", "Airplane", "Number", "TimeDepart", "FlightTime", "TimeArrival");
	}
}
