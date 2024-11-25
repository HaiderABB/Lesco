package test.ModelTests;

import org.mockito.Mockito;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;

import Model.Employees;

public class EmployeesTest {

  private Employees employees;

  @Before
  public void setUp() {
    employees = new Employees();
    employees.EmployeesData.clear();
  }

  @Test
  public void testRegistration_Success() {
    boolean result = employees.Registration("newUser", "password123");
    assertTrue(result);
    assertTrue(employees.EmployeesData.containsKey("newUser"));
  }

  @Test
  public void testRegistration_Failure() {
    employees.Registration("existingUser", "password123");
    boolean result = employees.Registration("existingUser", "newPassword");
    assertFalse(result);
  }

  @Test
  public void testLogIn_Success() {
    employees.Registration("validUser", "validPassword");
    boolean result = employees.LogIn("validUser", "validPassword");
    assertTrue(result);
  }

  @Test
  public void testLogIn_Failure_InvalidUsername() {
    employees.Registration("validUser", "validPassword");
    boolean result = employees.LogIn("invalidUser", "validPassword");
    assertFalse(result);
  }

  @Test
  public void testLogIn_Failure_InvalidPassword() {
    employees.Registration("validUser", "validPassword");
    boolean result = employees.LogIn("validUser", "wrongPassword");
    assertFalse(result);
  }

  @Test
  public void testUpdatePassword_Success() {
    employees.Registration("validUser", "validPassword");
    boolean result = employees.UpdatePassword("validUser", "newPassword");
    assertTrue(result);
    assertEquals("newPassword", employees.EmployeesData.get("validUser"));
  }

  @Test
  public void testUpdatePassword_Failure() {
    boolean result = employees.UpdatePassword("invalidUser", "newPassword");
    assertFalse(result);
  }

  @Test
  public void testReadFromFile() {
    Employees mockEmployees = Mockito.spy(new Employees());
    try {
      mockEmployees.ReadFromFile();
    } catch (Exception e) {
      fail();
    }
    assertFalse(mockEmployees.EmployeesData.isEmpty());
  }

}
