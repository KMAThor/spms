package nc.ukma.thor.spms.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static final String DEFAULT_DATE_FORMAT= "dd/MM/yyyy hh:mm a";

	public static Timestamp getTimeStamp(String dateTimeString) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Date parsedDate;
		try {
			parsedDate = dateFormat.parse(dateTimeString);
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			return timestamp;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Timestamp(0);
		}
	}

	public static String getStringRepresentation(Timestamp timestamp) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
		Date date = new Date(timestamp.getTime());
		return dateFormat.format(date);
	}
}
