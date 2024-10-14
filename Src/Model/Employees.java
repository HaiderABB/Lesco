package Model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Employees implements FileHandling {

  protected HashMap<String, String> EmployeesData = new HashMap<String, String>();
  final String EMPLOYEE_DATA = "EmployeesData.txt";
  String usernameRegex = "^[a-zA-Z][a-zA-Z0-9_.]{4,14}$";

  public Employees() {
    ReadFromFile();
  }

  public boolean Registration(String username, String password) {

    if (EmployeesData.containsKey(username)) {
      return false;
    }

    EmployeesData.put(username, password);
    return true;
  }

  public boolean LogIn(String username, String password) {

    if (EmployeesData.containsKey(username)) {
      if (EmployeesData.get(username).equals(password)) {
        return true;

      } else {
        return false;
      }
    }
    return false;

  }

  public boolean UpdatePassword(String username, String password) {
    if (EmployeesData.containsKey(username)) {
      EmployeesData.put(username, password);
      return true;
    }
    return false;
  }

  public void ReadFromFile() {
    FileReader fr = null;
    BufferedReader reader = null;
    System.out.println("Reading");
    try {
      fr = new FileReader(EMPLOYEE_DATA);
      reader = new BufferedReader(fr);
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        String name = parts[0];
        String pass = parts[1];
        EmployeesData.put(name, pass);
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
      fw = new FileWriter(EMPLOYEE_DATA);
      writer = new BufferedWriter(fw);
      for (String key : EmployeesData.keySet()) {
        writer.write(key + "," + EmployeesData.get(key));
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

  public static void main(String[] args) {
    Employees e = new Employees();
    e.Registration("Rida e Zahra", "ksnadfks");
    e.Registration("Moazzam", "qwoifns");
    e.Registration("Hussain", "jdfnsjdnf");
    e.UpdatePassword("Haider Abbas Moazzam", "Haider2002");
    e.WriteToFile();

  }

}
