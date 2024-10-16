package Views.Customer.Dashboard;

import Model.Bills;
import Model.Customers;
import Model.Employees;
import Model.MeterInfo;
import Model.Nadra;
import Views.Customer.Dashboard.UpdateExpiry.UpdateExpiry;
import Views.Customer.Dashboard.ViewBill.ViewBill;
import Views.DashboardSuper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DashboardFrame extends DashboardSuper {

  public JFrame mainFrame;
  public JPanel EmpPanel;
  public JPanel MainPanel;
  public JPanel ButtonPanel;
  public JPanel SubPanel;

  public JButton JUpdateCNIC;// done
  public JButton JViewBill;// done
  public JButton JLogout; // done

  public JLabel JCustomerName;
  public String EmployeeName;
  public BigInteger CNIC;

  public ViewBill ViewBillWindow;
  public UpdateExpiry UpdateExpiryWindow;

  Bills billData;
  Nadra nadraData;
  Customers customerData;

  public DashboardFrame(Customers C, Bills B,
      Nadra N, String s, BigInteger CNIC) {

    customerData = C;
    billData = B;
    nadraData = N;
    EmployeeName = s;
    this.CNIC = CNIC;

    init();

  }

  void init() {
    mainFrame = new JFrame("Employee Dashboard");
    mainFrame.setSize(1920, 1080);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    mainFrame.setLayout(new BorderLayout());

    initButtons();
    initPanels();
    initSubPanel();

    mainFrame.pack();
  }

  void initPanels() {
    initEmployeePanel();
    initMainPanel();
    mainFrame.add(MainPanel, BorderLayout.CENTER);
    mainFrame.add(EmpPanel, BorderLayout.WEST);
  }

  void initButtons() {
    JUpdateCNIC = new JButton("Update CNIC");
    styleButton(JUpdateCNIC);
    JUpdateCNIC.addActionListener((ActionEvent e) -> {
      UpdateExpiryWindow = new UpdateExpiry(nadraData, CNIC);
      SubPanel.removeAll();
      SubPanel.add(UpdateExpiryWindow.mainPanel, BorderLayout.CENTER);
      SubPanel.revalidate();
      SubPanel.repaint();
    });

    JViewBill = new JButton("View Bills");
    styleButton(JViewBill);
    JViewBill.addActionListener((ActionEvent e) -> {
      ViewBillWindow = new ViewBill(customerData, billData, CNIC);
      SubPanel.removeAll();
      SubPanel.add(ViewBillWindow.MainPanel, BorderLayout.CENTER);
      SubPanel.revalidate();
      SubPanel.repaint();

    });

    JLogout = new JButton("Logout");
    JLogout.setBackground(EPcolor);
    JLogout.setForeground(MPcolor);
    JLogout.setPreferredSize(new Dimension(200, 50));
    JLogout.setFocusPainted(false);
    JLogout.setContentAreaFilled(true);
    JLogout.setOpaque(true);

    JLogout.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {

        mainFrame.dispose();
      }
    });

  }

  void styleButton(JButton button) {
    button.setBackground(MPcolor);
    button.setForeground(EPcolor);
    button.setPreferredSize(new Dimension(200, 50));
    button.setFocusPainted(false);
    button.setContentAreaFilled(true);
    button.setOpaque(true);
  }

  void initEmployeePanel() {
    EmpPanel = new JPanel();
    EmpPanel.setLayout(new BorderLayout());
    EmpPanel.setPreferredSize(new Dimension(420, 1080));
    EmpPanel.setBackground(MPcolor);
    EmpPanel.setBorder(BorderFactory.createLineBorder(MPcolor, 5));
    initEmployeeData();
  }

  void initEmployeeData() {
    JPanel imagePanel = new JPanel(new GridBagLayout());
    imagePanel.setBackground(MPcolor);
    initLescoImage(imagePanel);

    JPanel JWelcomePanel = new JPanel(new FlowLayout());
    JWelcomePanel.setBackground(MPcolor);
    JWelcomePanel.setForeground(EPcolor);
    JWelcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    initEmployeeWelcome(JWelcomePanel);

    EmpPanel.add(imagePanel, BorderLayout.NORTH);
    EmpPanel.add(JLogout, BorderLayout.SOUTH);
    EmpPanel.add(JWelcomePanel, BorderLayout.CENTER);
  }

  void initSubPanel() {
    SubPanel = new JPanel();
    SubPanel.setLayout(new BorderLayout());

    SubPanel.setBackground(EPcolor);
    SubPanel.setForeground(MPcolor);

    JLabel text = new JLabel("Click button to continue");
    text.setFont(new Font("Arial", 2, 34));

    SubPanel.add(text);

    MainPanel.add(SubPanel, BorderLayout.CENTER);
  }

  void initEmployeeWelcome(JPanel j) {
    JCustomerName = new JLabel("Welcome, " + EmployeeName + "!");
    JCustomerName.setForeground(EPcolor);
    Font textFont = new Font("Arial", Font.BOLD, 25);
    JCustomerName.setFont(textFont);
    j.add(JCustomerName);
  }

  void initLescoImage(JPanel imagePanel) {
    ImageIcon LescoImage = new ImageIcon("/home/haider/Desktop/FAST-22/Lesco/Assets/images.png");
    JLabel ImageLabel = new JLabel(LescoImage);

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = 0;
    gbc.weightx = 1;
    gbc.weighty = 1;
    gbc.anchor = GridBagConstraints.CENTER;

    imagePanel.add(ImageLabel, gbc);
  }

  void initMainPanel() {
    MainPanel = new JPanel();
    MainPanel.setLayout(new BorderLayout());
    MainPanel.setPreferredSize(new Dimension(1500, 1080));
    MainPanel.setBackground(EPcolor);

    initButtonPanel();
    MainPanel.add(ButtonPanel, BorderLayout.NORTH);
  }

  void initButtonPanel() {
    ButtonPanel = new JPanel();
    ButtonPanel.setLayout(new GridLayout(1, 2));
    ButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    ButtonPanel.add(JUpdateCNIC);
    ButtonPanel.add(JViewBill);

    ButtonPanel.setBackground(EPcolor);
  }

  void setName(String Name) {
    EmployeeName = Name;
  }

  public static void main(String[] args) {
    Bills B = new Bills();
    Customers C = new Customers();
    Employees E = new Employees();
    MeterInfo M = new MeterInfo();
    Nadra N = new Nadra();
  }
}
