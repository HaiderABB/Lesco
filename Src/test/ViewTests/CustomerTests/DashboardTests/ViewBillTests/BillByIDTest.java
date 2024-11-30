package test.ViewTests.CustomerTests.DashboardTests.ViewBillTests;

import Model.Bills;
import Model.Customers;
import Views.Customer.Dashboard.ViewBill.BillByID;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.BorderLayout;
import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class BillByIDTest {

  private Customers mockCustomers;
  private Bills mockBills;
  private BigInteger testCNIC;
  private BillByID billByID;

  @Before
  public void setUp() {
    mockCustomers = mock(Customers.class);
    mockBills = mock(Bills.class);
    testCNIC = new BigInteger("3520105043723");
    billByID = new BillByID(mockCustomers, testCNIC, mockBills);
  }

  @Test
  public void testConstructorInitializesMainPanel() {
    assertNotNull(billByID.mainPanel);
    assertTrue(billByID.mainPanel.getLayout() instanceof BorderLayout);
  }

  @Test
  public void testValidateAndSubmitWithValidUserID() {
    when(mockCustomers.checkIDAgainstCNIC(1, testCNIC)).thenReturn(true);
    billByID.userIDField.setText("1");
    billByID.validateAndSubmit();

    assertNotNull(billByID.CustomerPanel);
    assertNotNull(billByID.BillsPanel);
    verify(mockCustomers, times(1)).checkIDAgainstCNIC(1, testCNIC);
  }

  @Test
  public void testValidateAndSubmitWithInvalidUserID() {
    when(mockCustomers.checkIDAgainstCNIC(1, testCNIC)).thenReturn(false);
    billByID.userIDField.setText("1");
    billByID.validateAndSubmit();

    assertEquals("Error: User ID does not exist against this CNIC.", billByID.messageLabel.getText());
  }

  @Test
  public void testLoadCustomerDataPopulatesTable() {
    DefaultTableModel model = new DefaultTableModel(billByID.CustomerColumnNames, 0);
    Object[] customerData = { 1, testCNIC, "John Doe", "123 Main St", "1234567890", "Domestic", "Single Phase" };
    doAnswer(invocation -> {
      Object[] row = invocation.getArgument(2);
      System.arraycopy(customerData, 0, row, 0, customerData.length);
      return null;
    }).when(mockCustomers).printCustomer(eq(1), eq(testCNIC), any(Object[].class));

    billByID.userID = 1;
    billByID.loadCustomerData(model);

    assertEquals(1, model.getRowCount());
    assertArrayEquals(customerData, model.getDataVector().get(0).toArray());
  }
}
