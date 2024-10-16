package Model;

import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;

public class Customers implements FileHandling {

  public ArrayList<Customer> customers = new ArrayList<Customer>();
  protected String filename = "CustomerInfo.txt";
  protected final String IDFile = "UserID.txt";
  protected int Current;

  double min = 100.0;
  double max = 900.0;

  public Customers() {
    ReadIDs();
    ReadFromFile();
  }

  public void ReadFromFile() {
    FileReader fr = null;
    BufferedReader reader = null;
    try {
      fr = new FileReader(filename);
      reader = new BufferedReader(fr);
      String line;

      while ((line = reader.readLine()) != null) {
        if (line.trim().isEmpty()) {
          continue;
        }

        String[] fields = line.split(",");

        if (fields.length < 10) {
          System.out.println("Skipping incomplete line: " + line);
          continue;
        }

        Customer customer = new Customer();

        if (!fields[0].trim().isEmpty()) {
          customer.setID(Integer.parseInt(fields[0].trim()));
        }

        if (!fields[1].trim().isEmpty()) {
          customer.setCNIC(new BigInteger(fields[1].trim()));
        }

        customer.setName(fields[2].trim());
        customer.setAddress(fields[3].trim());
        customer.setPhoneNumber(fields[4].trim());
        customer.setCustomerType(fields[5].trim());
        customer.setMeterType(fields[6].trim());
        customer.setConnectionDate(fields[7].trim());

        if (!fields[8].trim().isEmpty()) {
          customer.setRegularUnits(Double.parseDouble(fields[8].trim()));
        }

        if (!fields[9].trim().isEmpty()) {
          customer.setPeakUnits(Double.parseDouble(fields[9].trim()));
        }

        customers.add(customer);
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void ReadIDs() {
    BufferedReader reader = null;

    try {
      reader = new BufferedReader(new FileReader(IDFile));
      String line = reader.readLine();
      if (line != null) {
        Current = Integer.parseInt(line.trim());
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      System.out.println("The file does not contain a valid integer.");
    } finally {
      try {
        if (reader != null) {
          reader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

  }

  public void removeCustomer(int ID) {
    for (Customer c : customers) {
      if (c.ID == ID) {
        customers.remove(c);
        break;
      }
    }
  }

  public void WriteIDs() {
    FileWriter fw = null;
    BufferedWriter writer = null;

    try {

      fw = new FileWriter(IDFile);
      writer = new BufferedWriter(fw);
      System.out.println(Current);

      String line = Current + "";
      writer.write(line);

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void WriteToFile() {

    FileWriter fw = null;
    BufferedWriter writer = null;

    try {

      fw = new FileWriter(filename);
      writer = new BufferedWriter(fw);

      for (Customer customer : customers) {
        String line = customer.getID() + "," +
            customer.getCNIC() + "," +
            customer.getName() + "," +
            customer.getAddress() + "," +
            customer.getPhoneNumber() + "," +
            customer.getCustomerType() + "," +
            customer.getMeterType() + "," +
            customer.getConnectionDate() + "," +
            customer.getRegularUnits() + "," +
            customer.getPeakUnits();
        writer.write(line);
        writer.newLine();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    WriteIDs();
  }

  public boolean LogIn(int ID, BigInteger cnic) {
    int comparison;
    for (Customer c : customers) {
      comparison = c.getCNIC().compareTo(cnic);
      if (comparison == 0 && c.getID() == ID) {
        return true;
      }
    }
    return false;
  }

  public void Register(BigInteger CNIC, String name, String Address, String PhoneNumber, String CustomerType,
      String MeterType) {
    Current = Current + 1;
    Customer c = new Customer(CNIC, name, Address, PhoneNumber, CustomerType, MeterType, Current);

    customers.add(c);
  }

  public boolean CheckCNIC(BigInteger CNIC) {

    int comparison;

    for (Customer c : customers) {
      comparison = c.getCNIC().compareTo(CNIC);
      if (comparison == 0) {
        return true;
      }
    }
    return false;
  }

  public boolean CheckName(String name) {

    for (Customer c : customers) {
      if (c.getName() == name) {
        return true;
      }
    }
    return false;
  }

  public boolean CheckAddress(String Address) {

    for (Customer c : customers) {
      if (c.getAddress() == Address) {
        return true;
      }
    }
    return false;
  }

  public boolean CheckPhone(String Phone) {

    for (Customer c : customers) {
      if (c.getPhoneNumber() == Phone) {
        return true;
      }
    }
    return false;
  }

  public boolean checkID(int ID) {
    for (Customer c : customers) {
      if (c.ID == ID)
        return true;
    }
    return false;
  }

  public boolean checkIDAgainstCNIC(int ID, BigInteger cnic) {
    int comparison;
    for (Customer c : customers) {
      comparison = cnic.compareTo(c.getCNIC());
      if (c.ID == ID && comparison == 0)
        return true;
    }
    return false;
  }

  public Double GetRegularReading(int ID) {
    double randomValue = min + (Math.random() * (max - min));
    for (Customer c : customers) {
      if (c.getID() == ID) {

        Double temp = c.getRegularUnits();
        temp = temp + randomValue;
        c.setRegularUnits(temp);
        return randomValue;
      }
    }
    return 0.0;
  }

  public Double GetPeakReading(int ID) {
    double randomValue = min + (Math.random() * (max - min));
    for (Customer c : customers) {
      if (c.getID() == ID) {

        Double temp = c.getPeakUnits();
        temp = temp + randomValue;
        c.setPeakUnits(temp);
        return randomValue;
      }
    }
    return 0.0;
  }

  public String getMeterType(int ID) {

    for (Customer c : customers) {
      if (c.getID() == ID) {
        return c.getMeterType();
      }
    }
    return "";

  }

  public String getCustomerType(int ID) {
    for (Customer c : customers) {
      if (c.getID() == ID) {
        return c.getCustomerType();
      }
    }
    return "";
  }

  public void printCustomer(int ID, BigInteger cnic, Object[] row) {
    for (Customer c : customers) {

      int comparison = c.getCNIC().compareTo(cnic);

      if (c.getID() == ID && comparison == 0) {

        row[0] = c.getID();
        row[1] = c.getCNIC();
        row[2] = c.getName();
        row[3] = c.getAddress();
        row[4] = c.getPhoneNumber();
        row[5] = c.getCustomerType();
        row[6] = c.getMeterType();

        break;
      }
    }
  }

  public static void main(String[] args) {

  }

}
