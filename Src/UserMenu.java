import Model.Bills;
import Model.Customers;
import Model.EmployeeOperations;
import Model.Employees;
import Model.MeterInfo;
import Model.Nadra;
import java.math.BigInteger;
import java.util.Scanner;

public class UserMenu {

  static String CNICregex = "^[0-9]{13}$";

  public static void employeeMenu(Bills Bill) {
    Scanner scanner = new Scanner(System.in);

    Customers Customer = new Customers();
    MeterInfo Meter = new MeterInfo();
    Nadra n = new Nadra();

    EmployeeOperations EO = new EmployeeOperations(Customer, Meter, Bill, n);

    int choice = 0;
    do {
      System.out.println("------------------------------------");

      System.out.println("\nEmployee Menu:");
      System.out.println("1. Add Bill");
      System.out.println("2. Add Meter");
      System.out.println("3. Update Customer");
      System.out.println("4. Set Bill Status");
      System.out.println("5. Update Taxes");
      System.out.println("6. View Unpaid Bills");
      System.out.println("7. View Expiry");
      System.out.println("8. View Bills Report");
      System.out.println("9. Logout");

      System.out.print("Enter your choice: ");
      choice = scanner.nextInt();
      scanner.nextLine();

      switch (choice) {
        case 1:
          EO.AddBill();
          break;
        case 2:
          EO.AddMeter();
          break;
        case 3:
          EO.UpdateCustomer();
          break;
        case 4:
          EO.SetBillStatus();
          break;
        case 5:
          EO.TariffTaxInfo();
          break;
        case 6:
          EO.viewBill();
          break;
        case 7:
          EO.ViewExpiry();
          break;
        case 8:
          Bill.printReport();
          break;
        case 9:
          System.out.println("Logging out...");
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
      System.out.println("------------------------------------");

    } while (choice != 9);
    Customer.WriteToFile();
    Bill.WriteToFile();
    Meter.WriteToFile();
    n.WriteToFile();
    Bill.AllBills();
  }

  public static void customerMenu(Customers C, BigInteger cnic, int ID, Bills Bill) {
    Scanner scanner = new Scanner(System.in);
    int choice = 0;
    do {
      System.out.println("------------------------------------");

      System.out.println("\nCustomer Menu:");
      System.out.println("1. Update Expiry Date for CNIC");
      System.out.println("2. View Current Bill");
      System.out.println("3. Logout");

      System.out.print("Enter your choice: ");
      choice = scanner.nextInt();
      scanner.nextLine();

      switch (choice) {
        case 1:
          System.out.println("Updating Expiry Date for CNIC...");
          Nadra n = new Nadra();
          n.updateExpiry(cnic);
          System.out.println("Expiry Updated Successfully");
          n.WriteToFile();
          break;
        case 2:

          int input;
          System.out.println("For ID press 1 ");
          System.out.println("For Meter Readings and Phase Type Press 2");
          input = scanner.nextInt();
          scanner.nextLine();
          while (input < 0 || input > 2) {
            System.out.println("Enter Correct Input");
            input = scanner.nextInt();
            scanner.nextLine();
          }

          if (input == 1) {
            System.out.println("Enter Customer ID: ");
            ID = scanner.nextInt();
            scanner.nextLine();

            while (ID < 0) {
              System.out.println("ID cannot be less than zero");
              ID = scanner.nextInt();
              scanner.nextLine();
            }

            while (!C.checkIDAgainstCNIC(ID, cnic)) {
              System.out.println("Enter correct ID, user does not exist for this CNIC and ID combination ");
              ID = scanner.nextInt();
              scanner.nextLine();
            }

          } else if (input == 2) {
            System.out.println("Enter Meter Type (Single Phase or Three Phase): ");
            String meterType = scanner.nextLine();
            while (!meterType.equalsIgnoreCase("Single Phase") &&
                !meterType.equalsIgnoreCase("Three Phase")) {
              System.out.println("Enter Correct Meter Type (Single Phase or Three Phase):");
              meterType = scanner.nextLine();
            }
            if (meterType.equalsIgnoreCase("Single Phase")) {
              meterType = "Single Phase";
            } else if (meterType.equalsIgnoreCase("Three Phase")) {
              meterType = "Three Phase";
            }

            System.out.println("Enter Regular Meter Reading");
            double currentReading = scanner.nextDouble();
            scanner.nextLine();
            while (currentReading < 0.0) {
              System.out.println("Enter Correct Regular Meter Reading");
              currentReading = scanner.nextDouble();
              scanner.nextLine();

            }

            double pEAK = 0.0;

            if (meterType.equals("ThreePhase")) {
              System.out.println("Enter Current Regular Meter Reading");
              pEAK = scanner.nextDouble();
              scanner.nextLine();
              while (pEAK < 0.0) {
                System.out.println("Enter Correct Peak Meter Reading");
                currentReading = scanner.nextDouble();
                scanner.nextLine();

              }
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

            Bill.ExpectedBill(meterType, currentReading, customerType, pEAK);

          }

          System.out.println("------------------------------------");
          System.out.println("Your Billing Details are as follows:");

          break;
        case 3:
          System.out.println("Logging out...");
          break;
        default:
          System.out.println("Invalid choice. Please try again.");
      }
      System.out.println("------------------------------------");

    } while (choice != 3);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Bills Bill = new Bills();

    int userType;
    do {
      System.out.println("------------------------------------");

      System.out.println("Welcome to the System");
      System.out.println("1. Login as Employee");
      System.out.println("2. Login as Customer");
      System.out.println("Enter -1 to Exit");
      System.out.print("Enter your choice: ");
      userType = scanner.nextInt();
      scanner.nextLine();

      if (userType == 1) {
        Employees Employee = new Employees();

        System.out.println("Enter Employee Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Employee Password: ");
        String password = scanner.nextLine();

        if (Employee.LogIn(username, password)) {
          System.out.println("Login successful as Employee.");
          employeeMenu(Bill);
          Employee.WriteToFile();
        } else {
          System.out.println("Invalid Employee credentials.");
        }
      } else if (userType == 2) {
        Customers Customer = new Customers();

        System.out.println("Enter Customer ID: ");
        int ID = scanner.nextInt();
        scanner.nextLine();

        while (ID < 0) {
          System.out.println("ID cannot be less than zero");
          ID = scanner.nextInt();
          scanner.nextLine();
        }

        while (!Customer.checkID(ID)) {
          System.out.println("Enter correct ID, user does not exist or -1 to EXIT ");
          ID = scanner.nextInt();
          scanner.nextLine();
          if (ID == -1) {
            break;
          }
        }

        if (ID != -1) {
          System.out.println("Enter Customer CNIC: ");
          String cnicInput = scanner.nextLine();
          while (!cnicInput.matches(CNICregex)) {
            System.out.println("Enter Correct 13 digit CNIC");
            cnicInput = scanner.nextLine();
          }

          BigInteger cnic = new BigInteger(cnicInput);

          if (Customer.LogIn(ID, cnic)) {
            customerMenu(Customer, cnic, ID, Bill);
          } else {
            System.out.println("Invalid Customer credentials.");
          }
        }
      } else if (userType != -1) {
        System.out.println("Invalid choice. Please try again.");
      }

    } while (userType != -1);

    System.out.println("Exiting the system. Goodbye!");
    System.out.println("------------------------------------");

  }
}
