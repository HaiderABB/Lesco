package Views.Employee.Dashboard.ManageBills;

import Model.Bills;
import Model.Customers;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class AddBillPanel extends JPanel {
  private JTextField customerIdField;
  private JButton submitButton;

  Customers customerData;
  Bills billingData;

  public AddBillPanel(Customers C, Bills B) {
    this.customerData = C;
    this.billingData = B;
    setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10);

    gbc.gridx = 0;
    gbc.gridy = 0;
    add(new JLabel("Customer ID:"), gbc);

    gbc.gridx = 1;
    customerIdField = new JTextField();
    customerIdField.setPreferredSize(new Dimension(200, 30));
    add(customerIdField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    submitButton = new JButton("Submit Bill");
    submitButton.setPreferredSize(new Dimension(200, 40));
    add(submitButton, gbc);

    submitButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String customerId = customerIdField.getText().trim();

        if (validateInputs(customerId)) {
          int ID = Integer.parseInt(customerId);
          if (billingData.CheckBill(ID)) {
            if (customerData.getMeterType(ID).equals("Single Phase")
                && customerData.getCustomerType(ID).equals("Domestic")) {
              Double CurrentReading = customerData.GetRegularReading(ID);
              billingData.SinglePhaseDomesticBill(ID, CurrentReading);

            } else if (customerData.getMeterType(ID).equals("Single Phase")
                && customerData.getCustomerType(ID).equals("Commercial")) {
              Double CurrentReading = customerData.GetRegularReading(ID);
              billingData.SinglePhaseCommercialBill(ID, CurrentReading);
            } else if (customerData.getMeterType(ID).equals("Three Phase")
                && customerData.getCustomerType(ID).equals("Domestic")) {

              Double CurrentReading = customerData.GetRegularReading(ID);
              Double PeakReading = customerData.GetPeakReading(ID);
              billingData.ThreePhaseDomesticBill(ID, CurrentReading, PeakReading);

            } else if (customerData.getMeterType(ID).equals("Three Phase")
                && customerData.getCustomerType(ID).equals("Commercial")) {

              Double CurrentReading = customerData.GetRegularReading(ID);
              Double PeakReading = customerData.GetPeakReading(ID);
              billingData.ThreePhaseCommercialBill(ID, CurrentReading, PeakReading);

            }

            JOptionPane.showMessageDialog(AddBillPanel.this, "Bill Generated Successfully");
            billingData.WriteToFile();

          } else {
            JOptionPane.showMessageDialog(AddBillPanel.this, "Bill Exists Already");
          }

          clearFields();

        } else {

        }
      }
    });
  }

  private boolean validateInputs(String customerId) {
    if (customerId.length() == 0) {
      JOptionPane.showMessageDialog(this, "Customer ID cannot be empty");
      return false;
    } else if (Integer.parseInt(customerId) <= 0) {
      JOptionPane.showMessageDialog(this, "Customer ID cannot be less than equal to zero");
      return false;
    } else if (!customerData.checkID(Integer.parseInt(customerId))) {
      JOptionPane.showMessageDialog(this, "Customer ID does not exist");
      return false;
    }
    return true;
  }

  private void clearFields() {
    customerIdField.setText("");
  }
}
