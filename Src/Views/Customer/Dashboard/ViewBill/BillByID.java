package Views.Customer.Dashboard.ViewBill;

import Model.Bills;
import Model.Customers;
import Views.DashboardSuper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BillByID extends DashboardSuper {
  public JPanel mainPanel;
  public JPanel CustomerPanel;
  public JPanel BillsPanel;

  private JTextField userIDField;

  public JTable CustomerTable;
  public JTable BillTable;
  public String[] CustomerColumnNames = {
      "ID", "CNIC", "Name", "Address", "Phone", "Customer Type", "Meter Type"
  };
  public String[] BillColumnNames = {
      "Current Regular Reading", "Current Peak Reading", "Regular Unit Price", "Peak Unit Price", "Taxed Amount",
      "Fixed Charges", "Total Amount", "Due Date ", "Payment Date", "Tax Rate"
  };

  public JScrollPane scrollPane1;
  public JScrollPane scrollPane2;

  private JLabel messageLabel;
  BigInteger cnic;
  public Customers C;
  public Bills B;
  int userID;

  public BillByID(Customers C, BigInteger cnic, Bills B) {
    this.cnic = cnic;
    this.C = C;
    this.B = B;
    init();
  }

  private void init() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    JPanel formPanel = new JPanel(new GridLayout(2, 2, 10, 10));

    JLabel userIDLabel = new JLabel("Enter User ID:");
    userIDField = new JTextField();
    userIDField.setPreferredSize(new Dimension(200, 30));

    messageLabel = new JLabel();
    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        validateAndSubmit();
      }
    });

    formPanel.add(userIDLabel);
    formPanel.add(userIDField);
    formPanel.add(submitButton);

    mainPanel.add(formPanel, BorderLayout.NORTH);
    mainPanel.add(messageLabel, BorderLayout.SOUTH);
  }

  private void validateAndSubmit() {
    try {
      userID = Integer.parseInt(userIDField.getText().trim());

      if (userID < 0) {
        messageLabel.setText("Error: User ID must not be less than zero.");
      } else if (!C.checkIDAgainstCNIC(userID, cnic)) {
        messageLabel.setText("Error: User ID does not exist against this CNIC.");
      } else {
        CustomerPanel = new JPanel(new GridLayout(1, 1));
        BillsPanel = new JPanel(new GridLayout(1, 1));
        initCustomerTable();
        initBillsTable();

        mainPanel.removeAll();
        JPanel combinedPanel = new JPanel(new GridLayout(2, 1)); // Combining both tables
        combinedPanel.add(scrollPane1);
        combinedPanel.add(scrollPane2);

        mainPanel.add(combinedPanel, BorderLayout.CENTER);
        mainPanel.revalidate();
        mainPanel.repaint();
      }
    } catch (NumberFormatException ex) {
      messageLabel.setText("Error: Please enter a valid integer for User ID.");
    }
  }

  public void initCustomerTable() {
    DefaultTableModel customerModel = new DefaultTableModel(CustomerColumnNames, 0);
    loadCustomerData(customerModel);
    CustomerTable = new JTable(customerModel);
    scrollPane1 = new JScrollPane(CustomerTable);
    scrollPane1.setBorder(BorderFactory.createEmptyBorder(70, 10, 10, 10));
    scrollPane1.setBackground(EPcolor);
  }

  private void loadCustomerData(DefaultTableModel model) {
    Object[] row = new Object[7];
    C.printCustomer(userID, cnic, row);
    model.addRow(row);
  }

  public void initBillsTable() {
    DefaultTableModel billsModel = new DefaultTableModel(BillColumnNames, 0);
    loadBillsData(billsModel);
    BillTable = new JTable(billsModel);
    scrollPane2 = new JScrollPane(BillTable);
    scrollPane2.setBorder(BorderFactory.createEmptyBorder(70, 10, 10, 10));
    scrollPane2.setBackground(EPcolor);
  }

  private void loadBillsData(DefaultTableModel model) {
    Object[] row = new Object[10];
    B.printBill(userID, row);
    model.addRow(row);
  }

  public static void main(String[] args) {
    // You can test the UI by running this method if needed
  }
}
