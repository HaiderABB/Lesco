package Views.Employee.Dashboard.ViewExpiry;

import Model.Nadra;
import Model.NadraData;
import Views.DashboardSuper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ViewExpiry extends DashboardSuper {
  public JPanel MainPanel;
  public JTable ExpiredTable;
  public JScrollPane scrollPane;
  public JTextField SearchBar;
  public String[] ColumnNames = {
      "CNIC", "Issue Date", "Expiry Date" };

  public Nadra nadra;
  private int[] originalIndices;
  private ArrayList<NadraData> Expired;

  public ViewExpiry(Nadra n) {
    nadra = n;
    init();
  }

  public void init() {
    MainPanel = new JPanel();
    MainPanel.setLayout(new BorderLayout());
    MainPanel.setBackground(EPcolor);

    SearchBar = new JTextField("Enter CNIC");
    SearchBar.setPreferredSize(new Dimension(700, 40));
    SearchBar.addKeyListener(new java.awt.event.KeyAdapter() {
      public void keyReleased(java.awt.event.KeyEvent evt) {
        String searchTerm = SearchBar.getText().trim();
        filterCustomerData(searchTerm);
      }
    });

    initTable();

    MainPanel.add(SearchBar, BorderLayout.NORTH);
    MainPanel.add(scrollPane, BorderLayout.CENTER);
  }

  public void initTable() {
    DefaultTableModel TaxesModel = new DefaultTableModel(ColumnNames, 0) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return false;
      }
    };

    ExpiredTable = new JTable(TaxesModel);

    loadTaxesData(TaxesModel);

    scrollPane = new JScrollPane(ExpiredTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(70, 10, 10, 10));
    scrollPane.setBackground(EPcolor);

    originalIndices = new int[Expired.size()];
    for (int i = 0; i < Expired.size(); i++) {
      originalIndices[i] = i;
    }
  }

  private void loadTaxesData(DefaultTableModel model) {
    Expired = nadra.getExpired();
    for (NadraData c : Expired) {
      Object[] row = new Object[3];
      row[0] = c.getCNIC();
      row[1] = c.getIssueDate();
      row[2] = c.getExpiryDate();
      model.addRow(row);
    }
  }

  private void filterCustomerData(String searchTerm) {
    DefaultTableModel model = (DefaultTableModel) ExpiredTable.getModel();
    model.setRowCount(0);

    int currentIndex = 0;
    for (NadraData c : Expired) {
      if (String.valueOf(c.getCNIC()).contains(searchTerm)) {
        Object[] row = new Object[3];
        row[0] = c.getCNIC();
        row[1] = c.getIssueDate();
        row[2] = c.getExpiryDate();
        model.addRow(row);

        if (currentIndex < originalIndices.length) {
          originalIndices[currentIndex] = Expired.indexOf(c);
        }
        currentIndex++;
      }
    }
  }
}
