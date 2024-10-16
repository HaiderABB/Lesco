package Controllers.Employee;

import Model.Bills;
import Model.Customers;
import Model.Employees;
import Model.MeterInfo;
import Model.Nadra;
import Views.Employee.Dashboard.*;
import Views.Employee.Login.*;

public class EmployeeController {

  LoginFrame LF;
  DashboardFrame DF;

  Bills B;
  Customers C;
  Employees E;
  MeterInfo M;
  Nadra N;

  public EmployeeController() {

    createBillInstance();
    createCustomerInstance();
    createEmployeeInstance();
    createMeterInfoInstance();
    createNadraInstance();

    LF = new LoginFrame(DF, B, C, E, M, N);
    LF.mainFrame.setVisible(true);

  }

  void createBillInstance() {
    B = new Bills();
  }

  void createEmployeeInstance() {
    E = new Employees();

  }

  void createCustomerInstance() {
    C = new Customers();

  }

  void createMeterInfoInstance() {
    M = new MeterInfo();

  }

  void createNadraInstance() {
    N = new Nadra();
  }

  public static void main(String[] args) {
    new EmployeeController();
  }

}
