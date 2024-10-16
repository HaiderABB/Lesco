package Views.Customer.Dashboard.ViewBill;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import Model.Bills;

public class BillByMeter {
  public JPanel mainPanel;

  private JTextField regularReadingField;
  private JTextField peakReadingField;
  private JComboBox<String> meterTypeComboBox;
  private JComboBox<String> customerTypeComboBox;

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

  private JLabel messageLabel;
  private JTable billTable;
  private JScrollPane scrollPane;
  public Bills bills;

  public BillByMeter(Bills B) {
    bills = B;
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
          peakReadingField.setText(""); // Clear peak reading if it's Single Phase
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
    formPanel.add(new JLabel()); // Empty space in grid
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

      displayBillTable(meterType, customerType);

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

      bills.ExpectedBillSingleDomestic(Double.parseDouble(regularReadingField.getText()), rowData);
      tableModel.addRow(rowData);

      billTable = new JTable(tableModel);

    } else if (meterType.equals("Single Phase") && customerType.equals("Commercial")) {
      columns = SinglePhaseCommercial;
      DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
      Object[] rowData = new Object[columns.length];

      bills.ExpectedBillSingleCommercial(Double.parseDouble(regularReadingField.getText()), rowData);
      tableModel.addRow(rowData);

      billTable = new JTable(tableModel);
    } else if (meterType.equals("Three Phase") && customerType.equals("Domestic")) {
      columns = ThreePhaseDomestic;
      DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
      Object[] rowData = new Object[columns.length];

      bills.ExpectedBillThreeDomestic(Double.parseDouble(regularReadingField.getText()),
          Double.parseDouble(peakReadingField.getText()), rowData);
      tableModel.addRow(rowData);

      billTable = new JTable(tableModel);
    } else if (meterType.equals("Three Phase") && customerType.equals("Commercial")) {
      columns = ThreePhaseCommercial;
      columns = ThreePhaseDomestic;
      DefaultTableModel tableModel = new DefaultTableModel(columns, 0);
      Object[] rowData = new Object[columns.length];

      bills.ExpectedBillThreeCommercial(Double.parseDouble(regularReadingField.getText()),
          Double.parseDouble(peakReadingField.getText()), rowData);
      tableModel.addRow(rowData);

      billTable = new JTable(tableModel);
    }

    if (scrollPane != null) {
      mainPanel.remove(scrollPane);
    }
    scrollPane = new JScrollPane(billTable);
    mainPanel.add(scrollPane, BorderLayout.NORTH);
    mainPanel.revalidate();
    mainPanel.repaint();

    messageLabel.setText("Success! Table displayed based on " + meterType + " and " + customerType);
  }

  public static void main(String[] args) {

  }
}
