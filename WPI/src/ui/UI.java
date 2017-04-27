package ui;

import flight.Seats;
import flight.SeatsCollect;
import timeWindow.TimeWindow;
import flight.SeatType;

import java.util.Scanner;

import airport.Airport;
import airport.Airports;
import conf.Saps;
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
		arrivTime = new TimeWindow();
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
					if(departTime.start != null && departTime.end == null){
						departTime.end = departTime.start.plusDays(1);
					}
					break;
				case "b":
					departTime.end = inputTime();
					if(departTime.end != null && departTime.start == null){
						departTime.start = departTime.end.minusDays(1);
					}
					break;
				case "e":
					arrivTime.start = inputTime();
					break;
				case "f":
					arrivTime.end = inputTime();
					break;
				case "c":
					departAirport = selectAirport();
					break;
				case "g":
					arrivAirport = selectAirport();
					break;
				case "d":
					InputSeatType();
					break;
				case "s":
					Search(false);
					break;
				case "r":
					Search(true);
					break;
				case "o":
					confirm();
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

	private void Search(boolean searchReturn){
		SeatsCollect ans;
		if(searchReturn){
			if(arrivTime.start == null || arrivTime.end == null || arrivAirport == null|| departAirport == null){
				System.out.println("Please input the ealiest return time and airports at least");
				return;
			}
			ans = search.searchLocal(arrivAirport, departAirport, arrivTime, arrivTime.relaxDays(), Saps.legs, seatType);
			returnSearchResult = ans;
		}
		else{
			if(departTime.start == null || departTime.end == null || departAirport == null || arrivAirport == null){
				System.out.println("Please input the ealiest depart time and airports at least");
				return;
			}
			ans = search.searchLocal(departAirport, arrivAirport, departTime, departTime.relaxDays(), Saps.legs, seatType);
			searchResult = ans;
		}
		if(ans.isEmpty()){
			System.out.println("Nothing matches your search");
			return;
		}
		System.out.println(ans);
		while(true){
			System.out.println("Please Input p to sort on price, d to sort on departure time, s to search again, c to cancel, e to deselect or an integer to make a selection");
			System.out.print(">>>");
			String input = stdin.nextLine();
			switch(input){
				case "p": ans.sortOnPrice(true); System.out.println(ans); break;
				case "d": ans.sortOnDpartureTime(true); System.out.println(ans); break;
				case "s": System.out.println(ans); break;
				case "c": return;
				case "e":
					if(searchReturn){
						returnSelection = null;
					}
					else{
						selection = null;
					}
					return;
				default:
					int sel;
					try{
						sel = Integer.parseInt(input);
					}
					catch(NumberFormatException e){
						continue;
					}
					if(sel <= 0 || sel > ans.size()){
						System.out.println("Number out of range");
						break;
					}
					if(searchReturn){
						returnSelection = ans.get(sel-1);
					}
					else{
						selection = ans.get(sel-1);
					}
					System.out.println("Selection is saved");
					return;
			}
		}
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
	
	Airport selectAirport(){
		while(true){
			System.out.println("Please input Airport Code:");
			System.out.print(">>>");
			String code = stdin.nextLine();
			Airport a = search.getAirport(code);
			if(a != null){
				System.out.println("Your Airport is saved.");
				return a;
			}
			System.out.println("Your Code is not recognized.");
			return a;
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
				"Return Earliest Time",
				arrivTime.getStartDateUI(),
				"(e)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Return Latest Time",
				arrivTime.getEndDateUI(),
				"(f)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Departure Airport",
				printAirport(departAirport),
				"(c)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Arrival Airport",
				printAirport(arrivAirport),
				"(g)"));
		System.out.println(String.format("%-40s%-40s%s", 
				"Seat Type",
				printSeat(),
				"(d)"));
		System.out.println(String.format("%-80s%s%s", 
				"Search Flight",
				"(s)",
				printSelection(selection)
				));
		System.out.println(String.format("%-80s%s%s", 
				"Search Return Flight",
				"(r)",
				printSelection(returnSelection)
				));
		System.out.println(String.format("%-40s%-40s%s", 
				"Confirm and Reserve",
				"None",
				"(o)"));
		
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
	
	private void confirm() {
		System.out.println("Do you confirm your current selections? y or n");
		System.out.print(">>>");
		String input = stdin.nextLine();
		switch(input){
			case "y": break;
			default: return;
		}
		if(selection != null){
			try {
				search.reserve(selection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Fatal Error, Reservation failed");
				e.printStackTrace();
			}
		}
		if(returnSelection != null){
			try {
				search.reserve(returnSelection);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				System.out.println("Fatal Error, Reservation failed");
				e.printStackTrace();
			}
		}
		System.out.println("Reservation successfull");
		return;
	}

	String printSelection(Seats seats){
		if(seats == null){
			return "None";
		}
		return "\n"+seats.toString();
	}
	String printAirport(Airport airport){
		if(airport == null){
			return "None";
		}
		else{
			return airport.name;
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
	private TimeWindow departTime, arrivTime;
	private Airport departAirport, arrivAirport;
	private State state;
	private SeatType seatType;
	private SeatsCollect searchResult, returnSearchResult;
	private Seats selection, returnSelection;
	private Seats Chosen;
}
