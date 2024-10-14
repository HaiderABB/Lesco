package Controllers.Employee;

import Views.Employee.Dashboard.*;
import Views.Employee.Login.*;

public class EmployeeController {

  LoginFrame LF;
  DashboardFrame DF;

  public EmployeeController() {

    LF = new LoginFrame();
    DF = new DashboardFrame();

  }

  public static void main(String[] args) {
    new EmployeeController();
  }

}
