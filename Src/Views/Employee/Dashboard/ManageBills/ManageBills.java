package Views.Employee.Dashboard.ManageBills;

import Model.Bills;
import Model.Customers;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ManageBills {

  public JPanel MainPanel;
  public JButton AddBillButton;
  public JButton ViewBillsButton;
  public JButton PrintReportButton;

  private JPanel cardPanel; // This will be the panel that changes based on button clicks
  private AddBillPanel addBillPanel;
  private ViewBillsPanel viewBillsPanel;
  private PrintReportPanel printReportPanel;

  Customers customerData;
  Bills billingData;

  public ManageBills(Customers customerData, Bills billingData) {
    this.customerData = customerData;
    this.billingData = billingData;
    init();
  }

  private void init() {
    MainPanel = new JPanel();
    MainPanel.setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    AddBillButton = new JButton("Add Bill");
    ViewBillsButton = new JButton("View Bills");
    PrintReportButton = new JButton("Print Report");

    Dimension buttonSize = new Dimension(150, 40);
    AddBillButton.setPreferredSize(buttonSize);
    ViewBillsButton.setPreferredSize(buttonSize);
    PrintReportButton.setPreferredSize(buttonSize);

    buttonPanel.add(AddBillButton);
    buttonPanel.add(ViewBillsButton);
    buttonPanel.add(PrintReportButton);

    MainPanel.add(buttonPanel, BorderLayout.NORTH);

    addBillPanel = new AddBillPanel(customerData, billingData);

    cardPanel = new JPanel(new BorderLayout());

    cardPanel.add(addBillPanel, BorderLayout.CENTER);

    MainPanel.add(cardPanel, BorderLayout.CENTER);

    AddBillButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        switchPanel(addBillPanel);
      }
    });

    ViewBillsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        viewBillsPanel = new ViewBillsPanel(billingData);
        switchPanel(viewBillsPanel.MainPanel);
      }
    });

    PrintReportButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        printReportPanel = new PrintReportPanel(billingData);

        switchPanel(printReportPanel);
      }
    });
  }

  private void switchPanel(JPanel newPanel) {
    cardPanel.removeAll();
    cardPanel.add(newPanel, BorderLayout.CENTER);
    cardPanel.revalidate();
    cardPanel.repaint();
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Manage Bills");
    ManageBills manageBills = new ManageBills(new Customers(), new Bills());
    frame.setContentPane(manageBills.MainPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(600, 400);
    frame.setVisible(true);
  }
}
