package test.ViewTests.EmployeeTests.DashboardTests;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import Model.Customer;
import Model.Customers;
import Model.MeterInfo;
import Views.Employee.Dashboard.ManageCustomer.ManageCustomer;
import Views.Employee.Dashboard.ManageCustomer.ManageCustomer.ButtonEditor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ManageCustomerTest {

  private ManageCustomer manageCustomer;
  private Customers mockCustomerData;
  private MeterInfo mockMeterInfo;

  @Before
  public void setUp() {
    mockCustomerData = new Customers();
    mockMeterInfo = new MeterInfo();

    mockCustomerData.customers = new ArrayList<>();
    mockCustomerData.customers.add(new Customer(new BigInteger("1234512345671"), "John Doe", "123 Main St",
        "3001234567", "Residential", "Digital", 1));
    mockCustomerData.customers.add(new Customer(new BigInteger("1234512345672"), "Jane Smith", "456 Elm St",
        "3002345678", "Commercial", "Analog", 2));

    manageCustomer = new ManageCustomer(mockCustomerData, mockMeterInfo);
  }

  @Test
  public void testTableInitialization() {
    DefaultTableModel model = (DefaultTableModel) manageCustomer.CustomerTable.getModel();
    assertNotNull("Main Panel should be initialized", manageCustomer.MainPanel);
    assertNotNull("Customer Table should be initialized", manageCustomer.CustomerTable);
    assertEquals("Table should have correct number of columns", manageCustomer.ColumnNames.length,
        model.getColumnCount());
    assertTrue("Table should have rows based on customer data", model.getRowCount() > 0);
  }

  @Test
  public void testEmptySearchResult() {
    manageCustomer.SearchBar.setText("NonExistingID");

    manageCustomer.filterCustomerData("NonExistingID");

    DefaultTableModel model = (DefaultTableModel) manageCustomer.CustomerTable.getModel();

    assertEquals("Search with non-existing CNIC should result in an empty table", 0, model.getRowCount());
  }

  // Test Customer Date Initialization (Connection Date)
  @Test
  public void testConnectionDateInitialization() {
    Customer customer = new Customer(new BigInteger("1234512345673"), "Alice Johnson", "789 Oak St", "3003456789",
        "Residential", "Smart", 3);
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YY");
    String expectedDate = currentDate.format(formatter);
    assertEquals("Connection date should be today's date", expectedDate, customer.getConnectionDate());
  }

  // Test Customer Constructor with Valid Inputs
  @Test
  public void testCustomerConstructorWithValidInputs() {
    BigInteger testCNIC = new BigInteger("1234512345674");
    String testName = "Test Customer";
    String testAddress = "123 Test Street";
    String testPhone = "3009876543";
    String testType = "Residential";
    String testMeterType = "Digital";
    int testID = 4;

    Customer testCustomer = new Customer(testCNIC, testName, testAddress, testPhone, testType, testMeterType, testID);

    assertNotNull("Customer object should be created", testCustomer);
    assertEquals("Customer CNIC should match", testCNIC, testCustomer.getCNIC());
    assertEquals("Customer name should match", testName, testCustomer.getName());
    assertEquals("Customer address should match", testAddress, testCustomer.getAddress());
    assertEquals("Customer phone number should match", testPhone, testCustomer.getPhoneNumber());
    assertEquals("Customer type should match", testType, testCustomer.getCustomerType());
    assertEquals("Customer meter type should match", testMeterType, testCustomer.getMeterType());
    assertEquals("Customer ID should match", testID, testCustomer.getID());
  }
}
