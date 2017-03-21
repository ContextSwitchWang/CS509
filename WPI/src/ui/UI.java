package ui;

import flight.Seats;
import flight.SeatsCollect;
import timeWindow.TimeWindow;
import flight.SeatType;

import java.util.Scanner;

import airport.Airport;
import database.Search;
import java.lang.UnsupportedOperationException;

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
					InputDepartTime();
					break;
				case "b":
					throw e;
				case "c":
					InputSeatType();
					break;
				case "s":
					throw e;
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
	
	private void InputSeatType() {
		while(true){
			System.out.println("Please Input Seat Type, c for coach, f for first class:");
			System.out.print(">>>");
			String c = stdin.nextLine();
			switch(c){
				case "c": seatType = SeatType.Coach; break;
				case "f": seatType = SeatType.FirstClass; break;
				default:
					System.out.println("Your input cannot be recognized.");
					continue;
			}
			System.out.println("Your seat type is saved");
			return;
		}
		
	}

	private void InputDepartTime() {
		while(true){
			System.out.println("Please input departure time in yyyy-MM-dd-HH:mm format:");
			System.out.print(">>>");
			String time = stdin.nextLine();
			try{
				departTime.start = TimeWindow.parseDate(time);
			}
			catch(java.time.format.DateTimeParseException e){
				System.out.println("Your input cannot be parsed. The error is: "
						+ "\n" + e.getMessage() + '.');
				continue;
			}
			System.out.println("Your departure time is saved");
			return;
		}
		
	}

	public void DisplayMenu(){
		System.out.println(String.format("%-40s%-40s%s", 
				"Action",
				"Current Value",
				"Command"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Departure Time",
				departTime,
				"(a)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Departure Airport",
				printDepartAirport(),
				"(b)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Seat Type",
				printSeat(),
				"(c)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Search Flight",
				"None",
				"(s)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Display Menu",
				"",
				"(m)"));
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
			return departAirport.toString();
		}
	}
	
	String printSeat(){
		if(seatType == null){
			return "None";
		}
		else{
			return seatType.toString();
		}
	}
	
	Scanner stdin;
	private TimeWindow departTime;
	private Airport departAirport;
	private State state;
	private SeatType seatType;
	private SeatsCollect searchResult;
	private Seats Chosen;
}
