package ui;

import flight.Flights;
import flight.FlightsCollect;
import flight.SeatType;

public class UI {
	public enum Stage {
		DISABLE,
		SEARCH,
		SELECT,
		CONFIRM
	}

	private Stage stage;
	private SeatType seat;
	private FlightsCollect searchResult;
	private Flights Chosen;
}
