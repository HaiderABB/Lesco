package Views.Employee.Dashboard.ManageCustomer;

import Model.Customer;
import Model.Customers;
import Model.MeterInfo;
import Views.DashboardSuper;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
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
  Customers customerData;
  MeterInfo meterInfo;
  private int[] originalIndices;

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

  private void loadCustomerData(DefaultTableModel model) {
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

  private void filterCustomerData(String searchTerm) {
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
        updateCustomerData();
      } else {
        deleteCustomerData();
      }
      fireEditingStopped();
    }

    private boolean validateAndUpdateCustomer(Customer customer, JTextField[] fields) {
      try {
        int id = Integer.parseInt(fields[0].getText());
        if (id > 0 && !customerData.checkID(id)) {
          customer.setID(id);
        }

        BigInteger cnic = new BigInteger(fields[1].getText());
        if (fields[1].getText().matches(CNICregex) && customerData.checkIDAgainstCNIC(id, cnic)) {
          customer.setCNIC(cnic);

        }
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

    private void updateCustomerData() {
      Customer currentCustomer = customerData.customers.get(originalIndices[row]);
      JTextField[] fields = new JTextField[ColumnNames.length - 2];
      JPanel panel = new JPanel(new GridLayout(fields.length, 2));
      for (int i = 0; i < fields.length; i++) {
        fields[i] = new JTextField();
        panel.add(new JLabel(ColumnNames[i]));
        panel.add(fields[i]);
      }
      fields[0].setText(String.valueOf(currentCustomer.getID()));
      fields[1].setText(currentCustomer.getCNIC().toString());
      fields[2].setText(currentCustomer.getName());
      fields[3].setText(currentCustomer.getAddress());
      fields[4].setText(currentCustomer.getPhoneNumber());
      fields[5].setText(currentCustomer.getCustomerType());
      fields[6].setText(currentCustomer.getMeterType());
      fields[8].setText(String.valueOf(currentCustomer.getRegularUnits()));
      fields[9].setText(String.valueOf(currentCustomer.getPeakUnits()));
      int result = JOptionPane.showConfirmDialog(button, panel, "Update Customer Data", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
        if (validateAndUpdateCustomer(currentCustomer, fields)) {
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

        }
      }
    }

    private void deleteCustomerData() {
      int customerID = (int) CustomerTable.getValueAt(row, 0);
      int confirmDelete = JOptionPane.showConfirmDialog(button, "Are you sure you want to delete?",
          "Delete Confirmation", JOptionPane.YES_NO_OPTION);

      if (confirmDelete == JOptionPane.YES_OPTION) {
        customerData.removeCustomer(customerID);

        BigInteger meterId = new BigInteger(CustomerTable.getValueAt(row, 1).toString());
        meterInfo.removeMeter(meterId);

        DefaultTableModel model = (DefaultTableModel) CustomerTable.getModel();
        model.removeRow(row);
        meterInfo.WriteToFile();
        customerData.WriteToFile();
        JOptionPane.showMessageDialog(button, "Customer deleted successfully!");
      }
    }

  }
}
