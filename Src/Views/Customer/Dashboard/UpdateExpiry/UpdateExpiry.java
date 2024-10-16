package Views.Customer.Dashboard.UpdateExpiry;

import Model.Nadra;
import java.math.BigInteger;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class UpdateExpiry {

  public Nadra N;

  public JPanel mainPanel;

  public UpdateExpiry(Nadra nadraData, BigInteger CNIC) {
    N = nadraData;
    mainPanel = new JPanel();
    nadraData.updateExpiry(CNIC);
    JLabel p = new JLabel("CNIC Updated Successfully");
    mainPanel.add(p);
  }

}
