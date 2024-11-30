package test.ViewTests.EmployeeTests.DashboardTests;

import Model.Bills;
import Model.Customers;
import Views.Employee.Dashboard.ManageBills.AddBillPanel;

import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

public class AddBillPanelTest {

  private AddBillPanel addBillPanel;
  private Customers mockCustomerData;
  private Bills mockBillingData;

  @Before
  public void setUp() {
    mockCustomerData = mock(Customers.class);
    mockBillingData = mock(Bills.class);
    addBillPanel = new AddBillPanel(mockCustomerData, mockBillingData);
  }

  @Test
  public void testComponentsInitialized() {
    assertNotNull("Customer ID field is not initialized.", getComponent(addBillPanel, JTextField.class));
    assertNotNull("Submit button is not initialized.", getComponent(addBillPanel, JButton.class));
  }

  @Test
  public void testEmptyCustomerIdValidation() {
    JTextField customerIdField = getComponent(addBillPanel, JTextField.class);
    customerIdField.setText("");

    boolean result = invokeValidation("");
    assertFalse("Validation should fail for an empty Customer ID.", result);
    verify(mockCustomerData, never()).checkID(anyInt());
  }

  @Test
  public void testInvalidCustomerId_NegativeNumber() {
    boolean result = invokeValidation("-1");
    assertFalse("Validation should fail for a negative Customer ID.", result);
    verify(mockCustomerData, never()).checkID(anyInt());
  }

  @Test
  public void testNonexistentCustomerId() {
    when(mockCustomerData.checkID(12345)).thenReturn(false);
    boolean result = invokeValidation("12345");
    assertFalse("Validation should fail for a nonexistent Customer ID.", result);
    verify(mockCustomerData, times(1)).checkID(12345);
  }

  @Test
  public void testBillAlreadyExists() {
    when(mockBillingData.CheckBill(12345)).thenReturn(true);

    simulateSubmit("12345");
    verify(mockBillingData, never()).SinglePhaseDomesticBill(anyInt(), anyDouble());
    verify(mockBillingData, never()).WriteToFile();
  }

  @Test
  public void testValidation_FailureDoesNotProceed() {
    simulateSubmit("");
    verify(mockCustomerData, never()).getMeterType(anyInt());
    verify(mockBillingData, never()).CheckBill(anyInt());
  }

  private void mockCustomerBehavior(int id, String meterType, String customerType, double regularReading,
      double peakReading) {
    when(mockCustomerData.checkID(id)).thenReturn(true);
    when(mockCustomerData.getMeterType(id)).thenReturn(meterType);
    when(mockCustomerData.getCustomerType(id)).thenReturn(customerType);
    when(mockCustomerData.GetRegularReading(id)).thenReturn(regularReading);
    when(mockCustomerData.GetPeakReading(id)).thenReturn(peakReading);
  }

  private void simulateSubmit(String customerId) {
    JTextField customerIdField = getComponent(addBillPanel, JTextField.class);
    customerIdField.setText(customerId);

    JButton submitButton = getComponent(addBillPanel, JButton.class);
    submitButton.doClick();
  }

  private boolean invokeValidation(String customerId) {
    try {
      return (boolean) AddBillPanel.class
          .getDeclaredMethod("validateInputs", String.class)
          .invoke(addBillPanel, customerId);
    } catch (Exception e) {
      fail("Reflection error during validation: " + e.getMessage());
      return false;
    }
  }

  private void assertFieldsCleared() {
    JTextField customerIdField = getComponent(addBillPanel, JTextField.class);
    assertEquals("", customerIdField.getText(), "Fields were not cleared after successful submission.");
  }

  @SuppressWarnings("unchecked")
  private <T extends JComponent> T getComponent(JPanel panel, Class<T> componentType) {
    for (Component component : panel.getComponents()) {
      if (componentType.isInstance(component)) {
        return (T) component;
      }
    }
    throw new IllegalStateException("Component not found: " + componentType.getSimpleName());
  }
}
