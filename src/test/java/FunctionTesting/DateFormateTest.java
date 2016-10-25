package FunctionTesting;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.junit.Test;

public class DateFormateTest {

	@Test
	public void testDateFormate() throws ParseException{
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd,yyyy hh:mm", Locale.ENGLISH);
		Date date = dateFormat.parse("July 23,2016 15:1");
		
		System.out.println( date.toString() );
	}
	
}
