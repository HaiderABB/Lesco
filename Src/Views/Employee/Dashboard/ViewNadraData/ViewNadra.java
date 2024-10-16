package Views.Employee.Dashboard.ViewNadraData;

import Model.Nadra;
import Model.NadraData;
import Views.DashboardSuper;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ViewNadra extends DashboardSuper {
  public JPanel MainPanel;
  public JTable CNICTable;
  public JScrollPane scrollPane;
  public JTextField SearchBar;
  public String[] ColumnNames = {
      "CNIC", "Issue Date", "Expiry Date" };

  public Nadra nadra;
  private int[] originalIndices;

  public ViewNadra(Nadra n) {
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

    CNICTable = new JTable(TaxesModel); // Initialize CNICTable with the model

    loadTaxesData(TaxesModel);

    // Initialize the scroll pane with the table
    scrollPane = new JScrollPane(CNICTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(70, 10, 10, 10));
    scrollPane.setBackground(EPcolor);

    // Initialize original indices array
    originalIndices = new int[nadra.Data.size()];
    for (int i = 0; i < nadra.Data.size(); i++) {
      originalIndices[i] = i;
    }
  }

  private void loadTaxesData(DefaultTableModel model) {

    for (NadraData c : nadra.Data) {
      Object[] row = new Object[3];
      row[0] = c.getCNIC();
      row[1] = c.getIssueDate();
      row[2] = c.getExpiryDate();
      model.addRow(row);
    }
  }

  private void filterCustomerData(String searchTerm) {
    DefaultTableModel model = (DefaultTableModel) CNICTable.getModel();
    model.setRowCount(0);

    int currentIndex = 0;
    for (NadraData c : nadra.Data) {
      if (String.valueOf(c.getCNIC()).contains(searchTerm)) {
        Object[] row = new Object[3];
        row[0] = c.getCNIC();
        row[1] = c.getIssueDate();
        row[2] = c.getExpiryDate();
        model.addRow(row);

        // Update original indices
        if (currentIndex < originalIndices.length) {
          originalIndices[currentIndex] = nadra.Data.indexOf(c);
        }
        currentIndex++;
      }
    }
  }
}
