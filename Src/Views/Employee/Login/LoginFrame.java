package Views.Employee.Login;

import Model.Bills;
import Model.Customers;
import Model.Employees;
import Model.MeterInfo;
import Model.Nadra;
import Views.Employee.Dashboard.DashboardFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginFrame {

  public JFrame mainFrame;
  public JTextField JUsernameField;
  JPasswordField JPasswordField; // Changed to JPasswordField
  JButton JSubmitButton;
  JLabel JUserLabel;
  JLabel JPassLabel;
  JPanel inputPanel;
  Employees Employee;
  Customers customerData;
  Bills billData;
  MeterInfo meterData;
  Nadra nadraData;
  DashboardFrame D;

  public LoginFrame(DashboardFrame DF, Bills B,
      Customers C,
      Employees E,
      MeterInfo M,
      Nadra N) {
    D = DF;
    billData = B;
    customerData = C;
    nadraData = N;
    meterData = M;
    D = DF;
    Employee = new Employees();
    init();
  }

  void init() {
    mainFrame = new JFrame("Employee Login");
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setBounds(850, 300, 400, 200);
    mainFrame.setLayout(new BorderLayout());

    initTextFields();

    mainFrame.setVisible(true);
  }

  void initTextFields() {
    inputPanel = new JPanel();
    inputPanel.setLayout(new GridLayout(2, 2, 10, 10));
    inputPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

    JUserLabel = new JLabel("Username:");
    JUsernameField = new JTextField();
    JUsernameField.setPreferredSize(new Dimension(200, 30));

    JPassLabel = new JLabel("Password:");
    JPasswordField = new JPasswordField();
    JPasswordField.setPreferredSize(new Dimension(200, 30));

    inputPanel.add(JUserLabel);
    inputPanel.add(JUsernameField);
    inputPanel.add(JPassLabel);
    inputPanel.add(JPasswordField);

    mainFrame.add(inputPanel, BorderLayout.CENTER);

    Color EPcolor = new Color(211, 211, 211, 255);
    Color MPcolor = new Color(0, 71, 171, 255);

    JSubmitButton = new JButton("Submit");
    JSubmitButton.setPreferredSize(new Dimension(20, 40));
    JSubmitButton.setBackground(MPcolor);
    JSubmitButton.setForeground(EPcolor);
    JSubmitButton.addActionListener((ActionEvent e) -> {
      if (Employee.LogIn(JUsernameField.getText(), JPasswordField.getText())) {
        D = new DashboardFrame(billData, customerData, Employee, meterData, nadraData, JUsernameField.getText());
        D.mainFrame.setVisible(true);

      }
    });

    mainFrame.add(JSubmitButton, BorderLayout.SOUTH);
  }

  public static void main(String[] args) {

  }
}
