package test.ViewTests.CustomerTests.DashboardTests.ViewBillTests;

import Model.Bills;
import Model.Customers;
import Views.Customer.Dashboard.ViewBill.ViewBill;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.math.BigInteger;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

import Model.Bills;
import Model.Customers;
import Views.Customer.Dashboard.ViewBill.BillByMeter;
import java.awt.BorderLayout;

public class ViewBillTest {

  private Customers mockCustomers;
  private Bills mockBills;
  private ViewBill viewBill;

  @Before
  public void setUp() {
    mockCustomers = mock(Customers.class);
    mockBills = mock(Bills.class);
    viewBill = new ViewBill(mockCustomers, mockBills, new BigInteger("3520105043723"), 1);
  }

  @Test
  public void testConstructorInitializesMainPanel() {
    assertNotNull(viewBill.MainPanel);
    assertTrue(viewBill.MainPanel.getLayout() instanceof BorderLayout);
  }

  @Test
  public void testSwitchToBillByIDPanel() {
    viewBill.ViewBillByID.doClick();
    JPanel currentPanel = (JPanel) viewBill.cardPanel.getComponents()[0];
    assertNotNull(currentPanel);
  }

  @Test
  public void testSwitchToBillByMeterPanel() {
    viewBill.ViewBillByMeter.doClick();
    JPanel currentPanel = (JPanel) viewBill.cardPanel.getComponents()[0];
    assertNotNull(currentPanel);
  }
}
