package test.ViewTests.CustomerTests.DashboardTests.ViewBillTests;

import Model.Bills;
import Model.Customers;
import Views.Customer.Dashboard.ViewBill.BillByMeter;
import java.awt.BorderLayout;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BillByMeterTest {

  private Bills mockBills;
  private Customers mockCustomers;
  private BillByMeter billByMeter;

  @Before
  public void setUp() {
    mockBills = mock(Bills.class);
    mockCustomers = mock(Customers.class);
    billByMeter = new BillByMeter(mockBills, mockCustomers, 1, new BigInteger("3520105043723"));
  }

  @Test
  public void testConstructorInitializesMainPanel() {
    assertNotNull(billByMeter.mainPanel);
    assertTrue(billByMeter.mainPanel.getLayout() instanceof BorderLayout);
  }

  @Test
  public void testValidateAndSubmitWithInvalidRegularReading() {
    billByMeter.regularReadingField.setText("-100");
    billByMeter.validateAndSubmit();

    assertEquals("Error: Regular reading must be greater than zero.", billByMeter.messageLabel.getText());
  }

}
