package Model;

import java.math.BigInteger;

public class Meter {
  protected BigInteger CNIC;
  protected int number;

  Meter() {
  }

  Meter(BigInteger CNIC) {
    this.CNIC = CNIC;
    number = 1;
  }

  Meter(String CNIC, int number) {
    BigInteger n = new BigInteger(CNIC);
    this.CNIC = n;
    this.number = number;
  }

  public BigInteger getCNIC() {
    return CNIC;
  }

  public void setCNIC(BigInteger CNIC) {
    this.CNIC = CNIC;
  }

  public int getNumber() {
    return number;
  }

  public boolean addMeter() {
    if (number < 3) {
      ++number;
      return true;
    }
    return false;
  }

  public void setNumber(int number) {
    this.number = number;
  }
}
