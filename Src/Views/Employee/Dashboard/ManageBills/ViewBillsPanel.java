package Views.Employee.Dashboard.ManageBills;

import Model.Billing;
import Model.Bills;
import Views.DashboardSuper;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class ViewBillsPanel extends DashboardSuper {

  public JPanel MainPanel;
  public JTable BillsTable;
  public JTextField SearchBar;
  public JScrollPane scrollPane;
  public String[] ColumnNames = {
      "ID", "Billing Month", "Current Regular Reading", "Entry Date", "Current Peak Reading", "Sales Tax",
      "Fixed Charges", "Total Amount", "Due Date", "Paid Status", "Paid Date", "Tax Rate", "Regular Units Price",
      "Peak Units Price", "Update", "Delete"
  };

  private int[] originalIndices;
  public Bills BillsData;
  private HashMap<Integer, Integer> mostRecentBillIndices;

  public ViewBillsPanel(Bills B) {
    BillsData = B;
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
    DefaultTableModel BillsModel = new DefaultTableModel(ColumnNames, 0);
    loadBillsData(BillsModel);
    originalIndices = new int[BillsData.CustomerBills.size()];
    for (int i = 0; i < BillsData.CustomerBills.size(); i++) {
      originalIndices[i] = i;
    }
    BillsTable = new JTable(BillsModel) {
      @Override
      public boolean isCellEditable(int row, int column) {
        if (BillsData.CustomerBills.size() == 0)
          return false;
        int billId = (int) BillsTable.getValueAt(row, 0);
        int customerId = billId;
        return (column == 14 || column == 15) && mostRecentBillIndices.get(customerId) != null
            && row == mostRecentBillIndices.get(customerId);
      }

    };
    BillsTable.getColumn("Update").setCellRenderer(new ButtonRenderer());
    BillsTable.getColumn("Update").setCellEditor(new ButtonEditor("Update", true));
    BillsTable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
    BillsTable.getColumn("Delete").setCellEditor(new ButtonEditor("Delete", false));
    scrollPane = new JScrollPane(BillsTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(70, 10, 10, 10));
    scrollPane.setBackground(EPcolor);
  }

  private void loadBillsData(DefaultTableModel model) {
    mostRecentBillIndices = new HashMap<>();
    for (int i = 0; i < BillsData.CustomerBills.size(); i++) {
      Billing bill = BillsData.CustomerBills.get(i);
      int customerId = bill.getID();
      LocalDate billDate = parseDate(bill.getEntryDate());
      if (!mostRecentBillIndices.containsKey(customerId) ||
          isBillMoreRecent(billDate,
              BillsData.CustomerBills.get(mostRecentBillIndices.get(customerId)).getEntryDate())) {
        mostRecentBillIndices.put(customerId, i);
      }
    }
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
      row[10] = c.getPaidDate() == null || c.getPaidDate().equals("null") ? "-" : c.getPaidDate();
      row[11] = c.getTaxRate();
      row[12] = c.getRegularUnitsPrice();
      row[13] = c.getPeakUnitsPrice();
      if (mostRecentBillIndices.get(c.getID()).equals(BillsData.CustomerBills.indexOf(c))) {
        row[14] = "Update";
        row[15] = "Delete";
      } else {
        row[14] = "";
        row[15] = "";
      }
      model.addRow(row);
    }
  }

  private LocalDate parseDate(String dateStr) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    return LocalDate.parse(dateStr, formatter);
  }

  private boolean isBillMoreRecent(LocalDate currentBillDate, String existingBillDateStr) {
    LocalDate existingBillDate = parseDate(existingBillDateStr);
    return currentBillDate.isAfter(existingBillDate);
  }

  private void filterBillsData(String searchTerm) {
    DefaultTableModel model = (DefaultTableModel) BillsTable.getModel();
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
        row[10] = c.getPaidDate() == null || c.getPaidDate().equals("null") ? "-" : c.getPaidDate();
        row[11] = c.getTaxRate();
        row[12] = c.getRegularUnitsPrice();
        row[13] = c.getPeakUnitsPrice();
        if (mostRecentBillIndices.get(c.getID()).equals(BillsData.CustomerBills.indexOf(c))) {
          row[14] = "Update";
          row[15] = "Delete";
        } else {
          row[14] = "";
          row[15] = "";
        }
        model.addRow(row);
        originalIndices[currentIndex] = BillsData.CustomerBills.indexOf(c);
        currentIndex++;
      }
    }
  }

  class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButton button;
    private String label;
    private boolean isUpdate;
    private int row;

    public ButtonEditor(String buttonLabel, boolean isUpdate) {
      this.isUpdate = isUpdate;
      button = new JButton(buttonLabel);
      button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      this.row = table.convertRowIndexToModel(row);
      this.label = (value == null) ? "" : value.toString();
      button.setText(label);
      return button;
    }

    @Override
    public Object getCellEditorValue() {
      return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (isUpdate) {
        updateBillsData();
      } else {
        deleteBillsData();

      }

      fireEditingStopped();
    }

    private void updateBillsData() {
      Billing bill = BillsData.CustomerBills.get(originalIndices[row]);
      JTextField paidStatusField = new JTextField(bill.getPaidStatus());
      int result = JOptionPane.showConfirmDialog(button, paidStatusField, "Update Paid Status",
          JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
        try {
          String paidStatus = paidStatusField.getText();
          if (paidStatus.equalsIgnoreCase("Paid") || paidStatus.equalsIgnoreCase("Unpaid")) {
            bill.setPaidStatus(paidStatus);
            DefaultTableModel model = (DefaultTableModel) BillsTable.getModel();
            model.setValueAt(paidStatus, row, 9);
            BillsData.WriteToFile();

            JOptionPane.showMessageDialog(button, "Bill Updated Successfully");
          } else {
            JOptionPane.showMessageDialog(button, "Please enter a valid status (Paid/Unpaid)", "Invalid Input",
                JOptionPane.WARNING_MESSAGE);
          }
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(button, "Error updating bill: " + ex.getMessage(), "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      }
    }

    private void deleteBillsData() {
      int confirmation = JOptionPane.showConfirmDialog(button, "Are you sure you want to delete this bill?",
          "Confirm Deletion", JOptionPane.YES_NO_OPTION);
      if (confirmation == JOptionPane.YES_OPTION) {
        DefaultTableModel model = (DefaultTableModel) BillsTable.getModel();
        BillsData.CustomerBills.remove(originalIndices[row]);
        model.removeRow(row);
        BillsData.WriteToFile();

        updateMostRecentBillIndices();
        JOptionPane.showMessageDialog(button, "Bill Deleted Successfully");
      }

    }

    private void updateMostRecentBillIndices() {
      mostRecentBillIndices.clear();
      for (int i = 0; i < BillsData.CustomerBills.size(); i++) {
        Billing bill = BillsData.CustomerBills.get(i);
        int customerId = bill.getID();
        LocalDate billDate = parseDate(bill.getEntryDate());
        if (!mostRecentBillIndices.containsKey(customerId) ||
            isBillMoreRecent(billDate,
                BillsData.CustomerBills.get(mostRecentBillIndices.get(customerId)).getEntryDate())) {
          mostRecentBillIndices.put(customerId, i);
        }
      }
    }

  }

  class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
      setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
        int row, int column) {
      if (value != null) {
        setText(value.toString());
      }

      if (isSelected) {
        setBackground(Color.YELLOW);
      } else {
        setBackground(Color.LIGHT_GRAY);
      }

      return this;
    }
  }
}
