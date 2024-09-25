package DB;

import java.math.BigInteger;

public class NadraData {

  protected String IssueDate;
  protected String ExpiryDate;
  protected BigInteger CNIC;

  public NadraData() {
  }

  public NadraData(String issueDate, String expiryDate, BigInteger CNIC) {
    this.IssueDate = issueDate;
    this.ExpiryDate = expiryDate;
    this.CNIC = CNIC;
  }

  public NadraData(BigInteger CNIC) {
    this.CNIC = CNIC;
  }

  public void setIssueDate(String issueDate) {
    this.IssueDate = issueDate;
  }

  public String getIssueDate() {
    return IssueDate;
  }

  public void setExpiryDate(String expiryDate) {
    this.ExpiryDate = expiryDate;
  }

  public String getExpiryDate() {
    return ExpiryDate;
  }

  public void setCNIC(BigInteger CNIC) {
    this.CNIC = CNIC;
  }

  public BigInteger getCNIC() {
    return CNIC;
  }

  public void displayInfo() {
    System.out.println("Issue Date: " + IssueDate);
    System.out.println("Expiry Date: " + ExpiryDate);
    System.out.println("CNIC: " + CNIC);
  }
}
