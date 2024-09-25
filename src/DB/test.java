package DB;

import java.time.LocalDate;
import java.time.Month;

public class test {
  LocalDate d = LocalDate.now();
  Month month = d.getMonth();

  public static void main(String[] args) {
    test t = new test();
    System.out.println(t.month);

  }

}
