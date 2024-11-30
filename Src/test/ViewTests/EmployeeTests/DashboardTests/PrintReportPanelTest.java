package test.ViewTests.EmployeeTests.DashboardTests;

import Model.Bills;
import Views.Employee.Dashboard.ManageBills.PrintReportPanel;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class PrintReportPanelTest {

  private PrintReportPanel printReportPanel;
  private Bills mockBills;

  @Before
  public void setUp() {
    mockBills = mock(Bills.class);
    when(mockBills.getUnpaidBills()).thenReturn(5);
    when(mockBills.getUnpaidAmount()).thenReturn(500.0);
    when(mockBills.getPaidBills()).thenReturn(10);
    when(mockBills.getPaidAmount()).thenReturn(1000.0);
    printReportPanel = new PrintReportPanel(mockBills);
  }

  @Test
  public void testInitialization() {
    assertNotNull(printReportPanel);
    assertEquals(2, printReportPanel.getComponentCount());
  }

  @Test
  public void testLabelsDisplayCorrectValues() {
    JLabel unpaidLabel = (JLabel) printReportPanel.getComponent(0);
    JLabel paidLabel = (JLabel) printReportPanel.getComponent(1);
    assertEquals("Total Paid Bills are 10 of total amount 1000.0", unpaidLabel.getText());
    assertEquals("Total Unpaid Bills are 5 of total amount 500.0", paidLabel.getText());
  }

  @Test
  public void testInteractionWithBillsClass() {
    verify(mockBills).getUnpaidBills();
    verify(mockBills).getUnpaidAmount();
    verify(mockBills).getPaidBills();
    verify(mockBills).getPaidAmount();
  }

  @Test
  public void testUnpaidBillsLabel() {
    JLabel unpaidLabel = (JLabel) printReportPanel.getComponent(1);
    assertTrue(unpaidLabel.getText().contains("Total Unpaid Bills"));
    assertTrue(unpaidLabel.getText().contains("500.0"));
  }

  @Test
  public void testPaidBillsLabel() {
    JLabel paidLabel = (JLabel) printReportPanel.getComponent(0);
    assertTrue(paidLabel.getText().contains("Total Paid Bills"));
    assertTrue(paidLabel.getText().contains("1000.0"));
  }
}
