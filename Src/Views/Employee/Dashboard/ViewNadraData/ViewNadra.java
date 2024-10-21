package Views.Employee.Dashboard.ViewNadraData;

import Model.Nadra;
import Model.NadraData;
import Views.DashboardSuper;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class ViewNadra extends DashboardSuper {
  public JPanel MainPanel;
  public JTable CNICTable;
  public JScrollPane scrollPane;
  public JTextField SearchBar;
  public String[] ColumnNames = {
      "CNIC", "Issue Date", "Expiry Date", "Update" };

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

    DefaultTableModel NadraModel = new DefaultTableModel(ColumnNames, 0);

    loadTaxesData(NadraModel);

    originalIndices = new int[nadra.Data.size()];
    for (int i = 0; i < nadra.Data.size(); i++) {
      originalIndices[i] = i;
    }
    CNICTable = new JTable(NadraModel) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column == 3;
      }
    };
    CNICTable.getColumn("Update").setCellRenderer(new ButtonRenderer());
    CNICTable.getColumn("Update").setCellEditor(new ButtonEditor("Update", true));

    scrollPane = new JScrollPane(CNICTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(70, 10, 10, 10));
    scrollPane.setBackground(EPcolor);

  }

  private void loadTaxesData(DefaultTableModel model) {

    for (NadraData c : nadra.Data) {
      Object[] row = new Object[4];
      row[0] = c.getCNIC();
      row[1] = c.getIssueDate();
      row[2] = c.getExpiryDate();
      row[3] = "Update";
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

  class ButtonRenderer extends JButton implements TableCellRenderer {
    public ButtonRenderer() {
      setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
        int row, int column) {
      setText((value == null) ? "" : value.toString());
      return this;
    }
  }

  class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
    private JButton button;
    private String label;
    private boolean isUpdate;
    private int row;

    public ButtonEditor(String buttonLabel, boolean isUpdate) {
      this.isUpdate = isUpdate;
      button = new JButton(buttonLabel);
      button.addActionListener(this);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
      this.row = table.convertRowIndexToModel(row);
      this.label = (value == null) ? "" : value.toString();
      button.setText(label);
      return button;
    }

    @Override
    public Object getCellEditorValue() {
      return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      if (isUpdate) {
        updateCustomerData();
      }
      fireEditingStopped();
    }

    private void updateCustomerData() {
      NadraData currentData = nadra.Data.get(originalIndices[row]);
      JPanel panel = new JPanel();

      int option = JOptionPane.showConfirmDialog(button, panel, "Update CNIC",
          JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

      if (option == JOptionPane.OK_OPTION) {
        nadra.updateExpiry(currentData.getCNIC());
      }

      DefaultTableModel model = (DefaultTableModel) CNICTable.getModel();
      model.setValueAt(currentData.getIssueDate(), row, 1);
      model.setValueAt(currentData.getExpiryDate(), row, 2);
      JOptionPane.showMessageDialog(button, "CNIC Updated Successfully!");
      nadra.WriteToFile();

    }
  }
}
