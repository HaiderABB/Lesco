package Views.Employee.Dashboard.ManageBills;

import Model.Billing;
import Model.Bills;
import Model.Customer;
import Views.DashboardSuper;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ViewBillsPanel extends DashboardSuper {

  public JPanel MainPanel;
  public JTable CustomerTable;
  public JTextField SearchBar;
  public JScrollPane scrollPane;
  public String[] ColumnNames = {
      "ID", "Billing Month", "Current Regular Reading", "Entry Date", "Current Peak  Reading", "Sales Tax",
      "Fixed Charges",
      "Total Amount", "Due Date", "Paid Status", "Tax Rate", "Regular Units Price", "Peak Units Price", "Update",
      "Delete"
  };

  private int[] originalIndices;
  public Bills BillsData;

  public ViewBillsPanel() {

    init();
  }

  public void init() {

    MainPanel = new JPanel();
    MainPanel.setLayout(new BorderLayout());
    MainPanel.setBackground(EPcolor);
    SearchBar = new JTextField("Enter Customer ID");
    SearchBar.setPreferredSize(new Dimension(700, 40));
    SearchBar.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        String searchTerm = SearchBar.getText().trim();
        filterBillsData(searchTerm);
      }
    });
    initTable();
    MainPanel.add(SearchBar, BorderLayout.NORTH);
    MainPanel.add(scrollPane, BorderLayout.CENTER);
  }

  public void initTable() {
    DefaultTableModel CustomerModel = new DefaultTableModel(ColumnNames, 0);
    loadCustomerData(CustomerModel);
    originalIndices = new int[BillsData.CustomerBills.size()];
    for (int i = 0; i < BillsData.CustomerBills.size(); i++) {
      originalIndices[i] = i;
    }
    CustomerTable = new JTable(CustomerModel) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column == 10 || column == 11; // Only update/delete columns are editable
      }
    };
    CustomerTable.getColumn("Update").setCellRenderer(new ButtonRenderer());
    CustomerTable.getColumn("Update").setCellEditor(new ButtonEditor("Update", true));
    CustomerTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
    CustomerTable.getColumn("Delete").setCellEditor(new ButtonEditor("Delete", false));
    scrollPane = new JScrollPane(CustomerTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(70, 10, 10, 10));
    scrollPane.setBackground(EPcolor);
  }

  private void loadCustomerData(DefaultTableModel model) {
    for (Billing c : BillsData.CustomerBills) {
      Object[] row = new Object[16];
      row[0] = c.getID();
      row[1] = c.getBillingMonth();
      row[2] = c.getCurrentRegularReading();
      row[3] = c.getEntryDate();
      row[4] = c.getCurrentPeakReading();
      row[5] = c.getSalesTax();
      row[6] = c.getFixedCharges();
      row[7] = c.getTotalAmount();
      row[8] = c.getDueDate();
      row[9] = c.getPaidStatus();
      row[10] = c.getPaidDate();
      row[11] = c.getTaxRate();
      row[12] = c.getRegularUnitsPrice();
      row[13] = c.getPeakUnitsPrice();
      row[14] = "Update";
      row[15] = "Delete";
      model.addRow(row);
    }
  }

  private void filterBillsData(String searchTerm) {
    DefaultTableModel model = (DefaultTableModel) CustomerTable.getModel();
    model.setRowCount(0);
    int currentIndex = 0;
    for (Billing c : BillsData.CustomerBills) {
      if (String.valueOf(c.getID()).contains(searchTerm)) {
        Object[] row = new Object[16];
        row[0] = c.getID();
        row[1] = c.getBillingMonth();
        row[2] = c.getCurrentRegularReading();
        row[3] = c.getEntryDate();
        row[4] = c.getCurrentPeakReading();
        row[5] = c.getSalesTax();
        row[6] = c.getFixedCharges();
        row[7] = c.getTotalAmount();
        row[8] = c.getDueDate();
        row[9] = c.getPaidStatus();
        row[10] = c.getPaidDate();
        row[11] = c.getTaxRate();
        row[12] = c.getRegularUnitsPrice();
        row[13] = c.getPeakUnitsPrice();
        row[14] = "Update";
        row[15] = "Delete";
        model.addRow(row);
        originalIndices[currentIndex] = BillsData.CustomerBills.indexOf(c);
        currentIndex++;
      }
    }
  }

}
