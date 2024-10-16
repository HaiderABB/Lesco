package Views.Employee.Dashboard;

import Model.Bills;
import Model.Customers;
import Model.Employees;
import Model.MeterInfo;
import Model.Nadra;
import Views.DashboardSuper;
import Views.Employee.Dashboard.ManageBills.ManageBills;
import Views.Employee.Dashboard.ManageCustomer.ManageCustomer;
import Views.Employee.Dashboard.ManageMeter.AddMeter;
import Views.Employee.Dashboard.ManageTaxes.ManageTaxes;
import Views.Employee.Dashboard.ViewExpiry.ViewExpiry;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

  public JButton JManageCustomer;// done
  public JButton JManageMeter;// done

  public JButton JManageBills;// done
  public JButton JTaxes;// done
  public JButton JExpiry;
  public JButton JNadraData;
  public JButton JLogout;

  public JLabel JEmployeeName;
  public String EmployeeName;

  public ManageCustomer ManageCustomerWindow;
  public AddMeter AddMeterWindow;
  public ManageBills ManageBillsWindow;
  public ManageTaxes ManageTaxesWindow;
  public ViewExpiry ViewExpiryWindow;

  Customers customerData;
  Bills billData;
  MeterInfo meterData;
  Nadra nadraData;

  public DashboardFrame(Bills B,
      Customers C,
      Employees E,
      MeterInfo M,
      Nadra N) {
    setName("Talha Tariq");

    customerData = C;
    billData = B;
    meterData = M;
    nadraData = N;

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
    mainFrame.setVisible(true);
  }

  void initPanels() {
    initEmployeePanel();
    initMainPanel();
    mainFrame.add(MainPanel, BorderLayout.CENTER);
    mainFrame.add(EmpPanel, BorderLayout.WEST);
  }

  void initButtons() {
    JManageCustomer = new JButton("Manage Customers");
    styleButton(JManageCustomer);
    JManageCustomer.addActionListener((ActionEvent e) -> {
      ManageCustomerWindow = new ManageCustomer(customerData, meterData);
      SubPanel.removeAll();
      SubPanel.add(ManageCustomerWindow.MainPanel);
      SubPanel.revalidate();
      SubPanel.repaint();
    });

    JManageMeter = new JButton("Manage Meters");
    styleButton(JManageMeter);
    JManageMeter.addActionListener((ActionEvent e) -> {
      AddMeterWindow = new AddMeter(customerData, nadraData, meterData);
      SubPanel.removeAll();
      SubPanel.add(AddMeterWindow.MainPanel, BorderLayout.CENTER);
      SubPanel.revalidate();
      SubPanel.repaint();
    });

    JManageBills = new JButton("Manage Bills");
    styleButton(JManageBills);
    JManageBills.addActionListener((ActionEvent e) -> {
      ManageBillsWindow = new ManageBills(customerData, billData);
      SubPanel.removeAll();
      SubPanel.add(ManageBillsWindow.MainPanel, BorderLayout.CENTER);
      SubPanel.revalidate();
      SubPanel.repaint();
    });

    JExpiry = new JButton("View Expiry");
    styleButton(JExpiry);
    JExpiry.addActionListener((ActionEvent e) -> {
      ViewExpiryWindow = new ViewExpiry(nadraData);
      SubPanel.removeAll();
      SubPanel.add(ViewExpiryWindow.MainPanel, BorderLayout.CENTER);
      SubPanel.revalidate();
      SubPanel.repaint();
    });

    JTaxes = new JButton("Update Taxes");
    styleButton(JTaxes);
    JTaxes.addActionListener((ActionEvent e) -> {
      ManageTaxesWindow = new ManageTaxes(billData);
      SubPanel.removeAll();
      SubPanel.add(ManageTaxesWindow.MainPanel, BorderLayout.CENTER);
      SubPanel.revalidate();
      SubPanel.repaint();
    });

    JNadraData = new JButton("View Nadra Data");
    styleButton(JNadraData);

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
        customerData.WriteToFile();
        billData.WriteToFile();
        meterData.WriteToFile();
        nadraData.WriteToFile();
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
    JEmployeeName = new JLabel("Welcome, " + EmployeeName + "!");
    JEmployeeName.setForeground(EPcolor);
    Font textFont = new Font("Arial", Font.BOLD, 25);
    JEmployeeName.setFont(textFont);
    j.add(JEmployeeName);
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
    ButtonPanel.setLayout(new GridLayout(2, 3));
    ButtonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    ButtonPanel.add(JManageCustomer);
    ButtonPanel.add(JManageMeter);
    ButtonPanel.add(JManageBills);

    ButtonPanel.add(JTaxes);
    ButtonPanel.add(JExpiry);
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
