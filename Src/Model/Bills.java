package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Bills implements FileHandling {

  protected ArrayList<Billing> CustomerBills = new ArrayList<>();
  public ArrayList<TariffTaxInfo> Taxes = new ArrayList<>();
  String TaxFile = "TariffTaxInfo.txt";
  String BillsFile = "BillingInfo.txt";

  double min = 100.0;
  double max = 900.0;

  public Bills() {
    ReadTaxes();
    ReadFromFile();
  }

  public void ReadTaxes() {
    FileReader fr = null;
    BufferedReader reader = null;
    try {

      fr = new FileReader(TaxFile);
      reader = new BufferedReader(fr);
      String line;

      while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",");

        Double temp = 0.0;
        if (fields[2].isEmpty()) {
          TariffTaxInfo t = new TariffTaxInfo(fields[0], Double.parseDouble(fields[1]), temp,
              Double.parseDouble(fields[3]), Double.parseDouble(fields[4]));
          Taxes.add(t);

        }

        else {
          TariffTaxInfo t = new TariffTaxInfo(fields[0], Double.parseDouble(fields[1]), Double.parseDouble(fields[2]),
              Double.parseDouble(fields[3]), Double.parseDouble(fields[4]));
          Taxes.add(t);

        }

      }

    } catch (IOException e) {

      e.printStackTrace();

    } finally {
      try {
        reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void printTaxes() {
    for (TariffTaxInfo t : Taxes) {
      System.out.println(t.Phase + " , " + t.RegularUnitsPrice + " , " + t.PeakUnitsPrice + " , " + t.TaxRate + " , "
          + t.FixedCharges);
    }
  }

  public void ReadFromFile() {
    FileReader fr = null;
    BufferedReader reader = null;
    try {

      fr = new FileReader(BillsFile);
      reader = new BufferedReader(fr);
      String line;

      while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",");
        Billing b = new Billing();
        b.setID(Integer.parseInt(fields[0]));

        // Convert month to correct format
        String month = fields[1].toUpperCase();
        Month billingMonth = Month.valueOf(month);
        b.setBillingMonth(billingMonth);

        b.setCurrentRegularReading(Double.parseDouble(fields[2]));
        b.setEntryDate(fields[3]);
        b.setCurrentPeakReading(Double.parseDouble(fields[4]));
        b.setSalesTax(Double.parseDouble(fields[5]));
        b.setFixedCharges(Double.parseDouble(fields[6]));
        b.setTotalAmount(Double.parseDouble(fields[7]));

        b.setDueDate(fields[8]);

        if (!fields[9].isEmpty()) {
          b.setPaidStatus(fields[9]);
        } else {
          b.setPaidStatus("Unpaid");
        }

        if (!fields[10].isEmpty()) {
          b.setPaidDate(fields[10]);
        } else {
          b.setPaidDate("N/A"); 
        }
        b.setTaxRate(Double.parseDouble(fields[11]));
        b.setRegularUnitsPrice(Double.parseDouble(fields[12]));
        b.setPeakUnitsPrice(Double.parseDouble(fields[13]));

        CustomerBills.add(b);
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (NumberFormatException e) {
      System.out.println("Error parsing number: " + e.getMessage());
    } catch (IllegalArgumentException e) {
      System.out.println("Error parsing month or boolean: " + e.getMessage());
    } finally {
      try {
        if (reader != null)
          reader.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void WriteToFile() {

    FileWriter fw = null;
    BufferedWriter writer = null;

    try {

      fw = new FileWriter(BillsFile);
      writer = new BufferedWriter(fw);

      for (Billing bill : CustomerBills) {
        String line = bill.getID() + "," +
            bill.getBillingMonth() + "," +
            bill.getCurrentRegularReading() + "," +
            bill.getEntryDate() + "," +
            bill.getCurrentPeakReading() + "," +
            bill.getSalesTax() + "," +
            bill.getFixedCharges() + "," +
            bill.getTotalAmount() + "," +
            bill.getDueDate() + "," + bill.isPaidStatus() + "," + bill.getPaidDate() + "," + bill.getTaxRate() + ","
            + bill.getRegularUnitsPrice() + "," + bill.getPeakUnitsPrice();
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

  }

  public void SinglePhaseDomesticBill(int ID, Double CurrentRegularReading) {

    Double Cost = Taxes.get(0).getRegularUnitsPrice();
    Double percentage = Taxes.get(0).getTaxRate();
    Double fixed = Taxes.get(0).getFixedCharges();
    Double SalesTax = (CurrentRegularReading * Cost) * (percentage / 100);

    Double total = SalesTax + (CurrentRegularReading * Cost) + fixed;

    Billing tempBill = new Billing(ID, CurrentRegularReading, 0.0, SalesTax, fixed, total, Cost, 0.0, percentage);
    CustomerBills.add(tempBill);
  }

  public void SinglePhaseCommercialBill(int ID, Double CurrentRegularReading) {

    Double Cost = Taxes.get(1).getRegularUnitsPrice();
    Double percentage = Taxes.get(1).getTaxRate();
    Double fixed = Taxes.get(1).getFixedCharges();
    Double SalesTax = (CurrentRegularReading * Cost) * (percentage / 100);

    Double total = SalesTax + (CurrentRegularReading * Cost) + fixed;
    Billing tempBill = new Billing(ID, CurrentRegularReading, 0.0, SalesTax, fixed, total, Cost, 0.0, percentage);
    CustomerBills.add(tempBill);
  }

  public void ThreePhaseDomesticBill(int ID, Double CurrentRegularReading, Double PeakReading) {

    Double Cost = Taxes.get(2).getRegularUnitsPrice();
    Double PeakPrice = Taxes.get(2).getPeakUnitsPrice();
    Double percentage = Taxes.get(2).getTaxRate();
    Double fixed = Taxes.get(2).getFixedCharges();
    Double SalesTax = (CurrentRegularReading * Cost) + (PeakPrice * PeakReading) * (percentage / 100);

    Double total = SalesTax + (CurrentRegularReading * Cost) + fixed + (PeakPrice * PeakReading);

    Billing tempBill = new Billing(ID, CurrentRegularReading, 0.0, SalesTax, fixed, total, Cost, PeakPrice, percentage);
    CustomerBills.add(tempBill);
  }

  public void ThreePhaseCommercialBill(int ID, Double CurrentRegularReading, Double PeakReading) {

    Double Cost = Taxes.get(3).getRegularUnitsPrice();
    Double PeakPrice = Taxes.get(3).getPeakUnitsPrice();
    Double percentage = Taxes.get(3).getTaxRate();
    Double fixed = Taxes.get(3).getFixedCharges();
    Double SalesTax = (CurrentRegularReading * Cost) + (PeakPrice * PeakReading) * (percentage / 100);

    Double total = SalesTax + (CurrentRegularReading * Cost) + fixed + (PeakPrice * PeakReading);

    Billing tempBill = new Billing(ID, CurrentRegularReading, 0.0, SalesTax, fixed, total, Cost, PeakPrice, percentage);
    CustomerBills.add(tempBill);
  }

  public boolean CheckBill(int ID) {

    LocalDate d = LocalDate.now();
    Month month = d.getMonth();

    for (Billing b : CustomerBills) {
      if (b.getID() == ID && b.getBillingMonth() == month) {
        return false;
      }
    }
    return true;

  }

  public boolean UpdateStatus(int ID) {
    LocalDate da = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YY");
    String paidDate = da.format(formatter);
    for (Billing b : CustomerBills) {
      if (b.getID() == ID) {
        if (b.isPaidStatus().equals("Unpaid")) {
          b.setPaidStatus("Paid");
          b.setPaidDate(paidDate);
          return true;
        } else {
          return false;
        }

      }
    }
    return false;

  }

  public void printBillStatus(int ID) {
    for (Billing b : CustomerBills) {
      if (b.getID() == ID) {
        b.printBillDetails();
        System.out.println("---------------------------------");
      }
    }
  }

  public void printReport() {
    double paidAmount = 0.0;
    double unpaidAmount = 0.0;
    int paid = 0;
    int unpaid = 0;

    for (Billing b : CustomerBills) {
      if (b.isPaidStatus().equals("Unpaid")) {
        unpaidAmount = unpaidAmount + b.getTotalAmount();
        ++unpaid;
      } else {
        paidAmount = paidAmount + b.getTotalAmount();
        ++paid;
      }

    }

    System.out.println("Total Unpaid Bills " + unpaid + ", Total Amount Unpaid  " + unpaidAmount);

    System.out.println("Total Paid Bills " + paid + ", Total Amount Paid  " + paidAmount);
    System.out.println("---------------------------------");

  }

  public boolean printBill(int ID) {
    boolean flag = false;
    for (Billing b : CustomerBills) {
      if (b.getID() == ID) {
        flag = true;
        System.out.println("Current Regular Meter Reading : " + b.getCurrentRegularReading());
        System.out.println("Current Peak Meter Reading : " + b.getCurrentPeakReading());
        System.out.println("Regular Unit Price Cost : " + b.getRegularUnitsPrice());
        System.out.println("Peak Unit Price Cost : " + b.getPeakUnitsPrice());
        System.out.println("Taxed Amount : " + b.getSalesTax());
        System.out.println("Fixed Charges : " + b.getFixedCharges());

        System.out.println("Total Amount : " + b.getTotalAmount());
        System.out.println("Due Date : " + b.getDueDate());
        if (b.isPaidStatus().equals("Paid")) {
          System.out.println("Payment Date: " + b.getPaidDate());
        }
        System.out.println("Tax Rate: " + b.getTaxRate());
        flag = true;
        System.out.println("--------------------------------------");
        break;
      }
    }

    return flag;

  }

  public void AllBills() {
    for (Billing n : CustomerBills) {
      n.printBillDetails();
    }
  }

  public void ExpectedBill(String meterType, double MeterReading, String customerType, double PeakMeterReading) {
    if (meterType.equals("Single Phase") && customerType.equals("Domestic")) {

      Double Cost = Taxes.get(0).getRegularUnitsPrice();
      Double percentage = Taxes.get(0).getTaxRate();
      Double fixed = Taxes.get(0).getFixedCharges();
      Double SalesTax = (MeterReading * Cost) * (percentage / 100);

      Double total = SalesTax + (MeterReading * Cost) + fixed;

      System.out.println("--------------------");
      System.out.println("Regular Unit Price: " + Taxes.get(0).getRegularUnitsPrice());
      System.out.println("Tax Rate Percentage: " + Taxes.get(0).getTaxRate());
      System.out.println("Fixed Charges: " + Taxes.get(0).getFixedCharges());
      System.out.println("Meter Reading: " + MeterReading); // Assuming you have a MeterReading variable
      System.out.println("Calculated Sales Tax: " + SalesTax);
      System.out.println("Total Amount (including Sales Tax and Fixed Charges): " + total);
      System.out.println("--------------------");

    } else if (meterType.equals("Single Phase") && customerType.equals("Commercial")) {
      Double Cost = Taxes.get(1).getRegularUnitsPrice();
      Double percentage = Taxes.get(1).getTaxRate();
      Double fixed = Taxes.get(1).getFixedCharges();
      Double SalesTax = (MeterReading * Cost) * (percentage / 100);

      Double total = SalesTax + (MeterReading * Cost) + fixed;
      System.out.println("--------------------");
      System.out.println("Regular Unit Price: " + Taxes.get(0).getRegularUnitsPrice());
      System.out.println("Tax Rate Percentage: " + Taxes.get(0).getTaxRate());
      System.out.println("Fixed Charges: " + Taxes.get(0).getFixedCharges());
      System.out.println("Meter Reading: " + MeterReading); // Assuming you have a MeterReading variable
      System.out.println("Calculated Sales Tax: " + SalesTax);
      System.out.println("Total Amount (including Sales Tax and Fixed Charges): " + total);
      System.out.println("--------------------");
    } else if (meterType.equals("Three Phase") && customerType.equals("Domestic")) {
      Double Cost = Taxes.get(2).getRegularUnitsPrice();
      Double PeakPrice = Taxes.get(2).getPeakUnitsPrice();
      Double percentage = Taxes.get(2).getTaxRate();
      Double fixed = Taxes.get(2).getFixedCharges();
      Double SalesTax = (MeterReading * Cost) + (PeakPrice * PeakMeterReading) * (percentage / 100);

      Double total = SalesTax + (MeterReading * Cost) + fixed + (PeakPrice * PeakMeterReading);

      System.out.println("--------------------");

      System.out.println("Regular Unit Price: " + Taxes.get(2).getRegularUnitsPrice());
      System.out.println("Peak Unit Price: " + Taxes.get(2).getPeakUnitsPrice());
      System.out.println("Tax Rate Percentage: " + Taxes.get(2).getTaxRate());
      System.out.println("Fixed Charges: " + Taxes.get(2).getFixedCharges());
      System.out.println("Meter Reading (Regular): " + MeterReading);
      System.out.println("Peak Meter Reading: " + PeakMeterReading);
      System.out.println("Calculated Sales Tax: " + SalesTax);
      System.out.println("Total Amount (including Sales Tax, Regular, Peak Charges, and Fixed Charges): " + total);
      System.out.println("--------------------");

    } else if (meterType.equals("Three Phase") && customerType.equals("Commercial")) {
      Double Cost = Taxes.get(3).getRegularUnitsPrice();
      Double PeakPrice = Taxes.get(3).getPeakUnitsPrice();
      Double percentage = Taxes.get(3).getTaxRate();
      Double fixed = Taxes.get(3).getFixedCharges();
      Double SalesTax = (MeterReading * Cost) + (PeakPrice * PeakMeterReading) * (percentage / 100);

      Double total = SalesTax + (MeterReading * Cost) + fixed + (PeakPrice * PeakMeterReading);
      System.out.println("--------------------");

      System.out.println("Regular Unit Price: " + Taxes.get(2).getRegularUnitsPrice());
      System.out.println("Peak Unit Price: " + Taxes.get(2).getPeakUnitsPrice());
      System.out.println("Tax Rate Percentage: " + Taxes.get(2).getTaxRate());
      System.out.println("Fixed Charges: " + Taxes.get(2).getFixedCharges());
      System.out.println("Meter Reading (Regular): " + MeterReading);
      System.out.println("Peak Meter Reading: " + PeakMeterReading);
      System.out.println("Calculated Sales Tax: " + SalesTax);
      System.out.println("Total Amount (including Sales Tax, Regular, Peak Charges, and Fixed Charges): " + total);
      System.out.println("--------------------");

    }
  }

  public static void main(String[] args) {
    Bills b = new Bills();
    b.printTaxes();
  }
}
