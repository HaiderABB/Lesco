package Views.Employee.Dashboard.ManageTaxes;

import Model.Bills;
import Model.TariffTaxInfo;
import Views.DashboardSuper;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

public class ManageTaxes extends DashboardSuper {

  public JPanel MainPanel;
  public JTable TaxesTable;
  public JTextField SearchBar;
  public JScrollPane scrollPane;
  public String[] ColumnNames = {
      "Customer Type", "Phase", "Price of Regular Units", "Price of Peak Units", "Tax Rate", "Fixed Charges",
      "Update" };
  public Bills BillsData;

  public ManageTaxes(Bills B) {
    BillsData = B;
    init();
  }

  private int[] originalIndices;

  public void init() {
    MainPanel = new JPanel();
    MainPanel.setLayout(new BorderLayout());
    MainPanel.setBackground(EPcolor);
    SearchBar = new JTextField("Enter Customer Type");
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
    DefaultTableModel TaxesModel = new DefaultTableModel(ColumnNames, 0);
    loadTaxesData(TaxesModel);
    originalIndices = new int[BillsData.Taxes.size()];
    for (int i = 0; i < BillsData.Taxes.size(); i++) {
      originalIndices[i] = i;
    }
    TaxesTable = new JTable(TaxesModel) {
      @Override
      public boolean isCellEditable(int row, int column) {
        return column == 6;
      }
    };
    TaxesTable.getColumn("Update").setCellRenderer(new ButtonRenderer());
    TaxesTable.getColumn("Update").setCellEditor(new ButtonEditor("Update", true));

    scrollPane = new JScrollPane(TaxesTable);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(70, 10, 10, 10));
    scrollPane.setBackground(EPcolor);
  }

  private void loadTaxesData(DefaultTableModel model) {
    for (TariffTaxInfo c : BillsData.Taxes) {
      Object[] row = new Object[7];
      row[0] = c.getCustomerType();
      row[1] = c.getPhase();
      row[2] = c.getRegularUnitsPrice();
      row[3] = c.getPeakUnitsPrice();
      row[4] = c.getTaxRate();
      row[5] = c.getFixedCharges();
      row[6] = "Update";
      model.addRow(row);
    }
  }

  private void filterCustomerData(String searchTerm) {
    DefaultTableModel model = (DefaultTableModel) TaxesTable.getModel();
    model.setRowCount(0);
    int currentIndex = 0;
    for (TariffTaxInfo c : BillsData.Taxes) {
      if (String.valueOf(c.getCustomerType()).contains(searchTerm)) {
        Object[] row = new Object[7];
        row[0] = c.getCustomerType();
        row[1] = c.getPhase();
        row[2] = c.getRegularUnitsPrice();
        row[3] = c.getPeakUnitsPrice();
        row[4] = c.getTaxRate();
        row[5] = c.getFixedCharges();
        row[6] = "Update";
        model.addRow(row);
        originalIndices[currentIndex] = BillsData.Taxes.indexOf(c);
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
      TariffTaxInfo currentTax = BillsData.Taxes.get(originalIndices[row]);
      JPanel panel = new JPanel();
      JTextField[] fields = new JTextField[1];
      if (currentTax.getPhase().equals("1Phase")) {
        fields = new JTextField[3];
        panel.setLayout(new GridLayout(fields.length, 2));

        fields[0] = new JTextField();
        fields[1] = new JTextField();
        fields[2] = new JTextField();
        int j = 0;
        for (int i = 2; i < 6; i++) {
          if (i != 3) {
            panel.add(new JLabel(ColumnNames[i]));
            panel.add(fields[j]);
            ++j;
          }

        }

        fields[0].setText(String.valueOf(currentTax.getRegularUnitsPrice()));
        fields[1].setText(String.valueOf(currentTax.getTaxRate()));
        fields[2].setText(String.valueOf(currentTax.getFixedCharges()));

      } else {
        fields = new JTextField[4];
        System.out.println(ColumnNames.length);
        panel.setLayout(new GridLayout(fields.length, 2));
        int j = 2;
        for (int i = 0; i < fields.length; i++) {
          fields[i] = new JTextField();
          panel.add(new JLabel(ColumnNames[j]));
          panel.add(fields[i]);
          ++j;
        }
        fields[0].setText(String.valueOf(currentTax.getRegularUnitsPrice()));
        fields[1].setText(String.valueOf(currentTax.getPeakUnitsPrice()));
        fields[2].setText(String.valueOf(currentTax.getTaxRate()));
        fields[3].setText(String.valueOf(currentTax.getFixedCharges()));
      }

      int option = JOptionPane.showConfirmDialog(button, panel, "Update TariffTaxInfo Information",
          JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

      if (option == JOptionPane.OK_OPTION) {

        if (currentTax.getPhase().equals("1Phase")) {
          if (Double.parseDouble(fields[0].getText()) > 0.0) {
            currentTax.setRegularUnitsPrice(Double.parseDouble(fields[0].getText()));

          }

          if (Double.parseDouble(fields[1].getText()) > 0.0) {
            currentTax.setTaxRate(Double.parseDouble(fields[1].getText()));

          }
          if (Double.parseDouble(fields[2].getText()) > 0.0) {
            currentTax.setFixedCharges(Double.parseDouble(fields[2].getText()));

          }
        } else {
          if (Double.parseDouble(fields[0].getText()) > 0.0) {
            currentTax.setRegularUnitsPrice(Double.parseDouble(fields[0].getText()));

          }
          if (Double.parseDouble(fields[1].getText()) > 0.0) {
            currentTax.setPeakUnitsPrice(Double.parseDouble(fields[1].getText()));

          }
          if (Double.parseDouble(fields[2].getText()) > 0.0) {
            currentTax.setTaxRate(Double.parseDouble(fields[2].getText()));

          }
          if (Double.parseDouble(fields[3].getText()) > 0.0) {
            currentTax.setFixedCharges(Double.parseDouble(fields[3].getText()));

          }

        }

        DefaultTableModel model = (DefaultTableModel) TaxesTable.getModel();
        model.setValueAt(currentTax.getPhase(), row, 1);
        model.setValueAt(currentTax.getRegularUnitsPrice().toString(), row, 2);
        model.setValueAt(currentTax.getPeakUnitsPrice(), row, 3);
        model.setValueAt(currentTax.getTaxRate(), row, 4);
        model.setValueAt(currentTax.getFixedCharges(), row, 5);
        JOptionPane.showMessageDialog(button, "Tariff Taxes updated successfully!");
        BillsData.WriteTaxes();

      } else {
        JOptionPane.showMessageDialog(button, "Could not update the customer details.");
      }
    }
  }

}
