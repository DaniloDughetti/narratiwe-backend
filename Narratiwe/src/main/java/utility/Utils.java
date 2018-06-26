package utility;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;

import utility.ModelConstants.SystemConstants;

public class Utils {

	public static Utils instance;
	private DateFormat dateFormat;

	private Utils(int dateType) {
		if(dateType == SystemConstants.DATE_HOUR)
			dateFormat = new SimpleDateFormat("dd/MM/yyyy, HH:mm", Locale.getDefault());
		else
			dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
	}

	public static Utils getInstance(int dateType) {
		if (instance != null) return instance;
		else {
			instance = new Utils(dateType);
			return instance;
		}
	}

	public String formatDate(Date date){
		String dateFormatted = dateFormat.format(date);
		dateFormatted = dateFormatted.substring(0, 1).toUpperCase() + dateFormatted.substring(1);
		return dateFormatted;
	}
	
	public Date parseDate(String text) throws ParseException {
		return dateFormat.parse(text);
	}
	
	public Locale parseLocale(String locale) {
		  String[] parts = locale.split("_");
		  switch (parts.length) {
		    case 3: return new Locale(parts[0], parts[1], parts[2]);
		    case 2: return new Locale(parts[0], parts[1]);
		    case 1: return new Locale(parts[0]);
		    default: throw new IllegalArgumentException("Invalid locale: " + locale);
		  }
		}

	public boolean isValid(Locale locale) {
		  try {
		    return locale.getISO3Language() != null && locale.getISO3Country() != null;
		  } catch (MissingResourceException e) {
		    return false;
		  }
		}
	
}
