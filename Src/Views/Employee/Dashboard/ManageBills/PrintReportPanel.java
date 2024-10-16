package Views.Employee.Dashboard.ManageBills;

import Model.Bills;
import java.awt.GridLayout;
import javax.swing.*;

public class PrintReportPanel extends JPanel {
  private JTextField reportIdField;
  private JButton printButton;

  Bills B;

  public PrintReportPanel(Bills b) {
    B = b;

    JLabel unpaidLabel = new JLabel("Total Unpaid Bills are " + String.valueOf(B.getUnpaidBills()) + " of total amount "
        + String.valueOf(B.getUnpaidAmount()));
    JLabel paidLabel = new JLabel("Total Paid Bills are " + String.valueOf(B.getPaidBills()) + " of total amount "
        + String.valueOf(B.getPaidAmount()));

    setLayout(new GridLayout(2, 1));
    add(paidLabel);
    add(unpaidLabel);

  }

}
