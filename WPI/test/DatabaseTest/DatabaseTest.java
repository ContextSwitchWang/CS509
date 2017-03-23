package DatabaseTest;

import static org.junit.Assert.*;
import database.DAC;
import database.TimeConversion;

import org.junit.Before;
import org.junit.Test;

public class DatabaseTest {

	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void test() {
		DAC dac = new DAC();
		//dac.getAirports("aaa");
		//dac.getFlightsDeparting("BOS", "2017_05_10");
		//dac.getAirplanes("aaa");
		//dac.getTimeOffset("39.6034810", "-119.6822510");
	}
	
	@Test
	public void timeConvertionTest(){
		TimeConversion tca = new TimeConversion();
		System.out.println(tca.getTimeOffset("42", "-71"));
	}

}
