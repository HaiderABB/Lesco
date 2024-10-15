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

  private CardLayout cardLayout;

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

    cardLayout = new CardLayout();

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

    JPanel cardPanel = new JPanel(cardLayout);
    AddBillPanel addBillPanel = new AddBillPanel(customerData, billingData);
    ViewBillsPanel viewBillsPanel = new ViewBillsPanel();
    PrintReportPanel printReportPanel = new PrintReportPanel();

    cardPanel.add(addBillPanel, "AddBillPanel");
    cardPanel.add(viewBillsPanel.MainPanel, "ViewBillsPanel");
    cardPanel.add(printReportPanel, "PrintReportPanel");

    MainPanel.add(cardPanel, BorderLayout.CENTER);

    AddBillButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(cardPanel, "AddBillPanel");
      }
    });

    ViewBillsButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(cardPanel, "ViewBillsPanel");
      }
    });

    PrintReportButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        cardLayout.show(cardPanel, "PrintReportPanel");
      }
    });

    cardLayout.show(cardPanel, "AddBillPanel");
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Manage Bills");
    ManageBills manageBills = new ManageBills(new Customers(), new Bills());
    frame.setContentPane(manageBills.MainPanel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(400, 300);
    frame.setVisible(true);
  }
}
