package Model;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class Billing {
  protected int ID;
  protected Month BillingMonth;
  protected Double CurrentRegularReading;
  protected String EntryDate;
  protected Double CurrentPeakReading;
  protected Double SalesTax;
  protected Double FixedCharges;
  protected Double TotalAmount;
  protected String DueDate;
  protected String PaidStatus;
  protected String PaidDate;
  protected Double TaxRate;
  protected Double RegularUnitsPrice;
  protected Double PeakUnitsPrice;

  Billing() {
  }

  public Billing(int ID, Double currentRegularReading, Double currentPeakReading,
      Double salesTax, Double fixedCharges, Double totalAmount, Double RegularUnitsPrice, Double PeakUnitsPrice,
      Double TaxRate) {
    this.ID = ID;
    this.CurrentRegularReading = currentRegularReading;
    this.CurrentPeakReading = currentPeakReading;
    this.SalesTax = salesTax;
    this.FixedCharges = fixedCharges;
    this.TotalAmount = totalAmount;
    this.TaxRate = TaxRate;
    this.RegularUnitsPrice = RegularUnitsPrice;
    this.PeakUnitsPrice = PeakUnitsPrice;
    LocalDate currentDate = LocalDate.now();
    Month currentMonth = currentDate.getMonth();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate newDate = currentDate.plusDays(7);

    this.BillingMonth = currentMonth;
    this.EntryDate = currentDate.format(formatter);
    this.DueDate = newDate.format(formatter);
    this.PaidStatus = "Unpaid";
    this.PaidDate = null;
  }

  // Getters and Setters

  public void setTaxRate(Double units) {
    this.TaxRate = units;
  }

  public double getTaxRate() {
    return TaxRate;
  }

  public void setPeakUnitsPrice(Double units) {
    this.PeakUnitsPrice = units;
  }

  public double getPeakUnitsPrice() {
    return PeakUnitsPrice;
  }

  public void setRegularUnitsPrice(Double units) {
    this.RegularUnitsPrice = units;
  }

  public double getRegularUnitsPrice() {
    return RegularUnitsPrice;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }

  public Month getBillingMonth() {
    return BillingMonth;
  }

  public void setBillingMonth(Month billingMonth) {
    BillingMonth = billingMonth;
  }

  public Double getCurrentRegularReading() {
    return CurrentRegularReading;
  }

  public void setCurrentRegularReading(Double currentRegularReading) {
    CurrentRegularReading = currentRegularReading;
  }

  public String getEntryDate() {
    return EntryDate;
  }

  public void setEntryDate(String entryDate) {
    EntryDate = entryDate;
  }

  public Double getCurrentPeakReading() {
    return CurrentPeakReading;
  }

  public void setCurrentPeakReading(Double currentPeakReading) {
    CurrentPeakReading = currentPeakReading;
  }

  public Double getSalesTax() {
    return SalesTax;
  }

  public void setSalesTax(Double salesTax) {
    SalesTax = salesTax;
  }

  public Double getFixedCharges() {
    return FixedCharges;
  }

  public void setFixedCharges(Double fixedCharges) {
    FixedCharges = fixedCharges;
  }

  public Double getTotalAmount() {
    return TotalAmount;
  }

  public void setTotalAmount(Double totalAmount) {
    TotalAmount = totalAmount;
  }

  public String getDueDate() {
    return DueDate;
  }

  public void setDueDate(String dueDate) {
    DueDate = dueDate;
  }

  public String isPaidStatus() {
    return PaidStatus;
  }

  public void setPaidStatus(String paidStatus) {
    PaidStatus = paidStatus;
  }

  public String getPaidDate() {
    return PaidDate;
  }

  public void setPaidDate(String paidDate) {
    PaidDate = paidDate;
  }

  public void printBillDetails() {
    System.out.println("Bill Details:");
    System.out.println("ID: " + ID);
    System.out.println("Billing Month: " + (BillingMonth != null ? BillingMonth : "N/A"));
    System.out.println("Current Regular Reading: " + (CurrentRegularReading != null ? CurrentRegularReading : "N/A"));
    System.out.println("Entry Date: " + (EntryDate != null ? EntryDate : "N/A"));
    System.out.println("Current Peak Reading: " + (CurrentPeakReading != null ? CurrentPeakReading : "N/A"));

    System.out.println("Sales Tax: " + (SalesTax != null ? SalesTax : "N/A"));
    System.out.println("Fixed Charges: " + (FixedCharges != null ? FixedCharges : "N/A"));
    System.out.println("Total Amount: " + (TotalAmount != null ? TotalAmount : "N/A"));
    System.out.println("Due Date: " + (DueDate != null ? DueDate : "N/A"));
    System.out.println("Paid Status: " + (PaidStatus != null ? PaidStatus : "N/A"));
    System.out.println("Paid Date: " + (PaidDate != null ? PaidDate : "N/A"));
  }

}
