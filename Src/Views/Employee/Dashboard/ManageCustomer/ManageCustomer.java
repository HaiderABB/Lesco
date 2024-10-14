package Views.Employee.Dashboard.ManageCustomer;

import Model.Customer;
import Model.Customers;
import Views.DashboardSuper;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
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

  public ManageCustomer(Customers customerData) {
    this.customerData = customerData;
    init();
  }

  public void init() {
    MainPanel = new JPanel();
    MainPanel.setLayout(new BorderLayout());
    MainPanel.setBackground(EPcolor);

    SearchBar = new JTextField("Enter Customer ID");
    SearchBar.setPreferredSize(new Dimension(700, 40));
    initTable();

    MainPanel.add(SearchBar, BorderLayout.NORTH);
    MainPanel.add(scrollPane, BorderLayout.CENTER);
  }

  public void initTable() {
    DefaultTableModel CustomerModel = new DefaultTableModel(ColumnNames, 0);
    loadCustomerData(CustomerModel);

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
      this.row = row;
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

    private void updateCustomerData() {
      Customer currentCustomer = customerData.customers.get(row);
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
      fields[7].setText(currentCustomer.getConnectionDate());
      fields[8].setText(String.valueOf(currentCustomer.getRegularUnits()));
      fields[9].setText(String.valueOf(currentCustomer.getPeakUnits()));

      int result = JOptionPane.showConfirmDialog(button, panel, "Update Customer Data", JOptionPane.OK_CANCEL_OPTION);
      if (result == JOptionPane.OK_OPTION) {
        currentCustomer.setID(Integer.parseInt(fields[0].getText()));
        currentCustomer.setCNIC(new BigInteger(fields[1].getText()));
        currentCustomer.setName(fields[2].getText());
        currentCustomer.setAddress(fields[3].getText());
        currentCustomer.setPhoneNumber(fields[4].getText());
        currentCustomer.setCustomerType(fields[5].getText());
        currentCustomer.setMeterType(fields[6].getText());
        currentCustomer.setConnectionDate(fields[7].getText());
        currentCustomer.setRegularUnits(Double.parseDouble(fields[8].getText()));
        currentCustomer.setPeakUnits(Double.parseDouble(fields[9].getText()));

        DefaultTableModel model = (DefaultTableModel) CustomerTable.getModel();
        model.setValueAt(currentCustomer.getID(), row, 0);
        model.setValueAt(currentCustomer.getCNIC().toString(), row, 1);
        model.setValueAt(currentCustomer.getName(), row, 2);
        model.setValueAt(currentCustomer.getAddress(), row, 3);
        model.setValueAt(currentCustomer.getPhoneNumber(), row, 4);
        model.setValueAt(currentCustomer.getCustomerType(), row, 5);
        model.setValueAt(currentCustomer.getMeterType(), row, 6);
        model.setValueAt(currentCustomer.getConnectionDate(), row, 7);
        model.setValueAt(currentCustomer.getRegularUnits(), row, 8);
        model.setValueAt(currentCustomer.getPeakUnits(), row, 9);

        JOptionPane.showMessageDialog(button, "Updated row " + row);
      }
    }

    private void deleteCustomerData() {
      int confirm = JOptionPane.showConfirmDialog(button, "Are you sure you want to delete this customer?",
          "Delete Confirmation", JOptionPane.YES_NO_OPTION);
      if (confirm == JOptionPane.YES_OPTION) {
        ((DefaultTableModel) CustomerTable.getModel()).removeRow(row);
        customerData.customers.remove(row);
        JOptionPane.showMessageDialog(button, "Deleted row " + row);
      }
    }
  }
}
