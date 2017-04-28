package database;
import java.time.Instant;
import java.time.OffsetDateTime;


public interface TimeConversionAPI {
	
	/**
	 * @param latitude latitude of airport
	 * @param longitude longitude of airport
	 * @return return xml as specified by google map api
	 */
	public String getTimeOffset(String latitude, String longitude);
}
