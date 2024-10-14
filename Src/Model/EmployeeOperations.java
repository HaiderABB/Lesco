package Model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmployeeOperations {

  Customers c;
  MeterInfo m;
  Bills b;
  Nadra n;

  Scanner scanner = new Scanner(System.in);

  String cnicInput;
  String CNICregex = "^[0-9]{13}$";
  String nameRegex = "^[A-Za-z]+([ '-][A-Za-z]+)*$";
  String AddressRegex = "^[A-Za-z0-9 .-]+$";
  String PhoneRegex = "^\\d{11}$";

  Pattern pattern = Pattern.compile(AddressRegex);
  Pattern namePattern = Pattern.compile(nameRegex);

  EmployeeOperations() {
  }

  public EmployeeOperations(Customers C, MeterInfo M, Bills B, Nadra N) {
    this.c = C;
    this.m = M;
    this.b = B;
    this.n = N;
  }

  public void AddBill() {
    int ID;
    System.out.println("Enter the ID of the customer");
    ID = scanner.nextInt();
    scanner.nextLine();

    while (ID < 0) {
      System.out.println("ID cannot be less than zero");
      ID = scanner.nextInt();
      scanner.nextLine();
    }

    if (!c.checkID(ID)) {
      while (!c.checkID(ID)) {
        System.out.println("Enter correct ID, user does not exist or -1 to EXIT ");
        ID = scanner.nextInt();
        scanner.nextLine();
        if (ID == -1) {
          return;
        }
      }
    }

    if (b.CheckBill(ID)) {
      if (c.getMeterType(ID).equals("Single Phase") && c.getCustomerType(ID).equals("Domestic")) {
        Double CurrentReading = c.GetRegularReading(ID);
        b.SinglePhaseDomesticBill(ID, CurrentReading);

      } else if (c.getMeterType(ID).equals("Single Phase") && c.getCustomerType(ID).equals("Commercial")) {
        Double CurrentReading = c.GetRegularReading(ID);
        b.SinglePhaseCommercialBill(ID, CurrentReading);
      } else if (c.getMeterType(ID).equals("Three Phase") && c.getCustomerType(ID).equals("Domestic")) {

        Double CurrentReading = c.GetRegularReading(ID);
        Double PeakReading = c.GetPeakReading(ID);
        b.ThreePhaseDomesticBill(ID, CurrentReading, PeakReading);

      } else if (c.getMeterType(ID).equals("Three Phase") && c.getCustomerType(ID).equals("Commercial")) {

        Double CurrentReading = c.GetRegularReading(ID);
        Double PeakReading = c.GetPeakReading(ID);
        b.ThreePhaseCommercialBill(ID, CurrentReading, PeakReading);

      }
      System.out.println("Bill Added Successfully");

    } else {
      System.out.println("Bill already exists for this customer in this month");
    }

    System.out.println("------------------------------------------");

  }

  public void AddMeter() {

    System.out.println("Enter 13 Digit CNIC:");
    cnicInput = scanner.nextLine();
    while (!cnicInput.matches(CNICregex)) {
      System.out.println("Enter Correct 13 digit CNIC");
      cnicInput = scanner.nextLine();
    }

    System.out.println("Enter Name of the Customer :");
    String name = scanner.nextLine();
    Matcher matcher = namePattern.matcher(name);
    while (!matcher.matches()) {
      System.out.println("Name can only include Alphabets");
      name = scanner.nextLine();
      matcher = namePattern.matcher(name);

    }

    System.out.println("Enter Address of the customer, without commas :");
    String Address;
    Address = scanner.nextLine();
    matcher = pattern.matcher(Address);

    while (!matcher.matches()) {
      System.out.println("Invalid address. Please enter a valid address without commas.");
      Address = scanner.nextLine();
      matcher = pattern.matcher(Address);
    }

    System.out.println("Enter 11 digit Phone Number of the user:");
    String phone = scanner.nextLine();
    Pattern Phonepattern = Pattern.compile(PhoneRegex);
    matcher = Phonepattern.matcher(phone);
    while (!matcher.matches() || c.CheckPhone(phone)) {
      System.out.println("Invalid Number Format (11 Digit) , or Phone exists already");
      phone = scanner.nextLine();
      matcher = Phonepattern.matcher(phone);
    }

    System.out.println("Enter Customer Type (Commercial or Domestic): ");
    String customerType = scanner.nextLine();

    while (!customerType.equalsIgnoreCase("Commercial") && !customerType.equalsIgnoreCase("Domestic")) {
      System.out.println("Enter Correct Customer Type (Commercial or Domestic): ");
      customerType = scanner.nextLine();
    }

    if (customerType.equalsIgnoreCase("Commercial")) {
      customerType = "Commercial";
    } else if (customerType.equalsIgnoreCase("Domestic")) {
      customerType = "Domestic";
    }

    System.out.println("Enter Meter Type (Single Phase or Three Phase): ");
    String meterType = scanner.nextLine();

    while (!meterType.equalsIgnoreCase("Single Phase") && !meterType.equalsIgnoreCase("Three Phase")) {
      System.out.println("Enter Correct Meter Type (Single Phase or Three Phase): ");
      meterType = scanner.nextLine();
    }

    if (meterType.equalsIgnoreCase("Single Phase")) {
      meterType = "Single Phase";
    } else if (meterType.equalsIgnoreCase("Three Phase")) {
      meterType = "Three Phase";
    }

    BigInteger userCNIC = new BigInteger(cnicInput);

    if (n.checkCNIC(userCNIC)) {

      if (m.addMeter(userCNIC)) {
        c.Register(userCNIC, name, Address, phone, customerType, meterType);
        System.out.println("User Registered Successfully");
      }

    }

    System.out.println("------------------------------------------");

  }

  public void UpdateCustomer() {
    Scanner scanner = new Scanner(System.in);
    int choice;
    do {
      System.out.println("\n--- Update Customer Info ---");
      System.out.println("1. Select Customer");
      System.out.println("0. Exit");

      System.out.print("Enter your choice: ");
      choice = scanner.nextInt();
      scanner.nextLine();

      if (choice == 1) {
        System.out.println("Select a customer by ID:");
        for (Customer customer : c.customers) {
          System.out.println(customer.ID + ": " + customer.name);
        }

        int selectedID = scanner.nextInt();
        scanner.nextLine();
        Customer selectedCustomer = null;

        for (Customer customer : c.customers) {
          if (customer.ID == selectedID) {
            selectedCustomer = customer;
            break;
          }
        }

        if (selectedCustomer != null) {
          int updateChoice;
          do {
            System.out.println("\nUpdate Options:");
            System.out.println("1. Update Name");
            System.out.println("2. Update Address");
            System.out.println("3. Update Phone Number");
            System.out.println("4. Update Customer Type");
            System.out.println("5. Update Meter Type");
            System.out.println("6. Update Regular Units");
            System.out.println("7. Update Peak Units");
            System.out.println("0. Back to Main Menu");

            System.out.print("Enter your choice: ");
            updateChoice = scanner.nextInt();
            scanner.nextLine();

            switch (updateChoice) {
              case 1:
                System.out.print("Enter new Name: ");
                String newName = scanner.nextLine();
                Matcher matcher = namePattern.matcher(newName);
                while (!matcher.matches()) {
                  System.out.println("Name can only include Alphabets");
                  newName = scanner.nextLine();
                  matcher = namePattern.matcher(newName);
                }
                selectedCustomer.setName(newName);
                System.out.println("Name updated.");
                break;
              case 2:
                System.out.print("Enter new Address: ");
                String newAddress = scanner.nextLine();
                matcher = pattern.matcher(newAddress);

                while (!matcher.matches()) {
                  System.out.println("Invalid address. Please enter a valid address without commas.");
                  newAddress = scanner.nextLine();
                  matcher = pattern.matcher(newAddress);
                }
                selectedCustomer.setAddress(newAddress);
                System.out.println("Address updated.");
                break;
              case 3:
                System.out.print("Enter new Phone Number: ");
                String newPhoneNumber = scanner.nextLine();
                Pattern Phonepattern = Pattern.compile(PhoneRegex);
                matcher = Phonepattern.matcher(newPhoneNumber);
                while (!matcher.matches() || c.CheckPhone(newPhoneNumber)) {
                  System.out.println("Invalid Number Format (11 Digit) , or Phone exists already");
                  newPhoneNumber = scanner.nextLine();
                  matcher = Phonepattern.matcher(newPhoneNumber);
                }
                selectedCustomer.setPhoneNumber(newPhoneNumber);
                System.out.println("Phone Number updated.");
                break;
              case 4:
                System.out.print("Enter new Customer Type: ");
                String newCustomerType = scanner.nextLine();
                while (!newCustomerType.equalsIgnoreCase("Commercial")
                    && !newCustomerType.equalsIgnoreCase("Domestic")) {
                  System.out.println("Enter Correct Customer Type (Commercial or Domestic): ");
                  newCustomerType = scanner.nextLine();
                }

                if (newCustomerType.equalsIgnoreCase("Commercial")) {
                  newCustomerType = "Commercial";
                } else if (newCustomerType.equalsIgnoreCase("Domestic")) {
                  newCustomerType = "Domestic";
                }
                selectedCustomer.setCustomerType(newCustomerType);
                System.out.println("Customer Type updated.");
                break;
              case 5:
                System.out.print("Enter new Meter Type: ");
                String newMeterType = scanner.nextLine();
                while (!newMeterType.equalsIgnoreCase("Single Phase")
                    && !newMeterType.equalsIgnoreCase("Three Phase")) {
                  System.out.println("Enter Correct Meter Type (Single Phase or Three Phase): ");
                  newMeterType = scanner.nextLine();
                }

                if (newMeterType.equalsIgnoreCase("Single Phase")) {
                  newMeterType = "Single Phase";
                } else if (newMeterType.equalsIgnoreCase("Three Phase")) {
                  newMeterType = "Three Phase";
                }
                selectedCustomer.setMeterType(newMeterType);
                System.out.println("Meter Type updated.");
                break;
              case 6:
                System.out.print("Enter new Regular Units: ");
                Double newRegularUnits = scanner.nextDouble();
                scanner.nextLine();
                while (newRegularUnits < 0.0) {
                  System.out.print("Regular Units Cant be negative: ");
                  newRegularUnits = scanner.nextDouble();
                  scanner.nextLine();
                }
                selectedCustomer.setRegularUnits(newRegularUnits);
                System.out.println("Regular Units updated.");
                break;
              case 7:
                System.out.print("Enter new Peak Units: ");
                Double newPeakUnits = scanner.nextDouble();
                scanner.nextLine();
                while (newPeakUnits < 0.0) {
                  System.out.print("Peak Units Cant be negative: ");
                  newPeakUnits = scanner.nextDouble();
                  scanner.nextLine();
                }
                selectedCustomer.setPeakUnits(newPeakUnits);
                System.out.println("Peak Units updated.");
                break;
              case 0:
                System.out.println("Returning to main menu...");
                break;
              default:
                System.out.println("Invalid choice. Please try again.");
            }
          } while (updateChoice != 0);
        } else {
          System.out.println("Customer with ID " + selectedID + " not found.");
        }
      } else if (choice != 0) {
        System.out.println("Invalid choice. Please try again.");
      }
    } while (choice != 0);

    System.out.println("------------------------------------------");

  }

  public void SetBillStatus() {

    int ID;
    System.out.println("Enter the ID of the customer");
    ID = scanner.nextInt();
    scanner.nextLine();

    while (ID < 0) {
      System.out.println("ID cannot be less than zero");
      ID = scanner.nextInt();
      scanner.nextLine();
    }

    if (!c.checkID(ID)) {
      while (!c.checkID(ID)) {
        System.out.println("Enter correct ID, user does not exist or -1 to EXIT ");
        ID = scanner.nextInt();
        scanner.nextLine();
        if (ID == -1) {
          return;
        }
      }
    }

    if (b.CheckBill(ID)) {
      System.out.println("Bill Does not Exist, Add first");
      System.out.println("------------------------------------------");
      return;
    }

    if (b.UpdateStatus(ID)) {
      System.out.println("Bill Status set to Paid");
    } else {
      System.out.println("Bill Already Paid");
    }

    System.out.println("------------------------------------------");
  }

  public void TariffTaxInfo() {
    String meterType = "";
    String customerType = "";

    System.out.println("Select Meter Type (Single Phase or Three Phase): ");
    meterType = scanner.nextLine();

    while (!meterType.equalsIgnoreCase("Single Phase") && !meterType.equalsIgnoreCase("Three Phase")) {
      System.out.println("Invalid input. Please enter 'Single Phase' or 'Three Phase': ");
      meterType = scanner.nextLine();
    }

    if (meterType.equalsIgnoreCase("Single Phase")) {
      meterType = "Single Phase";
    } else if (meterType.equalsIgnoreCase("Three Phase")) {
      meterType = "Three Phase";
    }

    System.out.println("Select Customer Type (Commercial or Domestic): ");
    customerType = scanner.nextLine();

    while (!customerType.equalsIgnoreCase("Commercial") && !customerType.equalsIgnoreCase("Domestic")) {
      System.out.println("Invalid input. Please enter 'Commercial' or 'Domestic': ");
      customerType = scanner.nextLine();
    }

    if (customerType.equalsIgnoreCase("Commercial")) {
      customerType = "Commercial";
    } else if (customerType.equalsIgnoreCase("Domestic")) {
      customerType = "Domestic";
    }

    double regularUnitPrice = 0.0;
    double peakHourUnitPrice = 0.0;
    double taxPercentage = 0.0;
    double fixedCharges = 0.0;

    if (meterType.equals("Single Phase") && customerType.equals("Domestic")) {

      int choice = -1;

      while (choice != 0) {
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Regular Unit Price");
        System.out.println("2. Tax Percentage");
        System.out.println("3. Fixed Charges");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        choice = scanner.nextInt();

        switch (choice) {
          case 1:
            System.out.print("Enter new Regular Unit Price: ");
            regularUnitPrice = getValidDoubleInput(scanner);
            System.out.println("Regular Unit Price updated to: " + regularUnitPrice);
            b.Taxes.get(0).setRegularUnitsPrice(regularUnitPrice);
            break;

          case 2:
            System.out.print("Enter new Tax Percentage: ");
            taxPercentage = getValidDoubleInput(scanner);
            System.out.println("Tax Percentage updated to: " + taxPercentage);
            b.Taxes.get(0).setTaxRate(taxPercentage);
            break;

          case 3:
            System.out.print("Enter new Fixed Charges: ");
            fixedCharges = getValidDoubleInput(scanner);
            System.out.println("Fixed Charges updated to: " + fixedCharges);
            b.Taxes.get(0).setFixedCharges(fixedCharges);
            break;

          case 0:
            System.out.println("Exiting...");
            break;

          default:
            System.out.println("Invalid choice. Please select a valid option.");
            break;
        }
      }
    } else if (meterType.equals("Single Phase") && customerType.equals("Commercial")) {

      int choice = -1;

      while (choice != 0) {
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Regular Unit Price");
        System.out.println("2. Tax Percentage");
        System.out.println("3. Fixed Charges");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        choice = scanner.nextInt();

        switch (choice) {
          case 1:
            System.out.print("Enter new Regular Unit Price: ");
            regularUnitPrice = getValidDoubleInput(scanner);
            System.out.println("Regular Unit Price updated to: " + regularUnitPrice);
            b.Taxes.get(1).setRegularUnitsPrice(regularUnitPrice);
            break;
          case 2:
            System.out.print("Enter new Tax Percentage: ");
            taxPercentage = getValidDoubleInput(scanner);
            System.out.println("Tax Percentage updated to: " + taxPercentage);
            b.Taxes.get(1).setTaxRate(taxPercentage);
            break;

          case 3:
            System.out.print("Enter new Fixed Charges: ");
            fixedCharges = getValidDoubleInput(scanner);
            System.out.println("Fixed Charges updated to: " + fixedCharges);
            b.Taxes.get(1).setFixedCharges(fixedCharges);
            break;

          case 0:
            System.out.println("Exiting...");
            break;

          default:
            System.out.println("Invalid choice. Please select a valid option.");
            break;
        }
      }
    } else if (meterType.equals("Three Phase") && customerType.equals("Domestic")) {
      int choice = -1;

      while (choice != 0) {
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Regular Unit Price");
        System.out.println("2. Peak Hour Unit Price");
        System.out.println("3. Tax Percentage");
        System.out.println("4. Fixed Charges");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        choice = scanner.nextInt();

        switch (choice) {
          case 1:
            System.out.print("Enter new Regular Unit Price: ");
            regularUnitPrice = getValidDoubleInput(scanner);
            System.out.println("Regular Unit Price updated to: " + regularUnitPrice);
            b.Taxes.get(2).setRegularUnitsPrice(regularUnitPrice);
            break;

          case 2:
            System.out.print("Enter new Peak Hour Unit Price: ");
            peakHourUnitPrice = getValidDoubleInput(scanner);
            System.out.println("Peak Hour Unit Price updated to: " + peakHourUnitPrice);
            b.Taxes.get(2).setPeakUnitsPrice(peakHourUnitPrice);
            break;

          case 3:
            System.out.print("Enter new Tax Percentage: ");
            taxPercentage = getValidDoubleInput(scanner);
            System.out.println("Tax Percentage updated to: " + taxPercentage);
            b.Taxes.get(2).setTaxRate(taxPercentage);
            break;

          case 4:
            System.out.print("Enter new Fixed Charges: ");
            fixedCharges = getValidDoubleInput(scanner);
            System.out.println("Fixed Charges updated to: " + fixedCharges);
            b.Taxes.get(2).setFixedCharges(fixedCharges);
            break;

          case 0:
            System.out.println("Exiting...");
            break;

          default:
            System.out.println("Invalid choice. Please select a valid option.");
            break;
        }
      }
    } else if (meterType.equals("Three Phase") && customerType.equals("Commercial")) {
      int choice = -1;

      while (choice != 0) {
        System.out.println("\nWhat would you like to update?");
        System.out.println("1. Regular Unit Price");
        System.out.println("2. Peak Hour Unit Price");
        System.out.println("3. Tax Percentage");
        System.out.println("4. Fixed Charges");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        choice = scanner.nextInt();

        switch (choice) {
          case 1:
            System.out.print("Enter new Regular Unit Price: ");
            regularUnitPrice = getValidDoubleInput(scanner);
            System.out.println("Regular Unit Price updated to: " + regularUnitPrice);
            b.Taxes.get(3).setRegularUnitsPrice(regularUnitPrice);
            break;

          case 2:
            System.out.print("Enter new Peak Hour Unit Price: ");
            peakHourUnitPrice = getValidDoubleInput(scanner);
            System.out.println("Peak Hour Unit Price updated to: " + peakHourUnitPrice);
            b.Taxes.get(3).setPeakUnitsPrice(peakHourUnitPrice);
            break;

          case 3:
            System.out.print("Enter new Tax Percentage: ");
            taxPercentage = getValidDoubleInput(scanner);
            System.out.println("Tax Percentage updated to: " + taxPercentage);
            b.Taxes.get(3).setTaxRate(taxPercentage);
            break;

          case 4:
            System.out.print("Enter new Fixed Charges: ");
            fixedCharges = getValidDoubleInput(scanner);
            System.out.println("Fixed Charges updated to: " + fixedCharges);
            b.Taxes.get(3).setFixedCharges(fixedCharges);
            break;

          case 0:
            System.out.println("Exiting...");
            break;

          default:
            System.out.println("Invalid choice. Please select a valid option.");
            break;
        }
      }

    }
    System.out.println("------------------------------------------");
  }

  public static double getValidDoubleInput(Scanner scanner) {
    double value;
    do {
      value = scanner.nextDouble();
      if (value < 0) {
        System.out.print("Invalid input. Please enter a value greater than or equal to zero: ");
      }
    } while (value < 0);
    return value;
  }

  public void viewBill() {
    System.out.println("Enter the ID for the Bill");
    int ID;
    ID = scanner.nextInt();
    scanner.nextLine();

    while (ID < 0) {
      System.out.println("ID cannot be less than zero");
      ID = scanner.nextInt();
      scanner.nextLine();
    }

    if (!c.checkID(ID)) {
      while (!c.checkID(ID)) {
        System.out.println("Enter correct ID, user does not exist or -1 to EXIT ");
        ID = scanner.nextInt();
        scanner.nextLine();
        if (ID == -1) {
          return;
        }
      }
    }

    if (b.CheckBill(ID)) {
      System.out.println("Bill Does not Exist:");
      System.out.println("------------------------------------------");
      return;
    } else {
      b.printBillStatus(ID);
    }
  }

  public void ViewExpiry() {
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YY");
    currentDate = currentDate.plusDays(10);
    String ex = currentDate.format(formatter);
    int count = 0;
    System.out.println("CNIC getting expired in next 30 days are :");

    for (NadraData b : n.Data) {
      if (b.getExpiryDate() == ex) {
        System.out.println("CNIC Number : " + b.getCNIC());
        System.out.println("Expiry Date : " + b.getExpiryDate());

      }

    }

    if (count == 0) {
      System.out.println("No CNIC getting expired");
    }
    return;
  }

  public static void main(String[] args) {
    Customers C = new Customers();
    MeterInfo M = new MeterInfo();
    Bills B = new Bills();
    Nadra N = new Nadra();
    EmployeeOperations E = new EmployeeOperations(C, M, B, N);
    E.AddBill();
    C.WriteToFile();
    M.WriteToFile();
    B.WriteToFile();
    N.WriteToFile();
  }

}
