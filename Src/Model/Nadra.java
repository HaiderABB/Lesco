package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Nadra implements FileHandling {
  protected final String filename = "NadraDB.txt";
  public ArrayList<NadraData> Data = new ArrayList<NadraData>();

  public Nadra() {
    ReadFromFile();
  }

  public void IssueCNIC(BigInteger newCNIC) {
    NadraData n;
    LocalDate currentDate = LocalDate.now();
    LocalDate futureDate = currentDate.plusDays(15);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YY");
    String Issue = currentDate.format(formatter);
    String Expire = futureDate.format(formatter);
    n = new NadraData(Issue, Expire, newCNIC);
    Data.add(n);
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

        if (fields.length < 3) {
          System.out.println("Skipping incomplete line: " + line);
          continue;
        }

        NadraData m = new NadraData();

        if (!fields[0].trim().isEmpty()) {
          try {
            BigInteger n = new BigInteger(fields[0].trim());
            m.setCNIC(n);
          } catch (NumberFormatException e) {
            System.out.println("Invalid CNIC format in line: " + line);
            continue;
          }
        }

        m.setIssueDate(fields[1].trim());
        m.setExpiryDate(fields[2].trim());

        Data.add(m);
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

  public void WriteToFile() {
    FileWriter fw = null;
    BufferedWriter writer = null;
    try {
      fw = new FileWriter(filename);
      writer = new BufferedWriter(fw);
      for (NadraData key : Data) {
        writer.write(key.getCNIC() + "," + key.getIssueDate() + "," + key.getExpiryDate());
        writer.newLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        writer.close();
      } catch (IOException err) {
        err.printStackTrace();
      }
    }
  }

  public boolean checkCNIC(BigInteger CNIC) {
    for (NadraData n : Data) {
      int comparison = n.getCNIC().compareTo(CNIC);
      if (comparison == 0) {
        return true;
      }

    }
    IssueCNIC(CNIC);
    return true;
  }

  public boolean updateExpiry(BigInteger CNIC) {
    for (NadraData n : Data) {
      int comparison = n.getCNIC().compareTo(CNIC);
      if (comparison == 0) {
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(15);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YY");
        String Issue = currentDate.format(formatter);
        String Expire = futureDate.format(formatter);
        n.setIssueDate(Issue);
        n.setExpiryDate(Expire);
      }

    }
    return true;
  }

  public static void main(String[] args) {
    BigInteger n = new BigInteger("3520105043723");
    Nadra na = new Nadra();
    na.checkCNIC(n);
  }

}
