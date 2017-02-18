package ui;

import flight.Flights;
import flight.FlightsCollect;

public class UI {
	public enum Stage {
		DISABLE,
		SEARCH,
		SELECT,
		CONFIRM
	}

	private Stage stage;
	private FlightsCollect searchResult;
	private Flights Chosen;
}
