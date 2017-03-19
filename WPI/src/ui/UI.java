package ui;

import flight.Seats;
import flight.SeatsCollect;
import flight.SeatType;
import database.Search;

public class UI {
	public enum State {
		TOPLEVEL,
		SEARCH,
		SELECT,
		CONFIRM
	}
	
	public UI(){
		state = State.TOPLEVEL;
		DisplayMenu();
	}
	
	public void DisplayMenu(){
		
	}
	
	private State state;
	private SeatType seat;
	private SeatsCollect searchResult;
	private Seats Chosen;
}
