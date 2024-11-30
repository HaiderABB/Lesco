package test.ViewTests.EmployeeTests.DashboardTests;

import Model.Bills;
import Model.Customers;
import Views.Employee.Dashboard.ManageBills.ManageBills;
import Views.Employee.Dashboard.ManageBills.AddBillPanel;
import Views.Employee.Dashboard.ManageBills.ViewBillsPanel;
import Views.Employee.Dashboard.ManageBills.PrintReportPanel;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ManageBillsTest {

  private ManageBills manageBills;
  private Customers mockCustomerData;
  private Bills mockBillingData;

  @Before
  public void setUp() {
    mockCustomerData = mock(Customers.class);
    mockBillingData = mock(Bills.class);
    manageBills = new ManageBills(mockCustomerData, mockBillingData);
  }

  @Test
  public void testComponentsInitialized() {
    assertNotNull(manageBills.MainPanel);
    assertNotNull(manageBills.AddBillButton);
    assertNotNull(manageBills.ViewBillsButton);
    assertNotNull(manageBills.PrintReportButton);
  }

  @Test
  public void testAddBillButtonAction() {
    manageBills.AddBillButton.doClick();
    JPanel cardPanel = (JPanel) manageBills.MainPanel.getComponent(1);
    assertTrue(cardPanel.getComponent(0) instanceof AddBillPanel);
  }

  @Test
  public void testPrintReportButtonAction() {
    manageBills.PrintReportButton.doClick();
    JPanel cardPanel = (JPanel) manageBills.MainPanel.getComponent(1);
    assertTrue(cardPanel.getComponent(0) instanceof PrintReportPanel);
  }

}
