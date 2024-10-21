package Views.Customer.Dashboard.ViewBill;

import Model.Bills;
import Model.Customers;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BillByMeter {
  public JPanel mainPanel;

  private JTextField regularReadingField;
  private JTextField peakReadingField;
  private JComboBox<String> meterTypeComboBox;
  private JComboBox<String> customerTypeComboBox;

  // Define bill information columns
  public String[] SinglePhaseDomestic = {
      "Regular Units Price", "Tax Rate", "Fixed Charges", "Meter Reading", "Sales Tax", "Total Amount"
  };
  public String[] SinglePhaseCommercial = {
      "Regular Units Price", "Tax Rate", "Fixed Charges", "Meter Reading", "Commercial Tax", "Total Amount"
  };
  public String[] ThreePhaseDomestic = {
      "Regular Units Price", "Peak Units Price", "Tax Rate", "Fixed Charges", "Meter Reading", "Peak Meter Reading",
      "Sales Tax", "Total Amount"
  };
  public String[] ThreePhaseCommercial = {
      "Regular Units Price", "Peak Units Price", "Tax Rate", "Fixed Charges", "Meter Reading", "Peak Meter Reading",
      "Commercial Tax", "Total Amount"
  };

  // Customer Data Columns
  public String[] CustomerColumnNames = {
      "ID", "CNIC", "Name", "Address", "Phone", "Customer Type", "Meter Type"
  };

  // Bill Information Columns
  public String[] BillColumnNames = {
      "Current Regular Reading", "Current Peak Reading", "Regular Unit Price", "Peak Unit Price", "Taxed Amount",
      "Fixed Charges", "Total Amount", "Due Date", "Payment Date", "Tax Rate"
  };

  private JLabel messageLabel;
  private JTable billTable;
  private JTable customerTable; // New table for customer data
  private JTable lescoBillTable; // New table for LESCO billing information
  private JScrollPane scrollPane;
  private JScrollPane customerScrollPane;
  private JScrollPane lescoScrollPane; // New scroll pane for LESCO billing information

  public Bills bill;
  public Customers customerData;
  public int ID;
  public BigInteger CNIC;

  public BillByMeter(Bills b, Customers d, int ID, BigInteger cnic) {
    this.ID = ID;
    CNIC = cnic;
    bill = b;
    customerData = d;
    init();
  }

  private void init() {
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());

    JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

    JLabel meterTypeLabel = new JLabel("Meter Type:");
    String[] meterTypes = { "Single Phase", "Three Phase" };
    meterTypeComboBox = new JComboBox<>(meterTypes);

    JLabel customerTypeLabel = new JLabel("Customer Type:");
    String[] customerTypes = { "Domestic", "Commercial" };
    customerTypeComboBox = new JComboBox<>(customerTypes);

    JLabel regularReadingLabel = new JLabel("Current Regular Reading:");
    regularReadingField = new JTextField();

    JLabel peakReadingLabel = new JLabel("Current Peak Reading (only for Three Phase):");
    peakReadingField = new JTextField();
    peakReadingField.setEnabled(false); // Disable peak reading for Single Phase

    meterTypeComboBox.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        if (meterTypeComboBox.getSelectedItem().equals("Three Phase")) {
          peakReadingField.setEnabled(true);
        } else {
          peakReadingField.setEnabled(false);
          peakReadingField.setText("");
        }
      }
    });

    JButton submitButton = new JButton("Submit");
    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        validateAndSubmit();
      }
    });

    messageLabel = new JLabel();
    messageLabel.setHorizontalAlignment(SwingConstants.CENTER);

    formPanel.add(meterTypeLabel);
    formPanel.add(meterTypeComboBox);
    formPanel.add(customerTypeLabel);
    formPanel.add(customerTypeComboBox);
    formPanel.add(regularReadingLabel);
    formPanel.add(regularReadingField);
    formPanel.add(peakReadingLabel);
    formPanel.add(peakReadingField);
    formPanel.add(new JLabel());
    formPanel.add(submitButton);

    mainPanel.add(formPanel, BorderLayout.CENTER);
    mainPanel.add(messageLabel, BorderLayout.SOUTH);
  }

  private void validateAndSubmit() {
    String meterType = (String) meterTypeComboBox.getSelectedItem();
    String customerType = (String) customerTypeComboBox.getSelectedItem();
    double regularReading, peakReading = 0.0;

    try {
      regularReading = Double.parseDouble(regularReadingField.getText().trim());
      if (regularReading <= 0) {
        messageLabel.setText("Error: Regular reading must be greater than zero.");
        return;
      }

      if (meterType.equals("Three Phase")) {
        peakReading = Double.parseDouble(peakReadingField.getText().trim());
        if (peakReading <= 0) {
          messageLabel.setText("Error: Peak reading must be greater than zero for Three Phase.");
          return;
        }
      }

      if (!customerType.equals("Domestic") && !customerType.equals("Commercial")) {
        messageLabel.setText("Error: Customer type must be either Domestic or Commercial.");
        return;
      }

      mainPanel.removeAll();
      displayBillTable(meterType, customerType);
      displayCustomerTable();
      displayLescoBillTable(regularReading, peakReading, meterType, customerType);
      mainPanel.add(scrollPane, BorderLayout.NORTH);
      mainPanel.add(customerScrollPane, BorderLayout.CENTER);
      mainPanel.add(lescoScrollPane, BorderLayout.SOUTH);
      mainPanel.revalidate();
      mainPanel.repaint();

    } catch (NumberFormatException ex) {
      messageLabel.setText("Error: Please enter valid numbers for the readings.");
    }
  }

  private void displayBillTable(String meterType, String customerType) {
    String[] columns = {};
    if (meterType.equals("Single Phase") && customerType.equals("Domestic")) {
      columns = SinglePhaseDomestic;
      DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
      Object[] rowData = new Object[columns.length];

      bill.ExpectedBillSingleDomestic(Double.parseDouble(regularReadingField.getText()), rowData);
      tableModel.addRow(rowData);

      billTable = new JTable(tableModel);

    } else if (meterType.equals("Single Phase") && customerType.equals("Commercial")) {
      columns = SinglePhaseCommercial;
      DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
      Object[] rowData = new Object[columns.length];

      bill.ExpectedBillSingleCommercial(Double.parseDouble(regularReadingField.getText()), rowData);
      tableModel.addRow(rowData);

      billTable = new JTable(tableModel);
    } else if (meterType.equals("Three Phase") && customerType.equals("Domestic")) {
      columns = ThreePhaseDomestic;
      DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
      Object[] rowData = new Object[columns.length];

      bill.ExpectedBillThreeDomestic(Double.parseDouble(regularReadingField.getText()),
          Double.parseDouble(peakReadingField.getText()), rowData);
      tableModel.addRow(rowData);

      billTable = new JTable(tableModel);
    } else if (meterType.equals("Three Phase") && customerType.equals("Commercial")) {
      columns = ThreePhaseCommercial;

      DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
      Object[] rowData = new Object[columns.length];

      bill.ExpectedBillThreeCommercial(Double.parseDouble(regularReadingField.getText()),
          Double.parseDouble(peakReadingField.getText()), rowData);
      tableModel.addRow(rowData);

      billTable = new JTable(tableModel);
    }

    if (scrollPane != null) {
      mainPanel.remove(scrollPane);
    }
    scrollPane = new JScrollPane(billTable);

    messageLabel.setText("Success! Table displayed based on " + meterType + " and " + customerType);
  }

  private void displayCustomerTable() {
    DefaultTableModel customerTableModel = new DefaultTableModel(CustomerColumnNames, 0);
    Object[] customerRowData = new Object[CustomerColumnNames.length];

    customerData.printCustomer(ID, CNIC, customerRowData);
    customerTableModel.addRow(customerRowData);
    customerTable = new JTable(customerTableModel);

    // Add customerTable to the customerScrollPane and then to the mainPanel
    customerScrollPane = new JScrollPane(customerTable);
    mainPanel.add(customerScrollPane, BorderLayout.CENTER); // Adjust the layout if needed

    // Don't forget to revalidate and repaint
    mainPanel.revalidate();
    mainPanel.repaint();
  }

  private void displayLescoBillTable(double regularReading, double peakReading, String meterType, String customerType) {

    DefaultTableModel lescoTableModel = new DefaultTableModel(BillColumnNames, 0);
    Object[] lescoRowData = new Object[BillColumnNames.length];

    if (bill.printBill(ID, lescoRowData)) {
      lescoTableModel.addRow(lescoRowData);
      lescoBillTable = new JTable(lescoTableModel);

      // Add lescoBillTable to the lescoScrollPane and then to the mainPanel
      lescoScrollPane = new JScrollPane(lescoBillTable);
      mainPanel.add(lescoScrollPane, BorderLayout.SOUTH); // Adjust the layout if needed

      // Don't forget to revalidate and repaint
      mainPanel.revalidate();
      mainPanel.repaint();
    }
  }

}
