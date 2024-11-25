package test.ModelTests;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.math.BigInteger;
import Model.Customer;
import Model.Customers;

public class CustomersTest {

  private Customers customers;

  @Before
  public void setUp() {
    customers = new Customers();
  }

  @Test
  public void testRegister() {
    BigInteger cnic = new BigInteger("1234567890123");
    String name = "Alice";
    String address = "456 Oak St";
    String phone = "555-123-4567";
    String customerType = "Residential";
    String meterType = "Digital";

    customers.Register(cnic, name, address, phone, customerType, meterType);

    // Assuming the last registered customer will have ID 14 (based on your provided
    // data).
    assertTrue("Customer should be added.", customers.checkID(14));
  }

  @Test
  public void testLogIn() {
    BigInteger cnic = new BigInteger("3520105043723"); // Haider Abbas Moazzam's CNIC
    int id = 11; // Haider's ID

    boolean result = customers.LogIn(id, cnic);
    assertTrue("LogIn should be successful.", result);
  }

  @Test
  public void testCheckCNIC() {
    BigInteger cnic = new BigInteger("3520105043723"); // Haider Abbas Moazzam's CNIC

    boolean exists = customers.CheckCNIC(cnic);

    assertTrue("CNIC should exist.", exists);
  }

  @Test
  public void testCheckName() {
    String name = "Haider Abbas Moazzam";

    boolean exists = customers.CheckName(name);

    assertTrue("Name should exist.", exists);
  }

  @Test
  public void testCheckAddress() {
    String address = "Askari 10";

    boolean exists = customers.CheckAddress(address);

    assertTrue("Address should exist.", exists);
  }

  @Test
  public void testCheckPhone() {
    String phone = "03219306126";

    boolean exists = customers.CheckPhone(phone);

    assertTrue("Phone number should exist.", exists);
  }

  @Test
  public void testGetRegularReading() {
    int id = 11; // Haider Abbas Moazzam's ID

    double reading = customers.GetRegularReading(id);

    assertTrue("Regular reading should be greater than 0.", reading > 0);
  }

  @Test
  public void testGetPeakReading() {
    int id = 11; // Haider Abbas Moazzam's ID

    double reading = customers.GetPeakReading(id);

    assertTrue("Peak reading should be greater than 0.", reading > 0);
  }

  @Test
  public void testRemoveCustomer() {
    int id = 11; // Haider Abbas Moazzam's ID

    customers.removeCustomer(id);

    assertFalse("Customer should be removed.", customers.checkID(id));
  }

  @Test
  public void testGetMeterType() {
    int id = 11; // Haider Abbas Moazzam's ID
    String meterType = customers.getMeterType(id);

    assertEquals("Meter type should match.", "Single Phase", meterType);
  }

  @Test
  public void testGetCustomerType() {
    int id = 11; // Haider Abbas Moazzam's ID
    String customerType = customers.getCustomerType(id);

    assertEquals("Customer type should match.", "Domestic", customerType);
  }

  @Test
  public void testPrintCustomer() {
    int id = 11; // Haider Abbas Moazzam's ID
    BigInteger cnic = new BigInteger("3520105043723"); // Haider Abbas Moazzam's CNIC
    Object[] row = new Object[7];

    customers.printCustomer(id, cnic, row);

    assertNotNull("Row should be populated with customer ID.", row[0]);
    assertNotNull("Row should be populated with CNIC.", row[1]);
    assertNotNull("Row should be populated with customer name.", row[2]);
    assertNotNull("Row should be populated with customer address.", row[3]);
    assertNotNull("Row should be populated with customer phone number.", row[4]);
    assertNotNull("Row should be populated with customer type.", row[5]);
    assertNotNull("Row should be populated with meter type.", row[6]);

    // Verifying expected customer values
    assertEquals("Customer type should match.", "Domestic", row[5]);
    assertEquals("Meter type should match.", "Single Phase", row[6]);
  }

  // Additional test case to check behavior of methods on invalid data (e.g.,
  // removing non-existent customer)
  @Test
  public void testRemoveNonExistentCustomer() {
    int nonExistentId = 99999;

    customers.removeCustomer(nonExistentId);

    assertFalse("Non-existent customer should not be removed.", customers.checkID(nonExistentId));
  }

  // Test to check CNIC duplication during registration
  @Test
  public void testRegisterDuplicateCNIC() {
    BigInteger cnic = new BigInteger("3520105043723"); // Haider Abbas Moazzam's CNIC
    String name = "Bob";
    String address = "789 Pine St";
    String phone = "555-987-6543";
    String customerType = "Commercial";
    String meterType = "Analog";

    customers.Register(cnic, name, address, phone, customerType, meterType);

    boolean exists = customers.CheckCNIC(cnic);
    assertTrue("Duplicate CNIC should be detected.", exists);
  }
}
