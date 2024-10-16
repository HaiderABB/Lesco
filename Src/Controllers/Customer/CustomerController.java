package Controllers.Customer;

import Model.Bills;
import Model.Customers;
import Model.Nadra;
import Views.Customer.Dashboard.*;
import Views.Customer.Login.*;

public class CustomerController {

  LoginFrame LF;
  DashboardFrame DF;

  Bills B;
  Customers C;
  Nadra N;

  public CustomerController() {

    createBillInstance();
    createCustomerInstance();
    createNadraInstance();

    LF = new LoginFrame(DF, B, C, N);
    LF.mainFrame.setVisible(true);

  }

  void createBillInstance() {
    B = new Bills();
  }

  void createCustomerInstance() {
    C = new Customers();

  }

  void createNadraInstance() {
    N = new Nadra();
  }

  public static void main(String[] args) {
    new CustomerController();
  }

}
