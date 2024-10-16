package Views.Employee.Dashboard.ManageBills;

import Model.Billing;
import Model.Bills;
import Views.DashboardSuper;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        return column == 14 || column == 15;
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
      if (c.getPaidDate() == null || c.getPaidDate().equals("null")) {
        row[10] = "-";
        System.out.println("Here");

      } else {
        row[10] = c.getPaidDate();
      }
      row[11] = c.getTaxRate();
      System.out.println(c.getPaidDate());
      row[12] = c.getRegularUnitsPrice();
      row[13] = c.getPeakUnitsPrice();
      row[14] = "Update";
      row[15] = "Delete";
      model.addRow(row);
    }
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
        if (c.getPaidDate() == null || c.getPaidDate().equals("null")) {
          row[10] = "-";

        } else {
          row[10] = c.getPaidDate();
        }
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
      System.out.println("Button Clicked");
      if (isUpdate) {
        updateBillsData();
      } else {
        deleteBillsData();
      }

      fireEditingStopped();
    }

    private void updateBillsData() {
      Billing bill = BillsData.CustomerBills.get(originalIndices[row]);

      JTextField regularReadingField = new JTextField(String.valueOf(bill.getCurrentRegularReading()));
      JTextField peakReadingField = new JTextField(String.valueOf(bill.getCurrentPeakReading()));
      JTextField paidStatusField = new JTextField(bill.getPaidStatus());

      JPanel panel = new JPanel(new GridLayout(3, 2));
      panel.add(new JLabel("Current Regular Reading:"));
      panel.add(regularReadingField);
      panel.add(new JLabel("Current Peak Reading:"));
      panel.add(peakReadingField);
      panel.add(new JLabel("Paid Status:"));
      panel.add(paidStatusField);

      int option = JOptionPane.showConfirmDialog(button, panel, "Update Bill Information", JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.PLAIN_MESSAGE);

      if (option == JOptionPane.OK_OPTION) {
        try {
          double currentRegularReading = Double.parseDouble(regularReadingField.getText());
          double currentPeakReading = Double.parseDouble(peakReadingField.getText());
          String paidStatus = paidStatusField.getText();

          bill.setCurrentRegularReading(currentRegularReading);
          bill.setCurrentPeakReading(currentPeakReading);
          bill.setPaidStatus(paidStatus);

          DefaultTableModel model = (DefaultTableModel) BillsTable.getModel();
          model.setValueAt(bill.getCurrentRegularReading(), row, 2);
          model.setValueAt(bill.getCurrentPeakReading(), row, 4);
          model.setValueAt(bill.getPaidStatus(), row, 9);

          BillsData.WriteToFile();
          JOptionPane.showMessageDialog(button, "Data Updated Successfully");

        } catch (NumberFormatException ex) {
          JOptionPane.showMessageDialog(button, "Invalid number format in one of the fields.");
        } catch (Exception ex) {
          JOptionPane.showMessageDialog(button, "Unexpected error occurred.");
        }
      }
    }

    private void deleteBillsData() {
      DefaultTableModel model = (DefaultTableModel) BillsTable.getModel();
      BillsData.CustomerBills.remove(originalIndices[row]);
      model.removeRow(row); // Remove the row from the table
      BillsData.WriteToFile();
      JOptionPane.showMessageDialog(button, "Bill Deleted Successfully");
    }
  }

  class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
      setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
        int row, int column) {
      setText((value == null) ? "" : value.toString());
      return this;
    }
  }
}
