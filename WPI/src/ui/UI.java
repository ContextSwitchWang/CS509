package ui;

import flight.Seats;
import flight.SeatsCollect;
import timeWindow.TimeWindow;
import flight.SeatType;

import java.util.Scanner;

import airport.Airport;
import airport.Airports;
import database.DAC;
import database.Search;
import database.TimeConversion;

import java.lang.UnsupportedOperationException;
import java.time.LocalDateTime;

public class UI {
	private static final UnsupportedOperationException e = null;
	public enum State {
		TOPLEVEL,
		SEARCH,
		SELECT,
		CONFIRM
	}
	
	public UI(){
		departTime = new TimeWindow();
	}
	
	public void start(){
		state = State.TOPLEVEL;
		System.out.println("  _______                              ______ _ _       _     _     _____                                _   _             \n" + 
				" |__   __|                     /\\     |  ____| (_)     | |   | |   |  __ \\                              | | (_)            \n" + 
				"    | | ___  __ _ _ __ ___    /  \\    | |__  | |_  __ _| |__ | |_  | |__) |___  ___  ___ _ ____   ____ _| |_ _  ___  _ __  \n" + 
				"    | |/ _ \\/ _` | '_ ` _ \\  / /\\ \\   |  __| | | |/ _` | '_ \\| __| |  _  // _ \\/ __|/ _ \\ '__\\ \\ / / _` | __| |/ _ \\| '_ \\ \n" + 
				"    | |  __/ (_| | | | | | |/ ____ \\  | |    | | | (_| | | | | |_  | | \\ \\  __/\\__ \\  __/ |   \\ V / (_| | |_| | (_) | | | |\n" + 
				"    |_|\\___|\\__,_|_| |_| |_/_/    \\_\\ |_|    |_|_|\\__, |_| |_|\\__| |_|  \\_\\___||___/\\___|_|    \\_/ \\__,_|\\__|_|\\___/|_| |_|\n" + 
				"                                                   __/ |                                                                   \n" + 
				"                                                  |___/                                                                    ");
		

		System.out.println("\nWelcome!\n");
		System.out.println("Please input the command for desired action, or m to display menu again");
		DisplayMenu();
		stdin= new Scanner(System.in);
		while(true){
			System.out.print(">>>");
			switch(stdin.nextLine()){
				case "a":
					departTime.start = inputTime();
					if(departTime.end == null){
						departTime.end = departTime.start.plusDays(1);
					}
					break;
				case "b":
					departTime.end = inputTime();
					if(departTime.start == null){
						departTime.start = departTime.end.minusDays(1);
					}
					break;
				case "c":
					selectAirport();
					break;
				case "d":
					InputSeatType();
					break;
				case "s":
					Search();
					break;
				case "l":
					listAirports();
					break;
				case "m":
					DisplayMenu();
					break;
				case "q":
					System.out.println("Quiting from System");
					return;
				default:
					System.out.println("Unrecognized Command");	
			}
		}
	}
	private void Search(){
		if(departTime.start == null || departTime.end == null || departAirport == null){
			System.out.println("Please input the ealiest depart time and airport at least");
			return;
		}
		System.out.println(search.searchDepartLocal(departAirport, departTime, seatType));
	}
	
	private void InputSeatType() {
		while(true){
			System.out.println("Please Input Seat Type, c for coach, f for first class or b for both:");
			System.out.print(">>>");
			String c = stdin.nextLine();
			switch(c){
				case "c": seatType = SeatType.Coach; break;
				case "f": seatType = SeatType.FirstClass; break;
				case "b": seatType = null;break;
				default:
					System.out.println("Your input cannot be recognized.");
					return;
			}
			System.out.println("Your seat type is saved");
			return;
		}
		
	}
	
	void selectAirport(){
		while(true){
			System.out.println("Please input Airport Code:");
			System.out.print(">>>");
			String code = stdin.nextLine();
			Airport a = search.getAirport(code);
			if(a != null){
				departAirport = a;
				System.out.println("Your Airport is saved.");
				return;
			}
			System.out.println("Your Code is not recognized.");
			return;
		}
	}
	
	private LocalDateTime inputTime() {
		while(true){
			System.out.println("Please input your time in yyyy-MM-dd-HH:mm format:");
			System.out.print(">>>");
			LocalDateTime t;
			String time = stdin.nextLine();
			try{
				t = TimeWindow.parseDateUI(time);
			}
			catch(java.time.format.DateTimeParseException e){
				System.out.println("Your input cannot be parsed. The error is: "
						+ "\n" + e.getMessage() + '.');
				return null;
			}
			System.out.println("Your time is saved");
			return t;
		}
		
	}
	
	public void DisplayMenu(){
		System.out.println(String.format("%-40s%-40s%s", 
				"Action",
				"Current Value",
				"Command"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Departure Earliest Time",
				departTime.getStartDateUI(),
				"(a)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Departure Latest Time",
				departTime.getEndDateUI(),
				"(b)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Departure Airport",
				printDepartAirport(),
				"(c)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Seat Type",
				printSeat(),
				"(d)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Search Flight",
				"None",
				"(s)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Display Menu",
				"",
				"(m)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"List Airports",
				"",
				"(l)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Quit",
				"",
				"(q)"));	
	}
	
	String printDepartAirport(){
		if(departAirport == null){
			return "None";
		}
		else{
			return departAirport.name;
		}
	}
	
	
	
	void listAirports(){
		System.out.println(search.getAirports());
	}
	
	String printSeat(){
		if(seatType == null){
			return "Both";
		}
		else{
			return seatType.toString();
		}
	}
	
	Scanner stdin;
	Search search = new Search(new DAC(), new TimeConversion());
	private TimeWindow departTime;
	private Airport departAirport;
	private State state;
	private SeatType seatType;
	private SeatsCollect searchResult;
	private Seats Chosen;
}
