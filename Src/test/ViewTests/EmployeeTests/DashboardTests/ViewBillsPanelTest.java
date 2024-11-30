package test.ViewTests.EmployeeTests.DashboardTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import Model.Billing;
import Model.Bills;
import Views.Employee.Dashboard.ManageBills.ViewBillsPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class ViewBillsPanelTest {

  private ViewBillsPanel viewBillsPanel;
  private Bills mockBillsData;

  @Before
  public void setUp() {
    mockBillsData = createMockBillsData();
    viewBillsPanel = new ViewBillsPanel(mockBillsData);
  }

  private Bills createMockBillsData() {
    Bills bills = new Bills();
    List<Billing> billingList = new ArrayList<>();

    billingList.add(new Billing(1, 100.0, 50.0, 10.0, 5.0, 200.0, 0.18, 1.5, 2.0));
    billingList.add(new Billing(2, 150.0, 75.0, 15.0, 5.0, 250.0, 0.18, 1.6, 2.1));

    bills.setCustomerBills(billingList);
    return bills;
  }

  @Test
  public void testPanelInitialization() {
    assertNotNull(viewBillsPanel.MainPanel);
    assertNotNull(viewBillsPanel.BillsTable);
    assertNotNull(viewBillsPanel.SearchBar);
    assertNotNull(viewBillsPanel.scrollPane);
  }

  @Test
  public void testTablePopulation() {
    DefaultTableModel model = (DefaultTableModel) viewBillsPanel.BillsTable.getModel();
    assertTrue(model.getRowCount() > 0);
    assertEquals(16, model.getColumnCount()); // Make sure this matches the number of columns in your table
  }

  @Test
  public void testUpdateButtonAction() {
    viewBillsPanel.BillsTable.setValueAt("Unpaid", 0, 9);

    JButton updateButton = (JButton) viewBillsPanel.BillsTable.getCellRenderer(0, 14)
        .getTableCellRendererComponent(viewBillsPanel.BillsTable, "Update", false, false, 0, 14);
    updateButton.doClick();

    JTextField paidStatusField = new JTextField("Paid");
    int result = JOptionPane.showConfirmDialog(null, paidStatusField, "Update Paid Status",
        JOptionPane.OK_CANCEL_OPTION);
    assertEquals(JOptionPane.OK_OPTION, result);

    assertEquals("Unpaid", viewBillsPanel.BillsTable.getValueAt(0, 9)); // Make sure the column index is correct
  }

  @Test
  public void testDeleteButtonAction() {
    int rowCountBeforeDelete = viewBillsPanel.BillsTable.getRowCount();
    JButton deleteButton = (JButton) viewBillsPanel.BillsTable.getCellRenderer(0, 15)
        .getTableCellRendererComponent(viewBillsPanel.BillsTable, "Delete", false, false, 0, 15);
    deleteButton.doClick();

    int confirmation = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this bill?",
        "Confirm Deletion", JOptionPane.YES_NO_OPTION);
    assertEquals(JOptionPane.YES_OPTION, confirmation);

    assertEquals(rowCountBeforeDelete, viewBillsPanel.BillsTable.getRowCount());
  }

  @Test
  public void testCellEditing() {
    viewBillsPanel.BillsTable.setValueAt("Paid", 0, 9);

    assertEquals("Paid", viewBillsPanel.BillsTable.getValueAt(0, 9)); // Ensure you're updating the correct cell
  }

  @Test
  public void testTableHeader() {
    JTable table = viewBillsPanel.BillsTable;
    TableColumnModel columnModel = table.getColumnModel();

    for (int i = 0; i < columnModel.getColumnCount(); i++) {
      assertEquals(viewBillsPanel.ColumnNames[i], columnModel.getColumn(i).getHeaderValue());
    }
  }
}
