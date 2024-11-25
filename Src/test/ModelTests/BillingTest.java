package test.ModelTests;

import Model.Billing;

import java.time.Month;

import org.junit.Test;
import static org.junit.Assert.*;

public class BillingTest {

  @Test
  public void testDefaultConstructor() {
    Billing billing = new Billing();
    assertNotNull(billing);
  }

  @Test
  public void testParameterizedConstructor() {
    Billing billing = new Billing(
        1,
        150.5,
        75.0,
        10.0,
        50.0,
        285.5,
        5.0,
        10.0,
        0.2);

    assertEquals(1, billing.getID());
    assertEquals(150.5, billing.getCurrentRegularReading(), 0.01);
    assertEquals(75.0, billing.getCurrentPeakReading(), 0.01);
    assertEquals(10.0, billing.getSalesTax(), 0.01);
    assertEquals(50.0, billing.getFixedCharges(), 0.01);
    assertEquals(285.5, billing.getTotalAmount(), 0.01);
    assertEquals(0.2, billing.getTaxRate(), 0.01);
    assertEquals(5.0, billing.getRegularUnitsPrice(), 0.01);
    assertEquals(10.0, billing.getPeakUnitsPrice(), 0.01);

    assertNotNull(billing.getEntryDate());
    assertNotNull(billing.getDueDate());
    assertEquals("Unpaid", billing.getPaidStatus());
    assertNull(billing.getPaidDate());
  }

  @Test
  public void testSetAndGetTaxRate() {
    Billing billing = new Billing();
    billing.setTaxRate(0.15);
    assertEquals(0.15, billing.getTaxRate(), 0.01);
  }

  @Test
  public void testSetAndGetPeakUnitsPrice() {
    Billing billing = new Billing();
    billing.setPeakUnitsPrice(20.0);
    assertEquals(20.0, billing.getPeakUnitsPrice(), 0.01);
  }

  @Test
  public void testSetAndGetRegularUnitsPrice() {
    Billing billing = new Billing();
    billing.setRegularUnitsPrice(15.0);
    assertEquals(15.0, billing.getRegularUnitsPrice(), 0.01);
  }

  @Test
  public void testSetAndGetPaidStatus() {
    Billing billing = new Billing();
    billing.setPaidStatus("Paid");
    assertEquals("Paid", billing.getPaidStatus());
  }

  @Test
  public void testSetAndGetPaidDate() {
    Billing billing = new Billing();
    String paidDate = "25/11/2024";
    billing.setPaidDate(paidDate);
    assertEquals(paidDate, billing.getPaidDate());
  }

  @Test
  public void testPrintBillDetails() {
    Billing billing = new Billing(
        1,
        100.0,
        50.0,
        10.0,
        20.0,
        180.0,
        2.0,
        5.0,
        0.1);

    billing.printBillDetails();
    assertEquals(1, billing.getID());
    assertNotNull(billing.getBillingMonth()); // Ensure billing month is set.
    assertEquals("Unpaid", billing.getPaidStatus());
    assertNotNull(billing.getDueDate());
  }

  @Test
  public void testSetAndGetBillingMonth() {
    Billing billing = new Billing();
    billing.setBillingMonth(Month.JANUARY);
    assertEquals(Month.JANUARY, billing.getBillingMonth());
  }
}
