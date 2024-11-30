package Views.Customer.Dashboard.ViewBill;

import Model.Bills;
import Model.Customers;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import javax.swing.*;

public class ViewBill {

  public JPanel MainPanel;
  public JButton ViewBillByID;
  public JButton ViewBillByMeter;

  public JPanel cardPanel;
  public BillByID BillByIDPanel;
  public BillByMeter BillByMeterPanel;
  public BigInteger cnic;

  Customers customerData;
  Bills billingData;
  public int userID;

  public ViewBill(Customers customerData, Bills billingData, BigInteger c, int userID) {
    this.customerData = customerData;
    this.billingData = billingData;
    this.cnic = c;

    this.userID = userID;
    init();
  }

  public void init() {
    MainPanel = new JPanel();
    MainPanel.setLayout(new BorderLayout());

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
    ViewBillByID = new JButton("View Bill by ID");
    ViewBillByMeter = new JButton("View Bill by  Meter Number");

    Dimension buttonSize = new Dimension(150, 40);
    ViewBillByID.setPreferredSize(buttonSize);
    ViewBillByMeter.setPreferredSize(buttonSize);

    buttonPanel.add(ViewBillByID);
    buttonPanel.add(ViewBillByMeter);

    MainPanel.add(buttonPanel, BorderLayout.NORTH);

    BillByIDPanel = new BillByID(customerData, cnic, billingData);

    cardPanel = new JPanel(new BorderLayout());

    cardPanel.add(BillByIDPanel.mainPanel, BorderLayout.CENTER);

    MainPanel.add(cardPanel, BorderLayout.CENTER);

    ViewBillByID.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        BillByIDPanel = new BillByID(customerData, cnic, billingData);
        switchPanel(BillByIDPanel.mainPanel);
      }
    });

    ViewBillByMeter.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        BillByMeterPanel = new BillByMeter(billingData, customerData, userID, cnic);
        switchPanel(BillByMeterPanel.mainPanel);
      }
    });

  }

  public void switchPanel(JPanel newPanel) {
    cardPanel.removeAll();
    cardPanel.add(newPanel, BorderLayout.CENTER);
    cardPanel.revalidate();
    cardPanel.repaint();
  }

  public static void main(String[] args) {

  }
}
