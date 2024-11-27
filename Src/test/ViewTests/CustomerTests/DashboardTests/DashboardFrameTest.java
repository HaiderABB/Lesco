package test.ViewTests.CustomerTests.DashboardTests;

import Model.Bills;
import Model.Customers;
import Model.Nadra;
import Views.Customer.Dashboard.DashboardFrame;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.awt.Dimension;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

public class DashboardFrameTest {
  private FrameFixture window;
  private Customers customerData;
  private Bills billData;
  private Nadra nadraData;
  private BigInteger testCNIC;

  @Before
  public void setUp() {
    customerData = new Customers();
    billData = new Bills();
    nadraData = new Nadra();
    testCNIC = new BigInteger("3520105043723");

    DashboardFrame frame = GuiActionRunner.execute(() -> {
      DashboardFrame dashboardFrame = new DashboardFrame(customerData, billData, nadraData, "John Doe", testCNIC, 1);
      // Assign names to components for easier identification
      dashboardFrame.JUpdateCNIC.setName("JUpdateCNIC");
      dashboardFrame.JViewBill.setName("JViewBill");
      dashboardFrame.JLogout.setName("JLogout");
      dashboardFrame.JCustomerName.setName("JCustomerName");
      dashboardFrame.ButtonPanel.setName("ButtonPanel");
      dashboardFrame.EmpPanel.setName("EmpPanel");
      return dashboardFrame;
    });

    window = new FrameFixture(frame.mainFrame);
    window.show(); // Shows the frame
  }

  @After
  public void tearDown() {
    window.cleanUp(); // Clean up resources and close the GUI
  }

  @Test
  public void testWelcomeMessageDisplayedCorrectly() {
    window.label("JCustomerName").requireText("Welcome, John Doe!");
  }

  @Test
  public void testUpdateCNICButtonExistsAndWorks() {
    window.button("JUpdateCNIC").requireVisible().requireText("Update CNIC");
    window.button("JUpdateCNIC").click();
    // Assert SubPanel updates correctly (modify according to your implementation)
    window.panel("SubPanel").requireVisible();
  }

  @Test
  public void testViewBillsButtonExistsAndWorks() {
    window.button("JViewBill").requireVisible().requireText("View Bills");
    window.button("JViewBill").click();
    // Verify that SubPanel is updated correctly
    window.panel("SubPanel").requireVisible();
  }

  public void testMainFrameProperties() {
    // Check the title
    window.requireTitle("Employee Dashboard");

    // Verify the actual size of the frame
    Dimension expectedSize = new Dimension(1920, 1080);
    assertEquals(expectedSize, window.target().getSize());
  }

  @Test
  public void testButtonPanelLayoutAndComponents() {
    window.panel("ButtonPanel").requireVisible();
    window.button("JUpdateCNIC").requireVisible();
    window.button("JViewBill").requireVisible();
  }

  @Test
  public void testEmployeePanelContainsWelcomeMessage() {
    window.panel("EmpPanel").requireVisible();
    window.label("JCustomerName").requireText("Welcome, John Doe!");
  }
}
