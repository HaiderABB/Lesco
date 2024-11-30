package Views.Employee.Dashboard.ManageCustomer;

import Model.Customer;
import Model.Customers;
import Model.MeterInfo;
import Views.DashboardSuper;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class ManageCustomer extends DashboardSuper {
  public JPanel MainPanel;
  public JTable CustomerTable;
  public JTextField SearchBar;
  public JScrollPane scrollPane;
  public String[] ColumnNames = {
      "ID", "CNIC", "Name", "Address", "Phone", "Customer Type", "Meter Type",
      "Connection Date", "Regular Reading", "Peak Reading", "Update", "Delete"
  };
  public Customers customerData;
  public MeterInfo meterInfo;
  public int[] originalIndices;

  public ManageCustomer(Customers customerData, MeterInfo meterInfo) {
    this.customerData = customerData;
    this.meterInfo = meterInfo;
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
        filterCustomerData(searchTerm);
      }
    });
    initTable();
    MainPanel.add(SearchBar, BorderLayout.NORTH);
    MainPanel.add(scrollPane, BorderLayout.CENTER);
  }

  public void initTable() {
    DefaultTableModel CustomerModel = new DefaultTableModel(ColumnNames, 0);
    loadCustomerData(CustomerModel);
    originalIndices = new int[customerData.customers.size()];
    for (int i = 0; i < customerData.customers.size(); i++) {
      originalIndices[i] = i;
    }
    CustomerTable = new JTable(CustomerModel) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column == 10 || column == 11;
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

  public void loadCustomerData(DefaultTableModel model) {
    for (Customer c : customerData.customers) {
      Object[] row = new Object[12];
      row[0] = c.getID();
      row[1] = c.getCNIC();
      row[2] = c.getName();
      row[3] = c.getAddress();
      row[4] = c.getPhoneNumber();
      row[5] = c.getCustomerType();
      row[6] = c.getMeterType();
      row[7] = c.getConnectionDate();
      row[8] = c.getRegularUnits();
      row[9] = c.getPeakUnits();
      row[10] = "Update";
      row[11] = "Delete";
      model.addRow(row);
    }
  }

  public void filterCustomerData(String searchTerm) {
    DefaultTableModel model = (DefaultTableModel) CustomerTable.getModel();
    model.setRowCount(0);
    int currentIndex = 0;
    for (Customer c : customerData.customers) {
      if (String.valueOf(c.getID()).contains(searchTerm)) {
        Object[] row = new Object[12];
        row[0] = c.getID();
        row[1] = c.getCNIC();
        row[2] = c.getName();
        row[3] = c.getAddress();
        row[4] = c.getPhoneNumber();
        row[5] = c.getCustomerType();
        row[6] = c.getMeterType();
        row[7] = c.getConnectionDate();
        row[8] = c.getRegularUnits();
        row[9] = c.getPeakUnits();
        row[10] = "Update";
        row[11] = "Delete";
        model.addRow(row);
        originalIndices[currentIndex] = customerData.customers.indexOf(c);
        currentIndex++;
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
      setText((value == null) ? "" : value.toString());
      return this;
    }
  }

  public class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    public JButton button;
    public String label;
    public boolean isUpdate;
    public int row;

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
        updateCustomerData();
      } else {
        deleteCustomerData();
      }
      fireEditingStopped();
    }

    public boolean validateAndUpdateCustomer(Customer customer, JTextField[] fields) {
      try {

        if (fields[2].getText().matches(NameRegex.pattern())) {
          customer.setName(fields[2].getText());
        }

        Matcher matcher = AddressRegex.matcher(fields[3].getText());

        if (matcher.matches()) {
          customer.setAddress(fields[3].getText());
        } else {
          JOptionPane.showMessageDialog(button, "Couldn't update Address");
        }

        matcher = PhoneRegex.matcher(fields[4].getText());
        if (matcher.matches() && !customerData.CheckPhone((fields[4].getText()))) {
          customer.setPhoneNumber(fields[4].getText());
        } else {
          JOptionPane.showMessageDialog(button, "Couldn't update Phone Number");
        }

        if (fields[5].getText().equals(CustomerType1) || fields[5].getText().equals(CustomerType2)) {
          customer.setCustomerType(fields[5].getText());
        } else {
          JOptionPane.showMessageDialog(button, "Couldn't update Customer Type");
        }

        if (fields[6].getText().equals((MeterType1)) || fields[6].getText().equals(MeterType2)) {
          customer.setMeterType(fields[6].getText());
        }

        if (Double.parseDouble(fields[8].getText()) > 0.0) {
          customer.setRegularUnits(Double.parseDouble(fields[8].getText()));
        }

        if (Double.parseDouble(fields[9].getText()) > 0.0) {
          customer.setPeakUnits(Double.parseDouble(fields[9].getText()));
        }

        return true;
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(button, "Invalid number format in one of the fields.");
        return false;
      } catch (Exception e) {
        JOptionPane.showMessageDialog(button, "Unexpected error occurred.");
        return false;
      }
    }

    public void updateCustomerData() {
      Customer currentCustomer = customerData.customers.get(originalIndices[row]);
      JTextField[] fields = new JTextField[ColumnNames.length - 2];
      JPanel panel = new JPanel(new GridLayout(fields.length, 2));
      for (int i = 0; i < fields.length; i++) {
        fields[i] = new JTextField();
        panel.add(new JLabel(ColumnNames[i]));
        panel.add(fields[i]);
      }
      fields[0].setText(String.valueOf(currentCustomer.getID()));
      fields[1].setText(String.valueOf(currentCustomer.getCNIC()));
      fields[2].setText(currentCustomer.getName());
      fields[3].setText(currentCustomer.getAddress());
      fields[4].setText(currentCustomer.getPhoneNumber());
      fields[5].setText(currentCustomer.getCustomerType());
      fields[6].setText(currentCustomer.getMeterType());
      fields[7].setText(String.valueOf(currentCustomer.getConnectionDate()));
      fields[8].setText(String.valueOf(currentCustomer.getRegularUnits()));
      fields[9].setText(String.valueOf(currentCustomer.getPeakUnits()));

      int option = JOptionPane.showConfirmDialog(button, panel, "Update Customer Information",
          JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

      if (option == JOptionPane.OK_OPTION && validateAndUpdateCustomer(currentCustomer, fields)) {

        DefaultTableModel model = (DefaultTableModel) CustomerTable.getModel();
        model.setValueAt(currentCustomer.getID(), row, 0);
        model.setValueAt(currentCustomer.getCNIC().toString(), row, 1);
        model.setValueAt(currentCustomer.getName(), row, 2);
        model.setValueAt(currentCustomer.getAddress(), row, 3);
        model.setValueAt(currentCustomer.getPhoneNumber(), row, 4);
        model.setValueAt(currentCustomer.getCustomerType(), row, 5);
        model.setValueAt(currentCustomer.getMeterType(), row, 6);
        model.setValueAt(currentCustomer.getRegularUnits(), row, 8);
        model.setValueAt(currentCustomer.getPeakUnits(), row, 9);
        JOptionPane.showMessageDialog(button, "Customer updated successfully!");

        customerData.WriteToFile();
        meterInfo.WriteToFile();
        JOptionPane.showMessageDialog(button, "Updated Successfully");
      } else {
        JOptionPane.showMessageDialog(button, "Could not update the customer details.");
      }
    }

    public void deleteCustomerData() {
      int confirmation = JOptionPane.showConfirmDialog(button, "Are you sure you want to delete this customer?");
      if (confirmation == JOptionPane.YES_OPTION) {

        customerData.customers.remove(originalIndices[row]);
        customerData.WriteToFile();
        meterInfo.WriteToFile();
        JOptionPane.showMessageDialog(button, "Customer deleted successfully");
        ((DefaultTableModel) CustomerTable.getModel()).removeRow(row);
      }
    }
  }
}
