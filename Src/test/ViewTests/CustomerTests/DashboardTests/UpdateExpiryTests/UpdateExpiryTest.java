package test.ViewTests.CustomerTests.DashboardTests.UpdateExpiryTests;

import Model.Nadra;
import Views.Customer.Dashboard.UpdateExpiry.UpdateExpiry;

import org.junit.Before;
import org.junit.Test;

import javax.swing.JLabel;
import javax.swing.JPanel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.math.BigInteger;

public class UpdateExpiryTest {

  private Nadra mockNadra;
  private BigInteger testCNIC;

  @Before
  public void setUp() {
    mockNadra = mock(Nadra.class); // Mock the Nadra object
    testCNIC = new BigInteger("3520105043723");
  }

  @Test
  public void testUpdateExpiryConstructorInitializesPanel() {
    UpdateExpiry updateExpiry = new UpdateExpiry(mockNadra, testCNIC);

    // Assert that the mainPanel is not null
    assertNotNull(updateExpiry.mainPanel);

    // Assert that the panel contains a JLabel with the expected text
    JPanel mainPanel = updateExpiry.mainPanel;
    assertEquals(1, mainPanel.getComponentCount());
    assertTrue(mainPanel.getComponent(0) instanceof JLabel);

    JLabel label = (JLabel) mainPanel.getComponent(0);
    assertEquals("CNIC Updated Successfully", label.getText());
  }

  @Test
  public void testUpdateExpiryCallsNadraUpdateExpiry() {
    UpdateExpiry updateExpiry = new UpdateExpiry(mockNadra, testCNIC);

    // Verify that Nadra's updateExpiry method was called exactly once with the
    // correct CNIC
    verify(mockNadra, times(1)).updateExpiry(testCNIC);
  }
}
