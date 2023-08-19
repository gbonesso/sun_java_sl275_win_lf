package myutilities;

import java.util.*;
import java.text.*;

public final class DateConverter {
  private static String day_Of_The_Week[] =
      {"Sunday", "Monday", "Tuesday", "Wednesday",
        "Thursday", "Friday", "Saturday"};

  public static String getDayOfWeek (String theDate) {
    Date d = null;
    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

    try {
      d = sdf.parse (theDate);
    } catch (ParseException e) {
      System.out.println (e);
      e.printStackTrace();
    }

    // Create a GregorianCalendar object
    Calendar c =
        new GregorianCalendar(
            TimeZone.getTimeZone("EST"),Locale.US);
    c.setTime (d);

    return(
        day_Of_The_Week[(c.get(Calendar.DAY_OF_WEEK)-1)]);
  }
}
