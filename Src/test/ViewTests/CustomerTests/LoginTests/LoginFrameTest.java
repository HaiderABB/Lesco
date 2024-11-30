package test.ViewTests.CustomerTests.LoginTests;

import Model.Bills;
import Model.Customers;
import Model.Nadra;
import Views.Customer.Dashboard.DashboardFrame;
import Views.Customer.Login.LoginFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.math.BigInteger;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LoginFrameTest {

  private LoginFrame loginFrame;
  private Customers mockCustomerData;
  private Bills mockBillData;
  private Nadra mockNadraData;
  private DashboardFrame mockDashboardFrame;

  @BeforeEach
  void setUp() {
    mockCustomerData = mock(Customers.class);
    mockBillData = mock(Bills.class);
    mockNadraData = mock(Nadra.class);
    mockDashboardFrame = mock(DashboardFrame.class);

    loginFrame = new LoginFrame(mockDashboardFrame, mockBillData, mockCustomerData, mockNadraData);
  }

  @Test
  void testInitComponentsAreInitialized() {
    assertNotNull(loginFrame.mainFrame);
    assertNotNull(loginFrame.JUsernameField);
    assertNotNull(loginFrame.JPasswordField);
    assertNotNull(loginFrame.JSubmitButton);
    assertNotNull(loginFrame.inputPanel);
  }

  @Test
  void testSuccessfulLogin() {
    int validId = 12345;
    BigInteger validCnic = new BigInteger("1234567890123");

    when(mockCustomerData.LogIn(validId, validCnic)).thenReturn(true);

    loginFrame.JUsernameField.setText(String.valueOf(validId));
    loginFrame.JPasswordField.setText(validCnic.toString());

    loginFrame.JSubmitButton.doClick();

    verify(mockCustomerData, times(1)).LogIn(validId, validCnic);
    assertNotNull(loginFrame.D);
    verify(mockDashboardFrame, times(1)).mainFrame.setVisible(true);
  }

  @Test
  void testInvalidIdInput() {
    loginFrame.JUsernameField.setText("invalid-id");
    loginFrame.JPasswordField.setText("1234567890123");

    Exception exception = assertThrows(NumberFormatException.class, () -> loginFrame.JSubmitButton.doClick());

    assertEquals("For input string: \"invalid-id\"", exception.getMessage());
    verify(mockCustomerData, never()).LogIn(anyInt(), any(BigInteger.class));
  }

  @Test
  void testInvalidCnicInput() {
    int validId = 12345;
    loginFrame.JUsernameField.setText(String.valueOf(validId));
    loginFrame.JPasswordField.setText("invalid-cnic");

    Exception exception = assertThrows(NumberFormatException.class, () -> loginFrame.JSubmitButton.doClick());

    assertEquals("For input string: \"invalid-cnic\"", exception.getMessage());
    verify(mockCustomerData, never()).LogIn(anyInt(), any(BigInteger.class));
  }

  @Test
  void testFailedLogin() {
    int invalidId = 99999;
    BigInteger invalidCnic = new BigInteger("9876543210123");

    when(mockCustomerData.LogIn(invalidId, invalidCnic)).thenReturn(false);

    loginFrame.JUsernameField.setText(String.valueOf(invalidId));
    loginFrame.JPasswordField.setText(invalidCnic.toString());

    loginFrame.JSubmitButton.doClick();

    verify(mockCustomerData, times(1)).LogIn(invalidId, invalidCnic);
    assertNull(loginFrame.D);
    verify(mockDashboardFrame, never()).mainFrame.setVisible(true);
  }

  @Test
  void testLoginButtonActionListenerExists() {
    assertTrue(loginFrame.JSubmitButton.getActionListeners().length > 0);
  }

  @Test
  void testFrameProperties() {
    JFrame frame = loginFrame.mainFrame;
    assertEquals("Customer Login", frame.getTitle());
    assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
    assertTrue(frame.isVisible());
  }
}
