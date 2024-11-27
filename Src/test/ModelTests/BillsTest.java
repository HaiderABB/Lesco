package test.ModelTests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import Model.Billing;
import Model.Bills;

public class BillsTest {

  private Bills bills;

  @Before
  public void setUp() {
    bills = new Bills();
  }

  @Test
  public void testReadTaxes() {
    assertFalse("Taxes list should not be empty after reading from file.", bills.Taxes.isEmpty());
  }

  @Test
  public void testAddSinglePhaseDomesticBill() {
    int id = 1;
    double regularReading = 150.0;
    bills.SinglePhaseDomesticBill(id, regularReading);
    assertFalse("CustomerBills list should not be empty after adding a bill.", bills.CustomerBills.isEmpty());
    Billing addedBill = bills.CustomerBills.get(bills.CustomerBills.size() - 1);
    assertEquals("ID should match.", id, addedBill.getID());
    assertEquals("Regular reading should match.", regularReading, addedBill.getCurrentRegularReading(), 0.001);
  }

  @Test
  public void testUpdateStatus() {
    int id = 2;
    double regularReading = 200.0;
    bills.SinglePhaseDomesticBill(id, regularReading);
    boolean updated = bills.UpdateStatus(id);
    assertTrue("Bill status should be updated successfully.", updated);
    Billing updatedBill = bills.CustomerBills.get(bills.CustomerBills.size() - 1);
    assertEquals("Bill status should be 'Paid'.", "Paid", updatedBill.isPaidStatus());
  }

  @Test
  public void testCheckBill() {
    int id = 3;
    double regularReading = 100.0;
    bills.SinglePhaseDomesticBill(id, regularReading);
    boolean canAddAnotherBill = bills.CheckBill(id);
    assertFalse("CheckBill should return false for a duplicate ID in the same month.", canAddAnotherBill);
  }

  @Test
  public void testGetUnpaidAmount() {
    int id1 = 4;
    int id2 = 5;
    double regularReading1 = 300.0;
    double regularReading2 = 400.0;
    bills.SinglePhaseDomesticBill(id1, regularReading1);
    bills.SinglePhaseDomesticBill(id2, regularReading2);
    double unpaidAmount = bills.getUnpaidAmount();
    double expectedAmount = 0.0;
    for (Billing bill : bills.CustomerBills) {
      if (bill.isPaidStatus().equals("Unpaid")) {
        expectedAmount += bill.getTotalAmount();
      }
    }
    assertEquals("Unpaid amount should match the expected calculation.", expectedAmount, unpaidAmount, 0.001);
  }

  @Test
  public void testExpectedBillSingleDomestic() {
    double meterReading = 250.0;
    Object[] row = new Object[6];
    bills.ExpectedBillSingleDomestic(meterReading, row);
    assertNotNull("Row should be populated.", row[0]);
    assertNotNull("Row should be populated.", row[5]);
    double expectedTotal = (double) row[5];
    assertTrue("Expected total should be greater than 0.", expectedTotal > 0);
  }
}
