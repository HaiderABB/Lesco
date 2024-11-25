package test.ModelTests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.math.BigInteger;
import Model.Customer;

public class CustomerTest {

  private Customer customer;

  @Before
  public void setUp() {
    customer = new Customer(new BigInteger("1234567890123"), "John Doe", "123 Main St", "123-456-7890",
        "Residential", "Digital", 1);
  }

  @Test
  public void testGetID() {
    assertEquals("ID should match.", 1, customer.getID());
  }

  @Test
  public void testSetID() {
    customer.setID(2);
    assertEquals("ID should be updated.", 2, customer.getID());
  }

  @Test
  public void testGetCNIC() {
    assertEquals("CNIC should match.", new BigInteger("1234567890123"), customer.getCNIC());
  }

  @Test
  public void testSetCNIC() {
    BigInteger newCNIC = new BigInteger("9876543210987");
    customer.setCNIC(newCNIC);
    assertEquals("CNIC should be updated.", newCNIC, customer.getCNIC());
  }

  @Test
  public void testGetName() {
    assertEquals("Name should match.", "John Doe", customer.getName());
  }

  @Test
  public void testSetName() {
    customer.setName("Jane Doe");
    assertEquals("Name should be updated.", "Jane Doe", customer.getName());
  }

  @Test
  public void testGetAddress() {
    assertEquals("Address should match.", "123 Main St", customer.getAddress());
  }

  @Test
  public void testSetAddress() {
    customer.setAddress("456 Elm St");
    assertEquals("Address should be updated.", "456 Elm St", customer.getAddress());
  }

  @Test
  public void testGetPhoneNumber() {
    assertEquals("Phone number should match.", "123-456-7890", customer.getPhoneNumber());
  }

  @Test
  public void testSetPhoneNumber() {
    customer.setPhoneNumber("987-654-3210");
    assertEquals("Phone number should be updated.", "987-654-3210", customer.getPhoneNumber());
  }

  @Test
  public void testGetCustomerType() {
    assertEquals("Customer type should match.", "Residential", customer.getCustomerType());
  }

  @Test
  public void testSetCustomerType() {
    customer.setCustomerType("Commercial");
    assertEquals("Customer type should be updated.", "Commercial", customer.getCustomerType());
  }

  @Test
  public void testGetMeterType() {
    assertEquals("Meter type should match.", "Digital", customer.getMeterType());
  }

  @Test
  public void testSetMeterType() {
    customer.setMeterType("Analog");
    assertEquals("Meter type should be updated.", "Analog", customer.getMeterType());
  }

  @Test
  public void testGetConnectionDate() {
    assertNotNull("Connection date should not be null.", customer.getConnectionDate());
  }

  @Test
  public void testSetConnectionDate() {
    customer.setConnectionDate("01/01/2024");
    assertEquals("Connection date should be updated.", "01/01/2024", customer.getConnectionDate());
  }

  @Test
  public void testGetRegularUnits() {
    assertEquals("Regular units should be 0 initially.", 0.0, customer.getRegularUnits(), 0.001);
  }

  @Test
  public void testSetRegularUnits() {
    customer.setRegularUnits(120.5);
    assertEquals("Regular units should be updated.", 120.5, customer.getRegularUnits(), 0.001);
  }

  @Test
  public void testGetPeakUnits() {
    assertEquals("Peak units should be 0 initially.", 0.0, customer.getPeakUnits(), 0.001);
  }

  @Test
  public void testSetPeakUnits() {
    customer.setPeakUnits(50.0);
    assertEquals("Peak units should be updated.", 50.0, customer.getPeakUnits(), 0.001);
  }
}
