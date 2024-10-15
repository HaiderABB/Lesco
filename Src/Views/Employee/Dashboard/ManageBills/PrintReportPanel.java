package Views.Employee.Dashboard.ManageBills;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrintReportPanel extends JPanel {
  private JTextField reportIdField;
  private JButton printButton;

  public PrintReportPanel() {
    setLayout(new GridLayout(2, 2, 10, 10));

    add(new JLabel("Report ID:"));
    reportIdField = new JTextField();
    add(reportIdField);

    printButton = new JButton("Print Report");
    add(printButton);

    printButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String reportId = reportIdField.getText().trim();
        if (!reportId.isEmpty()) {
          // Simulate printing the report
          JOptionPane.showMessageDialog(PrintReportPanel.this, "Report " + reportId + " printed successfully!");
          clearFields();
        } else {
          JOptionPane.showMessageDialog(PrintReportPanel.this, "Report ID cannot be empty.");
        }
      }
    });
  }

  private void clearFields() {
    reportIdField.setText("");
  }
}
