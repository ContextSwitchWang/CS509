package database;
import java.time.OffsetDateTime;


public interface TimeConversionAPI {
	public OffsetDateTime convert(OffsetDateTime t, int Latitude, int longitude);
}
