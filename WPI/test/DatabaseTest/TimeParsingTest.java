package DatabaseTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import timeWindow.TimeWindow;

public class TimeParsingTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		TimeWindow.parseDateXML("2017 May 13 00:23 GMT");
	}

}
