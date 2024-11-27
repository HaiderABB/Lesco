package Model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Customer {
  protected int ID;
  protected BigInteger CNIC;
  protected String name;
  protected String Address;
  protected String PhoneNumber;
  protected String CustomerType;
  protected String MeterType;
  protected String ConnectionDate;
  protected Double RegularUnits;
  protected Double PeakUnits;

  public Customer() {
  }

  public Customer(BigInteger CNIC, String name, String Address, String PhoneNumber, String CustomerType,
      String MeterType, int current) {

    this.CNIC = CNIC;
    this.name = name;
    this.Address = Address;
    this.PhoneNumber = PhoneNumber;
    this.CustomerType = CustomerType;
    this.MeterType = MeterType;
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YY");
    this.ConnectionDate = currentDate.format(formatter);

    this.RegularUnits = 0.0;
    this.PeakUnits = 0.0;
    this.ID = current;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public BigInteger getCNIC() {
    return CNIC;
  }

  public void setCNIC(BigInteger CNIC) {
    this.CNIC = CNIC;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return Address;
  }

  public void setAddress(String Address) {
    this.Address = Address;
  }

  public String getPhoneNumber() {
    return PhoneNumber;
  }

  public void setPhoneNumber(String PhoneNumber) {
    this.PhoneNumber = PhoneNumber;
  }

  public String getCustomerType() {
    return CustomerType;
  }

  public void setCustomerType(String CustomerType) {
    this.CustomerType = CustomerType;
  }

  public String getMeterType() {
    return MeterType;
  }

  public void setMeterType(String MeterType) {
    this.MeterType = MeterType;
  }

  public String getConnectionDate() {
    return ConnectionDate;
  }

  public void setConnectionDate(String ConnectionDate) {
    this.ConnectionDate = ConnectionDate;
  }

  public Double getRegularUnits() {
    return RegularUnits;
  }

  public void setRegularUnits(Double RegularUnits) {
    this.RegularUnits = RegularUnits;
  }

  public Double getPeakUnits() {
    return PeakUnits;
  }

  public void setPeakUnits(Double PeakUnits) {
    this.PeakUnits = PeakUnits;
  }
}
