package database;
import java.time.Instant;
import java.time.OffsetDateTime;


public interface TimeConversionAPI {
	/*
	 * translate a time with zone info to time at the specified latitude and longitude
	 * */
	public OffsetDateTime convert(OffsetDateTime t, int Latitude, int longitude);
	/*
	 * translate a time at the specified latitude and longitude to gmt
	 * */
	public Instant convert(Instant t, int Latitude, int longitude);
}
