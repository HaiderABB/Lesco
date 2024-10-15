package Views;

import java.awt.Color;
import java.util.regex.Pattern;

public abstract class DashboardSuper {

  public Color MPcolor = new Color(0, 71, 171, 255);
  public Color EPcolor = new Color(211, 211, 211, 255);
  public static String CNICregex = "^[0-9]{13}$";
  public static String MeterType1 = "Single Phase";
  public static String MeterType2 = "Three Phase";
  public static String CustomerType1 = "Commercial";
  public static String CustomerType2 = "Domestic";

  public static Pattern AddressRegex = Pattern.compile("^[A-Za-z0-9 .-]+$");
  public static Pattern NameRegex = Pattern.compile("^[A-Za-z]+([ '-][A-Za-z]+)*$");
  public static Pattern PhoneRegex = Pattern.compile("^\\d{11}$");

}
