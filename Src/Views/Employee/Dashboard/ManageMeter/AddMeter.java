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
    MainPanel.setLayout(new GridBagLayout());
    MainPanel.setBackground(EPcolor);
    MainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(5, 5, 5, 5);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    JLabel cnicLabel = new JLabel("CNIC:");
    cnicField = new JTextField();
    cnicField.setPreferredSize(new Dimension(400, 60));

    JLabel nameLabel = new JLabel("Name:");
    nameField = new JTextField();
    nameField.setPreferredSize(new Dimension(400, 60));

    JLabel addressLabel = new JLabel("Address:");
    addressField = new JTextField();
    addressField.setPreferredSize(new Dimension(400, 60));

    JLabel phoneNumberLabel = new JLabel("Phone Number:");
    phoneNumberField = new JTextField();
    phoneNumberField.setPreferredSize(new Dimension(400, 60));

    JLabel customerTypeLabel = new JLabel("Customer Type:");
    customerTypeField = new JTextField();
    customerTypeField.setPreferredSize(new Dimension(400, 60));

    JLabel meterTypeLabel = new JLabel("Meter Type:");
    meterTypeField = new JTextField();
    meterTypeField.setPreferredSize(new Dimension(400, 60));

    addButton = new JButton("Add Meter");

    gbc.gridx = 0;
    gbc.gridy = 0;
    MainPanel.add(cnicLabel, gbc);
    gbc.gridx = 1;
    MainPanel.add(cnicField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 1;
    MainPanel.add(nameLabel, gbc);
    gbc.gridx = 1;
    MainPanel.add(nameField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 2;
    MainPanel.add(addressLabel, gbc);
    gbc.gridx = 1;
    MainPanel.add(addressField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 3;
    MainPanel.add(phoneNumberLabel, gbc);
    gbc.gridx = 1;
    MainPanel.add(phoneNumberField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 4;
    MainPanel.add(customerTypeLabel, gbc);
    gbc.gridx = 1;
    MainPanel.add(customerTypeField, gbc);

    gbc.gridx = 0;
    gbc.gridy = 5;
    MainPanel.add(meterTypeLabel, gbc);
    gbc.gridx = 1;
    MainPanel.add(meterTypeField, gbc);

    gbc.gridx = 1;
    gbc.gridy = 6;
    gbc.gridwidth = 2;
    MainPanel.add(addButton, gbc);

    addButton.addActionListener(new AddButtonListener());
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
