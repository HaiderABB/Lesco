package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class MeterInfo implements FileHandling {
  public ArrayList<Meter> Meters = new ArrayList<Meter>();
  public final String filename = "Meter.txt";

  public MeterInfo() {
    ReadFromFile();
  }

  public int getMeterNumber(BigInteger cnic) {
    for (Meter m : Meters) {
      int comparison = m.getCNIC().compareTo(cnic);
      if (comparison == 0) {
        return m.getNumber();
      }
    }
    return 0;
  }

  public void removeMeter(BigInteger cnic) {

    for (Meter m : Meters) {
      int comparison = m.getCNIC().compareTo(cnic);
      if (comparison == 0) {
        m.number = m.number - 1;
        if (m.number == 0) {
          Meters.remove(m);
        }
        return;
      }
    }

  }

  public boolean addMeter(BigInteger CNIC) {
    for (Meter m : Meters) {
      int comparison = m.getCNIC().compareTo(CNIC);
      if (comparison == 0 && m.getNumber() < 3) {
        m.addMeter();
        return true;
      } else if (comparison == 0 && m.getNumber() >= 3) {
        System.out.println("Not Allowed! Maximum 3 meters allowed per CNIC.");
        return false;
      }
    }
    if (Meters.size() == 0) {
      Meter mn = new Meter(CNIC);
      Meters.add(mn);
      return true;
    }

    return false;
  }

  public void ReadFromFile() {
    FileReader fr = null;
    BufferedReader reader = null;
    try {

      fr = new FileReader(filename);
      reader = new BufferedReader(fr);
      String line;

      while ((line = reader.readLine()) != null) {
        String[] fields = line.split(",");
        Meter m = new Meter();
        BigInteger n = new BigInteger(fields[0]);
        m.setCNIC(n);
        m.setNumber(Integer.parseInt(fields[1]));
        Meters.add(m);
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

  public void WriteToFile() {
    FileWriter fw = null;
    BufferedWriter writer = null;

    try {

      fw = new FileWriter(filename);
      writer = new BufferedWriter(fw);

      for (Meter m : Meters) {
        String line = m.getCNIC() + "," + m.getNumber();
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

}
