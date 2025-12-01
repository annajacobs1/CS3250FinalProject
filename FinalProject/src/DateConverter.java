import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.util.StringConverter;

/**
 * Hold tools to convert Strings to dates and dates to strings in yyyy-MM-dd format
 */
public class DateConverter {
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	private static StringConverter<LocalDate> converter = new StringConverter<LocalDate>() {
		@Override
		public String toString(LocalDate date) {
			if(date != null) {
				return formatter.format(date);
			} else {
				return "";
			}
		}
		
		@Override
		public LocalDate fromString(String string) {
			if(string != null && !string.isEmpty()) {
				return LocalDate.parse(string, formatter);
			} else {
				return null;
			}
		}
	};
	
	public static StringConverter<LocalDate> getConverter() {
		return converter;
	}
}
