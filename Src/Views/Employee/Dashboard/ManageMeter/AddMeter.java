package Views.Employee.Dashboard.ManageMeter;

import Model.Customers;
import Model.MeterInfo;
import Model.Nadra;
import Views.DashboardSuper;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import javax.swing.*;

public class AddMeter extends DashboardSuper {
  public JPanel MainPanel;
  private JTextField cnicField;
  private JTextField nameField;
  private JTextField addressField;
  private JTextField phoneNumberField;
  private JTextField customerTypeField;
  private JTextField meterTypeField;
  private JButton addButton;

  private Customers customerData;
  private Nadra nadraData;
  private MeterInfo meterInfo;

  public AddMeter(Customers customerData, Nadra nadraData, MeterInfo meterInfo) {
    this.customerData = customerData;
    this.nadraData = nadraData;
    this.meterInfo = meterInfo;
    init();
  }

  private void init() {
    MainPanel = new JPanel();
    MainPanel.setLayout(new GridLayout(7, 2));
    MainPanel.setBackground(EPcolor);

    JLabel cnicLabel = new JLabel("CNIC:");
    cnicField = new JTextField();

    JLabel nameLabel = new JLabel("Name:");
    nameField = new JTextField();

    JLabel addressLabel = new JLabel("Address:");
    addressField = new JTextField();

    JLabel phoneNumberLabel = new JLabel("Phone Number:");
    phoneNumberField = new JTextField();

    JLabel customerTypeLabel = new JLabel("Customer Type:");
    customerTypeField = new JTextField();

    JLabel meterTypeLabel = new JLabel("Meter Type:");
    meterTypeField = new JTextField();

    addButton = new JButton("Add Meter");
    addButton.addActionListener(new AddButtonListener());

    MainPanel.add(cnicLabel);
    MainPanel.add(cnicField);
    MainPanel.add(nameLabel);
    MainPanel.add(nameField);
    MainPanel.add(addressLabel);
    MainPanel.add(addressField);
    MainPanel.add(phoneNumberLabel);
    MainPanel.add(phoneNumberField);
    MainPanel.add(customerTypeLabel);
    MainPanel.add(customerTypeField);
    MainPanel.add(meterTypeLabel);
    MainPanel.add(meterTypeField);
    MainPanel.add(new JLabel());
    MainPanel.add(addButton);
  }

  private class AddButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
      String cnic = cnicField.getText().trim();
      String name = nameField.getText().trim();
      String address = addressField.getText().trim();
      String phoneNumber = phoneNumberField.getText().trim();
      String customerType = customerTypeField.getText().trim();
      String meterType = meterTypeField.getText().trim();

      if (validateInputs(cnic, name, address, phoneNumber, customerType, meterType)) {
        BigInteger userCNIC = new BigInteger(cnic);

        if (nadraData.checkCNIC(userCNIC)) {

          if (meterInfo.addMeter(userCNIC)) {
            customerData.Register(userCNIC, name, address, phoneNumber, customerType, meterType);

          }

        }
        if (meterInfo.getMeterNumber(userCNIC) == 3) {
          JOptionPane.showMessageDialog(MainPanel, "Max Meter Limit Reached!");
        } else {
          JOptionPane.showMessageDialog(MainPanel, "Meter and Customer added successfully!");
          nadraData.WriteToFile();
          meterInfo.WriteToFile();
          customerData.WriteToFile();
        }

        clearFields();
      } else {
        JOptionPane.showMessageDialog(MainPanel, "Please fill in all fields correctly.");
      }
    }

    private boolean validateInputs(String cnic, String name, String address, String phoneNumber, String customerType,
        String meterType) {
      if (cnic.isEmpty() || !cnic.matches(CNICregex)) {
        JOptionPane.showMessageDialog(MainPanel, "Invalid CNIC format.");
        return false;
      }

      if (name.isEmpty() || !NameRegex.matcher(name).matches()) {
        JOptionPane.showMessageDialog(MainPanel, "Invalid Name format.");
        return false;
      }

      if (address.isEmpty() || !AddressRegex.matcher(address).matches()) {
        JOptionPane.showMessageDialog(MainPanel, "Invalid Address format.");
        return false;
      }

      if (phoneNumber.isEmpty() || !PhoneRegex.matcher(phoneNumber).matches()) {
        JOptionPane.showMessageDialog(MainPanel, "Invalid Phone Number format.");
        return false;
      }

      if (customerType.isEmpty() || (!customerType.equals(CustomerType1) && !customerType.equals(CustomerType2))) {
        JOptionPane.showMessageDialog(MainPanel, "Invalid Customer Type.");
        return false;
      }

      if (meterType.isEmpty() || (!meterType.equals(MeterType1) && !meterType.equals(MeterType2))) {
        JOptionPane.showMessageDialog(MainPanel, "Invalid Meter Type.");
        return false;
      }

      return true;
    }

    private void clearFields() {
      cnicField.setText("");
      nameField.setText("");
      addressField.setText("");
      phoneNumberField.setText("");
      customerTypeField.setText("");
      meterTypeField.setText("");
    }
  }
}
