package Views.Employee.Login;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class LoginFrame {

  JFrame mainFrame;
  JTextField JUsernameField;
  JTextField JPasswordField;
  JButton JSubmitButton;
  JLabel JUserLabel;
  JLabel JPassLabel;
  JPanel inputPanel;

  public LoginFrame() {
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
    JPasswordField = new JTextField();
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
    mainFrame.add(JSubmitButton, BorderLayout.SOUTH);
  }

  public static void main(String[] args) {
    new LoginFrame();
  }
}
